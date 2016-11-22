package com.zlebank.zplatform.business.commons.dao;

import com.zlebank.zplatform.business.pojo.PojoTxncodeDef;

public interface TxncodeDefDAO extends BaseDAO<PojoTxncodeDef>{
	/***
	 * 获得业务代码
	 * @param txntype
	 * @param txnsubtype
	 * @param biztype
	 * @return
	 */
	public PojoTxncodeDef getBusiCode(String txntype,String txnsubtype,String biztype);
}
