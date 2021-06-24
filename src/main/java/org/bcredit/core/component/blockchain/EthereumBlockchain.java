package org.bcredit.core.component.blockchain;

import java.util.List;

import javax.annotation.Resource;

import org.bcredit.core.service.Web3jService;
import org.springframework.stereotype.Component;

@Component
public class EthereumBlockchain implements Blockchain {

    @Resource
    private Web3jService web3jService;

    @Override
    public void store(String creditItemJson) {

    }

    @Override
    public List<String> query(String id) {
        return null;
    }
}
