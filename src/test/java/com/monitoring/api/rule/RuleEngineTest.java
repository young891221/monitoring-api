package com.monitoring.api.rule;

import com.monitoring.api.domain.User;
import com.monitoring.api.domain.log.KaMoneyEventLog;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.monitoring.api.AcceptanceTest.TEST_ID;
import static com.monitoring.api.AcceptanceTest.TEST_NAME;
import static com.monitoring.api.domain.log.enums.KaMoneyEventType.OPEN;
import static com.monitoring.api.domain.log.enums.KaMoneyEventType.RECEIVE;
import static org.junit.Assert.assertEquals;

/**
 * Created by young891221@gmail.com on 2018-03-28
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
public class RuleEngineTest {

    private User user = User.generate(TEST_ID, TEST_NAME);

    @Test
    public void RuleB_RuleC를_동시에_어겼을_때_출력형식이_올바른가() {
        RuleList ruleList = RuleList.generateByArray(new RuleB(), new RuleC());
        RuleEngine ruleEngine = RuleEngine.create(ruleList, incorrectRuleBAndRuleC());

        assertEquals("RuleB, RuleC", ruleEngine.run().get());
    }

    @Test
    public void RuleC_RuleB_순서를_바꿔도_실행결과는_같은가() throws Exception {
        RuleList ruleList = RuleList.generateByArray(new RuleC(), new RuleB());
        RuleEngine ruleEngine = RuleEngine.create(ruleList, incorrectRuleBAndRuleC());

        assertEquals("RuleB, RuleC", ruleEngine.run().get());
    }

    @Test
    public void RuleC_중복을_제거하는가() throws Exception {
        RuleList ruleList = RuleList.generateByArray(new RuleC(), new RuleC());
        RuleEngine ruleEngine = RuleEngine.create(ruleList, incorrectRuleBAndRuleC());

        assertEquals("RuleC", ruleEngine.run().get());
    }

    @Test
    public void RuleEngine_맵핑_성능_테스트() {
        RuleList ruleList = RuleList.generateByArray(new RuleB(), new RuleC());

        List<KaMoneyEventLog> kaMoneyEventLogs = new ArrayList<>();
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(OPEN, null, 100000L, LocalDateTime.now().minusDays(1), user));
        for(int i = 0; i < 1000000; i++) {
            kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 100000L, 200000L, LocalDateTime.now().minusMinutes(5), user));
        }

        long start = System.currentTimeMillis();
        RuleEngine.create(ruleList, kaMoneyEventLogs);
        System.out.print(String.valueOf(System.currentTimeMillis() - start));
    }

    private List<KaMoneyEventLog> incorrectRuleBAndRuleC() {
        List<KaMoneyEventLog> kaMoneyEventLogs = new ArrayList<>();
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(OPEN, null, 100000L, LocalDateTime.now().minusDays(1), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 100000L, 200000L, LocalDateTime.now().minusMinutes(5), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 200000L, 300000L, LocalDateTime.now().minusMinutes(4), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 300000L, 400000L, LocalDateTime.now().minusMinutes(3), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 400000L, 500000L, LocalDateTime.now().minusMinutes(2), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 500000L, 600000L, LocalDateTime.now().minusMinutes(1), user));
        return kaMoneyEventLogs;
    }
}
