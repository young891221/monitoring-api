package com.monitoring.api.service;

import com.monitoring.api.domain.User;
import com.monitoring.api.domain.log.KaMoneyEventLog;
import com.monitoring.api.domain.log.RuleLog;
import com.monitoring.api.repository.RuleLogRepository;
import com.monitoring.api.rule.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by young891221@gmail.com on 2018-03-27
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
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
        return ruleLogRepository.findFirstByUserOrderByCreatedDateDesc(user);
    }

    public void remittanceKaMoneyRuleCheck(List<KaMoneyEventLog> kaMoneyEventLogs, User user) {
        RuleList ruleList = RuleList.generateByArray(RuleA.create(kaMoneyEventLogs));
        runRuleEngine(user, ruleList);
    }

    public void receiveKaMoneyRuleCheck(List<KaMoneyEventLog> kaMoneyEventLogs, User user) {
        final LocalDateTime ruleCTime = LocalDateTime.now().minusHours(2);
        List<KaMoneyEventLog> ruleCLogs = kaMoneyEventLogs.stream()
                .filter(log -> ruleCTime.isBefore(log.getCreatedDate()))
                .collect(Collectors.toList());
        RuleList ruleList = RuleList.generateByArray(RuleB.create(kaMoneyEventLogs), RuleC.create(ruleCLogs));
        runRuleEngine(user, ruleList);
    }

    private void runRuleEngine(User user, RuleList ruleList) {
        RuleEngine ruleEngine = new RuleEngine(ruleList);
        String notValidRules = ruleEngine.run();
        if(!StringUtils.isEmpty(notValidRules)) {
            saveRules(notValidRules, user);
        }
    }
}
