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
@RequestMapping("/server")
public class ServerRestController {

    UserService service;

    @Autowired
    public ServerRestController(UserService service){
        this.service = service;
    }

    @GetMapping
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

    @PostMapping
    public User addUser(@RequestParam("username") String username, @RequestParam("password") String password,
                        @RequestParam("name") String name, @RequestParam("surname") String surname,
                        @RequestParam("age") byte age, @RequestParam("roles") String[] roles, HttpServletResponse response) {
        try {
            User user = new User(username, password, name, surname, age);
            user.setRoles(parseRoles(roles));
            response.setStatus(200);
            return service.add(user);
        } catch (Exception e) {
            response.setStatus(500);
            return null;
        }
    }

    @DeleteMapping
    public void delUser(@RequestParam("id") Long id, HttpServletResponse response) {
        try {
            service.delete(id);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
        }
    }

    @PutMapping
    public User updUser(@RequestParam("id") Long id, @RequestParam("name") String name,
                           @RequestParam("surname") String surname, @RequestParam("age") byte age,
                           @RequestParam("username") String username, @RequestParam(value = "password", required = false) String password,
                           @RequestParam("roles") String[] roles, HttpServletResponse response) {
        try {
            if (password == null || password.equals("")) {
                password = service.get(id).getPassword();
            }
            User user = new User(id, username, password, name, surname, age, (byte) 1);
            user.setRoles(parseRoles(roles));
            service.set(user);
            response.setStatus(200);
            return user;
        } catch (Exception e) {
            response.setStatus(500);
            return null;
        }
    }

    @PostMapping("/get-user")
    public User getUser(@RequestParam String username, HttpServletResponse response) {
        try {
            User user = service.getByName(username);
            if (user != null) {
                response.setStatus(200);
                return user;
            } else {
                response.setStatus(404);
                return null;
            }
        } catch (Exception e) {
            response.setStatus(500);
            return null;
        }
    }

    private List<Role> parseRoles(String[] roles) {
        List<Role> result = new ArrayList<>();
        for (String r : roles) {
            result.add(new Role("ROLE_" + r.replace("[", "").replace("]", "")));
        }
        return result;
    }
}
