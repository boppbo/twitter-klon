package de.hska.twitterklon.api.rest;

import java.util.List;

import javax.validation.Valid;

import de.hska.twitterklon.api.transferobjects.UserTO;
import de.hska.twitterklon.api.transferobjects.converters.UserToConverter;
import de.hska.twitterklon.authentication.SessionInformation;
import de.hska.twitterklon.redis.RedisDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<UserTO> getFollowers() {
        return UserToConverter.fromStringList(redisDataService.getFollower(SessionInformation.getUserName()));
    }
}
