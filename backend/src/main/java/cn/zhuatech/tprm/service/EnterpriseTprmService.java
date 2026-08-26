/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.tprm.service;
import jakarta.validation.Valid; import jakarta.validation.constraints.*; import org.springframework.stereotype.Service; import java.math.*; import java.util.*;
@Service public class EnterpriseTprmService {
 public RiskResult assess(@Valid RiskRequest r){
  BigDecimal inherent=BigDecimal.valueOf(r.likelihood()*r.impact()*4L); BigDecimal residual=inherent.multiply(BigDecimal.valueOf(100-r.controlEffectiveness())).divide(BigDecimal.valueOf(100),2,RoundingMode.HALF_UP);
  List<String> blockers=new ArrayList<>(); if(r.questionnaireCompletion()<100) blockers.add("风险问卷未完整提交"); if(r.criticalFindings()>0) blockers.add("存在重大未关闭发现"); if(r.overdueRemediations()>0) blockers.add("存在逾期整改事项"); if(!r.securityEvidenceVerified()) blockers.add("关键安全证据未核验");
  String level=residual.compareTo(BigDecimal.valueOf(60))>=0?"CRITICAL":residual.compareTo(BigDecimal.valueOf(35))>=0?"HIGH":residual.compareTo(BigDecimal.valueOf(15))>=0?"MEDIUM":"LOW";
  boolean eligible=blockers.isEmpty()&&!List.of("CRITICAL","HIGH").contains(level);
  return new RiskResult(r.thirdPartyNo(),inherent,residual,level,blockers,eligible,eligible?"ELIGIBLE":"REMEDIATION_REQUIRED");
 }
 public record RiskRequest(@NotBlank String thirdPartyNo,@Min(0) @Max(100) int questionnaireCompletion,@Min(1) @Max(5) int likelihood,@Min(1) @Max(5) int impact,@Min(0) @Max(100) int controlEffectiveness,@Min(0) int criticalFindings,@Min(0) int overdueRemediations,boolean securityEvidenceVerified){}
 public record RiskResult(String thirdPartyNo,BigDecimal inherentRisk,BigDecimal residualRisk,String riskLevel,List<String> blockers,boolean onboardingEligible,String decision){}
}

