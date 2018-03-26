package com.monitoring.api.service;

import com.monitoring.api.domain.Account;
import com.monitoring.api.domain.enums.BankType;
import com.monitoring.api.repository.AccountRepository;

import org.springframework.stereotype.Service;

/**
 * Created by young891221@gmail.com on 2018-03-26
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
@Service
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * @return Account는 따로 요구사항이 없으므로 일정한 테스트 데이터를 넘긴다.
     */
    public Account createAccount() {
        return accountRepository.save(new Account(BankType.KB, 1234L));
    }
}
