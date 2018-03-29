package com.monitoring.api.test;

import com.monitoring.api.domain.User;
import com.monitoring.api.domain.log.KaMoneyEventLog;
import com.monitoring.api.service.RuleLogService;
import com.monitoring.api.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.monitoring.api.domain.log.enums.KaMoneyEventType.CHARGE;
import static com.monitoring.api.domain.log.enums.KaMoneyEventType.OPEN;
import static com.monitoring.api.domain.log.enums.KaMoneyEventType.RECEIVE;
import static com.monitoring.api.domain.log.enums.KaMoneyEventType.REMITTANCE;

/**
 * Created by young891221@gmail.com on 2018-03-29
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
public class TestMockModule {

    private UserService userService;
    private RuleLogService ruleLogService;

    public TestMockModule(UserService userService, RuleLogService ruleLogService) {
        this.userService = userService;
        this.ruleLogService = ruleLogService;
    }

    /**
     * RoleA 테스트 순서
     * 1)User 생성
     * 2)KaMoney 현재시간 기준 1시간 이내 계좌 생성
     * 3)1시간 이내 20만원 충전 후
     * 4)잔액이 1000원 이하가 되는 경우
     */
    public void testRoleAProcess() {
        User user = userService.createUser(User.generate("TestA", "TestA"));
        List<KaMoneyEventLog> kaMoneyEventLogs = new ArrayList<>();
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(OPEN, null, 100000L, LocalDateTime.now().minusMinutes(30), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(CHARGE, 100000L, 200000L, LocalDateTime.now().minusMinutes(20), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(REMITTANCE, 200000L, 1000L, LocalDateTime.now().minusMinutes(10), user));
        ruleLogService.remittanceKaMoneyRuleCheck(kaMoneyEventLogs, user);
    }

    /**
     * RoleB 테스트 순서
     * 1)User 생성
     * 2)KaMoney 현재시간 기준 7일 이내 계좌 생성
     * 3)받기 기능으로 10만원 이상 금액 5회 이상
     */
    public void testRoleBProcess() {
        User user = userService.createUser(User.generate("TestB", "TestB"));
        List<KaMoneyEventLog> kaMoneyEventLogs = new ArrayList<>();
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(OPEN, null, 100000L, LocalDateTime.now().minusDays(6), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 100000L, 200000L, LocalDateTime.now().minusDays(5), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 200000L, 300000L, LocalDateTime.now().minusDays(4), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 300000L, 400000L, LocalDateTime.now().minusDays(3), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 400000L, 500000L, LocalDateTime.now().minusDays(2), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 500000L, 600000L, LocalDateTime.now().minusDays(1), user));
        ruleLogService.receiveKaMoneyRuleCheck(kaMoneyEventLogs, user);
    }

    /**
     * RoleC 테스트 순서
     * 1)User 생성
     * 2)KaMoney 계좌 생성
     * 3)2시간 이내 받기 기능으로 5만원 이상 금액 3회 이상
     */
    public void testRoleCProcess() {
        User user = userService.createUser(User.generate("TestC", "TestC"));
        List<KaMoneyEventLog> kaMoneyEventLogs = new ArrayList<>();
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 100000L, 200000L, LocalDateTime.now().minusMinutes(5), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 200000L, 300000L, LocalDateTime.now().minusMinutes(4), user));
        kaMoneyEventLogs.add(KaMoneyEventLog.generateAtDate(RECEIVE, 300000L, 400000L, LocalDateTime.now().minusMinutes(3), user));
        ruleLogService.receiveKaMoneyRuleCheck(kaMoneyEventLogs, user);
    }
}
