package com.monitoring.api.rule;

import org.junit.Test;

import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by young891221@gmail.com on 2018-03-28
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
public class RuleListTest {

    @Test
    public void 제일_일찍인_기준날짜를_RuleParameter로_반환하는가() throws Exception {
        RuleList ruleList = RuleList.generateByArray(new RuleC(), new RuleB());
        RuleParameter firstRuleParameter = ruleList.getFirstRuleParameter();

        assertEquals(ChronoUnit.DAYS, firstRuleParameter.getChronoUnit());
        assertEquals(7, firstRuleParameter.getTime());
    }
}
