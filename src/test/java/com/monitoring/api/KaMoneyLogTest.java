package com.monitoring.api;

import com.monitoring.api.domain.KaMoney;
import com.monitoring.api.domain.User;
import com.monitoring.api.domain.log.KaMoneyEventLog;
import com.monitoring.api.domain.log.enums.KaMoneyEventType;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
public class KaMoneyLogTest {
    private static final String ID = "young891221";
    private static final String NAME = "김영재";
    private static User user;
    private static KaMoney kaMoney;

    @Before
    public void init() {
        user = User.generate(ID, NAME);
    }

    @Test
    public void 서비스_계좌_개설이_완료되었는가() {
        kaMoney = KaMoney.generateRandomNumber(user);
        KaMoneyEventLog kaMoneyEventLog = KaMoneyEventLog.openKaMoney(kaMoney);

        assertEquals(KaMoneyEventType.OPEN, kaMoneyEventLog.getKaMoneyEventType());
    }
}
