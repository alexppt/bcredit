package org.bcredit.core.service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.google.gson.Gson;
import org.bcredit.core.component.blockchain.Blockchain;
import org.bcredit.core.component.credit.CreditItem;
import org.bcredit.core.factory.BlockchainFactory;
import org.springframework.stereotype.Service;

@Service
public class CreditService {

    private static final Integer creditScoreType = 1;

    @Resource
    private BlockchainFactory blockchainFactory;

    private Blockchain blockchain;

    @PostConstruct
    public void init() {
        blockchain = blockchainFactory.create();
    }

    public void createCreditItem(CreditItem creditItem) {
        Gson gson = new Gson();
        String json = gson.toJson(creditItem);
        blockchain.store(json);
    }

    public List<CreditItem> query(String id, Boolean isScoreType) {
        List<String> jsonList = blockchain.query(id);
        if (jsonList == null || jsonList.isEmpty()) {
            return null;
        }

        List<CreditItem> creditItems = new ArrayList<>();
        for (String json : jsonList) {
            Gson gson = new Gson();
            CreditItem creditItem = gson.fromJson(json, CreditItem.class);

            if (isScoreType != null) {
                if (isScoreType && !creditScoreType.equals(creditItem.getType())) {
                    continue;
                }

                if (!isScoreType && creditScoreType.equals(creditItem.getType())) {
                    continue;
                }
            }

            creditItems.add(creditItem);
        }

        return creditItems;
    }
}
