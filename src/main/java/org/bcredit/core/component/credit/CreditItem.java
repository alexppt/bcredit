package org.bcredit.core.component.credit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CreditItem {

    private String id;

    /**
     * 1-credit score, 2-payment order, 3-repay credit card, 4-career information, 5-social relationship
     */
    private Integer type;

    private Double amount;

    private Boolean isOverdue;

    private Integer score;

    private Long timestamp;
}
