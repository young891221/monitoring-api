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
    /**
     * 날짜를 기준으로 제일 마지막에 저장된 RuleLog를 하나만 반환해 주는 쿼리
     * @param user RuleLog의 대상 User
     * @return
     */
    RuleLog findFirstByUserOrderByCreatedDateDesc(User user);
}
