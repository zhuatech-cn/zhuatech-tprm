/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.tprm.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class ThirdPartyAccessDecisionServiceTest {
    private final ThirdPartyAccessDecisionService service = new ThirdPartyAccessDecisionService();

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void grantsGovernedThirdPartyAccess() {
        var result = service.assess(new ThirdPartyAccessDecisionService.Request("TP-100", true, true, true,
                true, false, true, true, true, true, true, true));
        assertThat(result.decision()).isEqualTo(ThirdPartyAccessDecisionService.Decision.GRANT);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void routesAdministrativeGapsToRemediation() {
        var result = service.assess(new ThirdPartyAccessDecisionService.Request("TP-101", false, true, false,
                true, false, false, true, true, false, false, true));
        assertThat(result.actions()).hasSize(4);
        assertThat(result.decision()).isEqualTo(ThirdPartyAccessDecisionService.Decision.REMEDIATE);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void blocksHighRiskOrUncontrolledAccess() {
        var result = service.assess(new ThirdPartyAccessDecisionService.Request("", false, false, false,
                false, true, false, false, false, false, false, false));
        assertThat(result.blockers()).hasSize(7);
        assertThat(result.decision()).isEqualTo(ThirdPartyAccessDecisionService.Decision.BLOCKED);
    }
}
