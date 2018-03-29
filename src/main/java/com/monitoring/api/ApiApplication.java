package com.monitoring.api;

import com.monitoring.api.facade.KaMoneyFacade;
import com.monitoring.api.service.AccountService;
import com.monitoring.api.service.UserService;
import com.monitoring.api.test.TestModule;

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

	//TODO: 주석 및 에러처리 진행
	@Bean
	public CommandLineRunner runner(UserService userService, AccountService accountService, KaMoneyFacade kaMoneyFacade) {
		return (args) -> {
			TestModule testModule = new TestModule(userService, accountService, kaMoneyFacade);
			testModule.testRoleAProcess();
			testModule.testRoleBProcess();
		};
	}


}
