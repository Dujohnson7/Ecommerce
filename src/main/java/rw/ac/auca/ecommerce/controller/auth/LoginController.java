package rw.ac.auca.ecommerce.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ecommerce.core.address.model.Address;
import rw.ac.auca.ecommerce.core.address.service.IAddressService;
import rw.ac.auca.ecommerce.core.role.model.Role;
import rw.ac.auca.ecommerce.core.role.service.IRoleService;
import rw.ac.auca.ecommerce.core.users.model.Users;
import rw.ac.auca.ecommerce.core.users.service.IUsersService;
import rw.ac.auca.ecommerce.core.util.address.EDistrictState;
import rw.ac.auca.ecommerce.core.util.address.EProvinceState;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final IAddressService addressService;
    private final IUsersService usersService;
    private final IRoleService roleService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new Users());
        model.addAttribute("provinces", addressService.findDistinctProvinces());
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute Users user,
                               @RequestParam("provinceState") String provinceState,
                               @RequestParam("districtState") String districtState,
                               @RequestParam(value = "sector", required = false) String sector,
                               @RequestParam(value = "street", required = false) String street,
                               Model model) {
        try {
            Role customerRole = roleService.findRoleByName("CUSTOMER");
            if (Objects.isNull(customerRole)) {
                model.addAttribute("error", "Customer role not found");
                model.addAttribute("user", user);
                model.addAttribute("provinces", addressService.findDistinctProvinces());
                return "signup";
            }
            user.setRole(customerRole);

            Address address = addressService.findAddressByProvinceAndDistrict(
                    EProvinceState.valueOf(provinceState),
                    EDistrictState.valueOf(districtState)
            );

            user.setAddress(address);
            user.setActive(true);

            usersService.registerUsers(user);
            return "redirect:/login?success";
        } catch (Exception e) {
            model.addAttribute("error", "Error Registering User: " + e.getMessage());
            model.addAttribute("user", user);
            model.addAttribute("provinces", addressService.findDistinctProvinces());
            return "signup";
        }
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