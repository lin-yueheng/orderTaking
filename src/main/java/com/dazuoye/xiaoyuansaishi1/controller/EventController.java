package com.dazuoye.xiaoyuansaishi1.controller;


import com.dazuoye.xiaoyuansaishi1.dto.Result;
import com.dazuoye.xiaoyuansaishi1.entity.Event;
import com.dazuoye.xiaoyuansaishi1.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 比赛 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2023-05-10
 */
@RestController
@RequestMapping("/event")
@Slf4j
@CrossOrigin
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/postEvent")
    public Result postEvent(@RequestBody Event event, HttpServletRequest request){
        return eventService.postEvent(event,request);
    }

}

