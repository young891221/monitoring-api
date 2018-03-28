package com.monitoring.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monitoring.api.domain.Account;
import com.monitoring.api.domain.KaMoney;
import com.monitoring.api.domain.User;
import com.monitoring.api.domain.enums.BankType;
import com.monitoring.api.facade.KaMoneyFacade;
import com.monitoring.api.service.AccountService;
import com.monitoring.api.service.UserService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
@SpringBootApplication
public class ApiApplication {

	private static PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
	private static ObjectMapper objectMapper = new ObjectMapper();

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(UserService userService, AccountService accountService, KaMoneyFacade kaMoneyFacade) {
		return (args) -> {
			/**
			 * RoleA 테스트 순서
			 * 1)User 생성
			 * 2)KaMoney 현재시간 기준 1시간 이내 계좌 생성
			 * 3)1시간 이내 20만원 충전 후
			 * 4)잔액이 1000원 이하가 되는 경우
			 */
			/*Resource[] resources = patternResolver.getResources("classpath:/mock/rule_a.json");
			objectMapper.registerModule(new JavaTimeModule());
			List<KaMoneyEventLog> roleALogs = objectMapper.readValue(resources[0].getInputStream(), new TypeReference<List<KaMoneyEventLog>>(){});*/
			User user = userService.createUser(User.generate("TestId", "Test"));
			Account account = accountService.createAccount(new Account(BankType.KB, 1234L, 1000000L));
			KaMoney kaMoney = kaMoneyFacade.openKaMoney(user, account);
			kaMoneyFacade.chargeKaMoney(user, 210000);

			User toUser = userService.createUser(User.generate("toUser", "test"));
			Account toAccount = accountService.createAccount(new Account(BankType.KB, 1235L, 0L));
			kaMoneyFacade.openKaMoney(toUser, toAccount);
			kaMoneyFacade.remittanceKaMoney(user, toUser, 210000);
		};
	}
}
