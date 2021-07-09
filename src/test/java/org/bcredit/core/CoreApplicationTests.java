package org.bcredit.core;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.bcredit.core.component.credit.CreditItem;
import org.bcredit.core.component.credit.WeightCalculator;
import org.bcredit.core.component.enumeration.CreditTypeEnum;
import org.bcredit.core.service.CreditService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CoreApplicationTests {

    @Resource
    private CreditService creditService;

    @Resource
    private WeightCalculator weightCalculator;

    @Test
    void testAddCreditItem() {
        CreditItem creditItem = new CreditItem();
        creditItem.setId("F123456(0)");
        creditItem.setType(2);
        creditItem.setAmount(1000d);
        creditItem.setIsOverdue(false);
        creditItem.setTimestamp(System.currentTimeMillis());

        creditService.createCreditItem(creditItem);
    }

    @Test
    void testQueryById() {
        List<CreditItem> creditItems = creditService.query("F123456(0)", true);
        Assert.assertNotNull(creditItems);
    }

    @Test
    void testCalculateScore() {
        List<CreditItem> creditItems = new ArrayList<>();
        creditItems.add(buildCreditItem("F123456(0)", CreditTypeEnum.PAYMENT_ORDER.getCode(), 23d, false));
        creditItems.add(buildCreditItem("F123456(0)", CreditTypeEnum.REPAY_CREDIT_CARD.getCode(), 100d, false));
        creditItems.add(buildCreditItem("F123456(0)", CreditTypeEnum.REPAY_CREDIT_CARD.getCode(), 50d, false));
        creditItems.add(buildCreditItem("F123456(0)", CreditTypeEnum.REPAY_CREDIT_CARD.getCode(), 200d, true));
        creditItems.add(buildCreditItem("F123456(0)", CreditTypeEnum.CAREER_INFORMATION.getCode(), 23d, false));
        creditItems.add(buildCreditItem("F123456(0)", CreditTypeEnum.SOCIAL_RELATIONSHIP.getCode(), 23d, false));

        Integer creditScore = weightCalculator.calculate(creditItems);

        Assert.assertEquals(655L, creditScore.longValue());
    }

    private CreditItem buildCreditItem(String id, Integer type, Double amount, Boolean overdue) {
        CreditItem creditItem = new CreditItem();
        creditItem.setId(id);
        creditItem.setType(type);
        creditItem.setAmount(amount);
        creditItem.setIsOverdue(overdue);
        creditItem.setTimestamp(System.currentTimeMillis());

        return creditItem;
    }
}
