package com.monitoring.api.test;

import com.monitoring.api.domain.Account;
import com.monitoring.api.domain.KaMoney;
import com.monitoring.api.domain.User;
import com.monitoring.api.domain.enums.BankType;
import com.monitoring.api.facade.KaMoneyFacade;
import com.monitoring.api.service.AccountService;
import com.monitoring.api.service.UserService;

/**
 * Created by young891221@gmail.com on 2018-03-28
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
public class TestModule {

    /**
     * RoleA 테스트 순서
     * 1)User 생성
     * 2)KaMoney 현재시간 기준 1시간 이내 계좌 생성
     * 3)1시간 이내 20만원 충전 후
     * 4)잔액이 1000원 이하가 되는 경우
     */
    public void testRoleAProcess(UserService userService, AccountService accountService, KaMoneyFacade kaMoneyFacade) {
        User user = userService.createUser(User.generate("TestA", "TestA"));
        Account account = accountService.createAccount(new Account(BankType.KB, 1234L, 1000000L));
        KaMoney kaMoney = kaMoneyFacade.openKaMoney(user, account);
        kaMoneyFacade.chargeKaMoney(user, 210000);

        User toUser = userService.createUser(User.generate("toUser", "test"));
        Account toAccount = accountService.createAccount(new Account(BankType.KB, 1235L, 0L));
        kaMoneyFacade.openKaMoney(toUser, toAccount);

        kaMoneyFacade.remittanceKaMoney(user, toUser, 210000);
    }

    /**
     * RoleB 테스트 순서
     * 1)User 생성
     * 2)KaMoney 현재시간 기준 7일 이내 계좌 생성
     * 3)받기 기능으로 10만원 이상 금액 5회 이상
     */
    public void testRoleBProcess(UserService userService, AccountService accountService, KaMoneyFacade kaMoneyFacade) {
        //받을 User
        User toUser = userService.createUser(User.generate("TestB", "TestB"));
        Account toAccount = accountService.createAccount(new Account(BankType.KB, 1235L, 1000000L));
        kaMoneyFacade.openKaMoney(toUser, toAccount);

        kaMoneyFacade.receiveKaMoney(toUser, 100000);
        kaMoneyFacade.receiveKaMoney(toUser, 100000);
        kaMoneyFacade.receiveKaMoney(toUser, 100000);
        kaMoneyFacade.receiveKaMoney(toUser, 100000);
        kaMoneyFacade.receiveKaMoney(toUser, 100000);
    }
}
