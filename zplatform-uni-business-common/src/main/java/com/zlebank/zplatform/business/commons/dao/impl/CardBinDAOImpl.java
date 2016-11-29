/* 
 * CardBindDAOImpl.java  
 * 
 * version TODO
 *
 * 2016年6月22日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.business.commons.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.business.commons.bean.CardBin;
import com.zlebank.zplatform.business.commons.dao.CardBinDAO;
import com.zlebank.zplatform.business.commons.dao.base.HibernateBaseDAOImpl;
import com.zlebank.zplatform.business.pojo.PojoCardBin;
/***
 * liusm
 * @author SunXiaoshi
 *
 */
@Repository("cardBinDAO")
public class CardBinDAOImpl extends HibernateBaseDAOImpl<PojoCardBin> implements CardBinDAO{
	
	@Transactional(readOnly=true)
	public CardBin getCard(String cardNo) {
		StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("SELECT t.CARDBIN as cardBin,t.CARDLEN as cardLen,t.BINLEN as BINLEN   ");
        sqlBuffer.append(",t.CARDNAME as cardName, t.TYPE as Type,substr(trim(t.BANKCODE),0,4) as bankCode ,  b.bankname as bankName  ");
        sqlBuffer.append("FROM t_card_bin t  inner join  T_BANK_INSTI b on t.BANKCODE =b.BANKCODE ");
        sqlBuffer.append("WHERE ? LIKE t.cardbin || '%'  ");
        sqlBuffer.append("AND t.cardlen = ?  ");
        sqlBuffer.append("ORDER BY t.cardbin DESC  ");
        Session session=this.getSession();
        SQLQuery sqlquery=session.createSQLQuery(sqlBuffer.toString());
        sqlquery.setParameter(1, cardNo.intern().length());
        sqlquery.setParameter(0, cardNo);
        sqlquery .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> li=sqlquery.list();
        if(li.isEmpty())
            return null;
        CardBin cardBin=new CardBin();
        Map< String, Object> carBin= li.get(0);
        cardBin.setBinLen( carBin.get("BINLEN")!=null?String.valueOf(carBin.get("BINLEN")):null);
        cardBin.setCardBin( carBin.get("CARDBIN")!=null?String.valueOf(carBin.get("CARDBIN")):null);
        cardBin.setCardLen(carBin.get("CARDLEN")!=null?String.valueOf(carBin.get("CARDLEN")):null);
        cardBin.setCardName(carBin.get("CARDNAME")!=null?String.valueOf(carBin.get("CARDNAME")):null);
        cardBin.setType(   carBin.get("TYPE")!=null?String.valueOf(carBin.get("TYPE")):null);
        cardBin.setBankCode(carBin.get("BANKCODE")!=null?String.valueOf(carBin.get("BANKCODE")):null);
        cardBin.setBankName(carBin.get("BANKNAME")!=null?String.valueOf(carBin.get("BANKNAME")):null);
        return    cardBin;       
	}

	
	
	
}
