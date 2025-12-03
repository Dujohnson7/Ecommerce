package rw.ac.auca.ecommerce.controller.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ecommerce.core.customer.model.Customer;
import rw.ac.auca.ecommerce.core.orders.model.Orders;
import rw.ac.auca.ecommerce.core.orders.service.IOrdersService;
import rw.ac.auca.ecommerce.core.payment.model.Payment;
import rw.ac.auca.ecommerce.core.payment.service.IPaymentService;
import rw.ac.auca.ecommerce.core.users.model.Users;
import rw.ac.auca.ecommerce.core.util.order.EPaymentMethod;
import rw.ac.auca.ecommerce.core.util.order.EPaymentState;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment/")
public class PaymentController {


        private final IOrdersService orderService;
        private final IPaymentService paymentService;

        @GetMapping({"/","search/all"})
        public String getAllPayments(Model model){
            model.addAttribute("payment", new Orders());
            List<Payment> payments = paymentService.findAllPaymentsByState(Boolean.TRUE);
            model.addAttribute("payments", payments);

            List<Orders> orders = orderService.findAllOrdersByState(Boolean.TRUE);
            model.addAttribute("orders", orders);

            List<Users>  users = orderService.findAllUsers();
            model.addAttribute("users", users);

            model.addAttribute("paymentMethodState", EPaymentMethod.values());
            model.addAttribute("paymentStatusState", EPaymentState.values());
            return "/orders/paymentP";

        }


        @GetMapping("/register")
        public  String getPayments(Model model){
            model.addAttribute("order", new Payment());
            List<Orders> orders = paymentService.findAllOrders();
            List<Users>  users = orderService.findAllUsers();
            model.addAttribute("orders", orders);
            model.addAttribute("users", users);
            model.addAttribute("paymentMethodState", EPaymentMethod.values());
            model.addAttribute("paymentStatusState", EPaymentState.values());
            return "/orders/paymentP";
        }

        @PostMapping("/register")
        public String registerPayment(@ModelAttribute Payment thePayment, Model model){
            try {
                if (Objects.nonNull(thePayment)) {
                    paymentService.registerPayment(thePayment);
                    model.addAttribute("message", "Order has been registered successfully");
                    return "redirect:/payment/";
                } else {
                    model.addAttribute("error", "Data is invalid");
                }
            } catch (Exception e) {
                model.addAttribute("error", "Error Registering Payment: " + e.getMessage());
            }

            model.addAttribute("payment", new Payment());
            model.addAttribute("payments", paymentService.findAllPaymentsByState(Boolean.TRUE));;
            model.addAttribute("orders", paymentService.findAllOrders());
            model.addAttribute("users", paymentService.findAllUsers());
            model.addAttribute("paymentMethodState", EPaymentMethod.values());
            model.addAttribute("paymentStatusState", EPaymentState.values());
            return "/orders/paymentP";
        }


        @PostMapping("/update")
        public String updatePayment(@RequestParam("id")  String id , Model model){
            if(Objects.isNull(id)){
                Payment thePayment = paymentService.findPaymentByIdAndState(UUID.fromString(id), Boolean.TRUE);
                if(Objects.nonNull(thePayment)){
                    model.addAttribute("payment", thePayment);
                    paymentService.updatePayment(thePayment);
                    return "redirect:/payment/";
                }
            }
            model.addAttribute("error","Invalid Order ID");
            return "/orders/paymentP";
        }

        @PostMapping("/delete")
        public String deleteOrder(@RequestParam("id")  String id , Model model ) {
            if(Objects.isNull(id)){
                Payment thePayment = new Payment();
                thePayment.setId(UUID.fromString(id));
                paymentService.deletePayment(thePayment);
            }
            return "redirect:/payment/";
        }


    }
