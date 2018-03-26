package com.monitoring.api.rule;

/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */

/**
 * 서비스 계좌 개설 이후 1시간 이내, 20만원 충전 후 잔액이 1000원 이하가 되는 경우
 * 상황: 개설했을 때
 * 매개변수 : KaMoneyEventLogRepository(주입)
 * 로직 : KaMoneyEventLog의 status가 출금이던 송금이던 20만원 충전 후 1000원이 되었을 경우를 검색
 */
public class RuleA implements Rule {

    @Override
    public boolean valid() {
        return false;
    }
}
