package com.monitoring.api.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class KaMoney {

    @Id
    @GeneratedValue
    private Long idx;

    @Column
    private Long accountNumber;

    @Column
    private Long money;

    @Column
    private LocalDateTime createdDate;

    //@OneToMany
    //private Account account;

    public KaMoney() {
    }

    public Long getIdx() {
        return idx;
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

    /*public Account getAccount() {
        return account;
    }*/
}
