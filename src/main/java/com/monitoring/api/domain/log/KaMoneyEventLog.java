package com.monitoring.api.domain.log;

import com.monitoring.api.domain.KaMoney;
import com.monitoring.api.domain.User;
import com.monitoring.api.domain.log.enums.KaMoneyEventType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
@Entity
public class KaMoneyEventLog implements Serializable {

    @Id
    @GeneratedValue
    private Long idx;

    @Column
    @Enumerated(EnumType.STRING)
    private KaMoneyEventType kaMoneyEventType;

    @Column
    private Long beforeMoney;

    @Column
    private Long afterMoney;

    @Column
    private LocalDateTime createdDate;

    @ManyToOne
    private User user;

    private KaMoneyEventLog() {
    }

    public KaMoneyEventLog(KaMoneyEventType kaMoneyEventType, Long beforeMoney, Long afterMoney, User user) {
        this.kaMoneyEventType = kaMoneyEventType;
        this.beforeMoney = beforeMoney;
        this.afterMoney = afterMoney;
        this.createdDate = LocalDateTime.now();
        this.user = user;
    }

    public KaMoneyEventLog(KaMoneyEventType kaMoneyEventType, Long beforeMoney, Long afterMoney, LocalDateTime createdDate, User user) {
        this.kaMoneyEventType = kaMoneyEventType;
        this.beforeMoney = beforeMoney;
        this.afterMoney = afterMoney;
        this.createdDate = createdDate;
        this.user = user;
    }

    public static KaMoneyEventLog generate(KaMoneyEventType kaMoneyEventType, Long beforeMoney, Long afterMoney, User user) {
        return new KaMoneyEventLog(kaMoneyEventType, beforeMoney, afterMoney, user);
    }

    public static KaMoneyEventLog generateAtDate(KaMoneyEventType kaMoneyEventType, Long beforeMoney, Long afterMoney, LocalDateTime createdDate, User user) {
        return new KaMoneyEventLog(kaMoneyEventType, beforeMoney, afterMoney, createdDate, user);
    }

    public static KaMoneyEventLog openKaMoney(KaMoney kaMoney) {
        return KaMoneyEventLog.generate(KaMoneyEventType.OPEN, null, kaMoney.getMoney(), kaMoney.getUser());
    }

    public static KaMoneyEventLog cargeKaMoney(KaMoney kaMoney) {
        return KaMoneyEventLog.generate(KaMoneyEventType.CHARGE, kaMoney.getBeforeMoney(), kaMoney.getMoney(), kaMoney.getUser());
    }

    public static KaMoneyEventLog remittanceKaMoney(KaMoney kaMoney) {
        return KaMoneyEventLog.generate(KaMoneyEventType.REMITTANCE, kaMoney.getBeforeMoney(), kaMoney.getMoney(), kaMoney.getUser());
    }

    public static KaMoneyEventLog receiveKaMoney(KaMoney kaMoney) {
        return KaMoneyEventLog.generate(KaMoneyEventType.RECEIVE, kaMoney.getBeforeMoney(), kaMoney.getMoney(), kaMoney.getUser());
    }

    public Long subMoney() {
        return this.afterMoney - this.beforeMoney;
    }

    public Long getIdx() {
        return idx;
    }

    public KaMoneyEventType getKaMoneyEventType() {
        return kaMoneyEventType;
    }

    public Long getBeforeMoney() {
        return beforeMoney;
    }

    public Long getAfterMoney() {
        return afterMoney;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KaMoneyEventLog that = (KaMoneyEventLog) o;
        return Objects.equals(idx, that.idx) &&
                kaMoneyEventType == that.kaMoneyEventType;
    }

    @Override
    public int hashCode() {

        return Objects.hash(idx, kaMoneyEventType);
    }
}
