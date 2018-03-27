package com.monitoring.api.service;

import com.monitoring.api.domain.Account;
import com.monitoring.api.domain.KaMoney;
import com.monitoring.api.domain.User;
import com.monitoring.api.repository.KaMoneyRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
@Transactional
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

    public KaMoney chargeMoney(User user, long money) {
        KaMoney kaMoney = findByUser(user);
        return kaMoney.chargeMoney(money);
    }

    public KaMoney remittanceMoney(KaMoney kaMoney, long money) {
        return kaMoneyRepository.save(kaMoney.remittanceMoney(money));
    }

    public KaMoney receiveKaMoney(KaMoney kaMoney, long money) {
        return kaMoneyRepository.save(kaMoney.receiveMoney(money));
    }
}
