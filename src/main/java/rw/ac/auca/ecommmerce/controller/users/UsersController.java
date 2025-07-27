package rw.ac.auca.ecommmerce.controller.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ecommmerce.core.role.model.Role;
import rw.ac.auca.ecommmerce.core.users.model.Users;
import rw.ac.auca.ecommmerce.core.users.service.IUsersService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/users/")
public class UsersController {

    private final IUsersService usersService;

    @GetMapping({"", "/", "search/all"})
    public String getAllUsers(Model model) {
        model.addAttribute("user", new Users());
        model.addAttribute("users", usersService.findAllUsersByState(Boolean.TRUE));
        model.addAttribute("roles", usersService.findAllRoles());
        return "auth/userP";
    }

    @GetMapping("/register")
    public String registerUser(Model model) {
        model.addAttribute("user", new Users());
        model.addAttribute("roles", usersService.findAllRoles());
        return "auth/userP";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Users theUsers, Model model) {
        try {
            if (Objects.nonNull(theUsers)) {
                usersService.registerUsers(theUsers);
                model.addAttribute("message", "User has been registered successfully");
                return "redirect:/users/search/all";
            } else {
                model.addAttribute("error", "Data is invalid");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error Registering User: " + e.getMessage());
        }
        model.addAttribute("user", new Users());
        model.addAttribute("users", usersService.findAllUsersByState(Boolean.TRUE));
        model.addAttribute("roles", usersService.findAllRoles());
        return "auth/userP";
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

    @PostMapping("/update")
    public String updateUser(@RequestParam("id") String id, Model model) {
        if (Objects.nonNull(id)) {
            Users theUsers = usersService.findUserByIdAndState(UUID.fromString(id), Boolean.TRUE);
            if (Objects.nonNull(theUsers)) {
                model.addAttribute("user", theUsers);
                model.addAttribute("users", usersService.findAllUsersByState(Boolean.TRUE));
                model.addAttribute("roles", usersService.findAllRoles());
                return "auth/userP";
            }
        }
        model.addAttribute("error", "Wrong Information");
        model.addAttribute("user", new Users());
        model.addAttribute("users", usersService.findAllUsersByState(Boolean.TRUE));
        model.addAttribute("roles", usersService.findAllRoles());
        return "auth/userP";
    }
}