package com.monitoring.api.facade;

import com.monitoring.api.domain.User;
import com.monitoring.api.domain.log.RuleLog;
import com.monitoring.api.dto.RuleLogDto;
import com.monitoring.api.service.RuleLogService;
import com.monitoring.api.service.UserService;

import org.springframework.stereotype.Component;

/**
 * Created by KimYJ on 2018-03-28.
 */
@Component
public class RuleLogFacade {

    private UserService userService;
    private RuleLogService ruleLogService;

    public RuleLogFacade(UserService userService, RuleLogService ruleLogService) {
        this.userService = userService;
        this.ruleLogService = ruleLogService;
    }

    public RuleLogDto findRuleLogByUserId(long userId) {
        User user = userService.findById(userId).orElseThrow(() -> new RuntimeException("일치하는 User가 없습니다."));
        RuleLog ruleLog = ruleLogService.findByUser(user);
        return RuleLogDto.convert(ruleLog);
    }
}
