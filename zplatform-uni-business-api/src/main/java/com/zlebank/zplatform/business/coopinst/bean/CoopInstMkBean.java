package com.zlebank.zplatform.business.coopinst.bean;

import java.io.Serializable;

public class CoopInstMkBean  implements Serializable{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7532675218623895174L;
    private String coopInstiCode;
    /**rsa**/
    private String encryptAlgorithm;
    /***
     *  1:无线  2:个人门户 3:商户门户 4:WAP 5:开放接口
     */
    private String terminalAccessType;
    private String zplatformPriKey;
    private String zplatformPubKey;
    private String instiPriKey;
    private String instiPubKey;

    public CoopInstMkBean() {
    }

    public CoopInstMkBean(String coopInstiCode) {
        this.coopInstiCode = coopInstiCode;
    }

    public String getCoopInstiCode() {
        return coopInstiCode;
    }
    public String getEncryptAlgorithm() {
        return encryptAlgorithm;
    }
    public String getTerminalAccessType() {
        return terminalAccessType;
    }
    public String getZplatformPriKey() {
        return zplatformPriKey;
    }
    public String getZplatformPubKey() {
        return zplatformPubKey;
    }
    public String getInstiPriKey() {
        return instiPriKey;
    }
    public String getInstiPubKey() {
        return instiPubKey;
    }

    public void setEncryptAlgorithm(String encryptAlgorithm) {
        this.encryptAlgorithm = encryptAlgorithm;
    }

    public void setTerminalAccessType(String terminalAccessType) {
        this.terminalAccessType = terminalAccessType;
    }

    public void setZplatformPriKey(String zplatformPriKey) {
        this.zplatformPriKey = zplatformPriKey;
    }

    public void setZplatformPubKey(String zplatformPubKey) {
        this.zplatformPubKey = zplatformPubKey;
    }

    public void setInstiPriKey(String instiPriKey) {
        this.instiPriKey = instiPriKey;
    }

    public void setInstiPubKey(String instiPubKey) {
        this.instiPubKey = instiPubKey;
    }
}

