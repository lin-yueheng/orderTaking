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
    @Select("SELECT v.name , e.holding1,e.id\n" +
            "FROM venue v\n" +
            "         INNER JOIN event e ON v.id = e.venue_id;")
    List<EventDto> getEvent();

    @Select("SELECT v.name AS Vname,e.id, e.name, e.type, e.format, e.registration1, e.registration2, e.holding1, e.holding2, " +
            "e.venue_id, e.introduction, e.rule, e.demand, e.awards, " +
            "e.picture, e.create_time, e.update_time, e.create_user, e.update_user, e.status FROM event e  " +
            "INNER JOIN venue v ON v.id=e.venue_id WHERE e.id=#{id}")
    List<Event_n> getDetailEvent(Long id);
}
