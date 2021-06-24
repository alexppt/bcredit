package org.bcredit.core.component.credit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreditItem {

    private String id;

    private Integer type;

    private Double amount;
}
