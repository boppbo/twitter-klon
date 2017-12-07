package de.hska.twitterklon.api.templates;

import de.hska.twitterklon.authentication.SessionInformation;
import de.hska.twitterklon.redis.RedisDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class TimeLineTemplateController {

    private final RedisDataService redisDataService;

    @Autowired
    public TimeLineTemplateController(RedisDataService redisDataService) {
        this.redisDataService = redisDataService;
    }

    @GetMapping
    public String globalTimeLine(Model model) {
        if(SessionInformation.isUserSignedIn()) {
        TemplateModelFiller.fillStandardParameters(model);
            return "posts";
        }
        return "redirect:/login";
    }

    @RequestMapping(path = "user/{userName}", method = RequestMethod.GET)
    public String userTimeLine(@PathVariable String userName, Model model) {
        TemplateModelFiller.fillStandardParameters(model);
        if(SessionInformation.isUserSignedIn()) {
            return "posts";
        }
        return "redirect:/login";
    }
}
