package com.monitoring.api.facade;

import com.monitoring.api.domain.User;
import com.monitoring.api.domain.log.RuleLog;
import com.monitoring.api.dto.RuleLogDto;
import com.monitoring.api.service.RuleLogService;
import com.monitoring.api.service.UserService;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

/**
 * Created by young891221@gmail.com on 2018-03-28
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
@Component
@Transactional
public class RuleLogFacade {

    private UserService userService;
    private RuleLogService ruleLogService;

    public RuleLogFacade(UserService userService, RuleLogService ruleLogService) {
        this.userService = userService;
        this.ruleLogService = ruleLogService;
    }

    /**
     * userId에 대한 최근 RuleLog를 반환
     * @param userId User의 idx값
     * @return REST API로 반환할 RuleLogDto
     */
    public RuleLogDto findRuleLogByUserId(long userId) {
        User user = userService.findById(userId).orElseThrow(() -> new EntityNotFoundException("일치하는 User가 없습니다."));
        RuleLog ruleLog = ruleLogService.findByUser(user);
        return RuleLogDto.convert(ruleLog);
    }
}
