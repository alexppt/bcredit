package org.bcredit.core.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.bcredit.core.component.credit.CreditFactor;
import org.bcredit.core.component.credit.CreditItem;
import org.bcredit.core.component.credit.CreditScore;
import org.bcredit.core.component.enumeration.CreditTypeEnum;
import org.bcredit.core.manager.CreditManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreditController {

    @Resource
    private CreditManager creditManager;

    @RequestMapping(value = "queryCreditFactorsById", method = RequestMethod.GET)
    public List<CreditFactor> queryCreditFactorsById(String id) {
        List<CreditItem> creditItems = creditManager.queryCreditFactor(id);

        if (creditItems == null || creditItems.isEmpty()) {
            return null;
        }

        return creditItems.stream().map(this::buildCreditFactor).collect(Collectors.toList());
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
    public List<CreditScore> queryCreditScoreById(String id) {
        List<CreditItem> creditItems = creditManager.queryCreditScore(id);

        if (creditItems == null || creditItems.isEmpty()) {
            return null;
        }

        return creditItems.stream().map(this::buildCreditScore).collect(Collectors.toList());
    }

    @RequestMapping(value = "calculateCreditScoreById", method = RequestMethod.GET)
    public CreditScore calculateCreditScoreById(String id) {
        CreditItem creditItem = creditManager.calculateCreditScore(id);
        if (creditItem == null) {
            return null;
        }

        return buildCreditScore(creditItem);
    }

    private CreditScore buildCreditScore(CreditItem creditItem) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        CreditScore creditScore = new CreditScore();
        creditScore.setId(creditItem.getId());
        creditScore.setScore(creditItem.getScore());
        if (creditItem.getTimestamp() != null) {
            Date date = new Date(creditItem.getTimestamp());
            creditScore.setTimestamp(sdf.format(date));
        }

        return creditScore;
    }

    private CreditFactor buildCreditFactor(CreditItem creditItem) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        CreditFactor creditFactor = new CreditFactor();
        creditFactor.setId(creditItem.getId());
        creditFactor.setAmount(creditItem.getAmount() == null ? "N/A" : String.valueOf(creditItem.getAmount()));
        if (creditItem.getIsOverdue() != null) {
            creditFactor.setIsOverdue(creditItem.getIsOverdue() ? "Yes" : "No");
        } else {
            creditFactor.setIsOverdue("N/A");
        }
        creditFactor.setType(CreditTypeEnum.from(creditItem.getType()).toString());
        if (creditItem.getTimestamp() != null) {
            Date date = new Date(creditItem.getTimestamp());
            creditFactor.setTimestamp(sdf.format(date));
        }

        return creditFactor;
    }
}
