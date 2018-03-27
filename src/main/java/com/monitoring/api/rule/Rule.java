package com.monitoring.api.rule;

import com.monitoring.api.domain.User;
import com.monitoring.api.domain.log.KaMoneyEventLog;
import com.monitoring.api.domain.log.enums.KaMoneyEventType;

import java.util.EnumMap;
import java.util.List;

/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
public interface Rule {
    EnumMap<KaMoneyEventType, List<KaMoneyEventLog>> mapping(List<KaMoneyEventLog> kaMoneyEventLogs, User user);
    boolean valid();
}
