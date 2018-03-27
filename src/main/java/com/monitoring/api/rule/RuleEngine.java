package com.monitoring.api.rule;

import java.util.stream.Collectors;

/**
 * Created by young891221@gmail.com on 2018-03-26
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
public class RuleEngine {

    private RuleList ruleList;

    public RuleEngine(RuleList ruleList) {
        this.ruleList = ruleList;
    }

    public String run() {
        return ruleList.get().stream()
                .filter(Rule::valid)
                .map(rule -> rule.getClass().getSimpleName())
                .collect(Collectors.joining(", "));
    }
}
