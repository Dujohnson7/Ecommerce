package rw.ac.auca.ecommerce.controller.users;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ecommerce.core.address.model.Address;
import rw.ac.auca.ecommerce.core.address.service.IAddressService;
import rw.ac.auca.ecommerce.core.role.model.Role;
import rw.ac.auca.ecommerce.core.users.model.Users;
import rw.ac.auca.ecommerce.core.users.service.IUsersService;
import rw.ac.auca.ecommerce.core.util.address.EProvinceState;
import rw.ac.auca.ecommerce.core.util.address.EDistrictState;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/users/")
public class UsersController {

    private final IUsersService usersService;
    private final IAddressService addressService;

    @GetMapping({"", "/", "search/all"})
    public String getAllUsers(Model model) {
        model.addAttribute("user", new Users());
        model.addAttribute("users", usersService.findAllUsersByState(Boolean.TRUE));
        model.addAttribute("roles", usersService.findAllRoles());
        model.addAttribute("provinces", addressService.findDistinctProvinces());
        model.addAttribute("districts", addressService.findDistinctDistricts());
        return "auth/userP";
    }

    @GetMapping("/register")
    public String registerUser(Model model) {
        model.addAttribute("user", new Users());
        model.addAttribute("roles", usersService.findAllRoles());
        model.addAttribute("provinces", addressService.findDistinctProvinces());
        model.addAttribute("districts", addressService.findDistinctDistricts());
        return "auth/userP";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Users theUsers,
                               @RequestParam(value = "provinceState", required = false) String provinceState,
                               @RequestParam(value = "districtState", required = false) String districtState,
                               Model model) {
        try {
            if (provinceState == null || provinceState.isEmpty() || districtState == null || districtState.isEmpty()) {
                model.addAttribute("error", "Please select a valid province and district");
                model.addAttribute("user", theUsers);
                model.addAttribute("users", usersService.findAllUsersByState(Boolean.TRUE));
                model.addAttribute("roles", usersService.findAllRoles());
                model.addAttribute("provinces", addressService.findDistinctProvinces());
                model.addAttribute("districts", addressService.findDistinctDistricts());
                return "auth/userP";
            }

            Address address = addressService.findAddressByProvinceAndDistrict(
                    EProvinceState.valueOf(provinceState),
                    EDistrictState.valueOf(districtState)
            );
            if (address == null) {
                model.addAttribute("error", "No address found for the selected province and district");
                model.addAttribute("user", theUsers);
                model.addAttribute("users", usersService.findAllUsersByState(Boolean.TRUE));
                model.addAttribute("roles", usersService.findAllRoles());
                model.addAttribute("provinces", addressService.findDistinctProvinces());
                model.addAttribute("districts", addressService.findDistinctDistricts());
                return "auth/userP";
            }
            theUsers.setAddress(address);

            usersService.registerUsers(theUsers);
            model.addAttribute("message", "User has been registered successfully");
            return "redirect:/users/search/all";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Invalid province or district selected");
            model.addAttribute("user", theUsers);
            model.addAttribute("users", usersService.findAllUsersByState(Boolean.TRUE));
            model.addAttribute("roles", usersService.findAllRoles());
            model.addAttribute("provinces", addressService.findDistinctProvinces());
            model.addAttribute("districts", addressService.findDistinctDistricts());
            return "auth/userP";
        } catch (Exception e) {
            model.addAttribute("error", "Error Registering User: " + e.getMessage());
            model.addAttribute("user", theUsers);
            model.addAttribute("users", usersService.findAllUsersByState(Boolean.TRUE));
            model.addAttribute("roles", usersService.findAllRoles());
            model.addAttribute("provinces", addressService.findDistinctProvinces());
            model.addAttribute("districts", addressService.findDistinctDistricts());
            return "auth/userP";
        }
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") String id, Model model) {
        if (Objects.nonNull(id)) {
            Users theUsers = new Users();
            theUsers.setId(UUID.fromString(id));
            usersService.deleteUsers(theUsers);
        }
        return "redirect:/users/search/all";
    }
    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Users user = usersService.findUserByIdAndState(uuid, Boolean.TRUE);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid UUID format");
        }
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam("id") String id,
                             @ModelAttribute Users theUsers,
                             @RequestParam(value = "provinceState", required = false) String provinceState,
                             @RequestParam(value = "districtState", required = false) String districtState,
                             Model model) {
        if (Objects.nonNull(id)) {
            Users existingUser = usersService.findUserByIdAndState(UUID.fromString(id), Boolean.TRUE);
            if (Objects.nonNull(existingUser)) {
                existingUser.setFirstName(theUsers.getFirstName());
                existingUser.setLastName(theUsers.getLastName());
                existingUser.setEmail(theUsers.getEmail());
                existingUser.setPhoneNumber(theUsers.getPhoneNumber());
                existingUser.setUsername(theUsers.getUsername());
                existingUser.setRole(theUsers.getRole());
                existingUser.setSector(theUsers.getSector());
                existingUser.setStreet(theUsers.getStreet());
                existingUser.setActive(theUsers.isActive());
                if (theUsers.getPassword() != null && !theUsers.getPassword().isEmpty()) {
                    existingUser.setPassword(theUsers.getPassword());
                }

                if (provinceState == null || provinceState.isEmpty() || districtState == null || districtState.isEmpty()) {
                    model.addAttribute("error", "Please select a valid province and district");
                    model.addAttribute("user", existingUser);
                    model.addAttribute("users", usersService.findAllUsersByState(Boolean.TRUE));
                    model.addAttribute("roles", usersService.findAllRoles());
                    model.addAttribute("provinces", addressService.findDistinctProvinces());
                    model.addAttribute("districts", addressService.findDistinctDistricts());
                    return "auth/userP";
                }

                Address address = addressService.findAddressByProvinceAndDistrict(
                        EProvinceState.valueOf(provinceState),
                        EDistrictState.valueOf(districtState)
                );
                if (address == null) {
                    model.addAttribute("error", "No address found for the selected province and district");
                    model.addAttribute("user", existingUser);
                    model.addAttribute("users", usersService.findAllUsersByState(Boolean.TRUE));
                    model.addAttribute("roles", usersService.findAllRoles());
                    model.addAttribute("provinces", addressService.findDistinctProvinces());
                    model.addAttribute("districts", addressService.findDistinctDistricts());
                    return "auth/userP";
                }
                existingUser.setAddress(address);

                usersService.updateUsers(existingUser);
                model.addAttribute("message", "User updated successfully");
                return "redirect:/users/search/all";
            }
        }
        model.addAttribute("error", "User not found");
        model.addAttribute("user", new Users());
        model.addAttribute("users", usersService.findAllUsersByState(Boolean.TRUE));
        model.addAttribute("roles", usersService.findAllRoles());
        model.addAttribute("provinces", addressService.findDistinctProvinces());
        model.addAttribute("districts", addressService.findDistinctDistricts());
        return "auth/userP";
    }

    @GetMapping("/districts-by-province")
    @ResponseBody
    public List<String> getDistrictsByProvince(@RequestParam(value = "province", required = false) String province) {
        if (province == null || province.isEmpty()) {
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