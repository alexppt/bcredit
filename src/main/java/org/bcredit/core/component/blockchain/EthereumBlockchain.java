package org.bcredit.core.component.blockchain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.tomcat.util.buf.HexUtils;
import org.bcredit.core.service.Web3jService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.utils.Convert;
import org.web3j.utils.Strings;

@Component
public class EthereumBlockchain implements Blockchain {

    @Value("${wallet.address.from}")
    private String from;

    @Value("${wallet.address.to}")
    private String to;

    @Resource
    private Web3jService web3jService;

    @Override
    public void store(String creditItemJson) {
        BigDecimal amount = new BigDecimal("0.000001");
        BigInteger value = Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();
        String data = null;

        try {
            data = HexUtils.toHexString(creditItemJson.getBytes("UTF-8"));
        } catch (Exception e) {

        }

        Transaction transaction = new Transaction(from, null, null, null, to, value, data);
        web3jService.transact(transaction);
    }

    @Override
    public List<String> query(String id) {
        List<EthBlock.TransactionObject> trans = web3jService.getAllTransactions();
        if (trans == null || trans.isEmpty()) {
            return null;
        }

        List<String> jsonStr = new ArrayList<>();
        for (EthBlock.TransactionObject tran : trans) {
            String input = tran.getInput();
            if (Strings.isEmpty(input) || "0x".equals(input)) {
                continue;
            }

            String inputJson = hex2str(input);
            if ((!Strings.isEmpty(id) && !inputJson.contains(id))) {
                continue;
            }

            jsonStr.add(hex2str(input));
        }

        return jsonStr;
    }

    private String hex2str(String hex) {
        hex = hex.substring(2); // remove '0x'
        ByteBuffer buff = ByteBuffer.allocate(hex.length() / 2);
        for (int i = 0; i < hex.length(); i += 2) {
            buff.put((byte) Integer.parseInt(hex.substring(i, i + 2), 16));
        }
        buff.rewind();
        Charset cs = Charset.forName("UTF-8");
        CharBuffer cb = cs.decode(buff);
        return cb.toString();
    }
}
