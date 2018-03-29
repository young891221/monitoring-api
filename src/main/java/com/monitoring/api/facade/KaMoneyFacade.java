package com.monitoring.api.facade;

import com.monitoring.api.domain.Account;
import com.monitoring.api.domain.KaMoney;
import com.monitoring.api.domain.User;
import com.monitoring.api.domain.log.KaMoneyEventLog;
import com.monitoring.api.service.KaMoneyEventLogService;
import com.monitoring.api.service.KaMoneyService;
import com.monitoring.api.service.RuleLogService;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by young891221@gmail.com on 2018-03-26
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
@Component
@Transactional
public class KaMoneyFacade {

    private KaMoneyService kaMoneyService;
    private KaMoneyEventLogService kaMoneyEventLogService;
    private RuleLogService ruleLogService;

    public KaMoneyFacade(KaMoneyService kaMoneyService, KaMoneyEventLogService kaMoneyEventLogService, RuleLogService ruleLogService) {
        this.kaMoneyService = kaMoneyService;
        this.kaMoneyEventLogService = kaMoneyEventLogService;
        this.ruleLogService = ruleLogService;
    }

    /**
     * 계좌 개설 기능
     * @param user 계좌를 개설할 User
     * @param account KaMoney에 연결할 계좌
     */
    public KaMoney openKaMoney(final User user, final Account account) {
        KaMoney kaMoney = kaMoneyService.openKaMoney(user, account);
        kaMoneyEventLogService.saveLog(KaMoneyEventLog.openKaMoney(kaMoney));
        return kaMoney;
    }

    /**
     * 충전 기능
     * @param user 충전할 User
     * @param money 충전 금액
     */
    public void chargeKaMoney(final User user, final long money) {
        KaMoney kaMoney = kaMoneyService.findByUser(user);
        kaMoney = kaMoney.chargeMoney(money);
        kaMoneyEventLogService.saveLog(KaMoneyEventLog.cargeKaMoney(kaMoney));
    }

    /**
     * 받기 기능
     * @param toUser 받기 기능으로 돈을 받을 User
     * @param money 받을 금액
     */
    public void receiveKaMoney(final User toUser, final long money) {
        KaMoney kaMoney = kaMoneyService.findByUser(toUser);
        kaMoney = kaMoney.receiveMoney(money);
        kaMoneyEventLogService.saveLog(KaMoneyEventLog.receiveKaMoney(kaMoney));

        List<KaMoneyEventLog> ruleBLogs = kaMoneyEventLogService.findByCreatedDateAfterAndUser(LocalDateTime.now().minusDays(7), kaMoney.getUser());
        ruleLogService.receiveKaMoneyRuleCheck(ruleBLogs, kaMoney);
    }

    /**
     * 송금 기능
     * @param fromUser 송금할 User
     * @param toUser 받을 User
     * @param money 송금할 금액
     */
    public void remittanceKaMoney(final User fromUser, final User toUser, final long money) {
        KaMoney kaMoney = kaMoneyService.findByUser(fromUser);
        kaMoney = kaMoney.remittanceMoney(money);
        receiveKaMoney(toUser, money);
        kaMoneyEventLogService.saveLog(KaMoneyEventLog.remittanceKaMoney(kaMoney));

        List<KaMoneyEventLog> kaMoneyEventLogs = kaMoneyEventLogService.findByCreatedDateAfterAndUser(LocalDateTime.now().minusHours(1), kaMoney.getUser());
        ruleLogService.remittanceKaMoneyRuleCheck(kaMoneyEventLogs, kaMoney);
    }
}
