package org.bcredit.core;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
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
        creditItem.setId("F654321(0)");
        creditItem.setType(2);
        creditItem.setAmount(1000d);
        creditItem.setIsOverdue(false);
        creditItem.setTimestamp(System.currentTimeMillis());

        creditService.createCreditItem(creditItem);
    }

    @Test
    void testDecodeInput() {
        String hex = "0x7b226964223a2246363534333231283029222c2274797065223a322c22616d6f756e74223a313030302e302c2269734f766572647565223a66616c73652c2274696d657374616d70223a313632363334313136353436327d";
        hex = hex.substring(2); // remove '0x'
        ByteBuffer buff = ByteBuffer.allocate(hex.length() / 2);
        for (int i = 0; i < hex.length(); i += 2) {
            buff.put((byte) Integer.parseInt(hex.substring(i, i + 2), 16));
        }
        buff.rewind();
        Charset cs = Charset.forName("UTF-8");
        CharBuffer cb = cs.decode(buff);
        System.out.println(cb.toString());
    }

    @Test
    void testQueryById() {
        List<CreditItem> creditItems = creditService.query("F654321(0)", false);
        Assert.assertNotNull(creditItems);
    }

    @Test
    void testCalculateScore() {
        List<CreditItem> creditItems = new ArrayList<>();
        creditItems.add(buildCreditItem("F123456(0)", CreditTypeEnum.PAYMENT_ORDER.getCode(), 23d, null));
        creditItems.add(buildCreditItem("F123456(0)", CreditTypeEnum.REPAY_CREDIT_CARD.getCode(), 100d, false));
        creditItems.add(buildCreditItem("F123456(0)", CreditTypeEnum.REPAY_CREDIT_CARD.getCode(), 50d, false));
        creditItems.add(buildCreditItem("F123456(0)", CreditTypeEnum.REPAY_CREDIT_CARD.getCode(), 200d, true));
        creditItems.add(buildCreditItem("F123456(0)", CreditTypeEnum.CAREER_INFORMATION.getCode(), null, null));
        creditItems.add(buildCreditItem("F123456(0)", CreditTypeEnum.SOCIAL_RELATIONSHIP.getCode(), null, null));

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
