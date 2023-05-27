package com.dazuoye.xiaoyuansaishi1.service;

import com.dazuoye.xiaoyuansaishi1.dto.EventDto;
import com.dazuoye.xiaoyuansaishi1.dto.Result;
import com.dazuoye.xiaoyuansaishi1.entity.Event;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dazuoye.xiaoyuansaishi1.entity.Event_n;
import com.dazuoye.xiaoyuansaishi1.entity.user_event;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 比赛 服务类
 * </p>
 *
 * @author ${author}
 * @since 2023-05-10
 */
public interface EventService extends IService<Event> {

    Result postEvent(Event event, HttpServletRequest request);

    Result eventForm(int page, int pageSize,HttpServletRequest request);

    Result delEvent(String name);

    //获取赛程的信息
    List<EventDto> getEvent();

    //获取赛程的详细信息
    List<Event_n> getDetailEvent(Long id);

    int Insert(user_event userEvent);
}
