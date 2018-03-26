package com.monitoring.api.rule;

/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */

/**
 * 서비스 계좌 개설 이후 7일 이내, 받기 기능으로 10만원 이상 금액을 5회 이상 하는 경우
 * 상황 : 받기 기능 실행시
 * 매개변수 : KaMoneyEventLogRepository(주입)
 * 로직 : KaMoneyEventLog에서 먼저 계좌 개설이 7일 이내인지 검색, 앞의 조건이 맞으면 10만원 이상으로 5회이상 했는지 체크
 */
public class RuleB {
}
