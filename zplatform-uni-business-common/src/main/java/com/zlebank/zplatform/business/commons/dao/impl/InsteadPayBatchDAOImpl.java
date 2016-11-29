package com.zlebank.zplatform.business.commons.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.business.commons.dao.InsteadPayBatchDAO;
import com.zlebank.zplatform.business.commons.dao.base.HibernateBaseDAOImpl;
import com.zlebank.zplatform.business.pojo.PojoInsteadPayBatch;
@Repository("insteadPayBatchDAO")
public class InsteadPayBatchDAOImpl extends HibernateBaseDAOImpl<PojoInsteadPayBatch> implements InsteadPayBatchDAO {

	@Override
	@Transactional(readOnly=true)
	public PojoInsteadPayBatch getByBatchId(Long batchId) {
		 Criteria crite= this.getSession().createCriteria(PojoInsteadPayBatch.class);
        crite.add(Restrictions.eq("id", batchId));
        return (PojoInsteadPayBatch) crite.uniqueResult();
	}

	@Override
	@Transactional(readOnly=true)
	public PojoInsteadPayBatch getByBatchNo(String batchNo, String txnTime) {
		Criteria crite= this.getSession().createCriteria(PojoInsteadPayBatch.class);
        crite.add(Restrictions.eq("batchNo", batchNo));
        crite.add(Restrictions.eq("txnTime", txnTime));
        return (PojoInsteadPayBatch) crite.uniqueResult();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void updateBatchResult(String batchNo, String status) {
		 try {
	            String hql = " update PojoInsteadPayBatch set status = ? where id = ? ";
	            Query query = getSession().createQuery(hql);
	            query.setString(0, status);
	            query.setString(1, batchNo);
	            query.executeUpdate();
	        } catch (HibernateException e) {
	            e.printStackTrace();
	        }
	}

	@Override
	@Transactional(readOnly=true)
	public PojoInsteadPayBatch getInsteadPayBatchByBatchNo(String batchNo, String merchId) {
		  Criteria crite= this.getSession().createCriteria(PojoInsteadPayBatch.class);
          crite.add(Restrictions.eq("batchNo", Long.parseLong(batchNo)));
          crite.add(Restrictions.eq("merId", merchId));
          return (PojoInsteadPayBatch) crite.uniqueResult();
	}

}
