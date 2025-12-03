package rw.ac.auca.ecommerce.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ecommerce.core.customer.model.Customer;
import rw.ac.auca.ecommerce.core.orders.model.Orders;
import rw.ac.auca.ecommerce.core.orders.service.IOrdersService;
import rw.ac.auca.ecommerce.core.product.model.Product;
import rw.ac.auca.ecommerce.core.users.model.Users;
import rw.ac.auca.ecommerce.core.util.order.EOrderState;
import rw.ac.auca.ecommerce.core.util.order.EPaymentState;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order/")
public class OrdersController {

    private final IOrdersService orderService;

    @GetMapping({"/","search/all"})
    public String getAllOrders(Model model){
        model.addAttribute("order", new Orders());
        List<Orders> orders = orderService.findAllOrdersByState(Boolean.TRUE);
        model.addAttribute("orders", orders);

        List<Product> products = orderService.findAllProducts();
        model.addAttribute("products", products);

        List<Users>  users = orderService.findAllUsers();
        model.addAttribute("userss", users);

        model.addAttribute("orderStates", EOrderState.values());
        model.addAttribute("paymentStates", EPaymentState.values());
        return "/orders/orderP";

    }


    @GetMapping("/register")
    public String getOrders(Model model){
        model.addAttribute("order", new Orders());
        List<Product> products = orderService.findAllProducts();
        List<Users>  users = orderService.findAllUsers();
        model.addAttribute("products", products);
        model.addAttribute("userss", users);
        model.addAttribute("orderStates", EOrderState.values());
        model.addAttribute("paymentStates", EPaymentState.values());
        return "/orders/orderP";
    }

    @PostMapping("/register")
    public String registerOrder(@ModelAttribute("order") Orders theOrders, Model model){
        try {
            if (Objects.nonNull(theOrders)) {
                orderService.registerOrder(theOrders);
                model.addAttribute("message", "Order has been registered successfully");
                return "redirect:/order/";
            } else {
                model.addAttribute("error", "Data is invalid");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error Registering Product: " + e.getMessage());
        }

        model.addAttribute("order", new Orders());
        model.addAttribute("orders", orderService.findAllOrdersByState(Boolean.TRUE));;
        model.addAttribute("products", orderService.findAllProducts());
        model.addAttribute("userss", orderService.findAllUsers());
        model.addAttribute("orderStates", EOrderState.values());
        model.addAttribute("paymentStates", EPaymentState.values());
        return "/orders/orderP";
    }


    @PostMapping("/update")
    public String updateOrder(@ModelAttribute("order") Orders theOrders, @RequestParam("id") String id, Model model ){
        try {
            if(Objects.nonNull(id) && Objects.nonNull(theOrders)){
                Orders existingOrder = orderService.findOrderByIdAndState(UUID.fromString(id), Boolean.TRUE);
                if(Objects.nonNull(existingOrder)){
                    // Update the existing order with new values
                    if(Objects.nonNull(theOrders.getOrderState())){
                        existingOrder.setOrderState(theOrders.getOrderState());
                    }
                    if(Objects.nonNull(theOrders.getPaymentState())){
                        existingOrder.setPaymentState(theOrders.getPaymentState());
                    }
                    if(theOrders.getOrderQuantity() > 0){
                        existingOrder.setOrderQuantity(theOrders.getOrderQuantity());
                    }
                    if(theOrders.getOrderAmount() > 0){
                        existingOrder.setOrderAmount(theOrders.getOrderAmount());
                    }
                    orderService.updateOrder(existingOrder);
                    model.addAttribute("message", "Order updated successfully");
                    return "redirect:/order/";
                }
            }
            model.addAttribute("error","Invalid Order ID");
        } catch (Exception e) {
            model.addAttribute("error", "Error updating order: " + e.getMessage());
        }
        return "redirect:/order/";
    }

    @PostMapping("/delete")
    public String deleteOrder(@RequestParam("id")  String id , Model model ) {
        try {
            if(Objects.nonNull(id) && !id.isEmpty()){
                Orders theOrders = orderService.findOrderByIdAndState(UUID.fromString(id), Boolean.TRUE);
                if(Objects.nonNull(theOrders)){
                    // Check if order is paid
                    if(theOrders.getPaymentState() == EPaymentState.PAID){
                        model.addAttribute("error", "Cannot delete order that has been paid. Please refund the order first.");
                        return "redirect:/order/";
                    }
                    orderService.deleteOrder(theOrders);
                    model.addAttribute("message", "Order deleted successfully");
                } else {
                    model.addAttribute("error", "Order not found");
                }
            } else {
                model.addAttribute("error", "Invalid Order ID");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error deleting order: " + e.getMessage());
        }
        return "redirect:/order/";
    }


}
