package com.monitoring.api.rule;

import com.monitoring.api.domain.log.KaMoneyEventLog;
import com.monitoring.api.domain.log.enums.KaMoneyEventType;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */

/**
 * 전략 인터페이스
 */
public interface Rule {

    boolean valid();

    /**
     * KaMoneyEventType을 기준으로 넘어온 로그들을 그룹핑하여 Rule을 위한 데이터 생성
     * parallelStream(해당 서버의 코어수를 디폴트로 쓰레드를 생성)을 사용하여 병렬로 처리
     * @param kaMoneyEventLogs 대상 시간 안에 포함되는 KaMoneyEventLog 리스트
     * @return 키값은 KaMoneyEventType으로 해당 키에 맵핑되는 List<KaMoneyEventLog>를 가진 EnumMap 자료구조 반환
     */
    default EnumMap<KaMoneyEventType, List<KaMoneyEventLog>> mapping(List<KaMoneyEventLog> kaMoneyEventLogs) {
        Map<KaMoneyEventType, List<KaMoneyEventLog>> concurrentHashMap = kaMoneyEventLogs.parallelStream()
                .collect(Collectors.groupingByConcurrent(
                        KaMoneyEventLog::getKaMoneyEventType,
                        Collectors.toList()
                ));

        EnumMap<KaMoneyEventType, List<KaMoneyEventLog>> eventTypeListEnumMap = new EnumMap<>(KaMoneyEventType.class);
        eventTypeListEnumMap.putAll(concurrentHashMap);

        return eventTypeListEnumMap;
    }

}
