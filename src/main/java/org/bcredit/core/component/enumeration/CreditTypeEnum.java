package org.bcredit.core.component.enumeration;

public enum CreditTypeEnum {
    CREDIT_SCORE(1),
    PAYMENT_ORDER(2),
    REPAY_CREDIT_CARD(3),
    CAREER_INFORMATION(4),
    SOCIAL_RELATIONSHIP(5);

    private int code;

    CreditTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static CreditTypeEnum from(int code) {
        switch (code) {
            case 1:
                return CREDIT_SCORE;
            case 2:
                return PAYMENT_ORDER;
            case 3:
                return REPAY_CREDIT_CARD;
            case 4:
                return CAREER_INFORMATION;
            case 5:
                return SOCIAL_RELATIONSHIP;
            default:
                return null;
        }
    }
}
