package com.monitoring.api.rule;

import com.monitoring.api.domain.User;
import com.monitoring.api.domain.log.KaMoneyEventLog;
import com.monitoring.api.service.KaMoneyEventLogService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
@RunWith(MockitoJUnitRunner.class)
public class RuleTest {

    private static final LocalDateTime TWOHOURBEFORE = LocalDateTime.now().minusHours(2);
    private static final LocalDateTime NOW = LocalDateTime.now();
    private User user = User.generate(TEST_ID, TEST_NAME);

    @Mock
    private KaMoneyEventLogService kaMoneyEventLogService;

    @Test
    public void RuleA의_데이터가_올바르게_생성되는가() {
        RuleA rule = RuleA.create(mockRoleALogs(), user);

        assertNotNull(rule.getTypeListEnumMap().get(OPEN));
        assertNotNull(rule.getTypeListEnumMap().get(REMITTANCE));
    }

    @Test
    public void RuleEngine_실행결과가_올바른가() {
        //when(kaMoneyEventLogService.findByCreatedDateAfterAndKaMoneyEventTypeAndUser(OPEN, user)).thenReturn(mockRoleALogs());
        //when(kaMoneyEventLogService.findByCreatedDateAfterAndKaMoneyEventTypeAndUser(REMITTANCE, user)).thenReturn(mockRemittanceLogs());
        RuleList ruleList = RuleList.generateByArray(RuleA.create(mockRoleALogs(), user));
        RuleEngine ruleEngine = new RuleEngine(ruleList);

        assertEquals("RuleA", ruleEngine.run());
    }

    private List<KaMoneyEventLog> mockRoleALogs() {
        List<KaMoneyEventLog> kaMoneyEventLogs = new ArrayList<>();
        kaMoneyEventLogs.add(new KaMoneyEventLog(OPEN, null, 10000L, user));
        kaMoneyEventLogs.add(new KaMoneyEventLog(REMITTANCE, 10000L, 5000L, user));
        kaMoneyEventLogs.add(new KaMoneyEventLog(REMITTANCE, 5000L, 1000L, user));
        return kaMoneyEventLogs;
    }


}
