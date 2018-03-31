package com.monitoring.api;

import com.monitoring.api.facade.KaMoneyFacade;
import com.monitoring.api.facade.RuleLogFacade;
import com.monitoring.api.repository.KaMoneyEventLogRepository;
import com.monitoring.api.repository.RuleLogRepository;
import com.monitoring.api.repository.UserRepository;
import com.monitoring.api.service.AccountService;
import com.monitoring.api.service.RuleLogService;
import com.monitoring.api.service.UserService;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {

	public static final String TEST_ID = "young891221";
	public static final String TEST_NAME = "김영재";

	@Autowired
	public UserService userService;

	@Autowired
	public AccountService accountService;

	@Autowired
	public RuleLogService ruleLogService;

	@Autowired
	public KaMoneyFacade kaMoneyFacade;

	@Autowired
	public RuleLogFacade ruleLogFacade;

	@Autowired
	public KaMoneyEventLogRepository kaMoneyEventLogRepository;

}
