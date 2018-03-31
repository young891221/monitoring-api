package com.monitoring.api.rule;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by young891221@gmail.com on 2018-03-27
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */

/**
 * 여러 Rule을 리스트 타입으로 관리하는 일급 객체
 */
public class RuleList {
    private List<Rule> rules;

    /**
     * Rule 배열을 받아 시간이 더 일찍인 순서로 정렬한다
     * @param rules
     */
    private RuleList(Rule... rules) {
        this.rules = Arrays.stream(rules)
                .sorted((rule1, rule2) -> {
                    long firstTime = rule1.getRuleParameter().getLongTime();
                    long secondTime = rule2.getRuleParameter().getLongTime();
                    return Long.compare(firstTime, secondTime) * -1;
                })
                .collect(Collectors.toList());
    }

    public static RuleList generateByArray(Rule... rules) {
        return new RuleList(rules);
    }

    List<Rule> get() {
        return rules;
    }

    public RuleParameter getFirstRuleParameter() {
        return rules.get(0).getRuleParameter();
    }
}
