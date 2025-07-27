package rw.ac.auca.ecommmerce.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ecommmerce.core.customer.model.Customer;
import rw.ac.auca.ecommmerce.core.orders.model.Orders;
import rw.ac.auca.ecommmerce.core.orders.service.IOrdersService;
import rw.ac.auca.ecommmerce.core.product.model.Product;
import rw.ac.auca.ecommmerce.core.util.order.EOrderState;
import rw.ac.auca.ecommmerce.core.util.order.EPaymentState;

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

        List<Customer>  customers = orderService.findAllCustomers();
        model.addAttribute("customers", customers);

        model.addAttribute("orderStates", EOrderState.values());
        model.addAttribute("paymentStates", EPaymentState.values());
        return "/orders/orderP";

    }


    @GetMapping("/register")
    public String getOrders(Model model){
        model.addAttribute("order", new Orders());
        List<Product> products = orderService.findAllProducts();
        List<Customer>  customers = orderService.findAllCustomers();
        model.addAttribute("products", products);
        model.addAttribute("customers", customers);
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
        model.addAttribute("customers", orderService.findAllCustomers());
        model.addAttribute("orderStates", EOrderState.values());
        model.addAttribute("paymentStates", EPaymentState.values());
        return "/orders/orderP";
    }


    @PostMapping("/update")
    public String updateOrder(@RequestParam("id")  String id , Model model ){
        if(Objects.isNull(id)){
            Orders theOrders = orderService.findOrderByIdAndState(UUID.fromString(id), Boolean.TRUE);
            if(Objects.nonNull(theOrders)){
                model.addAttribute("order", theOrders);
                orderService.updateOrder(theOrders);
                return "redirect:/order/";
            }
        }
        model.addAttribute("error","Invalid Order ID");
        return "orders/orderP";
    }

    @PostMapping("/delete")
    public String deleteOrder(@RequestParam("id")  String id , Model model ) {
        if(Objects.isNull(id)){
            Orders theOrders = new Orders();
            theOrders.setId(UUID.fromString(id));
            orderService.deleteOrder(theOrders);
        }
        return "redirect:/order/";
    }


}
