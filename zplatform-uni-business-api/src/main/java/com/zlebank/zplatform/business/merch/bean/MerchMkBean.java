package com.zlebank.zplatform.business.merch.bean;

import java.io.Serializable;

public class MerchMkBean implements Serializable{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7668942559338279248L;
    private String memberId;
    private String safeSeq;
    private String safeType;
    private String memberPubKey;
    private String memberPriKey;
    private String localPubKey;
    private String localPriKey;
    private String status;
    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    public String getSafeSeq() {
        return safeSeq;
    }
    public void setSafeSeq(String safeSeq) {
        this.safeSeq = safeSeq;
    }
    public String getSafeType() {
        return safeType;
    }
    public void setSafeType(String safeType) {
        this.safeType = safeType;
    }
    public String getMemberPubKey() {
        return memberPubKey;
    }
    public void setMemberPubKey(String memberPubKey) {
        this.memberPubKey = memberPubKey;
    }
    public String getMemberPriKey() {
        return memberPriKey;
    }
    public void setMemberPriKey(String memberPriKey) {
        this.memberPriKey = memberPriKey;
    }
    public String getLocalPubKey() {
        return localPubKey;
    }
    public void setLocalPubKey(String localPubKey) {
        this.localPubKey = localPubKey;
    }
    public String getLocalPriKey() {
        return localPriKey;
    }
    public void setLocalPriKey(String localPriKey) {
        this.localPriKey = localPriKey;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
