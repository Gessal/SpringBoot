package springBoot.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import springBoot.model.Role;
import springBoot.model.User;
import springBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    UserService service;

    @Autowired
    public AdminController(UserService service){
        this.service = service;
    }

    @GetMapping("/admin")
    public String printUsers(ModelMap model) {
        List<User> users = service.list();
        model.addAttribute("users", users);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User curUser = service.getByName(userDetails.getUsername());
        model.addAttribute("curUser", curUser);

        return "admin";
    }

    @PostMapping("/admin/users")
    public String deleteUser(@RequestParam(name = "id") Long id, ModelMap model) {
        User user = service.get(id);
        service.delete(id);
        List<User> users = service.list();
        model.addAttribute("users", users);
        return "_users";
    }

    @GetMapping("/admin/update")
    public String printUserForUpdate(@RequestParam(name = "id") Long id, ModelMap model) {
        User user = service.get(id);
        boolean isUser = false;
        boolean isAdmin = false;
        for (Role role : user.getRoles()) {
            if (role.getRole().equals("ROLE_USER")) {
                isUser = true;
            } else if (role.getRole().equals("ROLE_ADMIN")) {
                isAdmin = true;
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("isUser", isUser);
        model.addAttribute("isAdmin", isAdmin);
        return "update";
    }

    @PostMapping("/admin/update")
    public String updateUser(@RequestParam(name = "id") Long id, @RequestParam(name = "username") String username,
                             @RequestParam(name = "password") String password, @RequestParam(name = "name") String name,
                             @RequestParam(name = "surname") String surname, @RequestParam(name = "age") byte age,
                             @RequestParam(name = "isEnabled", required = false) boolean isEnabled,
                             @RequestParam(name = "isUser", required = false) boolean isUser,
                             @RequestParam(name = "isAdmin", required = false) boolean isAdmin, ModelMap model) {
        User user = new User(id, username, password, name, surname, age, isEnabled ? (byte) 1 : 0);
        List<Role> roles = new ArrayList<>();
        if (isUser) {
            roles.add(new Role("ROLE_USER"));
        }
        if (isAdmin) {
            roles.add(new Role("ROLE_ADMIN"));
        }
        user.setRoles(roles);
        service.set(user);
        List<User> users = service.list();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/admin")
    public String AddUser(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password,
                          @RequestParam(name = "name") String name, @RequestParam(name = "surname") String surname,
                          @RequestParam(name = "age") byte age, @RequestParam(name = "roles") List<String> rol,
                          ModelMap model) {
        User user = new User(username, password, name, surname, age);
        List<Role> roles = new ArrayList<>();
        for (String r : rol) {
            roles.add(new Role("ROLE_" + r));
        }
        user.setRoles(roles);
        service.add(user);
        List<User> users = service.list();
        model.addAttribute("users", users);
        return "admin";
    }
}
