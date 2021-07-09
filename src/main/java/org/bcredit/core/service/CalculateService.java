package org.bcredit.core.service;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bcredit.core.component.credit.Calculator;
import org.bcredit.core.component.credit.CreditItem;
import org.bcredit.core.component.enumeration.CreditTypeEnum;
import org.bcredit.core.factory.CalculatorFactory;
import org.springframework.stereotype.Service;

@Service
public class CalculateService {

    @Resource
    private CreditService creditService;

    @Resource
    private CalculatorFactory calculatorFactory;

    private Calculator calculator;

    @PostConstruct
    public void init() {
        calculator = calculatorFactory.create();
    }

    public CreditItem calculateCreditScore(String id) {
        List<CreditItem> creditItems = creditService.query(id, false);
        Integer creditScore = calculator.calculate(creditItems);
        CreditItem creditItem = new CreditItem();
        creditItem.setId(id);
        creditItem.setType(CreditTypeEnum.CREDIT_SCORE.getCode());
        creditItem.setScore(creditScore);
        creditItem.setTimestamp(System.currentTimeMillis());
        creditService.createCreditItem(creditItem);

        return creditItem;
    }
}
