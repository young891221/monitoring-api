package com.monitoring.api.rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private RuleList(Rule... rules) {
        this.rules = new ArrayList<>(Arrays.asList(rules));
    }

    public static RuleList generateByArray(Rule... rules) {
        return new RuleList(rules);
    }

    public List<Rule> get() {
        return rules;
    }
}
