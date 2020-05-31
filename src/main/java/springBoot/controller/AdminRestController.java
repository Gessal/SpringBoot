package springBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springBoot.model.Role;
import springBoot.model.User;
import springBoot.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    UserService service;

    @Autowired
    public AdminRestController(UserService service){
        this.service = service;
    }

    @PostMapping("/add")
    public User addUser(@RequestParam("username") String username, @RequestParam("password") String password,
                        @RequestParam("name") String name, @RequestParam("surname") String surname,
                        @RequestParam("age") byte age, @RequestParam("roles") String[] roles) {
        try {
            User user = new User(username, new BCryptPasswordEncoder().encode(password), name, surname, age);
            if (service.getByName(username) == null) {
                List<Role> listRoles = new ArrayList<>();
                for (String r : roles) {
                    listRoles.add(new Role("ROLE_" + r));
                }
                user.setRoles(listRoles);
                return service.add(user);
            } else {
                user.setId(-1L);
                return user;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/delete")
    public boolean delUser(@RequestParam("id") Long id) {
        try {
            service.delete(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
