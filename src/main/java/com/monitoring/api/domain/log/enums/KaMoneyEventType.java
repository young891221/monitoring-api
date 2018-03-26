package com.monitoring.api.domain.log.enums;

/**
 * Created by young891221@gmail.com on 2018-03-26
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
public enum KaMoneyEventType {
    OPEN("개설"),
    CHARGE("충전"),
    REMITTANCE("송금"),
    RECEIVE("받기");

    private String value;

    KaMoneyEventType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
