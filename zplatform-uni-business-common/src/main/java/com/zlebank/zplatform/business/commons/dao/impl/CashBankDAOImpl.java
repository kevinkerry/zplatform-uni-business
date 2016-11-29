package com.zlebank.zplatform.business.commons.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.business.commons.dao.CashBankDAO;
import com.zlebank.zplatform.business.commons.dao.base.HibernateBaseDAOImpl;
import com.zlebank.zplatform.business.pojo.PojoCashBank;
@Repository("cashBankDAO")
public class CashBankDAOImpl extends HibernateBaseDAOImpl<PojoCashBank> implements CashBankDAO {
	@Transactional(readOnly=true)
	public List<PojoCashBank> getCardList(Map<String, Object> map, Integer pageNo, Integer pageSize) {
		StringBuffer hq = new StringBuffer();
		List<PojoCashBank> list =new ArrayList<PojoCashBank>();
		if(map!=null){
			//卡类型 1-借记卡 2=贷记卡
			String cardType = map.get("cardtype")==null ?"":map.get("cardtype").toString();
			//类型 01：快捷 02：网关
			String payType = map.get("paytype")==null ?"" :map.get("paytype").toString();
			//状态 00-在用
			String status = map.get("status")==null ?"00":map.get("status").toString();
			//支持业务类型
			String busiCode = map.get("busiCode")==null ?"" : map.get("busiCode").toString();
			//银行卡号
			String bankCode = map.get("bankCode") ==null?"": map.get("bankCode").toString();
			if(StringUtils.isNotEmpty(cardType)){
				hq.append("and cardtype=:cardtype ");
			}
			if(StringUtils.isNotEmpty(payType)){
				hq.append(" and paytype=:paytype ");
			}
			if(StringUtils.isNotEmpty(status)){
				hq.append(" and status =:status");
			}
			if(StringUtils.isNotEmpty(busiCode)){
				hq.append(" and buicode =:busicode ");
			}
			if(StringUtils.isNotEmpty(bankCode)){
				hq.append(" and bankcode=:bankcode ");
			}
			
			//查询列表
			StringBuffer listHql= new StringBuffer(" from  PojoCashBank where 1=1 ").append(hq);
	        
			Query query =getSession().createQuery(listHql.toString());
			
			if(StringUtils.isNotEmpty(cardType)){
				query.setParameter("cardtype", cardType);
			}
			if(StringUtils.isNotEmpty(payType)){
				query.setParameter("paytype", payType);
			}
			if(StringUtils.isNotEmpty(status)){
				query.setParameter("status", status);
			}
			if(StringUtils.isNotEmpty(busiCode)){
				query.setParameter("busiCode", busiCode);
			}
			if(StringUtils.isNotEmpty(bankCode)){
				query.setParameter("bankcode", bankCode);
			}
			if(pageSize!= null && pageNo !=null){
				query.setFirstResult(((pageNo==0?1:pageNo)-1)*pageSize);
		    	query.setMaxResults(pageSize);
			}
		   list= query.list();
		
		}
		return list;
	}
	@Transactional(readOnly=true)
	public int getTotal(Map<String, Object> map, Integer pageNo, Integer pageSize) {
		StringBuffer hq = new StringBuffer();
		int total =0 ;
		if(map!=null){
			//卡类型 1-借记卡 2=贷记卡
			String cardType = map.get("cardtype")==null ?"":map.get("cardtype").toString();
			//类型 01：快捷 02：网关
			String payType = map.get("paytype")==null ?"" :map.get("paytype").toString();
			//状态 00-在用
			String status = map.get("status")==null ?"00":map.get("status").toString();
			//支持业务类型
			String busiCode = map.get("busiCode")==null ?"" : map.get("busiCode").toString();
			//银行卡号
			String bankCode = map.get("bankCode") ==null?"": map.get("bankCode").toString();
			if(StringUtils.isNotEmpty(cardType)){
				hq.append("and cardtype=:cardtype ");
			}
			if(StringUtils.isNotEmpty(payType)){
				hq.append(" and paytype=:paytype ");
			}
			if(StringUtils.isNotEmpty(status)){
				hq.append(" and status =:status");
			}
			if(StringUtils.isNotEmpty(busiCode)){
				hq.append(" and buicode =:busicode ");
			}
			if(StringUtils.isNotEmpty(bankCode)){
				hq.append(" and bankcode=:bankcode ");
			}
			
			//查询总条数
			StringBuffer totalHql = new StringBuffer(" select count(*) from  PojoCashBank where 1=1 ").append(hq);
	        
			Query tquery = getSession().createQuery(totalHql.toString());
			
			if(StringUtils.isNotEmpty(cardType)){
				tquery.setParameter("cardtype", cardType);
			}
			if(StringUtils.isNotEmpty(payType)){
				tquery.setParameter("paytype", payType);
			}
			if(StringUtils.isNotEmpty(status)){
				tquery.setParameter("status", status);
			}
			if(StringUtils.isNotEmpty(busiCode)){
				tquery.setParameter("busiCode", busiCode);
			}
			if(StringUtils.isNotEmpty(bankCode)){
				tquery.setParameter("bankcode", bankCode);
			}
			total =((Number) tquery.iterate().next()).intValue();
		}
		return total;
	}
	@Transactional(readOnly=true)
	public PojoCashBank getBankICON(String bankCode) {
		Criteria criteria = getSession().createCriteria(PojoCashBank.class);
		criteria.add(Restrictions.like("bankcode", bankCode.substring(0,4)+"%"));
		List<PojoCashBank> resultList = criteria.list();
		if(resultList.size()>0){
			return resultList.get(0);
		}
		return null;
	}

	
	
}
