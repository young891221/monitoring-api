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

import static com.monitoring.api.domain.log.enums.KaMoneyEventType.OPEN;
import static com.monitoring.api.domain.log.enums.KaMoneyEventType.RECEIVE;

public class RuleB implements Rule {

    private ConcurrentMap<KaMoneyEventType, List<KaMoneyEventLog>> typeListConcurrentMap;

    private RuleB(List<KaMoneyEventLog> kaMoneyEventLogs) {
        this.typeListConcurrentMap = mapping(kaMoneyEventLogs);
    }

    public static RuleB create(List<KaMoneyEventLog> kaMoneyEventLogs) {
        return new RuleB(kaMoneyEventLogs);
    }

    /**
     * RuleB 검증로직
     * 서비스 계좌 개설 7일 이내, 받기 기능으로 10만원 이상 금액을 5회 이상 하는 경우
     * @return RuleB에 해당하면 true, 아니면 false
     */
    @Override
    public boolean valid() {
        return isWithinSevenDayOpen() && isReceiveTenThousandAtFiveTime();
    }

    @Override
    public String getName() {
        return "RuleB";
    }

    /**
     * 서비스 계좌 개설 7일 이내
     * @return
     */
    private boolean isWithinSevenDayOpen() {
        if(!typeListConcurrentMap.containsKey(OPEN)) return false;
        return typeListConcurrentMap.get(OPEN).stream()
                .anyMatch(log -> LocalDateTime.now().minusDays(7).isBefore(log.getCreatedDate()));
    }

    /**
     * 받기 기능으로 10만원 이상 금액을 5회 이상 하는 경우
     * @return
     */
    private boolean isReceiveTenThousandAtFiveTime() {
        List<KaMoneyEventLog> receiveLogs = typeListConcurrentMap.get(RECEIVE);
        return receiveLogs.stream()
                .filter(log -> log.subMoney() >= 100000L)
                .count() >= 5;
    }

    public ConcurrentMap<KaMoneyEventType, List<KaMoneyEventLog>> getTypeListConcurrentMap() {
        return typeListConcurrentMap;
    }
}
