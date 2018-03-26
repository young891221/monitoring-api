package com.monitoring.api.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long idx;

    @Column
    private String id;

    @Column
    private String name;

    @Column
    private LocalDateTime createdDate;

    @OneToOne
    private KaMoney kaMoney;

    private User() {
    }

    private User(String id, String name) {
        this.id = id;
        this.name = name;
        this.createdDate = LocalDateTime.now();
    }

    public static User generate(String id, String name) {
        return new User(id, name);
    }

    public static User generateWithKaAccount(String id, String name, KaMoney kaMoney) {
        User user = new User(id, name);
        user.addKaMoney(kaMoney);
        return user;
    }

    private void addKaMoney(KaMoney kaMoney) {
        this.kaMoney = kaMoney;
    }

    public Long getIdx() {
        return idx;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public KaMoney getKaMoney() {
        return kaMoney;
    }

    public void setKaMoney(KaMoney kaMoney) {
        this.kaMoney = kaMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(idx, user.idx) &&
                Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idx, id);
    }
}
