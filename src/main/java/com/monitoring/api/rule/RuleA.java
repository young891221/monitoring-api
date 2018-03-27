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
import java.util.stream.Collectors;

import static com.monitoring.api.domain.log.enums.KaMoneyEventType.OPEN;
import static com.monitoring.api.domain.log.enums.KaMoneyEventType.REMITTANCE;

/**
 * 서비스 계좌 개설 이후 1시간 이내, 20만원 충전 후 잔액이 1000원 이하가 되는 경우
 * 상황: 송금할때
 * 매개변수 : KaMoneyEventLogService(주입)
 * 로직 : KaMoneyEventLog의 status가 출금이던 송금이던 20만원 충전 후 1000원이 되었을 경우를 검색
 */
public class RuleA implements Rule {

    private EnumMap<KaMoneyEventType, List<KaMoneyEventLog>> typeListEnumMap;

    private RuleA(List<KaMoneyEventLog> kaMoneyEventLogs) {
        this.typeListEnumMap = mapping(kaMoneyEventLogs);
    }

    public static RuleA create(List<KaMoneyEventLog> kaMoneyEventLogs) {
        return new RuleA(kaMoneyEventLogs);
    }

    @Override
    public EnumMap<KaMoneyEventType, List<KaMoneyEventLog>> mapping(List<KaMoneyEventLog> kaMoneyEventLogs) {
        return kaMoneyEventLogs.stream()
                .collect(Collectors.groupingBy(
                        KaMoneyEventLog::getKaMoneyEventType,
                        () -> new EnumMap<>(KaMoneyEventType.class),
                        Collectors.toList()
                ));
    }

    @Override
    public boolean valid() {
        return isWithinOneHour() && isTwentyChargeAndLeftThousand();
    }

    private boolean isWithinOneHour() {
        return typeListEnumMap.get(OPEN).stream()
                    .anyMatch(log -> LocalDateTime.now().minusHours(1).isBefore(log.getCreatedDate()));
    }

    private boolean isTwentyChargeAndLeftThousand() {
        List<KaMoneyEventLog> remittanceLogs = typeListEnumMap.get(REMITTANCE);
        int length = remittanceLogs.size();
        int twentyIndex = -1;

        for (int i = 0; i < length; i++) {
            if(remittanceLogs.get(i).getAfterMoney() >= 200000L) {
                twentyIndex = i;
                break;
            }
        }

        if(twentyIndex > -1) {
            for (int j = twentyIndex + 1; j < length; j++) {
                if (remittanceLogs.get(j).getAfterMoney() <= 1000L) return true;
            }
        }

        return false;
    }

    public EnumMap<KaMoneyEventType, List<KaMoneyEventLog>> getTypeListEnumMap() {
        return typeListEnumMap;
    }
}
