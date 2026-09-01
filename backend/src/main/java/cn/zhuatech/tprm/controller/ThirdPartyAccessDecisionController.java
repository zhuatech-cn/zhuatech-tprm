/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.tprm.controller;

import cn.zhuatech.tprm.common.ApiResponse;
import cn.zhuatech.tprm.service.ThirdPartyAccessDecisionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enterprise/tprm")
public class ThirdPartyAccessDecisionController {
    private final ThirdPartyAccessDecisionService service;
    public ThirdPartyAccessDecisionController(ThirdPartyAccessDecisionService service) { this.service = service; }

    @PostMapping("/third-party-access-decision")
    public ApiResponse<?> assess(@RequestBody ThirdPartyAccessDecisionService.Request request) {
        return ApiResponse.ok(service.assess(request));
    }
}
