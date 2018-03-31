package com.monitoring.api.rule;

import java.time.temporal.ChronoUnit;

/**
 * Created by young891221@gmail.com on 2018-03-27
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
public class RuleParameter {
    private ChronoUnit chronoUnit;
    private long time;

    RuleParameter(ChronoUnit chronoUnit, long time) {
        this.chronoUnit = chronoUnit;
        this.time = time;
    }

    public ChronoUnit getChronoUnit() {
        return chronoUnit;
    }

    public long getTime() {
        return time;
    }

    long getLongTime() {
        return chronoUnit.getDuration().getSeconds() * time;
    }
}
