package com.monitoring.api.domain.log;

import com.monitoring.api.domain.User;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
@Entity
public class RuleLog {

    @Id
    @GeneratedValue
    private Long idx;

    @Column
    private String notValidRuls;

    @Column
    private LocalDateTime createdDate;

    @ManyToOne
    private User user;

    private RuleLog() {
    }

    public RuleLog(String notValidRuls, User user) {
        this.notValidRuls = notValidRuls;
        this.createdDate = LocalDateTime.now();
        this.user = user;
    }

    public Long getIdx() {
        return idx;
    }

    public String getNotValidRuls() {
        return notValidRuls;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public User getUser() {
        return user;
    }
}
