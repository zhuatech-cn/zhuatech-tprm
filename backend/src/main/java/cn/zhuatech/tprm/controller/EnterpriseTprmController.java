/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.tprm.controller;
import cn.zhuatech.tprm.common.ApiResponse; import cn.zhuatech.tprm.service.EnterpriseTprmService; import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/enterprise/tprm") public class EnterpriseTprmController {
 private final EnterpriseTprmService service; /**
                                               * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                               */
public EnterpriseTprmController(EnterpriseTprmService service){this.service=service;}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @PostMapping("/assess-risk") ApiResponse<?> execute(@Valid @RequestBody EnterpriseTprmService.RiskRequest request){return ApiResponse.ok(service.assess(request));}
}

