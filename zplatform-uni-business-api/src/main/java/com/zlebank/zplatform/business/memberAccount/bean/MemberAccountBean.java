package com.zlebank.zplatform.business.memberAccount.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class MemberAccountBean implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4034159287672312169L;
    /** 业务号 */
    private String busiCode;
    /** 余额 **/
    private BigDecimal balance;
    /** 冻结余额 **/
    private BigDecimal frozenBalance;
    /** 总余额 **/
    private BigDecimal totalBalance;
    /** 状态 */
    private String status;
    /** 用途 */
    private String usage;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    

    public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public BigDecimal getFrozenBalance() {
        return frozenBalance;
    }

    public void setFrozenBalance(BigDecimal frozenBalance) {
        this.frozenBalance = frozenBalance;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }
    


}
