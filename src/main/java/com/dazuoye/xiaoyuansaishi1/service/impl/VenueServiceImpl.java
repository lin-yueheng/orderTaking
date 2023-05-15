package com.dazuoye.xiaoyuansaishi1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dazuoye.xiaoyuansaishi1.dto.Result;
import com.dazuoye.xiaoyuansaishi1.entity.Venue;
import com.dazuoye.xiaoyuansaishi1.mapper.VenueMapper;
import com.dazuoye.xiaoyuansaishi1.service.VenueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 比赛场馆 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2023-05-10
 */
@Service
@Slf4j
public class VenueServiceImpl extends ServiceImpl<VenueMapper, Venue> implements VenueService {

    @Override
    public Result getUniverList() {

        LambdaQueryWrapper<Venue> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(Venue::getUniversity).groupBy(Venue::getUniversity);

        List<Venue> univerList = list(lambdaQueryWrapper);
        return Result.ok(univerList);
    }

    @Override
    public Result getVenueList(String university) {
        LambdaQueryWrapper<Venue> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(Venue::getName,Venue::getId)
                          .eq(Venue::getUniversity,university)
                          .eq(Venue::getStatus,1);

        List<Venue> venueList = list(lambdaQueryWrapper);
        return Result.ok(venueList);
    }
}
