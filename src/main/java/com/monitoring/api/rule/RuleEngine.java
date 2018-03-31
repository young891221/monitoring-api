package com.monitoring.api.rule;

import java.util.stream.Collectors;

/**
 * Created by young891221@gmail.com on 2018-03-26
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */

/**
 * RuleList을 활용하여 검증과 어떠한 동작을 수행하는 일급 객체
 */
public class RuleEngine {

    private RuleList ruleList;

    public RuleEngine(RuleList ruleList) {
        this.ruleList = ruleList;
    }

    /**
     * RuleList의 Rule들을 valid() 메서드로 검증하고 해당하는 Rule은 리플렉션 메서드로 클래스 이름을 참조하여 반환한다.
     * @return "RuleA, RuleB"와 같이 여러 Rule값이 있으면 콤마로 구분하여 반환
     */
    public String run() {
        return ruleList.get().stream()
                .filter(Rule::valid)
                .map(Rule::getName)
                .collect(Collectors.joining(", "));
    }
}
