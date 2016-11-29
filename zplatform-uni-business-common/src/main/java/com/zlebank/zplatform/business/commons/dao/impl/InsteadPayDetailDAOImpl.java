package com.zlebank.zplatform.business.commons.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.business.commons.dao.InsteadPayDetailDAO;
import com.zlebank.zplatform.business.commons.dao.base.HibernateBaseDAOImpl;
import com.zlebank.zplatform.business.commons.enums.InsteadPayDetailStatusEnum;
import com.zlebank.zplatform.business.pojo.PojoInsteadPayDetail;
@Repository("insteadPayDetailDAO")
public class InsteadPayDetailDAOImpl extends HibernateBaseDAOImpl<PojoInsteadPayDetail> implements InsteadPayDetailDAO {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public List<PojoInsteadPayDetail> getByBatchDetail(String batchNo) {
		 Criteria crite = this.getSession().createCriteria(
	                PojoInsteadPayDetail.class);
        crite.add(Restrictions.eq("batchNo", batchNo));
        return crite.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public List<PojoInsteadPayDetail> getBatchDetailByBatchId(Long batchId) {
		 Criteria crite = this.getSession().createCriteria(
                 PojoInsteadPayDetail.class);
         crite.add(Restrictions.eq("insteadPayBatch.id", batchId));
         crite.add(Restrictions.eq("status", InsteadPayDetailStatusEnum.WAIT_INSTEAD_APPROVE.getCode()));
         return crite.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public List<PojoInsteadPayDetail> getBatchDetailByIds(List<Long> ids) {
		Criteria crite = this.getSession().createCriteria(
                PojoInsteadPayDetail.class);
        crite.add(Restrictions.in("id", ids));
        crite.add(Restrictions.eq("status", InsteadPayDetailStatusEnum.WAIT_INSTEAD_APPROVE.getCode()));
        return crite.list();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void updateBatchDetailResult(PojoInsteadPayDetail insteadPayDetail) {
		 // TODO Auto-generated method stub
        try {
            String hql = "update PojoInsteadPayDetail set status = ?,channelCode = ?,batchFileNo = ?,respCode = ?,respMsg = ? where merId = ? and orderId = ?";
            Session session = getSession();
            Query query = session.createQuery(hql);
            query.setString(0, insteadPayDetail.getStatus());
            query.setString(1, insteadPayDetail.getChannelCode());
            query.setString(2, insteadPayDetail.getBatchFileNo());
            query.setString(3, insteadPayDetail.getRespCode());
            query.setString(4, insteadPayDetail.getRespMsg());
            query.setString(5, insteadPayDetail.getMerId());
            query.setString(6, insteadPayDetail.getOrderId());
            query.executeUpdate();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	@Override
	@Transactional(readOnly=true)
	public PojoInsteadPayDetail getDetailByTxnseqno(String txnseqno, String status) {
		 Criteria crite = this.getSession().createCriteria(
	                PojoInsteadPayDetail.class);
        crite.add(Restrictions.eq("txnseqno", txnseqno));
        crite.add(Restrictions.eq("status", status));
        return (PojoInsteadPayDetail)crite.uniqueResult();
	}

	@Override
	@Transactional(readOnly=true)
	public PojoInsteadPayDetail getDetailByTxnseqno(String txnseqno) {
		 Criteria crite = this.getSession().createCriteria(
	                PojoInsteadPayDetail.class);
        crite.add(Restrictions.eq("txnseqno", txnseqno));
        return (PojoInsteadPayDetail)crite.uniqueResult();
	}

	@Override
	@Transactional(readOnly=true)
	public boolean isBatchProcessFinished(Long batchId) {
		 Criteria crite = this.getSession().createCriteria(
                 PojoInsteadPayDetail.class);
         crite.add(Restrictions.eq("insteadPayBatch.id", batchId));
         String[] codes = new String[]{"00", "01","02","03","04","09"};
         crite.add(Restrictions.not(Restrictions.in("respCode", codes)));
         crite.setProjection(Projections.rowCount());  
         int count = Integer.parseInt(crite.uniqueResult().toString());
         return count > 0 ? false : true;
	}
	

	

}
