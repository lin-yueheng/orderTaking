package com.dazuoye.xiaoyuansaishi1.service;

import com.dazuoye.xiaoyuansaishi1.dto.Result;
import com.dazuoye.xiaoyuansaishi1.entity.Event;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

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
}
