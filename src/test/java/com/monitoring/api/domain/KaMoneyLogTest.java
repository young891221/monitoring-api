package com.monitoring.api.domain;

import com.monitoring.api.domain.log.KaMoneyEventLog;
import com.monitoring.api.domain.log.enums.KaMoneyEventType;

import org.junit.Before;
import org.junit.Test;

import static com.monitoring.api.AcceptanceTest.TEST_ID;
import static com.monitoring.api.AcceptanceTest.TEST_NAME;
import static org.junit.Assert.assertEquals;

/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
public class KaMoneyLogTest {

    private User user;
    private KaMoney kaMoney;

    @Before
    public void init() {
        user = User.generate(TEST_ID, TEST_NAME);
    }

    @Test
    public void 서비스_계좌_개설_타입이_올바른가() {
        kaMoney = KaMoney.generateRandomNumber(user);
        KaMoneyEventLog kaMoneyEventLog = KaMoneyEventLog.openKaMoney(kaMoney);

        assertEquals(KaMoneyEventType.OPEN, kaMoneyEventLog.getKaMoneyEventType());
    }
}
