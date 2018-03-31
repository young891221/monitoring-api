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

public class RuleC implements Rule {

    private RuleParameter ruleParameter = new RuleParameter(ChronoUnit.HOURS, 2);

    /**
     * RuleC 검증로직
     * 2시간 이내, 받기 기능으로 5만원 이상 금액을 3회 이상 하는 경우
     * @return RuleC에 해당하면 true, 아니면 false
     * @param kaMoneyEventLogs
     */
    @Override
    public boolean valid(Map<KaMoneyEventType, List<KaMoneyEventLog>> kaMoneyEventLogs) {
        if(!kaMoneyEventLogs.containsKey(KaMoneyEventType.RECEIVE)) return false;

        final LocalDateTime withinTwoHour = LocalDateTime.now().minusHours(2);
        return kaMoneyEventLogs.get(KaMoneyEventType.RECEIVE).stream()
                .filter(log -> withinTwoHour.isBefore(log.getCreatedDate()) && (log.subMoney() >= 50000L))
                .count() >= 3;
    }

    @Override
    public RuleParameter getRuleParameter() {
        return ruleParameter;
    }

    @Override
    public String getName() {
        return "RuleC";
    }

}
