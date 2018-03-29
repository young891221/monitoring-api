package com.monitoring.api.rule;

import com.monitoring.api.domain.User;
import com.monitoring.api.domain.log.KaMoneyEventLog;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.monitoring.api.AcceptanceTest.TEST_ID;
import static com.monitoring.api.AcceptanceTest.TEST_NAME;
import static com.monitoring.api.domain.log.enums.KaMoneyEventType.RECEIVE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by young891221@gmail.com on 2018-03-27
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
public class RuleCTest {

    private User user = User.generate(TEST_ID, TEST_NAME);

    @Test
    public void RuleC의_데이터가_올바르게_생성되는가() {
        RuleC rule = RuleC.create(rightMockRoleCLogs());

        assertNotNull(rule.getTypeListConcurrentMap().get(RECEIVE));
    }

    @Test
    public void RuleC에_해당하는_검증이_올바른가() {
        RuleList ruleList = RuleList.generateByArray(RuleC.create(rightMockRoleCLogs()));
        RuleEngine ruleEngine = new RuleEngine(ruleList);

        assertEquals("RuleC", ruleEngine.run());
    }

    @Test
    public void RuleC_검증에서_받기가_2시간_이내가_아닐떄() {
        RuleList ruleList = RuleList.generateByArray(RuleC.create(notWithinTwoHour()));
        RuleEngine ruleEngine = new RuleEngine(ruleList);

        assertEquals("", ruleEngine.run());
    }

    @Test
    public void RuleC_검증에서_받기가_5만원_이상이_3회가_아닐때() {
        RuleList ruleList = RuleList.generateByArray(RuleC.create(incorrectCount()));
        RuleEngine ruleEngine = new RuleEngine(ruleList);

        assertEquals("", ruleEngine.run());
    }

    private List<KaMoneyEventLog> rightMockRoleCLogs() {
        List<KaMoneyEventLog> kaMoneyEventLogs = new ArrayList<>();
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 100000L, 200000L, LocalDateTime.now().minusMinutes(5), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 200000L, 300000L, LocalDateTime.now().minusMinutes(4), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 300000L, 400000L, LocalDateTime.now().minusMinutes(3), user));
        return kaMoneyEventLogs;
    }

    private List<KaMoneyEventLog> notWithinTwoHour() {
        List<KaMoneyEventLog> kaMoneyEventLogs = new ArrayList<>();
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 100000L, 200000L, LocalDateTime.now().minusHours(3), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 200000L, 300000L, LocalDateTime.now().minusMinutes(4), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 300000L, 400000L, LocalDateTime.now().minusMinutes(3), user));
        return kaMoneyEventLogs;
    }

    private List<KaMoneyEventLog> incorrectCount() {
        List<KaMoneyEventLog> kaMoneyEventLogs = new ArrayList<>();
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 100000L, 200000L, LocalDateTime.now().minusHours(3), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 200000L, 300000L, LocalDateTime.now().minusMinutes(4), user));
        return kaMoneyEventLogs;
    }

}
