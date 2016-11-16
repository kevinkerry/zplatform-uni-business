package com.zlebank.zplatform.business.memberCard.bean;

import java.io.Serializable;

public class QuerySupportedCardResultBean  implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Bank bank;
    private String cardType;
    public Bank getBank() {
        return bank;
    }
    public void setBank(Bank bank) {
        this.bank = bank;
    }
    public String getCardType() {
        return cardType;
    }
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }


}
