package org.bcredit.core.component.credit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditFactor {

    private String id;

    private String type;

    private String amount;

    private String isOverdue;

    private String timestamp;
}
