package com.monitoring.api.rule;

import com.monitoring.api.AcceptanceTest;
import com.monitoring.api.dto.RuleLogDto;
import com.monitoring.api.facade.KaMoneyFacade;
import com.monitoring.api.facade.RuleLogFacade;
import com.monitoring.api.repository.KaMoneyEventLogRepository;
import com.monitoring.api.repository.RuleLogRepository;
import com.monitoring.api.service.AccountService;
import com.monitoring.api.service.UserService;
import com.monitoring.api.test.TestModule;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by young891221@gmail.com on 2018-03-27
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
public class RuleIntegrationTest extends AcceptanceTest {

    private TestModule testModule;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private KaMoneyFacade kaMoneyFacade;

    @Autowired
    private RuleLogFacade ruleLogFacade;

    @Autowired
    private KaMoneyEventLogRepository kaMoneyEventLogRepository;

    @Autowired
    private RuleLogRepository ruleLogRepository;

    @Before
    public void init() {
         testModule = new TestModule();
         kaMoneyEventLogRepository.deleteAll();
         ruleLogRepository.deleteAll();
    }

    @Test
    public void RuleA_통합_테스트() {
        testModule.testRoleAProcess(userService, accountService, kaMoneyFacade);
        RuleLogDto ruleLogDto = ruleLogFacade.findRuleLogByUserId(1);

        assertTrue(ruleLogDto.isFraud());
        assertEquals("RuleA", ruleLogDto.getRule());
    }

    @Test
    public void RuleB_통합_테스트() {
        testModule.testRoleBProcess(userService, accountService, kaMoneyFacade);
        RuleLogDto ruleLogDto = ruleLogFacade.findRuleLogByUserId(1);

        assertTrue(ruleLogDto.isFraud());
        assertEquals("RuleB, RuleC", ruleLogDto.getRule());
    }

    @Test
    public void RuleC_통합_테스트() {

    }
}
