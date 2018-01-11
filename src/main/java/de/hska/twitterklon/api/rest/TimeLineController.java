package de.hska.twitterklon.api.rest;

import de.hska.twitterklon.api.transferobjects.PostDto;
import de.hska.twitterklon.authentication.SessionInformation;
import de.hska.twitterklon.redis.RedisDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/timeline")
public class TimeLineController {

    private static final int POST_COUNT = 16;

    private final RedisDataService redisDataService;

    @Autowired
    public TimeLineController(RedisDataService redisDataService) {
        this.redisDataService = redisDataService;
    }


    @RequestMapping(path = "/global", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<PostDto> getGlobalTimeLinePosts(@RequestParam(name = "skipCount", required = true) int skipCount) {
        return redisDataService.getLatestTimeline(POST_COUNT, skipCount);
    }

    @RequestMapping(path = "/self", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<PostDto> getSelfTimeLinePosts(@RequestParam(name = "skipCount", required = true) int skipCount) {
        return redisDataService.getLatestTimeline(SessionInformation.getUserName(), POST_COUNT, skipCount);
    }

    @RequestMapping(path = "/self", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewPost(@RequestBody PostDto postDto) {
        redisDataService.addPost(postDto.getContent());
    }

    @RequestMapping(path = "/user/{userName}", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<PostDto> getUserTimeLinePosts(
            @PathVariable("userName") String userName,
            @RequestParam(name = "skipCount", required = true) int skipCount)
    {
        return redisDataService.getLastPosts(userName, POST_COUNT, skipCount);
    }

}
