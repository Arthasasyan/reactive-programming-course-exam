package com.griddynamics.reactive.course.userinfoservice.controller;

import com.griddynamics.reactive.course.userinfoservice.service.UserInfoService;
import com.griddynamics.reactive.course.userinfoservice.util.MDCUtil;
import com.griddynamics.reactive.course.userinfoservice.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/userInfoService")
public class UserInfoController {

    private final UserInfoService userInfoService;

    @Autowired
    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping(value = "/userInfo/userId", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<UserInfoVo> getUserInfo(@RequestParam("userId") String userId,
                                        @RequestHeader("requestId") String requestId) {
        return userInfoService.findUserInfoByUserId(userId).contextWrite(it -> it.put(MDCUtil.REQUEST_ID_MDC_VALUE, requestId));
    }
}
