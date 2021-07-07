package org.bcredit.core;

import java.util.List;
import javax.annotation.Resource;

import org.bcredit.core.component.credit.CreditItem;
import org.bcredit.core.service.CreditService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CoreApplicationTests {

    @Resource
    private CreditService creditService;

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
}
