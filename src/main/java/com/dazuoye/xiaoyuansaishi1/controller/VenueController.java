package com.dazuoye.xiaoyuansaishi1.controller;


import com.dazuoye.xiaoyuansaishi1.dto.Result;
import com.dazuoye.xiaoyuansaishi1.service.VenueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 比赛场馆 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2023-05-10
 */
@RestController
@RequestMapping("/venue")
@Slf4j
@CrossOrigin
public class VenueController {

    @Autowired
    private VenueService venueService;

    @GetMapping("/univerList")
    public Result getUniverList(){
        return venueService.getUniverList();
    }

    @GetMapping("/venueList/{university}")
    public Result getVenueList(@PathVariable("university") String university){
        return venueService.getVenueList(university);
    }

}

