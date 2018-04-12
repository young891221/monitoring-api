package com.monitoring.api.rule;

import com.monitoring.api.domain.log.KaMoneyEventLog;
import com.monitoring.api.domain.log.enums.KaMoneyEventType;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

/**
 * Created by young891221@gmail.com on 2018-03-26
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */

/**
 * RuleList을 활용하여 검증과 어떠한 동작을 수행하는 객체
 */
public class RuleEngine {

    private RuleList ruleList;
    private Map<KaMoneyEventType, List<KaMoneyEventLog>> kaMoneyEventLogs;

    private RuleEngine(RuleList ruleList, List<KaMoneyEventLog> kaMoneyEventLogs) {
        this.ruleList = ruleList;
        this.kaMoneyEventLogs = mapping(kaMoneyEventLogs);
    }

    public static RuleEngine create(RuleList ruleList, List<KaMoneyEventLog> kaMoneyEventLogs) {
        return new RuleEngine(ruleList, kaMoneyEventLogs);
    }

    /**
     * RuleList의 Rule들을 valid() 메서드로 검증하고 해당하는 Rule은 리플렉션 메서드로 클래스 이름을 참조하여 반환한다.
     * 중복된 Role 제거, 이름순으로 오름차순 정렬 수행
     * @return "RuleA, RuleB"와 같이 여러 Rule값이 있으면 콤마로 구분하여 반환
     */
    public Optional<String> run() {
        return Optional.ofNullable(ruleList.get().stream()
                .filter(rule -> rule.valid(kaMoneyEventLogs))
                .map(Rule::getName)
                .distinct()
                .sorted()
                .collect(Collectors.joining(", ")));
    }

    /**
     * KaMoneyEventType을 기준으로 넘어온 로그들을 그룹핑하여 Rule을 위한 데이터 생성
     * parallelStream(해당 서버의 코어수를 디폴트로 쓰레드를 생성)을 사용하여 병렬로 처리
     * @param kaMoneyEventLogs 대상 시간 안에 포함되는 KaMoneyEventLog 리스트
     * @return 키값은 KaMoneyEventType으로 해당 키에 맵핑되는 List<KaMoneyEventLog>를 가진 EnumMap 자료구조 반환
     */
    private Map<KaMoneyEventType, List<KaMoneyEventLog>> mapping(final List<KaMoneyEventLog> kaMoneyEventLogs) {
        return kaMoneyEventLogs.stream()
                .collect(Collectors.groupingBy(
                        KaMoneyEventLog::getKaMoneyEventType,
                        () -> new EnumMap<>(KaMoneyEventType.class),
                        Collectors.toList()
                ));

        /*ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        Map<KaMoneyEventType, List<KaMoneyEventLog>> result = null;
        try {
            result = forkJoinPool.submit(() -> kaMoneyEventLogs.parallelStream()
                    .collect(Collectors.groupingBy(
                            KaMoneyEventLog::getKaMoneyEventType,
                            () -> new EnumMap<>(KaMoneyEventType.class),
                            Collectors.toList()
                    ))).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;*/
    }

}
