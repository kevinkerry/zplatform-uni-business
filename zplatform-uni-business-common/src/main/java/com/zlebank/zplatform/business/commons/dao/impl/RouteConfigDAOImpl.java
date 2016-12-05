package com.zlebank.zplatform.business.commons.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.business.commons.dao.RouteConfigDAO;
import com.zlebank.zplatform.business.commons.dao.base.HibernateBaseDAOImpl;
import com.zlebank.zplatform.business.pojo.RouteConfigModel;
import com.zlebank.zplatform.payment.commons.bean.ResultBean;
@Repository("routeConfigDAO")
public class RouteConfigDAOImpl extends HibernateBaseDAOImpl<RouteConfigModel> implements RouteConfigDAO  {
	private final static Logger log = LoggerFactory.getLogger(RouteConfigDAOImpl.class);
	@Override
	public ResultBean getTransRout(String merchRoutver,String transTime, String transAmt, String memberId, String busiCode, String cardNo,
			String cashCode) {
        try {
        	/*String merchRoutver = null;
        	if(memberId.startsWith("3")){//合作机构
        		merchRoutver = getDefaultVerInfo(memberId,busiCode,20);
        	}else{//商户
        		PojoMerchDeta merch = merchService.getMerchBymemberId(memberId);
        		merchRoutver = merch.getRoutVer();
        	}*/
            if (log.isDebugEnabled()) {
                log.debug("transTime：" + transTime);
                log.debug("transAmt：" + transAmt);
                log.debug("memberId：" + memberId);
                log.debug("busiCode：" + busiCode);
                log.debug("cardNo：" + cardNo);
                log.debug("cashCode：" + cashCode);
                log.debug("merchRoutver：" + merchRoutver);
            }
            String bankcode = null;
            String cardType = null;
            if(StringUtils.isNotEmpty(cardNo)){
                Map<String, Object> cardInfoMap = getCardInfo(cardNo);
                if(cardInfoMap==null){
                    return new ResultBean("RC23", "银行卡验证失败 ");
                }
                bankcode = cardInfoMap.get("BANKCODE").toString();
                cardType = cardInfoMap.get("TYPE").toString();
            }
            StringBuffer sqlBuffer = new StringBuffer();
            List<Object> paramList = new ArrayList<Object>();
            sqlBuffer = new StringBuffer();
            sqlBuffer.append("SELECT rt ");
            sqlBuffer.append("FROM (SELECT t.rid AS rid, t.ROUTVER AS rt ");
            sqlBuffer.append("FROM t_route_config t ");
            sqlBuffer.append("WHERE 1=1 ");
            if(StringUtils.isNotEmpty(cashCode)){
                sqlBuffer.append("AND t.CASHCODE = ? ");
                paramList.add(cashCode);
            }
            if(StringUtils.isNotEmpty(merchRoutver)){
                sqlBuffer.append("AND t.merchroutver = ? ");
                paramList.add(merchRoutver);
            }
            sqlBuffer.append("AND t.status = '00' ");
            sqlBuffer.append("AND t.stime <= ? ");
            paramList.add(transTime);
            sqlBuffer.append("AND t.etime > ? ");
            paramList.add(transTime);
            sqlBuffer.append("AND t.minamt <= ? ");
            paramList.add(transAmt);
            sqlBuffer.append("AND t.maxamt >= ? ");
            paramList.add(transAmt);
            
            sqlBuffer.append("AND t.isdef = '1' ");
            if (StringUtils.isNotEmpty(bankcode)) {
                sqlBuffer.append("AND (t.bankcode like ? or t.bankcode is null) ");
                paramList.add("%"+bankcode + ";%");
            }
            if (StringUtils.isNotEmpty(busiCode)) {
                sqlBuffer.append("AND  (t.busicode like ? or t.busicode is null) ");
                paramList.add("%"+busiCode + ";%");
            }
            if (StringUtils.isNotEmpty(cardType)) {
                sqlBuffer.append("AND (t.cardtype like ? or t.cardtype is null) ");
                paramList.add("%"+cardType + ";%");
            }
            sqlBuffer
                    .append(" ORDER BY t.ordertype, t.orders) t1 WHERE ROWNUM = 1");
            log.info("member rout sql:" + sqlBuffer);
            List<Map<String, Object>> memberRoutList = (List<Map<String, Object>>) super
                    .queryBySQL(sqlBuffer.toString(), paramList.toArray());
            if (memberRoutList.size() > 0) {
                log.info("member rout:" + memberRoutList.get(0));
                return new ResultBean(memberRoutList.get(0).get("RT"));
            } else {//查找当前路由版本下的默认路由
                sqlBuffer = new StringBuffer();
                sqlBuffer.append("SELECT t.ROUTVER as rout ");
                sqlBuffer.append("FROM t_route_config t ");
                sqlBuffer.append("WHERE t.merchroutver = ? ");
                sqlBuffer.append("AND t.status = '00' ");
                sqlBuffer.append("AND t.isdef = '0' ");
                log.info("member default rout sql:" + sqlBuffer);
                List<Map<String, Object>> defaultMemberRoutList = (List<Map<String, Object>>) super
                        .queryBySQL(sqlBuffer.toString(), new Object[]{merchRoutver});
                if (defaultMemberRoutList.size() > 0) {
                    log.info("member default rout:"
                            + defaultMemberRoutList.get(0));
                    return new ResultBean(defaultMemberRoutList.get(0).get("ROUT"));
                }
            }
        } catch (Exception e) {
            // TODO: handle exceptionInsteadBatchServiceImpl
           e.printStackTrace();
        }
        log.info("member "+memberId+" no find member rout!!!");
        return new ResultBean("RC99", "交易路由异常");
    }

	private ResultBean getDefaultVerInfo(String instiCode,String busicode,int verType) {
		List<Map<String, Object>> resultList = (List<Map<String, Object>>) super.queryBySQL("select COOP_INSTI_CODE,BUSI_CODE,VER_TYPE,VER_VALUE from T_NONMER_DEFAULT_CONFIG where COOP_INSTI_CODE=? and BUSI_CODE=? and VER_TYPE=?", new Object[]{instiCode,busicode,verType+""});
    	if(resultList.size()>0){
    		Map<String, Object> valueMap = resultList.get(0);
    		String value =valueMap.get("VER_VALUE").toString();
    		return new ResultBean(value);
    	}
		return new ResultBean("GW03", "获取不到默认路由");
	}

	@Override
	public Map<String, Object> getCardInfo(String cardNo) {
		  StringBuffer sqlBuffer = new StringBuffer();
	      sqlBuffer.append("SELECT type,bankcode,bankname ");
	      sqlBuffer.append("FROM (SELECT t.TYPE,t.BANKCODE,b.bankname ");
	      sqlBuffer.append("FROM t_card_bin t, t_bank_insti b ");
	      sqlBuffer.append("WHERE t.bankcode = b.bankcode ");
	      sqlBuffer.append("AND ? LIKE t.cardbin || '%' ");
	      sqlBuffer.append("AND t.cardlen = ? ");
	      sqlBuffer.append("ORDER BY t.cardbin DESC) ");
	      sqlBuffer.append("WHERE ROWNUM = 1 ");
	      List<Map<String, Object>> routList =  (List<Map<String, Object>>) super.queryBySQL(sqlBuffer.toString(), new Object[]{cardNo,cardNo.trim().length()});
	      if(routList.size()>0){
	          return routList.get(0);
	       }
	       return null;
	}

	@Override
	public ResultBean getWapTransRout(String merchRoutver,String transTimeStart, String transAmt, String memberId, String busiCode,
			String cardNo) {
        try {
        	/*if(StringUtil.isNotEmpty(memberId)){
        		if(memberId.startsWith("3")){//合作机构
            		merchRoutver = getDefaultVerInfo(memberId,busiCode,20);
            	}else{//商户
            		PojoMerchDeta merch = merchService.getMerchBymemberId(memberId);
            		merchRoutver = merch.getRoutVer();
            	}
        	}*/
    	    String transTime = transTimeStart.substring(8, 14);
            log.info("transTime：" + transTime);
            log.info("transAmt：" + transAmt);
            log.info("memberId：" + memberId);
            log.info("busiCode：" + busiCode);
            log.info("cardNo：" + cardNo);
            log.info("merchRoutver：" + merchRoutver);
            String bankcode = null;
            String cardType = null;
            if(StringUtils.isNotEmpty(cardNo)){
                Map<String, Object> cardInfoMap = getCardInfo(cardNo);
                if(cardInfoMap==null){
                    return new ResultBean("RC23", "银行卡验证失败");
                }
                bankcode = cardInfoMap.get("BANKCODE").toString();
                cardType = cardInfoMap.get("TYPE").toString();
            }
            
            
            StringBuffer sqlBuffer = new StringBuffer();
            List<Object> paramList = new ArrayList<Object>();
            sqlBuffer = new StringBuffer();
            sqlBuffer.append("SELECT rt ");
            sqlBuffer.append("FROM (SELECT t.rid AS rid, t.ROUTVER AS rt ");
            sqlBuffer.append("FROM t_route_config t ");
            sqlBuffer.append("WHERE 1=1 ");
            if(StringUtils.isNotEmpty(merchRoutver)){
                sqlBuffer.append("AND t.merchroutver = ? ");
                paramList.add(merchRoutver);
            }
            sqlBuffer.append("AND t.status = '00' ");
            sqlBuffer.append("AND t.stime <= ? ");
            paramList.add(transTime);
            sqlBuffer.append("AND t.etime > ? ");
            paramList.add(transTime);
            sqlBuffer.append("AND t.minamt <= ? ");
            paramList.add(transAmt);
            sqlBuffer.append("AND t.maxamt >= ? ");
            paramList.add(transAmt);
            sqlBuffer.append("AND t.isdef = '1' ");
            /*if (StringUtils.isNotEmpty(bankcode)) {
                sqlBuffer.append("AND t.bankcode like ? ");
                paramList.add("%"+bankcode + ";%");
            }
            if (StringUtils.isNotEmpty(busiCode)) {
                sqlBuffer.append("AND t.busicode like ? ");
                
                paramList.add("%"+busiCode + ";%");
            }
            if (StringUtils.isNotEmpty(cardType)) {
                sqlBuffer.append("AND t.cardtype like ? ");
                paramList.add("%"+cardType + ";%");
            }*/
            if (StringUtils.isNotEmpty(bankcode)) {
                sqlBuffer.append("AND (t.bankcode like ? or t.bankcode is null) ");
                paramList.add("%"+bankcode + ";%");
            }
            if (StringUtils.isNotEmpty(busiCode)) {
                sqlBuffer.append("AND  (t.busicode like ? or t.busicode is null) ");
                paramList.add("%"+busiCode + ";%");
            }
            if (StringUtils.isNotEmpty(cardType)) {
                sqlBuffer.append("AND (t.cardtype like ? or t.cardtype is null) ");
                paramList.add("%"+cardType + ";%");
            }
            sqlBuffer
                    .append(" ORDER BY t.ordertype, t.orders) t1 WHERE ROWNUM = 1");
            log.info("member rout sql:" + sqlBuffer);
            List<Map<String, Object>> memberRoutList = (List<Map<String, Object>>) super
                    .queryBySQL(sqlBuffer.toString(), paramList.toArray());
            if (memberRoutList.size() > 0) {
                log.info("member rout:" + memberRoutList.get(0));
                return new ResultBean(memberRoutList.get(0).get("RT"));
            } else {//查找当前路由版本下的默认路由
                sqlBuffer = new StringBuffer();
                sqlBuffer.append("SELECT t.ROUTVER as rout ");
                sqlBuffer.append("FROM t_route_config t ");
                sqlBuffer.append("WHERE t.merchroutver = ? ");
                sqlBuffer.append("AND t.status = '00' ");
                sqlBuffer.append("AND t.isdef = '0' ");
                log.info("member default rout sql:" + sqlBuffer);
                List<Map<String, Object>> defaultMemberRoutList = (List<Map<String, Object>>) super
                        .queryBySQL(sqlBuffer.toString(), new Object[]{merchRoutver});
                if (defaultMemberRoutList.size() > 0) {
                    log.info("member default rout:"
                            + defaultMemberRoutList.get(0));
                    return new ResultBean(defaultMemberRoutList.get(0).get("ROUT"));
                }
            }
        } catch (Exception e) {
           e.printStackTrace();
           log.error("查询路由失败"+e.getMessage());
        }
        log.info("member "+memberId+" no find member rout!!!");
        return new ResultBean("RC99", "交易路由异常");
    
	}
    
	@Override
	@Transactional(readOnly=true)
	public Map<String, Object> getCardPBCCode(String cardNo) {
		Map<String, Object> cardMap = getCardInfo(cardNo);
    	if(cardMap==null){
    		return null;
    	}
    	String sql = "select t.pbc_bankcode,t.bankname from t_bank_insti t where t.bankcode= ? and status = ?";
    	List<Map<String, Object>> valueList = (List<Map<String, Object>>) super.queryBySQL(sql, new Object[]{cardMap.get("BANKCODE"),"1"});
    	if(valueList.size()>0){
    		return valueList.get(0);
    	}
    	return null;
	}

	@Override
	@Transactional(readOnly=true)
	public String getBankName(String bankCode) {
		String sql = "select bank_name from t_bank_info  where bank_node = ?";
    	List<Map<String, Object>> valueList = (List<Map<String, Object>>) super.queryBySQL(sql, new Object[]{bankCode});
    	if(valueList.size()>0){
    		return valueList.get(0).get("BANK_NAME").toString();
    	}
		return null;
	}


}
