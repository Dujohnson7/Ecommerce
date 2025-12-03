package rw.ac.auca.ecommerce.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ecommerce.core.address.service.IAddressService;
import rw.ac.auca.ecommerce.core.customer.model.Customer;
import rw.ac.auca.ecommerce.core.customer.service.ICustomerService;
import rw.ac.auca.ecommerce.core.role.model.Role;
import rw.ac.auca.ecommerce.core.role.service.IRoleService;
import rw.ac.auca.ecommerce.core.users.model.Users;
import rw.ac.auca.ecommerce.core.users.service.IUsersService;
import rw.ac.auca.ecommerce.core.util.address.EDistrictState;
import rw.ac.auca.ecommerce.core.util.address.EProvinceState;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/customer/")
public class CustomerController {
    private final IUsersService customerService;
    private final IRoleService roleService;
    private final IAddressService addressService;

    @GetMapping({"/","/search/all"})
    public String getAllCustomers(Model model) {
        model.addAttribute("customer", new Users());
        Role customerRole = roleService.findRoleByName("CUSTOMER");
        List<Users> customers = customerService.findUsersByRoleAndActive(customerRole,Boolean.TRUE);
        model.addAttribute("customers", customers);
        model.addAttribute("roles", customerService.findAllRoles());
        model.addAttribute("provinces", addressService.findDistinctProvinces());
        model.addAttribute("districts", addressService.findDistinctDistricts());
        return "customer/customerP";
    }

    @GetMapping("/register")
    public String getCustomerRegistrationPage(Model model) {
        model.addAttribute("customer", new Users());
        model.addAttribute("provinces", addressService.findDistinctProvinces());
        model.addAttribute("districts", addressService.findDistinctDistricts());
        return "customer/customerP";
    }

    @PostMapping("/register")
    public String registerCustomer(@ModelAttribute("customer") Users theCustomer, Model model) {
        try {
            if (theCustomer != null) {
                customerService.registerUsers(theCustomer);
                model.addAttribute("message", "Customer registered successfully!");
                return "redirect:/customer/search/all";
            } else {
                model.addAttribute("error", "Customer data is invalid");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error registering customer: " + e.getMessage());
        }

        Role customerRole = roleService.findRoleByName("CUSTOMER");
        model.addAttribute("customers", customerService.findUsersByRoleAndActive(customerRole,Boolean.TRUE));
        model.addAttribute("roles", customerService.findAllRoles());
        model.addAttribute("provinces", addressService.findDistinctProvinces());
        model.addAttribute("districts", addressService.findDistinctDistricts());
        return "customer/customerP";
    }


    @PostMapping("/delete")
    public String deleteCustomer(@RequestParam("id") String id, Model model){
        if(Objects.nonNull(id)){
            Users theCustomer = new Users();
            theCustomer.setId(UUID.fromString(id));
            customerService.deleteUsers(theCustomer);
        }
        return "redirect:/customer/search/all";
    }


    @PostMapping("/update")
    public String updateCustomer(@RequestParam("id") String id, Model model){
        if(Objects.nonNull(id)){
            Users theCustomer = customerService
                    .findUserByIdAndState(UUID.fromString(id) , Boolean.TRUE);
            if(Objects.nonNull(theCustomer)){
                model.addAttribute("customer" , theCustomer);
                return "customer/";
            }
        }
        model.addAttribute("error" , "Wrong Information");
        return "customer/customerP";
    }

    @PostMapping("/updateCustomer")
    public String updateCustomer(@ModelAttribute("customer") Users theCustomer,  Model model){
        if(Objects.nonNull(theCustomer)){
            System.out.println("The customer: "+theCustomer);
            customerService.updateUsers(theCustomer);
        }
        return "redirect:/customer/search/all";
    }


    @GetMapping("/districts-by-province")
    @ResponseBody
    public List<String> getDistrictsByProvince(@RequestParam(value = "province", required = false) String province) {
        if (province == null) {
            return List.of();
        }
        try {
            EProvinceState provinceState = EProvinceState.valueOf(province);
            return addressService.findDistrictsByProvince(provinceState)
                    .stream()
                    .map(EDistrictState::name)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            return List.of();
        }
    }
}