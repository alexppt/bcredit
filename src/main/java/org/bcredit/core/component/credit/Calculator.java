package org.bcredit.core.component.credit;

import java.util.List;

public interface Calculator {

    Integer calculate(List<CreditItem> creditItems);
}
