package com.dazuoye.xiaoyuansaishi1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dazuoye.xiaoyuansaishi1.dto.EventDto;
import com.dazuoye.xiaoyuansaishi1.entity.Event;
import com.dazuoye.xiaoyuansaishi1.entity.Event_n;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 比赛 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2023-05-10
 */
@Mapper
public interface EventMapper extends BaseMapper<Event> {
    @Select("SELECT v.name , e.name AS competitionName\n" +
            "FROM venue v\n" +
            "         INNER JOIN event e ON v.id = e.venue_id;")
    List<EventDto> getEvent();

    @Select("SELECT id, name, type, format, registration1, registration2, holding1, holding2, " +
            "venue_id, introduction, rule, demand, awards, " +
            "picture, create_time, update_time, create_user, update_user, status FROM event;")
    List<Event_n> getDetailEvent();
}
