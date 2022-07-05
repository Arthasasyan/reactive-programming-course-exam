package com.griddynamics.reactive.course.userinfoservice.service;

import com.griddynamics.reactive.course.userinfoservice.vo.UserInfoVo;
import reactor.core.publisher.Flux;

public interface UserInfoService {
    Flux<UserInfoVo> findUserInfoByUserId(String userId);
}
