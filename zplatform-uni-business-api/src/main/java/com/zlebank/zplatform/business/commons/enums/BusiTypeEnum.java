package com.zlebank.zplatform.business.commons.enums;


public enum BusiTypeEnum {
	 /**消费**/
    CONSUME("1000"),
    /**充值**/
    RECHARGE("2000"),
    /**提现**/
    WITHDRAWALS("3000"),
    /**退款**/
    REFUND("4000"),
    /**转账**/
    TRANSFER("5000"),
    /**代付**/
    INSTEADPAY("7000"),
	 /**保障金**/
    SECURITY("6000"),
    /**未知**/
    UNKNOW("9999");
	private String busiType;
    
    private BusiTypeEnum(String busiType){
        this.busiType = busiType;
    }
    
    public static BusiTypeEnum fromValue(String value) {
        for(BusiTypeEnum busi:values()){
            if(busi.busiType.equals(value)){
                return busi;
            }
        }
        return UNKNOW;
    }
    
    public String getBusiType(){
        return busiType;
    }
}
