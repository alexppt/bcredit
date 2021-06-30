package org.bcredit.core.component.credit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreditItem {

    private String id;

    /**
     * 0-credit score, 1-payment order, 2-repay credit card, 3-career information, 4-social relationship
     */
    private Integer type;

    private Double amount;

    private Boolean isOverdue;

    private Integer score;
}
