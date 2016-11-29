package com.zlebank.zplatform.business.commons.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.business.commons.dao.MerchWhiteListDAO;
import com.zlebank.zplatform.business.commons.dao.base.HibernateBaseDAOImpl;
import com.zlebank.zplatform.business.pojo.PojoMerchWhiteList;
@Repository("merchWhiteListDAO")
public class MerchWhiteListDAOImpl extends HibernateBaseDAOImpl<PojoMerchWhiteList> implements MerchWhiteListDAO {
	 /**
     * 得到指定的白名单信息
     * @param merId
     * @param accNo
     * @param accName
     * @return
     */
    @Override
    @Transactional(readOnly=true)
    public PojoMerchWhiteList getWhiteListByCardNoAndName(String merId, String accNo, String accName) {
        Criteria crite= this.getSession().createCriteria(PojoMerchWhiteList.class);
        if (StringUtils.isNotEmpty(merId))
            crite.add(Restrictions.eq("merchId", merId));
        if (StringUtils.isNotEmpty(accNo))
            crite.add(Restrictions.eq("cardNo", accNo));
        if (StringUtils.isNotEmpty(accName))
            crite.add(Restrictions.eq("accName", accName));
        crite.add(Restrictions.in("status", new String[]{"0", "1"}));
        return (PojoMerchWhiteList) crite.uniqueResult();
    }

	/**
	 * @param id
	 * @return
	 */
	@Override
	 @Transactional(readOnly=true)
	public PojoMerchWhiteList getMerchWhiteListById(Long id) {
		Criteria crite= this.getSession().createCriteria(PojoMerchWhiteList.class);
		crite.add(Restrictions.eq("id", id));
		return (PojoMerchWhiteList) crite.uniqueResult();
	}

	@Override
	 @Transactional(readOnly=true)
	public String checkMerchWhiteList(String merId, String accName, String accNo) {
		 String rtn = null;
        PojoMerchWhiteList oldPojo = this.getWhiteListByCardNoAndName(merId, accNo,accName);
        if (oldPojo == null) {
            rtn = String.format("(%s,%s)", accName,accNo);
        }
        return rtn;
	}

}
