/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.tprm.domain;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class DomainCatalog {
    private final Map<String, WorkflowAction> actions = new LinkedHashMap<>();

    public DomainCatalog() {
        actions.put("SUBMIT", new WorkflowAction("SUBMIT", "提交评估", List.of("草稿"), "评估中", "OPERATOR"));
        actions.put("REMEDIATE", new WorkflowAction("REMEDIATE", "发起整改", List.of("评估中"), "整改中", "ADMIN"));
        actions.put("REASSESS", new WorkflowAction("REASSESS", "提交复评", List.of("整改中"), "复评中", "OPERATOR"));
        actions.put("APPROVE", new WorkflowAction("APPROVE", "批准准入", List.of("评估中","复评中"), "准入", "ADMIN"));
        actions.put("REJECT", new WorkflowAction("REJECT", "拒绝准入", List.of("评估中","复评中"), "拒绝", "ADMIN"));
        actions.put("OFFBOARD", new WorkflowAction("OFFBOARD", "完成退出", List.of("准入"), "已退出", "ADMIN"));
    }

    public String systemName() { return "知华科技第三方风险管理 TPRM"; }
    public String scene() { return "第三方准入、尽调、问卷、风险评估、整改、持续监控、合同与退出"; }
    public String initialStatus() { return "草稿"; }
    public String partyLabel() { return "第三方/责任部门"; }
    public String amountLabel() { return "风险敞口"; }
    public String quantityLabel() { return "风险事项数"; }
    public String dueLabel() { return "复评日期"; }

    public List<ModuleDefinition> modules() {
        return List.of(
            new ModuleDefinition("VENDOR", "第三方主数据", "维护供应商、合作方、分包商与服务关系"),
            new ModuleDefinition("INTAKE", "准入申请", "采集服务范围、数据接触、地域和关键性"),
            new ModuleDefinition("QUESTIONNAIRE", "风险问卷", "按风险域动态生成问卷并验证完整性"),
            new ModuleDefinition("DUE_DILIGENCE", "尽职调查", "核验资质、制裁、财务、隐私和安全证据"),
            new ModuleDefinition("RISK", "风险评估", "计算固有风险、控制有效性和剩余风险"),
            new ModuleDefinition("CONTROL", "控制评审", "映射控制要求、证据、例外和补偿措施"),
            new ModuleDefinition("ISSUE", "整改事项", "管理责任人、期限、证据和逾期升级"),
            new ModuleDefinition("MONITORING", "持续监控", "接入舆情、评级、漏洞与履约异常信号"),
            new ModuleDefinition("CONTRACT", "合同控制", "管理安全条款、审计权、SLA 和退出义务"),
            new ModuleDefinition("OFFBOARDING", "退出管理", "完成权限回收、数据销毁和最终证明")
        );
    }

    public Map<String, WorkflowAction> actions() { return Collections.unmodifiableMap(actions); }

    public record ModuleDefinition(String code, String name, String description) {}
    public record WorkflowAction(String code, String label, List<String> from, String to, String requiredRole) {}
}
