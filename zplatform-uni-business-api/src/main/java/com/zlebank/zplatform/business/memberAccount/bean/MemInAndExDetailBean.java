package com.zlebank.zplatform.business.memberAccount.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class MemInAndExDetailBean implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 904576437009401473L;
    /**收支类型  00：收入； 01：支出 **/
    private String budgetType;
    /** 金额 **/
    private BigDecimal txnAmt ;
    /** 时间**/
    private String txnTime;

    public BigDecimal getTxnAmt() {
        return txnAmt;
    }
    public void setTxnAmt(BigDecimal txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getBudgetType() {
        return budgetType;
    }
    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }
    public String getTxnTime() {
        return txnTime;
    }
    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }


}
