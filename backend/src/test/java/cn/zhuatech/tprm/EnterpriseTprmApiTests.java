/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.tprm;
import org.junit.jupiter.api.Test; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc; import org.springframework.http.MediaType; import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic; import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post; import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest @AutoConfigureMockMvc class EnterpriseTprmApiTests { @Autowired MockMvc mvc;

 @Test void controlledLowRiskThirdPartyIsEligible() throws Exception {mvc.perform(post("/api/enterprise/tprm/assess-risk").with(httpBasic("operator","operator123")).contentType(MediaType.APPLICATION_JSON).content("""
 {"thirdPartyNo":"TP-001","questionnaireCompletion":100,"likelihood":3,"impact":3,"controlEffectiveness":80,"criticalFindings":0,"overdueRemediations":0,"securityEvidenceVerified":true}
 """)).andExpect(status().isOk()).andExpect(jsonPath("$.data.residualRisk").value(7.20)).andExpect(jsonPath("$.data.riskLevel").value("LOW")).andExpect(jsonPath("$.data.onboardingEligible").value(true));}
 @Test void findingsAndIncompleteEvidenceRequireRemediation() throws Exception {mvc.perform(post("/api/enterprise/tprm/assess-risk").with(httpBasic("operator","operator123")).contentType(MediaType.APPLICATION_JSON).content("""
 {"thirdPartyNo":"TP-002","questionnaireCompletion":80,"likelihood":5,"impact":5,"controlEffectiveness":20,"criticalFindings":1,"overdueRemediations":2,"securityEvidenceVerified":false}
 """)).andExpect(status().isOk()).andExpect(jsonPath("$.data.riskLevel").value("CRITICAL")).andExpect(jsonPath("$.data.blockers.length()").value(4)).andExpect(jsonPath("$.data.decision").value("REMEDIATION_REQUIRED"));}
}

