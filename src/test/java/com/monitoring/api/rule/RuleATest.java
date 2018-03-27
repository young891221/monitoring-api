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
import static com.monitoring.api.domain.log.enums.KaMoneyEventType.OPEN;
import static com.monitoring.api.domain.log.enums.KaMoneyEventType.REMITTANCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by young891221@gmail.com on 2018-03-27
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
public class RuleATest {

    private User user = User.generate(TEST_ID, TEST_NAME);

    @Test
    public void RuleA의_데이터가_올바르게_생성되는가() {
        RuleA rule = RuleA.create(rightMockRoleALogs());

        assertNotNull(rule.getTypeListEnumMap().get(OPEN));
        assertNotNull(rule.getTypeListEnumMap().get(REMITTANCE));
    }

    @Test
    public void RuleA에_해당하는_검증이_올바른가() {
        RuleList ruleList = RuleList.generateByArray(RuleA.create(rightMockRoleALogs()));
        RuleEngine ruleEngine = new RuleEngine(ruleList);

        assertEquals("RuleA", ruleEngine.run());
    }

    @Test
    public void RuleA_검증에서_한시간이내_개설이_아닐때_반환이_올바른가() {
        RuleList ruleList = RuleList.generateByArray(
                RuleA.create(Collections.singletonList(KaMoneyEventLog.generateAtDate(OPEN, null, 100000L, LocalDateTime.now().minusMinutes(70), user))));
        RuleEngine ruleEngine = new RuleEngine(ruleList);

        assertEquals("", ruleEngine.run());
    }

    @Test
    public void RuleA_검증에서_20만원_충전_이후에_1000원으로_바뀐것만_체크하는가() {
        RuleList ruleList = RuleList.generateByArray(RuleA.create(incorrectChargeOrder()));
        RuleEngine ruleEngine = new RuleEngine(ruleList);

        assertEquals("", ruleEngine.run());
    }

    private List<KaMoneyEventLog> rightMockRoleALogs() {
        List<KaMoneyEventLog> kaMoneyEventLogs = new ArrayList<>();
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(OPEN, null, 100000L, LocalDateTime.now().minusMinutes(30), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(REMITTANCE, 100000L, 200000L, LocalDateTime.now().minusMinutes(20), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(REMITTANCE, 200000L, 1000L, LocalDateTime.now().minusMinutes(10), user));
        return kaMoneyEventLogs;
    }

    private List<KaMoneyEventLog> incorrectChargeOrder() {
        List<KaMoneyEventLog> kaMoneyEventLogs = new ArrayList<>();
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(OPEN, null, 100000L, LocalDateTime.now().minusMinutes(30), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(REMITTANCE, 100000L, 1000L, LocalDateTime.now().minusMinutes(20), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(REMITTANCE, 1000L, 200000L, LocalDateTime.now().minusMinutes(10), user));
        return kaMoneyEventLogs;
    }


}
