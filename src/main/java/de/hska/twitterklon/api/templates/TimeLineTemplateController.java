package de.hska.twitterklon.api.templates;

import de.hska.twitterklon.authentication.SessionInformation;
import de.hska.twitterklon.redis.RedisDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TimeLineTemplateController {

    private final RedisDataService redisDataService;

    @Autowired
    public TimeLineTemplateController(RedisDataService redisDataService) {
        this.redisDataService = redisDataService;
    }

    @GetMapping
    public String timeLine(Model model) {
        if(SessionInformation.isUserSignedIn()) {
            model.addAttribute("siteName", "Twiffer");
            return "posts";
        }
        return "redirect:/login";
    }
}
