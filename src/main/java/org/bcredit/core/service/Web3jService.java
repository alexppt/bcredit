package org.bcredit.core.service;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthBlock;

@Service
public class Web3jService {

    @Resource
    private Web3j web3j; // defaults to http://localhost:8545/

    @Resource
    private Admin admin;

    public void transact(Transaction transaction) {

    }

    public List<EthBlock.TransactionObject> queryTransactions(String id) {
        return null;
    }
}
