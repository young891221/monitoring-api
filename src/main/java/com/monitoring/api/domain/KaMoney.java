package com.monitoring.api.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
@Entity
public class KaMoney implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "SEQUENCE_KAMONEY", initialValue = 1000000000)
    private Long accountNumber;

    @Transient
    private Long beforeMoney = 0L;

    @Column
    private Long money = 0L;

    @Column
    private LocalDateTime createdDate;

    @OneToOne
    private User user;

    @OneToOne
    private Account account;


    private KaMoney() {
    }

    private KaMoney(User user) {
        this.createdDate = LocalDateTime.now();
        this.user = user;
        user.setKaMoney(this);
    }

    public static KaMoney generate(User user) {
        return new KaMoney(user);
    }

    public static KaMoney generateRandomNumber(User user) {
        KaMoney kaMoney = new KaMoney(user);
        return kaMoney.randomNumber();
    }

    private KaMoney randomNumber() {
        this.accountNumber = ThreadLocalRandom.current().nextLong(1000000000, 9999999999L);
        return this;
    }

    public KaMoney linkAccount(final Account account) {
        this.account = account;
        account.setUser(getUser());
        return this;
    }

    public KaMoney chargeMoney(final long chargeMoney) {
        long accountMoney = this.account.getMoney();

        if(chargeMoney <= 0) throw new IllegalArgumentException("충전 금액을 올바르게 입력해 주세요.");
        if(accountMoney < chargeMoney) throw new IllegalArgumentException("금액이 충분하지 않습니다.");

        this.beforeMoney = this.money;
        this.money += chargeMoney;
        this.account.minusMoney(chargeMoney);

        return this;
    }

    public KaMoney remittanceMoney(final long money) {
        if(money <= 0) throw new IllegalArgumentException("송금 금액을 올바르게 입력해 주세요.");
        if(this.money < money) throw new IllegalArgumentException("금액이 충분하지 않습니다.");

        this.beforeMoney = this.money;
        this.money -= money;

        return this;
    }

    public KaMoney receiveMoney(final long money) {
        if(money <= 0) throw new IllegalArgumentException("금액을 올바르게 입력해 주세요.");

        this.beforeMoney = this.money;
        this.money += money;

        return this;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public Long getBeforeMoney() {
        return beforeMoney;
    }

    public Long getMoney() {
        return money;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public User getUser() {
        return user;
    }

    public Account getAccount() {
        return account;
    }
}
