package com.monitoring.api.facade;

import com.monitoring.api.domain.Account;
import com.monitoring.api.domain.KaMoney;
import com.monitoring.api.domain.User;
import com.monitoring.api.domain.log.KaMoneyEventLog;
import com.monitoring.api.rule.RuleA;
import com.monitoring.api.rule.RuleEngine;
import com.monitoring.api.rule.RuleList;
import com.monitoring.api.service.AccountService;
import com.monitoring.api.service.KaMoneyEventLogService;
import com.monitoring.api.service.KaMoneyService;

import org.springframework.stereotype.Component;

import java.util.List;

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

    public void openKaMoney(final User user, final Account account) {
        KaMoney kaMoney = kaMoneyService.openKaMoney(user, account);
        kaMoneyEventLogService.saveLog(KaMoneyEventLog.openKaMoney(kaMoney));
    }

    public void chargeKaMoney(final User user, final long money) {
        KaMoney beforeKaMoney = kaMoneyService.findByUser(user);
        KaMoney afterKaMoney = kaMoneyService.chargeMoney(user, money);
        kaMoneyEventLogService.saveLog(KaMoneyEventLog.cargeKaMoney(beforeKaMoney, afterKaMoney));
    }

    public void receiveKaMoney(final User toUser, final long money) {
        KaMoney beforeKaMoney = kaMoneyService.findByUser(toUser);
        KaMoney afterKaMoney = kaMoneyService.receiveKaMoney(beforeKaMoney, money);
        kaMoneyEventLogService.saveLog(KaMoneyEventLog.receiveKaMoney(beforeKaMoney, afterKaMoney));
    }

    public void remittanceKaMoney(final User fromUser, final User toUser, final long money) {
        KaMoney beforeKaMoney = kaMoneyService.findByUser(fromUser);
        KaMoney afterKaMoney = kaMoneyService.remittanceMoney(beforeKaMoney, money);
        receiveKaMoney(toUser, money);
        kaMoneyEventLogService.saveLog(KaMoneyEventLog.remittanceKaMoney(beforeKaMoney, afterKaMoney));

        RuleList ruleList = RuleList.generateByArray(RuleA.create(kaMoneyEventLogService));
        RuleEngine ruleEngine = new RuleEngine(ruleList);
        List<String> notValidRules = ruleEngine.run(); //TODO: RuleService 작성하여 RuleLog에 저장
    }
}
