package com.monitoring.api;

import com.monitoring.api.domain.KaMoney;
import com.monitoring.api.domain.User;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
public class MoneyLogTest {
    private static final String ID = "young891221";
    private static final String NAME = "김영재";
    private static User user;

    @Before
    public void init() {
        user = User.generateWithKaAccount(ID, NAME, new KaMoney());
    }

    @Test
    public void 서비스_계좌_개설이_완료되었는가() {

    }
}
