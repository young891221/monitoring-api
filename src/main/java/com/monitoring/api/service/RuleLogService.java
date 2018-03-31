package com.monitoring.api.service;

import com.monitoring.api.domain.User;
import com.monitoring.api.domain.log.KaMoneyEventLog;
import com.monitoring.api.domain.log.RuleLog;
import com.monitoring.api.repository.KaMoneyEventLogRepository;
import com.monitoring.api.repository.RuleLogRepository;
import com.monitoring.api.rule.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by young891221@gmail.com on 2018-03-27
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
@Service
public class RuleLogService {

    private RuleLogRepository ruleLogRepository;
    private KaMoneyEventLogRepository kaMoneyEventLogRepository;

    public RuleLogService(RuleLogRepository ruleLogRepository, KaMoneyEventLogRepository kaMoneyEventLogRepository) {
        this.ruleLogRepository = ruleLogRepository;
        this.kaMoneyEventLogRepository = kaMoneyEventLogRepository;
    }

    public void saveRules(String rules, User user) {
        ruleLogRepository.save(new RuleLog(rules, user));
    }

    public RuleLog findByUser(User user) {
        return ruleLogRepository.findFirstByUserOrderByCreatedDateDesc(user);
    }

    /**
     * 송금한 User의 Rule을 체크한다.
     * @param user
     */
    public void remittanceKaMoneyRuleCheck(User user) {
        RuleList ruleList = RuleList.generateByArray(new RuleA());
        RuleParameter firstRuleParameter = ruleList.getFirstRuleParameter();
        runRuleEngine(user, ruleList, getKaMoneyEventLogs(user, firstRuleParameter));
    }

    /**
     * 받기 기능의 Rule을 체크한다.
     * @param user
     */
    public void receiveKaMoneyRuleCheck(User user) {
        RuleList ruleList = RuleList.generateByArray(new RuleB(), new RuleC());
        RuleParameter firstRuleParameter = ruleList.getFirstRuleParameter();
        runRuleEngine(user, ruleList, getKaMoneyEventLogs(user, firstRuleParameter));
    }

    private List<KaMoneyEventLog> getKaMoneyEventLogs(User user, RuleParameter firstRuleParameter) {
        return kaMoneyEventLogRepository.findByUserAndCreatedDateAfter(user,
                LocalDateTime.now().minus(firstRuleParameter.getTime(), firstRuleParameter.getChronoUnit()));
    }

    private void runRuleEngine(User user, RuleList ruleList, List<KaMoneyEventLog> kaMoneyEventLogs) {
        RuleEngine ruleEngine = RuleEngine.create(ruleList, kaMoneyEventLogs);
        Optional<String> notValidRules = ruleEngine.run();
        notValidRules.ifPresent(rules -> saveRules(rules, user));
    }
}
