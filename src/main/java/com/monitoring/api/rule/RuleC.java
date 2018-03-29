package com.monitoring.api.rule;

/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */

import com.monitoring.api.domain.log.KaMoneyEventLog;
import com.monitoring.api.domain.log.enums.KaMoneyEventType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class RuleC implements Rule {

    private ConcurrentMap<KaMoneyEventType, List<KaMoneyEventLog>> typeListConcurrentMap;

    private RuleC(List<KaMoneyEventLog> kaMoneyEventLogs) {
        this.typeListConcurrentMap = mapping(kaMoneyEventLogs);
    }

    public static RuleC create(List<KaMoneyEventLog> kaMoneyEventLogs) {
        return new RuleC(kaMoneyEventLogs);
    }

    /**
     * RuleC 검증로직
     * 2시간 이내, 받기 기능으로 5만원 이상 금액을 3회 이상 하는 경우
     * @return RuleC에 해당하면 true, 아니면 false
     */
    @Override
    public boolean valid() {
        if(!typeListConcurrentMap.containsKey(KaMoneyEventType.RECEIVE)) return false;

        final LocalDateTime withinTwoHour = LocalDateTime.now().minusHours(2);
        return typeListConcurrentMap.get(KaMoneyEventType.RECEIVE).stream()
                .filter(log -> withinTwoHour.isBefore(log.getCreatedDate()) && (log.subMoney() >= 50000L))
                .count() >= 3;
    }

    public ConcurrentMap<KaMoneyEventType, List<KaMoneyEventLog>> getTypeListConcurrentMap() {
        return typeListConcurrentMap;
    }
}
