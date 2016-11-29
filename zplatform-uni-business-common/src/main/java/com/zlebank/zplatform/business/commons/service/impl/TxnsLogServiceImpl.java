package com.zlebank.zplatform.business.commons.service.impl;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.business.commons.dao.TxncodeDefDAO;
import com.zlebank.zplatform.business.commons.service.TxnsLogService;
import com.zlebank.zplatform.business.commons.service.base.BaseServiceImpl;
import com.zlebank.zplatform.business.pojo.TxnsLogModel;
@Service("txnsLogService")
public class TxnsLogServiceImpl extends BaseServiceImpl<TxnsLogModel, String> implements TxnsLogService {
	@Autowired
	private TxncodeDefDAO txncodeDefDAO;
	@Override
	public Session getSession() {
		return this.txncodeDefDAO.getSession();
	}

}
