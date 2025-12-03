package rw.ac.auca.ecommerce.controller.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ecommerce.core.role.model.Role;
import rw.ac.auca.ecommerce.core.role.service.IRoleService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/role/")
public class RoleController {

    private final IRoleService roleService;

    @GetMapping({"/","/search/all"})
    public String getAllRoles(Model model){
        model.addAttribute("role", new Role());
        List<Role> roles = roleService.findRoleByState(Boolean.TRUE);
        model.addAttribute("roles", roles);
        return "auth/roleP";
    }

    @GetMapping("/register")
    public  String getRoleRegisterPage(Model model){
        model.addAttribute("role", new Role());
        return "auth/roleP";
    }

    @PostMapping("/register")
    public  String registerRole(@ModelAttribute("role") Role theRole, Model model){
        try {
            if (Objects.nonNull(theRole)) {
                roleService.registerRole(theRole);
                model.addAttribute("message", "Role has been registered successfully");
                return "redirect:/role/search/all";
            } else {
                model.addAttribute("error", "Data is invalid");
            }
        }catch (Exception e){
            model.addAttribute("error", "Error Registering Role: " + e.getMessage());
        }
        model.addAttribute("role", roleService.findRoleByState(Boolean.TRUE));
        return "auth/roleP";
    }


    @PostMapping("/delete")
    public String deleteRole(@RequestParam("id") String id, Model model){
        if(Objects.nonNull(id)){
            Role theRole = new Role();
            theRole.setId(UUID.fromString(id));
            roleService.deleteRole(theRole);
        }
        return "redirect:/role/search/all";
    }

    @PostMapping("/update")
    public String updateRole(@RequestParam("id") String id, Model model){
        if(Objects.nonNull(id)){
            Role theRole = roleService
                    .findRoleByIdAndState(UUID.fromString(id) , Boolean.TRUE);
            if(Objects.nonNull(theRole)){
                model.addAttribute("role" , theRole);
                return "auth/roleP";
            }
        }
        model.addAttribute("error" , "Wrong Information");
        return "auth/roleP";
    }


}
