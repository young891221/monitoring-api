package com.monitoring.api.rule;

/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */

import com.monitoring.api.domain.log.KaMoneyEventLog;
import com.monitoring.api.domain.log.enums.KaMoneyEventType;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;

import static com.monitoring.api.domain.log.enums.KaMoneyEventType.CHARGE;
import static com.monitoring.api.domain.log.enums.KaMoneyEventType.OPEN;
import static com.monitoring.api.domain.log.enums.KaMoneyEventType.REMITTANCE;

public class RuleA implements Rule {

    private EnumMap<KaMoneyEventType, List<KaMoneyEventLog>> typeListEnumMap;

    private RuleA(List<KaMoneyEventLog> kaMoneyEventLogs) {
        this.typeListEnumMap = mapping(kaMoneyEventLogs);
    }

    public static RuleA create(List<KaMoneyEventLog> kaMoneyEventLogs) {
        return new RuleA(kaMoneyEventLogs);
    }

    /**
     * RuleA 검증로직
     * 서비스 계좌 개설 1시간 이내, 20만원 충전 후 잔액이 1000원 이하가 되는 경우
     * @return RuleA에 해당하면 true, 아니면 false
     */
    @Override
    public boolean valid() {
        return isWithinOneHourOpen() && isTwentyChargeAndLeftThousand();
    }

    /**
     * 서비스 계좌 개설 1시간 이내
     * @return
     */
    private boolean isWithinOneHourOpen() {
        return typeListEnumMap.get(OPEN).stream()
                    .anyMatch(log -> LocalDateTime.now().minusHours(1).isBefore(log.getCreatedDate()));
    }

    /**
     * 20만원 충전 후 잔액이 1000원 이하가 되는 경우
     * @return
     */
    private boolean isTwentyChargeAndLeftThousand() {
        List<KaMoneyEventLog> chargeLogs = typeListEnumMap.get(CHARGE);
        List<KaMoneyEventLog> remittanceLogs = typeListEnumMap.get(REMITTANCE);
        LocalDateTime twentyChargeTime = null;

        for (KaMoneyEventLog chargeLog : chargeLogs) {
            if(chargeLog.getAfterMoney() >= 200000L) {
                twentyChargeTime = chargeLog.getCreatedDate();
                break;
            }
        }

        if(twentyChargeTime != null) {
            final LocalDateTime finalTwentyChargeTime = twentyChargeTime;
            final boolean isThousandTarget = remittanceLogs.stream()
                    .anyMatch(log -> finalTwentyChargeTime.isBefore(log.getCreatedDate()) && log.getAfterMoney() <= 1000L);
            return isThousandTarget;
        }

        return false;
    }

    public EnumMap<KaMoneyEventType, List<KaMoneyEventLog>> getTypeListEnumMap() {
        return typeListEnumMap;
    }
}
