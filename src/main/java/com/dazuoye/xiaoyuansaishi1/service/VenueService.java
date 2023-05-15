package com.dazuoye.xiaoyuansaishi1.service;

import com.dazuoye.xiaoyuansaishi1.dto.Result;
import com.dazuoye.xiaoyuansaishi1.entity.Venue;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 比赛场馆 服务类
 * </p>
 *
 * @author ${author}
 * @since 2023-05-10
 */
public interface VenueService extends IService<Venue> {

    Result getUniverList();

    Result getVenueList(String university);
}
