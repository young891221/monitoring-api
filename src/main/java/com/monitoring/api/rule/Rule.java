package com.monitoring.api.rule;

import com.monitoring.api.domain.log.KaMoneyEventLog;
import com.monitoring.api.domain.log.enums.KaMoneyEventType;

import java.util.List;
import java.util.Map;
/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */

/**
 * 전략 인터페이스
 */
public interface Rule {

    boolean valid(Map<KaMoneyEventType, List<KaMoneyEventLog>> kaMoneyEventLogs);

    RuleParameter getRuleParameter();

    String getName();

}
