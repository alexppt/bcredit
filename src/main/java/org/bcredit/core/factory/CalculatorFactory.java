package org.bcredit.core.factory;

import java.util.List;
import javax.annotation.Resource;

import org.bcredit.core.component.credit.Calculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class CalculatorFactory {

    @Value("${calculator.name}")
    private String calculatorName;

    @Resource
    private List<Calculator> calculators;

    public Calculator create() {
        for (Calculator calculator : calculators) {
            if (calculator.getClass().getSimpleName().equals(calculatorName + "Calculator")) {
                return calculator;
            }
        }

        return null;
    }
}
