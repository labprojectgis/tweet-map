package com.advancedatabase.project.controller;

import com.advancedatabase.project.model.Search;
import com.advancedatabase.project.model.Tweet;
import com.advancedatabase.project.service.TweetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Polygon;
import org.springframework.web.bind.annotation.*;
import twitter4j.Location;
import twitter4j.TwitterException;

import java.util.List;
import java.util.Optional;

import static com.advancedatabase.project.util.TweetUtil.getTweetsBySearch;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class TweetController {

    TweetService tweetService;

    @Autowired
    TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping(value = "/search")
    public List<Tweet> search(@RequestBody Optional<Search> search) {

        if (search.isPresent()) {
            return getTweetsBySearch(tweetService, search.get());
        } else {
            return tweetService.findTweetGeoNotNull();
        }

    }

    @PostMapping(value = "/search/circle")
    public List<Tweet> searchInCircle(@RequestBody Circle circle) {

        return tweetService.findTweetsInCircle(circle);

    }

    @PostMapping(value = "/search/polygon")
    public List<Tweet> searchInPolygon(@RequestBody Polygon polygon) {

        return tweetService.findTweetsInPolygon(polygon);

    }

    @PostMapping(value = "/search/box")
    public List<Tweet> searchInBox(@RequestBody Box box) {

        return tweetService.findTweetsInBox(box);

    }

    @PostMapping(value = "/search/{key}/circle")
    public List<Tweet> searchInCircle(@PathVariable String key, @RequestBody Circle circle) {

        return tweetService.findTweetsInCircleWithTextCriteria(circle, key);

    }

    @PostMapping(value = "/search/{key}/polygon")
    public List<Tweet> searchInPolygon(@PathVariable String key, @RequestBody Polygon polygon) {

        return tweetService.findTweetsInPolygonAndKey(polygon, key);

    }

    @PostMapping(value = "/search/{key}/box")
    public List<Tweet> searchInBox(@PathVariable String key, @RequestBody Box box) {

        return tweetService.findTweetsInBoxAndKey(box, key);

    }

    @GetMapping(value = "/locations")
    public List<Location> getLocations() throws TwitterException {

        return tweetService.getLocations();

    }
}
