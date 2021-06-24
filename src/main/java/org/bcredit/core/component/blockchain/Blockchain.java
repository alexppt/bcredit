package org.bcredit.core.component.blockchain;

import java.util.List;

public interface Blockchain {

    void store(String creditItemJson);

    List<String> query(String id);
}
