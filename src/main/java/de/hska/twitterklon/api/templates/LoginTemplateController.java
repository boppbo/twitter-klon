package de.hska.twitterklon.api.templates;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("login")
public class LoginTemplateController {

    @GetMapping
    public String login(Model model) {
        model.addAttribute("siteName", "Twiffer");
        return "login";
    }
}
