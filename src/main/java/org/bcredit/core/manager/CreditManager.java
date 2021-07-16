package org.bcredit.core.manager;

import java.util.List;
import javax.annotation.Resource;

import org.bcredit.core.component.credit.CreditItem;
import org.bcredit.core.service.CalculateService;
import org.bcredit.core.service.CreditService;
import org.springframework.stereotype.Service;

@Service
public class CreditManager {

    @Resource
    private CreditService creditService;

    @Resource
    private CalculateService calculateService;

    public void addCreditFacotr(CreditItem creditItem) {
        creditService.createCreditItem(creditItem);
    }

    public List<CreditItem> queryCreditFactor(String id) {
        List<CreditItem> creditItems = creditService.query(id, false);

        if (creditItems == null || creditItems.isEmpty()) {
            return null;
        }

        creditItems.sort((left, right) -> Long.valueOf(right.getTimestamp() - left.getTimestamp()).intValue());
        return creditItems;
    }

    public List<CreditItem> queryCreditScore(String id) {
        List<CreditItem> creditItems = creditService.query(id, true);

        if (creditItems == null || creditItems.isEmpty()) {
            return null;
        }

        creditItems.sort((left, right) -> Long.valueOf(right.getTimestamp() - left.getTimestamp()).intValue());
        return creditItems;
    }

    public CreditItem calculateCreditScore(String id) {
        return calculateService.calculateCreditScore(id);
    }
}
