package org.bcredit.core.component.credit;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.bcredit.core.component.enumeration.CreditTypeEnum;
import org.springframework.stereotype.Component;

@Component
public class WeightCalculator implements Calculator {

    private static final HashMap<Integer, Integer> typeToWeight = new HashMap<Integer, Integer>() {
        {
            put(CreditTypeEnum.PAYMENT_ORDER.getCode(), 5);
            put(CreditTypeEnum.REPAY_CREDIT_CARD.getCode(), 10);
            put(CreditTypeEnum.CAREER_INFORMATION.getCode(), 20);
            put(CreditTypeEnum.SOCIAL_RELATIONSHIP.getCode(), 20);
        }
    };

    private static final int UPPER_BOUDARY = 850;

    private static final int LOWER_BOUDARY = 350;

    @Override
    public Integer calculate(List<CreditItem> creditItems) {
        int baseline = (UPPER_BOUDARY + LOWER_BOUDARY) / 2;

        for (CreditItem creditItem : creditItems) {
            int weight = typeToWeight.get(creditItem.getType());
            weight = Objects.equals(creditItem.getIsOverdue(), true) ? -weight : weight;
            baseline += weight;
        }

        baseline = Math.min(baseline, UPPER_BOUDARY);
        baseline = Math.max(baseline, LOWER_BOUDARY);

        return baseline;
    }
}
