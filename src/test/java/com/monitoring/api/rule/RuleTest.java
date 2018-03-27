package com.monitoring.api.rule;

import com.monitoring.api.domain.User;
import com.monitoring.api.domain.log.KaMoneyEventLog;
import com.monitoring.api.service.KaMoneyEventLogService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.monitoring.api.AcceptanceTest.TEST_ID;
import static com.monitoring.api.AcceptanceTest.TEST_NAME;
import static com.monitoring.api.domain.log.enums.KaMoneyEventType.OPEN;
import static com.monitoring.api.domain.log.enums.KaMoneyEventType.REMITTANCE;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Created by young891221@gmail.com on 2018-03-27
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
@RunWith(MockitoJUnitRunner.class)
public class RuleTest {

    private User user = User.generate(TEST_ID, TEST_NAME);

    @Mock
    private KaMoneyEventLogService kaMoneyEventLogService;

    @Test
    public void RuleA의_데이터가_올바르게_생성되는가() {
        when(kaMoneyEventLogService.findByKaMoneyEventTypeAndUser(OPEN, user)).thenReturn(mockOpenLogs());
        when(kaMoneyEventLogService.findByKaMoneyEventTypeAndUser(REMITTANCE, user)).thenReturn(mockRemittanceLogs());
        RuleA rule = RuleA.create(kaMoneyEventLogService, user);

        assertNotNull(rule.getTypeListEnumMap().get(OPEN));
        assertNotNull(rule.getTypeListEnumMap().get(REMITTANCE));
    }

    private List<KaMoneyEventLog> mockOpenLogs() {
        return Collections.singletonList(new KaMoneyEventLog(OPEN, null, 10000L, user));
    }

    private List<KaMoneyEventLog> mockRemittanceLogs() {
        List<KaMoneyEventLog> kaMoneyEventLogs = new ArrayList<>();
        kaMoneyEventLogs.add(new KaMoneyEventLog(REMITTANCE, 10000L, 5000L, user));
        kaMoneyEventLogs.add(new KaMoneyEventLog(REMITTANCE, 5000L, 1000L, user));
        return kaMoneyEventLogs;
    }

}
