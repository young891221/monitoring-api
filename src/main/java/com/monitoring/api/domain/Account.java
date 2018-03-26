package com.monitoring.api.domain;

import com.monitoring.api.domain.enums.BankType;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class Account implements Serializable {

    @Id
    @GeneratedValue
    private Long idx;

    @Column
    @Enumerated(EnumType.STRING)
    private BankType bankType;

    @Column
    private Long accountNumber;

    @Column
    private Long money;

    @Column
    private LocalDateTime createdDate;

    @ManyToOne
    private User user;

    private Account() {
    }

    public Account(BankType bankType, Long accountNumber, Long money) {
        this.bankType = bankType;
        this.accountNumber = accountNumber;
        this.money = money;
        this.createdDate = LocalDateTime.now();
    }

    public void minusMoney(long chargeMoney) {
        this.money -= chargeMoney;
    }

    public Long getIdx() {
        return idx;
    }

    public BankType getBankType() {
        return bankType;
    }

    public Long getAccountNumber() {
        return accountNumber;
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

    public void setUser(User user) {
        this.user = user;
    }
}
