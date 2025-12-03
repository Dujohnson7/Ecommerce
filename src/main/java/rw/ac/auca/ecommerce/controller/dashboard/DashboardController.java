package rw.ac.auca.ecommerce.controller.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import rw.ac.auca.ecommerce.core.orders.model.Orders;
import rw.ac.auca.ecommerce.core.orders.service.IOrdersService;
import rw.ac.auca.ecommerce.core.product.model.Product;
import rw.ac.auca.ecommerce.core.product.service.IProductService;
import rw.ac.auca.ecommerce.core.productCategory.model.ProductCategory;
import rw.ac.auca.ecommerce.core.users.model.Users;
import rw.ac.auca.ecommerce.core.users.service.IUsersService;
import rw.ac.auca.ecommerce.core.users.service.UserDetailsServiceImpl.CustomUserDetails;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class DashboardController {

    private final IOrdersService orderService;
    private final IUsersService usersService;
    private final IProductService productService;

    @GetMapping("/dashboard/")
    public String dashboard(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("userId", userDetails.getId().toString());

            /*
            model.addAttribute("order", new Orders());
            List<Orders> orders = orderService.findAllOrdersByState(Boolean.TRUE);
            model.addAttribute("orders", orders);*/


            model.addAttribute("order", new Orders());
            List<Orders> orders = orderService.findAllOrdersByState(Boolean.TRUE);

            List<Orders> recentOrders = orderService.findAllOrdersByState(Boolean.TRUE)
                    .stream()
                    .sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()))
                    .limit(5)
                    .collect(Collectors.toList());
            model.addAttribute("recentOrders", recentOrders);
            model.addAttribute("orders", orders);


            double totalRevenue = orders.stream()
                    .mapToDouble(Orders::getOrderAmount)
                    .sum();
            model.addAttribute("totalRevenue", String.format("%.2f", totalRevenue));

            model.addAttribute("user", new Users());
            List<Users> users = usersService.findAllUsersByState(Boolean.TRUE);
            model.addAttribute("users", users);

            List<ProductCategory> productCategories = productService.findAllCategories();
            model.addAttribute("productCategories", productCategories);

            model.addAttribute("product", new Product());
            List<Product> products = productService.findProductsByState(Boolean.TRUE);
            model.addAttribute("products", products);

        } else {
            return "login";
        }
        return "dashboard";
    }
}