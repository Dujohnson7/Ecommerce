
package rw.ac.auca.ecommerce.controller.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import rw.ac.auca.ecommerce.core.orders.model.Orders;
import rw.ac.auca.ecommerce.core.orders.service.IOrdersService;
import rw.ac.auca.ecommerce.core.product.model.Product;
import rw.ac.auca.ecommerce.core.product.service.IProductService;
import rw.ac.auca.ecommerce.core.productCategory.model.ProductCategory;
import rw.ac.auca.ecommerce.core.users.model.Users;
import rw.ac.auca.ecommerce.core.users.service.IUsersService;
import rw.ac.auca.ecommerce.core.users.service.UserDetailsServiceImpl;
import rw.ac.auca.ecommerce.core.util.order.EOrderState;
import rw.ac.auca.ecommerce.core.util.order.EPaymentState;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class HomeController {

    private final IProductService productService;
    private final IUsersService usersService;
    private final IOrdersService orderService;

    private final Map<UUID, List<Map<String, Object>>> userCarts = new HashMap<>();

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsServiceImpl.CustomUserDetails userDetails) {
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("userId", userDetails.getId().toString());
        }

        List<Product> products = productService.findProductsByState(Boolean.TRUE);
        model.addAttribute("products", products);

        List<ProductCategory> productCategories = productService.findAllCategories();
        model.addAttribute("productCategories", productCategories);

        return "index";
    }

    @GetMapping("/product/{id}")
    public String productDetails(@PathVariable UUID id, Model model) {
        Product product = productService.findProductByIdAndState(id, Boolean.TRUE);
        if (product == null) {
            return "redirect:/";
        }
        model.addAttribute("product", product);
        return "product_details";
    }

    @GetMapping("/orders")
    public String userOrders(@AuthenticationPrincipal UserDetailsServiceImpl.CustomUserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        model.addAttribute("orders", orderService.findOrdersByUserIdAndState(userDetails.getId(), Boolean.TRUE));
        return "orders_customer";
    }

    @GetMapping("/profile")
    public String userProfile(@AuthenticationPrincipal UserDetailsServiceImpl.CustomUserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        Users user = usersService.findUserByIdAndState(userDetails.getId(), Boolean.TRUE);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/api/user")
    public ResponseEntity<Map<String, Object>> getCurrentUser(@AuthenticationPrincipal UserDetailsServiceImpl.CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
        Map<String, Object> user = new HashMap<>();
        user.put("id", userDetails.getId().toString());
        user.put("username", userDetails.getUsername());
        user.put("role", userDetails.getAuthorities() );
        return ResponseEntity.ok(user);
    }

    @GetMapping("/api/cart")
    public ResponseEntity<Map<String, List<Map<String, Object>>>> getCart(@AuthenticationPrincipal UserDetailsServiceImpl.CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
        List<Map<String, Object>> cartItems = userCarts.getOrDefault(userDetails.getId(), new ArrayList<>());
        Map<String, List<Map<String, Object>>> response = new HashMap<>();
        response.put("items", cartItems);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/cart")
    public ResponseEntity<Void> saveCart(@RequestBody Map<String, List<Map<String, Object>>> request,
                                         @AuthenticationPrincipal UserDetailsServiceImpl.CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
        List<Map<String, Object>> items = request.get("items");
        if (items != null) {
            userCarts.put(userDetails.getId(), items);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/checkout")
    public ResponseEntity<Map<String, String>> checkout(@RequestBody Map<String, List<Map<String, Object>>> request,
                                                        @AuthenticationPrincipal UserDetailsServiceImpl.CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        List<Map<String, Object>> items = request.get("items");
        if (items == null || items.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty");
        }

        Orders theOrder = new Orders();

        Users user = usersService.findUserByIdAndState(userDetails.getId(), Boolean.TRUE);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        theOrder.setUsers(user);

        double totalQuantity = 0;
        double totalAmount = 0;
        List<Product> products = new ArrayList<>();

        for (Map<String, Object> item : items) {
            UUID productId = UUID.fromString((String) item.get("id"));
            int quantity = ((Number) item.get("quantity")).intValue();

            Product product = productService.findProductByIdAndState(productId, Boolean.TRUE);
            if (product == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Product not found: " + productId);
            }

            products.add(product);
            totalQuantity += quantity;
            totalAmount += product.getProductPrice() * quantity;
        }

        theOrder.setProducts(products);
        theOrder.setOrderQuantity(totalQuantity);
        theOrder.setOrderAmount(totalAmount);
        theOrder.setOrderState(rw.ac.auca.ecommerce.core.util.order.EOrderState.PENDING);
        theOrder.setPaymentState(rw.ac.auca.ecommerce.core.util.order.EPaymentState.PENDING);

        Orders savedOrder = orderService.registerOrder(theOrder);

        userCarts.remove(userDetails.getId());

        Map<String, String> response = new HashMap<>();
        response.put("orderNumber", savedOrder.getId().toString());
        return ResponseEntity.ok(response);
    }




    @PostMapping("/api/order/pay/{orderId}")
    public ResponseEntity<Map<String, String>> payOrder(@PathVariable UUID orderId,
                                                        @AuthenticationPrincipal UserDetailsServiceImpl.CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
        Orders order = orderService.findOrderByIdAndUserId(orderId, userDetails.getId());
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found or not authorized");
        }
        if (order.getPaymentState() != EPaymentState.PENDING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order payment is not pending");
        }
        order.setPaymentState(EPaymentState.PAID);
        orderService.updateOrder(order);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Payment successful");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/order/cancel/{orderId}")
    public ResponseEntity<Map<String, String>> cancelOrder(@PathVariable UUID orderId,
                                                           @AuthenticationPrincipal UserDetailsServiceImpl.CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
        Orders order = orderService.findOrderByIdAndUserId(orderId, userDetails.getId());
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found or not authorized");
        }
        if (order.getOrderState() != EOrderState.PENDING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order is not in a cancellable state");
        }
        order.setOrderState(EOrderState.CANCELED);
        order.setPaymentState(EPaymentState.CANCELED);

        orderService.updateOrder(order);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Order cancelled successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/order/receive/{orderId}")
    public ResponseEntity<Map<String, String>> receiveOrder(@PathVariable UUID orderId,
                                                            @AuthenticationPrincipal UserDetailsServiceImpl.CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
        Orders order = orderService.findOrderByIdAndUserId(orderId, userDetails.getId());
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found or not authorized");
        }
        if (order.getOrderState() != EOrderState.PENDING || order.getPaymentState() != EPaymentState.PAID) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order is not in a receivable state");
        }
        order.setOrderState(EOrderState.DELIVERED);
        order.setPaymentState(EPaymentState.PAID);
        orderService.updateOrder(order);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Order received successfully");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/api/order/refund/{orderId}")
    public ResponseEntity<Map<String, String>> refundOrder(@PathVariable UUID orderId,
                                                           @AuthenticationPrincipal UserDetailsServiceImpl.CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
        Orders order = orderService.findOrderByIdAndUserId(orderId, userDetails.getId());
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found or not authorized");
        }
        if (order.getOrderState() != EOrderState.DELIVERED || order.getPaymentState() != EPaymentState.PAID) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order is not in a receivable state");
        }
        order.setOrderState(EOrderState.REFUND);
        order.setPaymentState(EPaymentState.CANCELED);
        orderService.updateOrder(order);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Order received successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/order/details/{orderId}")
    public ResponseEntity<Map<String, Object>> getOrderDetails(@PathVariable UUID orderId,
                                                               @AuthenticationPrincipal UserDetailsServiceImpl.CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
        Orders order = orderService.findOrderByIdAndUserId(orderId, userDetails.getId());
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found or not authorized");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("orderNumber", order.getOrderNumber());
        response.put("createdAt", order.getCreatedAt().toString());
        response.put("orderState", order.getOrderState().toString());
        response.put("paymentState", order.getPaymentState().toString());
        response.put("orderAmount", order.getOrderAmount());

        List<Map<String, Object>> items = new ArrayList<>();
        double quantities = order.getOrderQuantity();
        for (Product product : order.getProducts()) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", product.getId().toString());
            item.put("name", product.getProductName());
            item.put("price", product.getProductPrice());
            item.put("quantity", quantities);
            items.add(item);
        }
        response.put("items", items);

        return ResponseEntity.ok(response);
    }
}

