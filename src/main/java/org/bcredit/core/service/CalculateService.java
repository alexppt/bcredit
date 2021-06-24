package org.bcredit.core.service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bcredit.core.component.credit.Calculator;
import org.bcredit.core.factory.CalculatorFactory;
import org.springframework.stereotype.Service;

@Service
public class CalculateService {

    @Resource
    private CalculatorFactory calculatorFactory;

    private Calculator calculator;

    @PostConstruct
    public void init() {
        calculator = calculatorFactory.create();
    }
}
