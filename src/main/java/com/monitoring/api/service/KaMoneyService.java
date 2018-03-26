package com.monitoring.api.service;

import com.monitoring.api.domain.KaMoney;
import com.monitoring.api.domain.User;
import com.monitoring.api.domain.log.KaMoneyEventLog;
import com.monitoring.api.repository.KaMoneyEventLogRepository;
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
    private KaMoneyEventLogRepository kaMoneyEventLogRepository;

    public KaMoneyService(KaMoneyRepository kaMoneyRepository, KaMoneyEventLogRepository kaMoneyEventLogRepository) {
        this.kaMoneyRepository = kaMoneyRepository;
        this.kaMoneyEventLogRepository = kaMoneyEventLogRepository;
    }

    public void openKaMoney(User user) {
        KaMoney kaMoney = kaMoneyRepository.save(KaMoney.generate(user));
        kaMoneyEventLogRepository.save(KaMoneyEventLog.openKaMoney(kaMoney)); //TODO:파사드 레이어 두면 좋을 듯(스파게티 소스 방지)
    }
}
