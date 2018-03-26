package com.monitoring.api.service;

import com.monitoring.api.AcceptanceTest;
import com.monitoring.api.domain.User;
import com.monitoring.api.facade.KaMoneyFacade;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

/**
 * Created by young891221@gmail.com on 2018-03-26
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
public class KaMoneyServiceTest extends AcceptanceTest {

    private User user;

    @Autowired
    private UserService userService;

    @Autowired
    private KaMoneyFacade kaMoneyFacade;

    @Autowired
    private KaMoneyEventLogService kaMoneyEventLogService;

    @Before
    public void init() {
        user = userService.createUser(User.generate(TEST_ID, TEST_NAME));
    }

    @Test
    public void 계좌_개설이_정상적으로_동작하는가() {
        kaMoneyFacade.openKaMoney(user);
        assertNotNull(kaMoneyEventLogService.findByUser(user));
    }

    @Test
    public void 충전이_정상적으로_이루어지는가() {

    }
}
