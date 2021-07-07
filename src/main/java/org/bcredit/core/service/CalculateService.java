package org.bcredit.core.service;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bcredit.core.component.credit.Calculator;
import org.bcredit.core.component.credit.CreditItem;
import org.bcredit.core.factory.CalculatorFactory;
import org.springframework.stereotype.Service;

import static org.bcredit.core.component.constant.CreditConstant.creditScoreType;

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
        creditItem.setType(creditScoreType);
        creditItem.setScore(creditScore);
        creditItem.setTimestamp(System.currentTimeMillis());
        creditService.createCreditItem(creditItem);

        return creditItem;
    }
}
