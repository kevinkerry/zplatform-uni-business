	package com.zlebank.zplatform.business.commons.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.business.commons.dao.TxncodeDefDAO;
import com.zlebank.zplatform.business.commons.dao.base.HibernateBaseDAOImpl;
import com.zlebank.zplatform.business.pojo.PojoTxncodeDef;
@Repository("txncodeDefDAO")
public class TxncodeDefDAOImpl extends HibernateBaseDAOImpl<PojoTxncodeDef> implements TxncodeDefDAO {
	    
	@Override
	public Session getSession() {
		return super.getSession();
	}
	
	
	@Transactional(readOnly=true)
    public PojoTxncodeDef getBusiCode(String txntype,String txnsubtype,String biztype){
		Criteria crite= this.getSession().createCriteria(PojoTxncodeDef.class);
        if (StringUtils.isNotEmpty(txntype))
            crite.add(Restrictions.eq("txntype", txntype));
        if (StringUtils.isNotEmpty(txnsubtype))
            crite.add(Restrictions.eq("txnsubtype", txnsubtype));
        if (StringUtils.isNotEmpty(biztype))
            crite.add(Restrictions.eq("biztype", biztype));
        return (PojoTxncodeDef) crite.uniqueResult();
    }
}
