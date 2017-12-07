package de.hska.twitterklon.api.templates;

import de.hska.twitterklon.authentication.SessionInformation;
import org.springframework.ui.Model;

public class TemplateModelFiller {

    public static void fillStandardParameters(Model model) {
        model.addAttribute("loggedin", "true");
        model.addAttribute("username", SessionInformation.getUserName());
        model.addAttribute("siteName", "Twiffer");
    }
}
