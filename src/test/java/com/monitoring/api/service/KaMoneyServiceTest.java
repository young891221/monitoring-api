package com.monitoring.api.service;

import com.monitoring.api.AcceptanceTest;
import com.monitoring.api.domain.Account;
import com.monitoring.api.domain.KaMoney;
import com.monitoring.api.domain.User;
import com.monitoring.api.domain.enums.BankType;
import com.monitoring.api.facade.KaMoneyFacade;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by young891221@gmail.com on 2018-03-26
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
public class KaMoneyServiceTest extends AcceptanceTest {

    private User user;

    @Autowired
    private KaMoneyFacade kaMoneyFacade;

    @Autowired
    private KaMoneyService kaMoneyService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private KaMoneyEventLogService kaMoneyEventLogService;

    @Before
    public void init() {
        user = userService.createUser(User.generate(TEST_ID, TEST_NAME));
    }

    @Test
    public void 계좌_개설이_정상적으로_동작하는가() {
        Account account = accountService.createAccount(new Account(BankType.KB, 1234L, 1000L));
        kaMoneyFacade.openKaMoney(user, account);

        assertNotNull(kaMoneyEventLogService.findByUser(user));
    }

    @Test
    public void 충전이_정상적으로_이루어지는가() {
        Account account = accountService.createAccount(new Account(BankType.KB, 1234L, 300000L));
        kaMoneyFacade.openKaMoney(user, account);
        kaMoneyFacade.chargeKaMoney(user, 200000L);
        KaMoney kaMoney = kaMoneyService.findByUser(user);

        assertThat(kaMoney.getMoney(), is(200000L));
    }

    @Test
    public void 송금이_정상적으로_이루어지는가() {
        long targetMoney = 10000L;

        Account fromAccount = accountService.createAccount(new Account(BankType.KB, 1234L, targetMoney));
        kaMoneyFacade.openKaMoney(user, fromAccount);
        kaMoneyFacade.chargeKaMoney(user, targetMoney);

        User toUser = userService.createUser(User.generate("toUser", "test"));
        Account toAccount = accountService.createAccount(new Account(BankType.KB, 1235L, 0L));
        kaMoneyFacade.openKaMoney(toUser, toAccount);

        kaMoneyFacade.remittanceKaMoney(user, toUser, targetMoney);

        assertThat(kaMoneyService.findByUser(user).getMoney(), is(0L));
        assertThat(kaMoneyService.findByUser(toUser).getMoney(), is(targetMoney));
    }
}
