package com.monitoring.api.facade;

import com.monitoring.api.domain.Account;
import com.monitoring.api.domain.KaMoney;
import com.monitoring.api.domain.User;
import com.monitoring.api.domain.log.KaMoneyEventLog;
import com.monitoring.api.rule.RuleA;
import com.monitoring.api.rule.RuleEngine;
import com.monitoring.api.rule.RuleList;
import com.monitoring.api.service.KaMoneyEventLogService;
import com.monitoring.api.service.KaMoneyService;
import com.monitoring.api.service.RuleLogService;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
    private RuleLogService ruleLogService;

    public KaMoneyFacade(KaMoneyService kaMoneyService, KaMoneyEventLogService kaMoneyEventLogService, RuleLogService ruleLogService) {
        this.kaMoneyService = kaMoneyService;
        this.kaMoneyEventLogService = kaMoneyEventLogService;
        this.ruleLogService = ruleLogService;
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

        List<KaMoneyEventLog> kaMoneyEventLogs = kaMoneyEventLogService.findByCreatedDateAfterAndUser(LocalDateTime.now().minusHours(1), afterKaMoney.getUser());
        RuleList ruleList = RuleList.generateByArray(RuleA.create(kaMoneyEventLogs, afterKaMoney.getUser()));
        RuleEngine ruleEngine = new RuleEngine(ruleList);
        ruleLogService.saveRules(ruleEngine.run(), afterKaMoney.getUser());
    }
}
