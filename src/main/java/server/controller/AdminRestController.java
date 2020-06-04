package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import server.model.Role;
import server.model.User;
import server.service.UserService;

import javax.servlet.http.HttpServletResponse;
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

    @GetMapping("/get")
    public List<User> getUsers(HttpServletResponse response) {
        try {
            List<User> users = service.list();
            response.setStatus(200);
            return users;
        } catch (Exception e) {
            response.setStatus(500);
            return null;
        }
    }

    @PostMapping("/add")
    public User addUser(@RequestParam("username") String username, @RequestParam("password") String password,
                        @RequestParam("name") String name, @RequestParam("surname") String surname,
                        @RequestParam("age") byte age, @RequestParam("roles") String[] roles, HttpServletResponse response) {
        try {
            User user = new User(username, new BCryptPasswordEncoder().encode(password), name, surname, age);
            if (!service.existsByName(username)) {
                List<Role> listRoles = new ArrayList<>();
                for (String r : roles) {
                    listRoles.add(new Role("ROLE_" + r));
                }
                user.setRoles(listRoles);
                response.setStatus(201);
                return service.add(user);
            } else {
                response.setStatus(406);
                return null;
            }
        } catch (Exception e) {
            response.setStatus(500);
            return null;
        }
    }

    @PostMapping("/delete")
    public void delUser(@RequestParam("id") Long id, HttpServletResponse response) {
        try {
            service.delete(id);
            response.setStatus(202);
        } catch (Exception e) {
            response.setStatus(500);
        }
    }

    @PostMapping("/update")
    public User updUser(@RequestParam("id") Long id, @RequestParam("name") String name,
                           @RequestParam("surname") String surnane, @RequestParam("age") byte age,
                           @RequestParam("username") String username, @RequestParam(value = "password", required = false) String password,
                           @RequestParam("roles[]") String[] roles, HttpServletResponse response) {
        try {
            if (password == null || password.equals("")) {
                password = service.get(id).getPassword();
            } else {
                password = new BCryptPasswordEncoder().encode(password);
            }
            User user = new User(id, username, password, name, surnane, age, (byte) 1);
            List<Role> listRoles = new ArrayList<>();
            for (String r : roles) {
                listRoles.add(new Role("ROLE_" + r));
            }
            user.setRoles(listRoles);
            service.set(user);
            response.setStatus(202);
            return user;
        } catch (Exception e) {
            response.setStatus(500);
            return null;
        }
    }
}
