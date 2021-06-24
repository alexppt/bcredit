package org.bcredit.core.service;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bcredit.core.component.blockchain.Blockchain;
import org.bcredit.core.component.credit.CreditItem;
import org.bcredit.core.factory.BlockchainFactory;
import org.springframework.stereotype.Service;

@Service
public class CreditService {

    @Resource
    private BlockchainFactory blockchainFactory;

    private Blockchain blockchain;

    @PostConstruct
    public void init() {
        blockchain = blockchainFactory.create();
    }

    public void createCreditItem(CreditItem creditItem) {

    }

    public List<CreditItem> query(String id) {
        return null;
    }
}
