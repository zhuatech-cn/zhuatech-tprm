/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.tprm.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class ThirdPartyAccessDecisionServiceTest {
    private final ThirdPartyAccessDecisionService service = new ThirdPartyAccessDecisionService();

    @Test void grantsGovernedThirdPartyAccess() {
        var result = service.assess(new ThirdPartyAccessDecisionService.Request("TP-100", true, true, true,
                true, false, true, true, true, true, true, true));
        assertThat(result.decision()).isEqualTo(ThirdPartyAccessDecisionService.Decision.GRANT);
    }

    @Test void routesAdministrativeGapsToRemediation() {
        var result = service.assess(new ThirdPartyAccessDecisionService.Request("TP-101", false, true, false,
                true, false, false, true, true, false, false, true));
        assertThat(result.actions()).hasSize(4);
        assertThat(result.decision()).isEqualTo(ThirdPartyAccessDecisionService.Decision.REMEDIATE);
    }

    @Test void blocksHighRiskOrUncontrolledAccess() {
        var result = service.assess(new ThirdPartyAccessDecisionService.Request("", false, false, false,
                false, true, false, false, false, false, false, false));
        assertThat(result.blockers()).hasSize(7);
        assertThat(result.decision()).isEqualTo(ThirdPartyAccessDecisionService.Decision.BLOCKED);
    }
}
