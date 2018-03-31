package com.monitoring.api.rule;

/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */

import com.monitoring.api.domain.log.KaMoneyEventLog;
import com.monitoring.api.domain.log.enums.KaMoneyEventType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static com.monitoring.api.domain.log.enums.KaMoneyEventType.OPEN;
import static com.monitoring.api.domain.log.enums.KaMoneyEventType.RECEIVE;

public class RuleB implements Rule {

    private RuleParameter ruleParameter = new RuleParameter(ChronoUnit.DAYS, 7);

    /**
     * RuleB 검증로직
     * 서비스 계좌 개설 7일 이내, 받기 기능으로 10만원 이상 금액을 5회 이상 하는 경우
     * @return RuleB에 해당하면 true, 아니면 false
     * @param kaMoneyEventLogs
     */
    @Override
    public boolean valid(Map<KaMoneyEventType, List<KaMoneyEventLog>> kaMoneyEventLogs) {
        return isWithinSevenDayOpen(kaMoneyEventLogs) && isReceiveTenThousandAtFiveTime(kaMoneyEventLogs);
    }

    @Override
    public RuleParameter getRuleParameter() {
        return ruleParameter;
    }

    @Override
    public String getName() {
        return "RuleB";
    }

    /**
     * 서비스 계좌 개설 7일 이내
     * @return
     * @param kaMoneyEventLogs
     */
    private boolean isWithinSevenDayOpen(Map<KaMoneyEventType, List<KaMoneyEventLog>> kaMoneyEventLogs) {
        if(!kaMoneyEventLogs.containsKey(OPEN)) return false;
        return kaMoneyEventLogs.get(OPEN).stream()
                .anyMatch(log -> LocalDateTime.now().minusDays(7).isBefore(log.getCreatedDate()));
    }

    /**
     * 받기 기능으로 10만원 이상 금액을 5회 이상 하는 경우
     * @return
     * @param kaMoneyEventLogs
     */
    private boolean isReceiveTenThousandAtFiveTime(Map<KaMoneyEventType, List<KaMoneyEventLog>> kaMoneyEventLogs) {
        List<KaMoneyEventLog> receiveLogs = kaMoneyEventLogs.get(RECEIVE);
        return receiveLogs.stream()
                .filter(log -> log.subMoney() >= 100000L)
                .count() >= 5;
    }

}
