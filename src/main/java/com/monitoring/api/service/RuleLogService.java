package com.monitoring.api.service;

import com.monitoring.api.domain.User;
import com.monitoring.api.domain.log.RuleLog;
import com.monitoring.api.repository.RuleLogRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by young891221@gmail.com on 2018-03-27
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
@Transactional
@Service
public class RuleLogService {

    private RuleLogRepository ruleLogRepository;

    public RuleLogService(RuleLogRepository ruleLogRepository) {
        this.ruleLogRepository = ruleLogRepository;
    }

    public void saveRules(String rules, User user) {
        ruleLogRepository.save(new RuleLog(rules, user));
    }

    public RuleLog findByUser(User user) {
        return ruleLogRepository.findByUser(user);
    }
}
