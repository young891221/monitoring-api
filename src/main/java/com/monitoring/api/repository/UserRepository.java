package com.monitoring.api.repository;

import com.monitoring.api.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);

    /**
     * @return 마지막으로 생성된 User 반환 쿼리
     */
    User findFirstByOrderByCreatedDateDesc();
}
