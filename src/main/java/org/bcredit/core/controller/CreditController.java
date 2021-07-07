package org.bcredit.core.controller;

import java.util.List;

import javax.annotation.Resource;

import org.bcredit.core.component.credit.CreditItem;
import org.bcredit.core.manager.CreditManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreditController {

    @Resource
    private CreditManager creditManager;

    @RequestMapping("/getCreditScore")
    public Integer getCreditScore() {
        return 0;
    }

    @RequestMapping(value = "queryCreditFactorsById", method = RequestMethod.GET)
    public List<CreditItem> queryCreditFactorsById(String id) {
        return creditManager.queryCreditFactor(id);
    }

    @RequestMapping(value = "addCreditFactor", method = RequestMethod.POST)
    public String addCreditFactor(@RequestBody CreditItem creditItem) {
        try {
            creditManager.addCreditFacotr(creditItem);
        } catch (Exception e) {
            return "error";
        }

        return "success";
    }

    @RequestMapping(value = "queryCreditScoreById", method = RequestMethod.GET)
    public List<CreditItem> queryCreditScoreById(String id) {
        return creditManager.queryCreditScore(id);
    }
}
