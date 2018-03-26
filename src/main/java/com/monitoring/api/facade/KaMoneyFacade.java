package com.monitoring.api.facade;

import com.monitoring.api.domain.Account;
import com.monitoring.api.domain.KaMoney;
import com.monitoring.api.domain.User;
import com.monitoring.api.domain.log.KaMoneyEventLog;
import com.monitoring.api.service.AccountService;
import com.monitoring.api.service.KaMoneyEventLogService;
import com.monitoring.api.service.KaMoneyService;

import org.springframework.stereotype.Component;

/**
 * Created by young891221@gmail.com on 2018-03-26
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
@Component
public class KaMoneyFacade {

    private KaMoneyService kaMoneyService;
    private KaMoneyEventLogService kaMoneyEventLogService;
    private AccountService accountService;

    public KaMoneyFacade(KaMoneyService kaMoneyService, KaMoneyEventLogService kaMoneyEventLogService, AccountService accountService) {
        this.kaMoneyService = kaMoneyService;
        this.kaMoneyEventLogService = kaMoneyEventLogService;
        this.accountService = accountService;
    }

    public void openKaMoney(User user) {
        KaMoney kaMoney = kaMoneyService.openKaMoney(user);
        Account account = accountService.createAccount();
        kaMoney = kaMoney.linkAccount(account);
        kaMoneyEventLogService.saveLog(KaMoneyEventLog.openKaMoney(kaMoney));
    }

    public void chargeKaMoney(User user, long money) {
        KaMoney kaMoney = kaMoneyService.findByUser(user);
        kaMoney.chargeMoney(money);
    }
}
