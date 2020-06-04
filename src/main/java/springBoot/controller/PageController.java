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
import java.util.List;

@Controller
@RequestMapping(value = {"/admin", "/user"})
public class PageController {

    UserService service;

    @Autowired
    public PageController(UserService service){
        this.service = service;
    }

    @GetMapping
    public String printUsers(ModelMap model) {
        User user = getAuthUser();
        model.addAttribute("curUser", user);
        for (Role role : user.getRoles()) {
            if (role.getRole().equals("ROLE_ADMIN")) {
                List<User> users = service.list();
                model.addAttribute("users", users);
            }
        }
        return "admin";
    }

    private User getAuthUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.getByName(userDetails.getUsername());
    }
}
