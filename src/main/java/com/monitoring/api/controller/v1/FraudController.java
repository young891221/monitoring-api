package com.monitoring.api.controller.v1;

import com.monitoring.api.dto.RuleLogDto;
import com.monitoring.api.facade.RuleLogFacade;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by young891221@gmail.com on 2018-03-25
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
@RestController
@RequestMapping("/v1/fraud")
public class FraudController {

    private RuleLogFacade ruleLogFacade;

    public FraudController(RuleLogFacade ruleLogFacade) {
        this.ruleLogFacade = ruleLogFacade;
    }

    @GetMapping("/{userId}")
    public RuleLogDto fraudUser(@PathVariable long userId) {
        return ruleLogFacade.findRuleLogByUserId(userId);
    }
}
