package com.monitoring.api;

import com.monitoring.api.domain.KaMoney;
import com.monitoring.api.domain.User;
import com.monitoring.api.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class DomainTest {
    private static final String ID = "young891221";
    private static final String NAME = "김영재";

    @Autowired
    private UserRepository userRepository;

    @Test
    public void Commone_User_생성_테스트() {
        User saveUser = userRepository.save(User.generate(ID, NAME));

        assertNotNull(saveUser.getIdx());
        assertNotNull(saveUser.getCreatedDate());
        assertEquals(ID, saveUser.getId());
    }

    @Test
    public void KaAccount_User_생성_테스트() {
        User user = User.generateWithKaAccount(ID, NAME, new KaMoney());
        User saveUser = userRepository.save(user);

        assertNotNull(saveUser.getIdx());
        assertNotNull(saveUser.getKaMoney());
    }
}
