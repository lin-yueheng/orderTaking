package com.dazuoye.xiaoyuansaishi1.controller;


import cn.hutool.db.Page;
import com.dazuoye.xiaoyuansaishi1.dto.EventDto;
import com.dazuoye.xiaoyuansaishi1.dto.Result;
import com.dazuoye.xiaoyuansaishi1.entity.Event;
import com.dazuoye.xiaoyuansaishi1.entity.Event_n;
import com.dazuoye.xiaoyuansaishi1.entity.user_event;
import com.dazuoye.xiaoyuansaishi1.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    /**
     * 发布赛事
     * @param event
     * @param request
     * @return
     */
    @PostMapping("/postEvent")
    public Result postEvent(@RequestBody Event event, HttpServletRequest request){
        return eventService.postEvent(event,request);
    }


    /**
     * 赛事分页查询
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/eventForm")
    public Result eventForm(@RequestParam("page") int page,@RequestParam("pageSize")int pageSize,HttpServletRequest request){
        return eventService.eventForm(page,pageSize,request);
    }

    /**
     * 删除赛事
     * @param name
     * @return
     */
    @DeleteMapping("/delEvent")
    public Result delEvent(@RequestParam("name")String name){
        return eventService.delEvent(name);
    }

    /**
     * 获取所有的赛事
     * @return
     */
    @GetMapping("getEvent")
    public List<EventDto> getEvent() {
        return eventService.getEvent();
    }

    /**
     * 获取详细的赛事信息
     * @return
     */
    @GetMapping("getDetailEvent")
    public List<Event_n> getDetailEvent(@RequestParam(value = "id") Long id){
        return eventService.getDetailEvent(id);
    }


    @PostMapping("Insert")
    public int Insert(@RequestBody user_event userEvent){
        return eventService.Insert(userEvent);
    }
}

