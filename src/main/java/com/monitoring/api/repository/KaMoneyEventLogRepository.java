package com.monitoring.api.repository;

import com.monitoring.api.domain.User;
import com.monitoring.api.domain.log.KaMoneyEventLog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by young891221@gmail.com on 2018-03-26
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
public interface KaMoneyEventLogRepository extends JpaRepository<KaMoneyEventLog, Long> {

    KaMoneyEventLog findByUser(User user);

    /**
     * 대상 User에 대해 지정 날짜 이후의 KaMoney Event Log 리스트를 반환해 주는 쿼리
     * @param createdDate 받고 싶은 로그의 기준이 되는 날짜
     * @param user 대상 User
     * @return
     */
    List<KaMoneyEventLog> findByCreatedDateAfterAndUser(LocalDateTime createdDate, User user);
}
