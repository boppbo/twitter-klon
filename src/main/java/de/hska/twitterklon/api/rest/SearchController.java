package de.hska.twitterklon.api.rest;

import java.util.List;

import de.hska.twitterklon.api.transferobjects.UserTO;
import de.hska.twitterklon.api.transferobjects.converters.UserToConverter;
import de.hska.twitterklon.redis.RedisDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
public class SearchController {

    private final RedisDataService redisDataService;

    @Autowired
    public SearchController(RedisDataService redisDataService) {
        this.redisDataService = redisDataService;
    }

    @RequestMapping(path = "search", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<UserTO> searchForUser(@RequestParam String value, @RequestParam int count, @RequestParam int skipCount) {
        return UserToConverter.fromStringList(redisDataService.searchUser(value, count, skipCount));
    }
}
