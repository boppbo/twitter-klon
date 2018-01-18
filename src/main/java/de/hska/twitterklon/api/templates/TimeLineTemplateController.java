package de.hska.twitterklon.api.templates;

import de.hska.twitterklon.api.exceptions.ResourceNotFoundException;
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

    @RequestMapping(path = "global", method = RequestMethod.GET)
    public String globalTimeLine(Model model) {
        model.addAttribute("postUrl", "/api/v1/timeline/global?foo=bar");
        return getTimeLineTemplate(model);
    }

    @RequestMapping(path = "search", method = RequestMethod.GET)
    public String search(String value, Model model) {
        model.addAttribute("postUrl", "api/v1/search?value=" + value);
        return getTimeLineTemplate(model);
    }

    @RequestMapping(path = "user/{userName}", method = RequestMethod.GET)
    public String userTimeLine(@PathVariable String userName, Model model) {
        if (!redisDataService.searchUser(userName, Integer.MAX_VALUE, 0).contains(userName))
            throw new ResourceNotFoundException();

        model.addAttribute("profileUser", userName);
        model.addAttribute("postUrl", "/api/v1/timeline/user/" + userName + "?foo=bar");
        return getTimeLineTemplate(model);
    }

    @GetMapping
    public String selfTimeLine(Model model) {
        model.addAttribute("postUrl", "/api/v1/timeline/self?foo=bar");
        return getTimeLineTemplate(model);
    }

    private String getTimeLineTemplate(Model model) {
        TemplateModelFiller.fillStandardParameters(model);
        return "posts";
    }
}
