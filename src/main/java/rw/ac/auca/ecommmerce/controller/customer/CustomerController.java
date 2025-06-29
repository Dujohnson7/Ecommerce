package rw.ac.auca.ecommmerce.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rw.ac.auca.ecommmerce.core.customer.model.Customer;
import rw.ac.auca.ecommmerce.core.customer.service.ICustomerService;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/customer")
public class CustomerController {
    private final ICustomerService customerService;

    @GetMapping("/search/all")
    public String getAllCustomers(Model model) {
        model.addAttribute("customer", new Customer());
        List<Customer> customers = customerService.findCustomersByState(Boolean.TRUE);
        model.addAttribute("customers", customers);
        return "customer/customerP";
    }

    @GetMapping("/register")
    public String getCustomerRegistrationPage(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("customers", List.of());
        return "customer/customerP";
    }

    @PostMapping("/register")
    public String registerCustomer(@ModelAttribute("customer") Customer theCustomer, Model model) {
        try {
            if (theCustomer != null) {
                customerService.registerCustomer(theCustomer);
                model.addAttribute("message", "Customer registered successfully!");
                return "redirect:/customer/search/all";
            } else {
                model.addAttribute("error", "Customer data is invalid");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error registering customer: " + e.getMessage());
        }

        model.addAttribute("customers", customerService.findCustomersByState(Boolean.TRUE));
        return "customer/customerP";
    }
}