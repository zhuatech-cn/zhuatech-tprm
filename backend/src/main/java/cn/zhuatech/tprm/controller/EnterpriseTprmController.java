/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.tprm.controller;
import cn.zhuatech.tprm.common.ApiResponse; import cn.zhuatech.tprm.service.EnterpriseTprmService; import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/enterprise/tprm") public class EnterpriseTprmController {
 private final EnterpriseTprmService service; public EnterpriseTprmController(EnterpriseTprmService service){this.service=service;}
 @PostMapping("/assess-risk") ApiResponse<?> execute(@Valid @RequestBody EnterpriseTprmService.RiskRequest request){return ApiResponse.ok(service.assess(request));}
}

