package de.hska.twitterklon.api.rest;

import de.hska.twitterklon.api.transferobjects.PostDto;
import de.hska.twitterklon.redis.RedisDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<PostDto> searchForUser(@RequestParam String value, @RequestParam(name = "skipCount", required = true) int skipCount) {
        return redisDataService.searchUser(value, 16, skipCount).stream().map(u -> new PostDto("", "", u, "")).collect(Collectors.toList());
    }
}
