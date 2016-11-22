package com.zlebank.zplatform.business.merch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.business.exception.BusinessMerchException;
import com.zlebank.zplatform.business.merch.bean.MerchMkBean;
import com.zlebank.zplatform.business.merch.service.QueryMerchMkService;
import com.zlebank.zplatform.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.member.exception.DataCheckFailedException;
import com.zlebank.zplatform.member.merchant.bean.MerchMK;
import com.zlebank.zplatform.member.merchant.service.MerchMKService;
@Service("busQueryMerchMkService")
public class QueryMerchMkServiceImpl implements QueryMerchMkService {
	@Autowired
	private MerchMKService merchMkService;
	
	@Override
	public MerchMkBean queryMerchMkByMemberId(String memberId) throws BusinessMerchException {
		if(StringUtil.isEmpty(memberId)){
			throw new BusinessMerchException("BT0000");
		}
		MerchMK merchMk=this.merchMkService.get(memberId);
		if(merchMk==null) return null;
		MerchMkBean result=BeanCopyUtil.copyBean(MerchMkBean.class, merchMk);
		return result;
	}

	@Override
	public MerchMkBean getByMerchType(String memberId, String merchType) throws BusinessMerchException {
		if(StringUtil.isEmpty(memberId)||StringUtil.isEmpty(merchType)){
			throw new BusinessMerchException("BT0000");
		}
		MerchMK merchMk;
		try {
			merchMk = this.merchMkService.getByMerchType(memberId, merchType);
			if(merchMk==null) return null;
			MerchMkBean result=BeanCopyUtil.copyBean(MerchMkBean.class, merchMk);
			return result;
		} catch (DataCheckFailedException e) {
			throw new BusinessMerchException("BT0009");
		}
	}
	
	
	

}
