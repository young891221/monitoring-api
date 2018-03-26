package com.monitoring.api.domain.log.enums;

/**
 * Created by KimYJ on 2018-03-26.
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
