package com.monitoring.api.service;

import com.monitoring.api.domain.Account;
import com.monitoring.api.domain.KaMoney;
import com.monitoring.api.domain.User;
import com.monitoring.api.repository.KaMoneyRepository;

import org.springframework.stereotype.Service;

/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
@Service
public class KaMoneyService {

    private KaMoneyRepository kaMoneyRepository;

    public KaMoneyService(KaMoneyRepository kaMoneyRepository) {
        this.kaMoneyRepository = kaMoneyRepository;
    }

    public KaMoney openKaMoney(User user, Account account) {
        KaMoney kaMoney = kaMoneyRepository.save(KaMoney.generate(user));
        return kaMoney.linkAccount(account);
    }

    public KaMoney findByUser(User user) {
        return kaMoneyRepository.findByUser(user);
    }

}
