package com.monitoring.api.repository;

import com.monitoring.api.domain.KaMoney;
import com.monitoring.api.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by young891221@gmail.com on 2018-03-26
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
public interface KaMoneyRepository extends JpaRepository<KaMoney, Long> {

    KaMoney findByUser(User user);

}
