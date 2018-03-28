package com.monitoring.api.dto;

import com.monitoring.api.domain.log.RuleLog;

import org.springframework.util.StringUtils;

/**
 * Created by KimYJ on 2018-03-28.
 */
public class RuleLogDto {
    private long userId;
    private boolean isFraud;
    private String rule;

    public RuleLogDto(long userId, boolean isFraud, String rule) {
        this.userId = userId;
        this.isFraud = isFraud;
        this.rule = rule;
    }

    public static RuleLogDto convert(RuleLog ruleLog) {
        return new RuleLogDto(ruleLog.getIdx(), !StringUtils.isEmpty(ruleLog.getNotValidRuls()), ruleLog.getNotValidRuls());
    }

    public long getUserId() {
        return userId;
    }

    public boolean isFraud() {
        return isFraud;
    }

    public String getRule() {
        return rule;
    }
}
