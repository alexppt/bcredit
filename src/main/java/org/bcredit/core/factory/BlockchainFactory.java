package org.bcredit.core.factory;

import java.util.List;
import javax.annotation.Resource;

import org.bcredit.core.component.blockchain.Blockchain;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class BlockchainFactory {

    @Value("${blockchain.name}")
    private String blockchainName;

    @Resource
    private List<Blockchain> blockchains;

    public Blockchain create() {
        for (Blockchain blockchain : blockchains) {
            if (blockchain.getClass().getSimpleName().equals(blockchainName + "Blockchain")) {
                return blockchain;
            }
        }

        return null;
    }
}
