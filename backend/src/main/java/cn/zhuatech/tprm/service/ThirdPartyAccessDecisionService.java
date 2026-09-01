/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.tprm.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ThirdPartyAccessDecisionService {
    public Result assess(Request request) {
        var blockers = new ArrayList<String>();
        var actions = new ArrayList<String>();
        if (request.thirdPartyId() == null || request.thirdPartyId().isBlank()) blockers.add("第三方编号不能为空");
        if (!request.sanctionsClear()) blockers.add("制裁与黑名单筛查未通过");
        if (!request.securityAssessmentPassed()) blockers.add("信息安全评估未通过");
        if (request.criticalFindingsOpen() && (!request.compensatingControlsApproved() || !request.riskAccepted())) {
            blockers.add("重大风险未关闭且缺少补偿控制或风险接受");
        }
        if (!request.leastPrivilegeDefined()) blockers.add("未定义最小权限范围");
        if (!request.accessExpirySet()) blockers.add("第三方访问未设置到期时间");
        if (!request.auditReady()) blockers.add("第三方访问审计证据不完整");
        if (!request.dueDiligenceComplete()) actions.add("完成第三方尽职调查");
        if (!request.dataProcessingAgreement()) actions.add("签署数据处理协议");
        if (!request.businessOwnerApproved()) actions.add("取得业务责任人批准");
        if (!request.riskAccepted()) actions.add("记录剩余风险接受结论");
        var decision = !blockers.isEmpty() ? Decision.BLOCKED : actions.isEmpty() ? Decision.GRANT : Decision.REMEDIATE;
        return new Result(decision, List.copyOf(blockers), List.copyOf(actions));
    }

    public enum Decision { GRANT, REMEDIATE, BLOCKED }
    public record Request(String thirdPartyId, boolean dueDiligenceComplete, boolean sanctionsClear,
                          boolean dataProcessingAgreement, boolean securityAssessmentPassed,
                          boolean criticalFindingsOpen, boolean businessOwnerApproved,
                          boolean leastPrivilegeDefined, boolean accessExpirySet,
                          boolean compensatingControlsApproved, boolean riskAccepted,
                          boolean auditReady) {}
    public record Result(Decision decision, List<String> blockers, List<String> actions) {}
}
