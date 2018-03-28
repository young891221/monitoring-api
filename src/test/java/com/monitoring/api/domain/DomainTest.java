package com.monitoring.api.domain;

import com.monitoring.api.repository.KaMoneyRepository;
import com.monitoring.api.repository.UserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.monitoring.api.AcceptanceTest.TEST_ID;
import static com.monitoring.api.AcceptanceTest.TEST_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class DomainTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KaMoneyRepository kaMoneyRepository;

    @Test
    public void Commone_User_생성_테스트() {
        User saveUser = userRepository.save(User.generate(TEST_ID, TEST_NAME));

        assertNotNull(saveUser.getIdx());
        assertNotNull(saveUser.getCreatedDate());
        assertEquals(TEST_ID, saveUser.getUserId());
    }

    @Test
    public void KaAccount_User_생성_테스트() {
        User user = userRepository.save(User.generate(TEST_ID, TEST_NAME));
        KaMoney kaMoney = kaMoneyRepository.save(KaMoney.generate(user));

        assertNotNull(user.getIdx());
        assertNotNull(user.getKaMoney());
        assertTrue(kaMoney.getAccountNumber() >= 1000000000);
    }
}
