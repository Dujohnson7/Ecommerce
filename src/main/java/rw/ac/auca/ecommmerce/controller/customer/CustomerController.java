package rw.ac.auca.ecommmerce.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ecommmerce.core.customer.model.Customer;
import rw.ac.auca.ecommmerce.core.customer.service.ICustomerService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/customer/")
public class CustomerController {
    private final ICustomerService customerService;

    @GetMapping({"/","/search/all"})
    public String getAllCustomers(Model model) {
        model.addAttribute("customer", new Customer());
        List<Customer> customers = customerService.findCustomersByState(Boolean.TRUE);
        model.addAttribute("customers", customers);
        return "customer/customerP";
    }

    @GetMapping("/register")
    public String getCustomerRegistrationPage(Model model) {
        model.addAttribute("customer", new Customer());
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


    @PostMapping("/delete")
    public String deleteCustomer(@RequestParam("id") String id, Model model){
        if(Objects.nonNull(id)){
            Customer theCustomer = new Customer();
            theCustomer.setId(UUID.fromString(id));
            customerService.deleteCustomer(theCustomer);
        }
        return "redirect:/customer/";
    }

    @PostMapping("/update")
    public String updateCustomer(@RequestParam("id") String id, Model model){
        if(Objects.nonNull(id)){
            Customer theCustomer = customerService
                    .findCustomerByIdAndState(UUID.fromString(id) , Boolean.TRUE);
            if(Objects.nonNull(theCustomer)){
                model.addAttribute("customer" , theCustomer);
                return "customer/";
            }
        }
        model.addAttribute("error" , "Wrong Information");
        return "customer/customerP";
    }

    @PostMapping("/updateCustomer")
    public String updateCustomer(@ModelAttribute("customer") Customer theCustomer,  Model model){
        if(Objects.nonNull(theCustomer)){
            System.out.println("The customer: "+theCustomer);
            customerService.updateCustomer(theCustomer);
        }
        return "redirect:/customer/search/all";
    }
}