/* 
 * InsteadPayBatchDAOImpl.java  
 * 
 * version TODO
 *
 * 2015年11月24日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.business.commons.dao.impl;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.business.commons.dao.ConfigInfoDAO;
import com.zlebank.zplatform.business.pojo.ConfigInfoModel;
import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;

/**
 * 配置DAO
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 下午1:48:00
 * @since 
 */
@Repository("configInfoDAO")
public class ConfigInfoDAOImpl extends HibernateBaseDAOImpl<ConfigInfoModel> implements ConfigInfoDAO {

    /**
     * 根据参数名称得到配置信息
     * @param paraName
     * @return
     */
    @Override
    @Transactional(readOnly=true)
    public ConfigInfoModel getConfigByParaName(String paraName) {
        Criteria crite= this.getSession().createCriteria(ConfigInfoModel.class);
        crite.add(Restrictions.eq("paraname", paraName));
        return (ConfigInfoModel) crite.uniqueResult();
    }
    @Transactional(readOnly=true)
    public long getSequence(String sequences){
        String sql = " SELECT "+sequences+".NEXTVAL nextvalue FROM DUAL";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        long maxId = (long)(query.addScalar("nextvalue", StandardBasicTypes.LONG) ).uniqueResult();
         return  maxId;
       }

    /**
     *  根据参数名称得到配置信息列表
     * @param paraName
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly=true)
    public List<ConfigInfoModel> getConfigListByParaName(String paraName) {
        Criteria crite= this.getSession().createCriteria(ConfigInfoModel.class);
        crite.add(Restrictions.eq("paraname", paraName));
        return crite.list();
    }
    
    public Session getSession(){
    	return super.getSession();
    }
}
