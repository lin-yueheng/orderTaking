package com.dazuoye.xiaoyuansaishi1.service.impl;

import com.dazuoye.xiaoyuansaishi1.dto.Result;
import com.dazuoye.xiaoyuansaishi1.entity.Event;
import com.dazuoye.xiaoyuansaishi1.mapper.EventMapper;
import com.dazuoye.xiaoyuansaishi1.service.EventService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.dazuoye.xiaoyuansaishi1.utils.RedisConstants.LOGIN_USER_KEY;

/**
 * <p>
 * 比赛 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2023-05-10
 */
@Service
@Slf4j
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements EventService {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public Result postEvent(Event event,HttpServletRequest request){
        String token = request.getHeader("token");
        String tokenKey = LOGIN_USER_KEY + token;
        String user = String.valueOf(stringRedisTemplate.opsForHash().get(tokenKey, "companyName"));
        event.setCreateUser(user);
        event.setUpdateUser(user);
        event.setStatus(1);
        save(event);
        return Result.ok();
    }
}
