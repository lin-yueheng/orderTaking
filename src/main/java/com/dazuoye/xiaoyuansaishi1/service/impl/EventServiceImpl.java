package com.dazuoye.xiaoyuansaishi1.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dazuoye.xiaoyuansaishi1.dto.EventDto;
import com.dazuoye.xiaoyuansaishi1.dto.Result;
import com.dazuoye.xiaoyuansaishi1.entity.Event;
import com.dazuoye.xiaoyuansaishi1.entity.Event_n;
import com.dazuoye.xiaoyuansaishi1.mapper.EventMapper;
import com.dazuoye.xiaoyuansaishi1.service.EventService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    EventMapper eventMapper;

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

    @Override
    public Result eventForm(int page ,int pageSize,HttpServletRequest request) {

        // TODO 获取当前登录主办方的token
        String token = request.getHeader("token");
        String tokenKey = LOGIN_USER_KEY + token;
        String companyname = String.valueOf(stringRedisTemplate.opsForHash().get(tokenKey, "companyName"));

        Page<Event> pageInfo = new Page(page,pageSize);

        Page<EventDto> eventDtoPage = new Page<>();

        // TODO 查询赛事基本信息
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name","type","format","registration1","registration2","holding1","holding2","status")
                    .eq("create_user",companyname)
                    .orderByDesc("holding1");

        page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo,eventDtoPage,"records");

        List<Event> eventList = pageInfo.getRecords();

        // TODO 前端需要的赛事列表
        List<EventDto> list =eventList.stream().map((item) -> {
            EventDto eventDto = new EventDto();
            // TODO 复制
            BeanUtil.copyProperties(item,eventDto);

            Date registration1 = item.getRegistration1();
            Date registration2 = item.getRegistration2();
            Date holding1 = item.getHolding1();
            Date holding2 = item.getHolding2();
            int status = item.getStatus();
            eventDto.setSchedule(compareTime(registration1,registration2,holding1,holding2,status));

            return eventDto;

        }).collect(Collectors.toList());

        eventDtoPage.setRecords(list);

        return Result.ok(eventDtoPage);
    }

    // TODO 时间对比
    public String compareTime(Date registration1,Date registration2,Date holding1,Date holding2,int status){

        String schedule = "";

        LocalDateTime localDateTime = LocalDateTime.now();
        Date now = Date.from( localDateTime.atZone( ZoneId.systemDefault()).toInstant());
        System.out.println(now);
        System.out.println(registration1+"/"+registration2);
        System.out.println(holding1+"/"+holding2);

        if(status==1) {
            if (now.before(holding1)) {
                schedule = "未开始";
            } else if (now.before(holding2) && now.after(holding1)) {
                schedule = "进行中";
            } else if (now.after(holding2)) {
                schedule = "已结束";
            }
        }else if(status==0){
            schedule = "待审核";
        }else if(status==-1){
            schedule = "未通过";
        }

        return schedule;
    }

    @Override
    public Result delEvent(String name){
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        remove(queryWrapper);
        return Result.ok();
    }

    @Override
    public List<EventDto> getEvent() {
        return eventMapper.getEvent();
    }

    @Override
    public List<Event_n> getDetailEvent(Long id){
            return eventMapper.getDetailEvent(id);
    }
}
