package com.monitoring.api.rule;

import com.monitoring.api.domain.User;
import com.monitoring.api.domain.log.KaMoneyEventLog;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.monitoring.api.AcceptanceTest.TEST_ID;
import static com.monitoring.api.AcceptanceTest.TEST_NAME;
import static com.monitoring.api.domain.log.enums.KaMoneyEventType.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by young891221@gmail.com on 2018-03-27
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
public class RuleBTest {

    private User user = User.generate(TEST_ID, TEST_NAME);

    @Test
    public void RuleB의_데이터가_올바르게_생성되는가() {
        RuleB rule = RuleB.create(rightMockRoleBLogs());

        assertNotNull(rule.getTypeListConcurrentMap().get(OPEN));
        assertNotNull(rule.getTypeListConcurrentMap().get(RECEIVE));
    }

    @Test
    public void RuleB에_해당하는_검증이_올바른가() {
        RuleList ruleList = RuleList.generateByArray(RuleB.create(rightMockRoleBLogs()));
        RuleEngine ruleEngine = new RuleEngine(ruleList);

        assertEquals("RuleB", ruleEngine.run());
    }

    @Test
    public void RuleB_검증에서_7일이내_개설이_아닐때_반환이_올바른가() {
        RuleList ruleList = RuleList.generateByArray(
                RuleB.create(Collections.singletonList(KaMoneyEventLog.generateAtDate(OPEN, null, 100000L, LocalDateTime.now().minusDays(8), user))));
        RuleEngine ruleEngine = new RuleEngine(ruleList);

        assertEquals("", ruleEngine.run());
    }

    @Test
    public void RuleB_검증에서_받기가_10만원_이상이_아닐때() {
        RuleList ruleList = RuleList.generateByArray(RuleB.create(incorrectTenThousand()));
        RuleEngine ruleEngine = new RuleEngine(ruleList);

        assertEquals("", ruleEngine.run());
    }

    @Test
    public void RuleB_검증에서_받기가_10만원_이상이_5회가_아닐때() {
        RuleList ruleList = RuleList.generateByArray(RuleB.create(incorrectCount()));
        RuleEngine ruleEngine = new RuleEngine(ruleList);

        assertEquals("", ruleEngine.run());
    }

    private List<KaMoneyEventLog> rightMockRoleBLogs() {
        List<KaMoneyEventLog> kaMoneyEventLogs = new ArrayList<>();
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(OPEN, null, 100000L, LocalDateTime.now().minusDays(6), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 100000L, 200000L, LocalDateTime.now().minusDays(5), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 200000L, 300000L, LocalDateTime.now().minusDays(4), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 300000L, 400000L, LocalDateTime.now().minusDays(3), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 400000L, 500000L, LocalDateTime.now().minusDays(2), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 500000L, 600000L, LocalDateTime.now().minusDays(1), user));
        return kaMoneyEventLogs;
    }

    private List<KaMoneyEventLog> incorrectTenThousand() {
        List<KaMoneyEventLog> kaMoneyEventLogs = new ArrayList<>();
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(OPEN, null, 100000L, LocalDateTime.now().minusDays(6), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 100000L, 150000L, LocalDateTime.now().minusDays(5), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 150000L, 300000L, LocalDateTime.now().minusDays(4), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 300000L, 400000L, LocalDateTime.now().minusDays(3), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 400000L, 500000L, LocalDateTime.now().minusDays(2), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 500000L, 600000L, LocalDateTime.now().minusDays(1), user));
        return kaMoneyEventLogs;
    }

    private List<KaMoneyEventLog> incorrectCount() {
        List<KaMoneyEventLog> kaMoneyEventLogs = new ArrayList<>();
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(OPEN, null, 100000L, LocalDateTime.now().minusDays(6), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 100000L, 150000L, LocalDateTime.now().minusDays(5), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 150000L, 300000L, LocalDateTime.now().minusDays(4), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 300000L, 400000L, LocalDateTime.now().minusDays(3), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 400000L, 500000L, LocalDateTime.now().minusDays(2), user));
        return kaMoneyEventLogs;
    }
}
