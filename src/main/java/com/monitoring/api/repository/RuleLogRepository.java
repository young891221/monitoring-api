package com.monitoring.api.repository;

import com.monitoring.api.domain.User;
import com.monitoring.api.domain.log.RuleLog;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by young891221@gmail.com on 2018-03-27
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
public interface RuleLogRepository extends JpaRepository<RuleLog, Long> {
    RuleLog findByUser(User user);
}
