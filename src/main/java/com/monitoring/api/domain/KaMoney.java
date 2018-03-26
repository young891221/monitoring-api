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

    @Column
    private Long money = 0L;

    @Column
    private LocalDateTime createdDate;

    @OneToOne
    private User user;

    //@OneToMany
    //private Account account;


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

    /*public Account getAccount() {
        return account;
    }*/
}
