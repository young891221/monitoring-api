package com.monitoring.api.rule;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by young891221@gmail.com on 2018-03-26
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
public class RuleEngine {

    private List<Rule> rules;

    public RuleEngine(List<Rule> rules) {
        this.rules = rules;
    }

    public List<String> run() {
        return rules.stream()
                .filter(rule -> !rule.valid())
                .map(Object::toString)
                .collect(Collectors.toList());
    }
}
