/* 
 * InsteadPayBatchDAO.java  
 * 
 * version TODO
 *
 * 2015年11月24日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.business.commons.dao;

import com.zlebank.zplatform.business.pojo.PojoInsteadPayBatch;

/**
 * 代付批次DAO
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 下午12:29:35
 * @since 
 */
public interface InsteadPayBatchDAO extends BaseDAO	<PojoInsteadPayBatch> {

    /**
     * 通过批次ID得到批次
     * @param batchNo
     * @return
     */
    public PojoInsteadPayBatch getByBatchId(Long batchId);

    /**
     * 通过批次号得到批次
     * @param batchNo
     * @return
     */
    public PojoInsteadPayBatch getByBatchNo(String batchNo, String txnTime);
    
    /**
     * 更新批次划拨结果
     * @param batchNo
     * @param status
     */
    public void updateBatchResult(String batchNo,String status);
    
    /**
     * 
     * @param batchNo
     * @param merchId
     * @return
     */
    public PojoInsteadPayBatch getInsteadPayBatchByBatchNo(String batchNo, String merchId);

}
