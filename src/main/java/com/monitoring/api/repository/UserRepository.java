package com.monitoring.api.repository;

import com.monitoring.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
