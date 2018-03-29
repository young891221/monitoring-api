package com.monitoring.api.rule;

import com.monitoring.api.AcceptanceTest;
import com.monitoring.api.dto.RuleLogDto;
import com.monitoring.api.service.RuleLogService;
import com.monitoring.api.test.TestIntegrationModule;
import com.monitoring.api.test.TestMockModule;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by young891221@gmail.com on 2018-03-27
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
public class RuleIntegrationTest extends AcceptanceTest {

    private TestIntegrationModule testIntegrationModule;
    private TestMockModule testMockModule;

    @Before
    public void init() {
        testIntegrationModule = new TestIntegrationModule(userService, accountService, kaMoneyFacade);
        testMockModule = new TestMockModule(userService, ruleLogService);
    }

    @Test
    public void RuleA_통합_테스트() {
        testMockModule.testRoleAProcess();
        RuleLogDto ruleLogDto = ruleLogFacade.findRuleLogByUserId(1);

        assertTrue(ruleLogDto.isFraud());
        assertEquals("RuleA", ruleLogDto.getRule());
    }

    @Test
    public void RuleB_통합_테스트() {
        testMockModule.testRoleBProcess();
        RuleLogDto ruleLogDto = ruleLogFacade.findRuleLogByUserId(userService.findLastCreatedUser().getIdx());

        assertTrue(ruleLogDto.isFraud());
        assertEquals("RuleB", ruleLogDto.getRule());
    }

    @Test
    public void RuleC_통합_테스트() {
        testMockModule.testRoleCProcess();
        RuleLogDto ruleLogDto = ruleLogFacade.findRuleLogByUserId(userService.findLastCreatedUser().getIdx());

        assertTrue(ruleLogDto.isFraud());
        assertEquals("RuleC", ruleLogDto.getRule());
    }
}
