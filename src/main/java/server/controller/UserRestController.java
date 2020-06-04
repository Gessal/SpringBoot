package server.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.model.User;
import server.service.UserService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserRestController {

    UserService service;

    public UserRestController(UserService service) {
        this.service = service;
    }

    @PostMapping("/get-by-uname")
    public User test(@RequestParam("username") String username, HttpServletResponse response) {
        try {
            User user = service.getByName(username);
            if (user != null) {
                response.setStatus(200);
            } else {
                response.setStatus(404);
                return null;
            }
            return user;
        } catch (Exception e) {
            response.setStatus(500);
            return null;
        }
    }
}
