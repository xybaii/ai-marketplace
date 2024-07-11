package aigc.backend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import aigc.backend.models.LoginRequest;

@Controller
@RequestMapping
public class LoginController {

    // @GetMapping("/login")
    // public String loadLoginPage(@ModelAttribute LoginRequest loginrequest, Model model) {
    //     model.addAttribute("loginrequest", new LoginRequest());
    //     return "login";
    // }

    // @GetMapping("/welcome")
    // public String loadWelcomePgae() {
    //     return "welcome";
    // }

}
