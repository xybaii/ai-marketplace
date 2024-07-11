package aigc.backend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import aigc.backend.models.User;

@Controller
@RequestMapping
public class RegistrationController {

    // @GetMapping("/register")
    // public String loadRegistrationPage(@ModelAttribute User user, Model model) {
    //     model.addAttribute("user", new User());
    //     return "register";
    // }


}
