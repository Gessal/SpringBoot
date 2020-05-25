package springBoot.controller;

import springBoot.model.User;
import springBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/*")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping("/user")
    public String loginPage(ModelMap model) {
        User user = service.get(1L);
        model.addAttribute("user", user);
        return "user";
    }
}
