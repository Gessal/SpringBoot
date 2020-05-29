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
@RequestMapping("/admin")
public class AdminController {

    UserService service;

    @Autowired
    public AdminController(UserService service){
        this.service = service;
    }

    @GetMapping
    public String printUsers(ModelMap model) {
        List<User> users = service.list();
        model.addAttribute("users", users);
        model.addAttribute("curUser", getAuthUser());
        return "admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam(name = "id") Long id) {
        service.delete(id);
        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam(name = "id") Long id, @RequestParam(name = "username") String username,
                             @RequestParam(name = "password") String password, @RequestParam(name = "name") String name,
                             @RequestParam(name = "surname") String surname, @RequestParam(name = "age") byte age,
                             @RequestParam(name = "roles", required = false) String[] roles) {
        User user = new User(id, username, password, name, surname, age, (byte) 1);
        List<Role> roleList = new ArrayList<>();
        for (String r : roles) {
            roleList.add(new Role(r));
        }
        user.setRoles(roleList);
        service.set(user);
        return "redirect:/admin";
    }

    @PostMapping
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
        model.addAttribute("curUser", getAuthUser());
        return "admin";
    }

    private User getAuthUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.getByName(userDetails.getUsername());
    }
}
