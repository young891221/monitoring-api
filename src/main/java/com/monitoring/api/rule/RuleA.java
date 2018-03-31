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
import java.util.Optional;

import static com.monitoring.api.domain.log.enums.KaMoneyEventType.*;

public class RuleA implements Rule {

    private RuleParameter ruleParameter = new RuleParameter(ChronoUnit.HOURS, 1);

    /**
     * RuleA 검증로직
     * 서비스 계좌 개설 1시간 이내, 20만원 충전 후 잔액이 1000원 이하가 되는 경우
     * @return RuleA에 해당하면 true, 아니면 false
     * @param kaMoneyEventLogs
     */
    @Override
    public boolean valid(Map<KaMoneyEventType, List<KaMoneyEventLog>> kaMoneyEventLogs) {
        return isWithinOneHourOpen(kaMoneyEventLogs) && isTwentyChargeAndLeftThousand(kaMoneyEventLogs);
    }

    @Override
    public RuleParameter getRuleParameter() {
        return ruleParameter;
    }

    @Override
    public String getName() {
        return "RuleA";
    }

    /**
     * 서비스 계좌 개설 1시간 이내
     * @return boolean
     * @param kaMoneyEventLogs
     */
    private boolean isWithinOneHourOpen(Map<KaMoneyEventType, List<KaMoneyEventLog>> kaMoneyEventLogs) {
        return kaMoneyEventLogs.get(OPEN).stream()
                    .anyMatch(log -> LocalDateTime.now().minusHours(1).isBefore(log.getCreatedDate()));
    }

    /**
     * 20만원 충전 후 잔액이 1000원 이하가 되는 경우
     * @return boolean
     * @param kaMoneyEventLogs
     */
    private boolean isTwentyChargeAndLeftThousand(Map<KaMoneyEventType, List<KaMoneyEventLog>> kaMoneyEventLogs) {
        List<KaMoneyEventLog> chargeLogs = kaMoneyEventLogs.get(CHARGE);
        List<KaMoneyEventLog> remittanceLogs = kaMoneyEventLogs.get(REMITTANCE);

        Optional<KaMoneyEventLog> twentyChargeLog = chargeLogs.stream()
                .filter(log -> log.getAfterMoney() >= 200000L)
                .findFirst();

        final Optional<Boolean> isThousandTarget = twentyChargeLog.map((log) -> remittanceLogs.stream()
                .anyMatch(remittanceLog -> log.getCreatedDate().isBefore(remittanceLog.getCreatedDate()) && remittanceLog.getAfterMoney() <= 1000L));

        return isThousandTarget.orElse(false);
    }

}
