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
        return creditService.query(id, false);
    }

    public List<CreditItem> queryCreditScore(String id) {
        return creditService.query(id, true);
    }

    public CreditItem calculateCreditScore(String id) {
        return calculateService.calculateCreditScore(id);
    }
}
