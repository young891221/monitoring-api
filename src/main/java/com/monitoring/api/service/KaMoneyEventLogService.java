package com.monitoring.api.service;

import com.monitoring.api.domain.User;
import com.monitoring.api.domain.log.KaMoneyEventLog;
import com.monitoring.api.repository.KaMoneyEventLogRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by young891221@gmail.com on 2018-03-26
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
@Transactional
@Service
public class KaMoneyEventLogService {

    private KaMoneyEventLogRepository kaMoneyEventLogRepository;

    public KaMoneyEventLogService(KaMoneyEventLogRepository kaMoneyEventLogRepository) {
        this.kaMoneyEventLogRepository = kaMoneyEventLogRepository;
    }

    public KaMoneyEventLog findByUser(User user) {
        return kaMoneyEventLogRepository.findByUser(user);
    }

    public void saveLog(KaMoneyEventLog kaMoneyEventLog) {
        kaMoneyEventLogRepository.save(kaMoneyEventLog);
    }

    public List<KaMoneyEventLog> findByCreatedDateAfterAndUser(LocalDateTime createdDate, User user) {
        return kaMoneyEventLogRepository.findByCreatedDateAfterAndUser(createdDate, user);
    }
}
