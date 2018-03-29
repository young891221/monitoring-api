package com.monitoring.api.dto;

import com.monitoring.api.domain.log.RuleLog;

import org.springframework.util.StringUtils;

/**
 * Created by young891221@gmail.com on 2018-03-28
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
public class RuleLogDto {
    private Long userId;
    private boolean isFraud;
    private String rule;

    public RuleLogDto(Long userId, boolean isFraud, String rule) {
        this.userId = userId;
        this.isFraud = isFraud;
        this.rule = rule;
    }

    public static RuleLogDto convert(RuleLog ruleLog) {
        if(ruleLog == null) {
            return new RuleLogDto(null, false, "");
        }
        return new RuleLogDto(ruleLog.getIdx(), !StringUtils.isEmpty(ruleLog.getNotValidRuls()), ruleLog.getNotValidRuls());
    }

    public Long getUserId() {
        return userId;
    }

    public boolean isFraud() {
        return isFraud;
    }

    public String getRule() {
        return rule;
    }
}
