/* 
 * IRouteConfigDAO.java  
 * 
 * version TODO
 *
 * 2015年9月2日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.business.commons.dao;

import java.util.Map;

import org.hibernate.Session;

import com.zlebank.zplatform.business.pojo.RouteConfigModel;
import com.zlebank.zplatform.payment.commons.bean.ResultBean;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月2日 下午12:07:23
 * @since 
 */
public interface RouteConfigDAO extends BaseDAO<RouteConfigModel>{
	/***
	 * 获取路由信息
	 * @param merchRoutver
	 * @param transTime
	 * @param transAmt
	 * @param memberId
	 * @param busiCode
	 * @param cardNo
	 * @param cashCode
	 * @return
	 */
    public ResultBean getTransRout(String merchRoutver,String transTime,String transAmt,String memberId,String busiCode,String cardNo,String cashCode);
    /***
     * 根据卡号获取卡信息
     * @param cardNo
     * @return
     */
    public Map<String, Object> getCardInfo(String cardNo);
    /***
     * 获取路由信息
     * @param merchRoutver
     * @param transTime
     * @param transAmt
     * @param memberId
     * @param busiCode
     * @param cardNo
     * @return
     */
    public ResultBean getWapTransRout(String merchRoutver,String transTime,String transAmt,String memberId,String busiCode,String cardNo);
    /**
     * 通过卡号获取人行的联行号
     * @param cardNo
     * @return
     */
    public Map<String, Object> getCardPBCCode(String cardNo);
    
    /**
     * 获取银行通用名称，
     * @param bankCode 总行的联行号
     * @return
     */
    public String getBankName(String bankCode);
}
