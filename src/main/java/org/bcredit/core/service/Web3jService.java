package org.bcredit.core.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthBlock;

@Service
public class Web3jService {

    @Value("${wallet.address.passphrase}")
    private String passphrase;

    @Resource
    private Web3j web3j; // defaults to http://localhost:8545/

    @Resource
    private Admin admin;

    public void transact(Transaction transaction) {
        try {
            PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(transaction.getFrom(), passphrase).send();
            if (personalUnlockAccount.accountUnlocked()) {
                web3j.ethSendTransaction(transaction).send();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Transact failed, " + e.getMessage());
        }
    }

    public List<EthBlock.TransactionObject> getAllTransactions() {
        List<EthBlock.TransactionResult> trans = new ArrayList<>();
        try {
            BigInteger blockNumber = web3j.ethBlockNumber().send().getBlockNumber();

            for (long i = 0L; i <= blockNumber.longValue(); i++) {
                List<EthBlock.TransactionResult> txs = web3j.ethGetBlockByNumber(new DefaultBlockParameterNumber(i), true).send().getBlock().getTransactions();
                if (txs != null && !txs.isEmpty()) {
                    trans.addAll(txs);
                }
            }
        } catch (Exception e) {

        }

        if (!trans.isEmpty()) {
            return trans.stream().map(tx -> (EthBlock.TransactionObject) tx.get()).collect(Collectors.toList());
        }

        return null;
    }
}
