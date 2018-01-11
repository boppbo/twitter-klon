package de.hska.twitterklon.api.rest;

import de.hska.twitterklon.api.transferobjects.UserTO;
import de.hska.twitterklon.api.transferobjects.converters.UserToConverter;
import de.hska.twitterklon.authentication.SessionInformation;
import de.hska.twitterklon.redis.RedisDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/follow")
public class FollowController {
    private final RedisDataService redisDataService;

    @Autowired
    public FollowController(RedisDataService redisDataService) {
        this.redisDataService = redisDataService;
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addFollower(@RequestBody @Valid UserTO userTO) {
        redisDataService.addFollower(SessionInformation.getUserName(), userTO.getName());
    }

    @DeleteMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFollower(@RequestBody @Valid UserTO userTO) {
        redisDataService.removeFollower(SessionInformation.getUserName(), userTO.getName());
    }

    @RequestMapping(path = "/user/{userName}/followers", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<UserTO> getFollowers(@PathVariable("userName") String userName) {
        return UserToConverter.fromStringList(redisDataService.getFollower(userName));
    }

    @RequestMapping(path = "/user/{userName}/following", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<UserTO> getFollowing(@PathVariable("userName") String userName) {
        return UserToConverter.fromStringList(redisDataService.getFollowing(userName));
    }
}
