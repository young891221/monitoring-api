package com.monitoring.api;

import com.monitoring.api.facade.KaMoneyFacade;
import com.monitoring.api.service.AccountService;
import com.monitoring.api.service.RuleLogService;
import com.monitoring.api.service.UserService;
import com.monitoring.api.test.TestIntegrationModule;
import com.monitoring.api.test.TestMockModule;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	/**
	 * 애플리케이션 동작시 테스트 Mock 데이터를 위해 CommandLineRunner 동작하도록 작성
	 */
	@Bean
	public CommandLineRunner runner(UserService userService, AccountService accountService, KaMoneyFacade kaMoneyFacade, RuleLogService ruleLogService) {
		return (args) -> {
			TestIntegrationModule testIntegrationModule = new TestIntegrationModule(userService, accountService, kaMoneyFacade);
			//testIntegrationModule.testRoleAProcess();
			//testIntegrationModule.testRoleBProcess();
			//testIntegrationModule.testRoleCProcess();

			TestMockModule testMockModule = new TestMockModule(userService, ruleLogService);
			//testMockModule.testRoleAProcess();
			//testMockModule.testRoleBProcess();
			//testMockModule.testRoleCProcess();
		};
	}


}
