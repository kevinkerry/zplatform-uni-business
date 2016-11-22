package com.zlebank.zplatform.business.enterprise.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.business.enterprise.bean.EnterpriseBean;
import com.zlebank.zplatform.business.enterprise.service.QueryEnterpriseService;
import com.zlebank.zplatform.business.exception.BusinessEnterpriseException;
import com.zlebank.zplatform.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.member.commons.utils.StringUtil;
import com.zlebank.zplatform.member.merchant.service.EnterpriseService;
@Service("busQueryEnterpriseService")
public class QueryEnterpriseServiceImpl implements QueryEnterpriseService {
    @Autowired
	private EnterpriseService enterpriseService;
	@Override
	public EnterpriseBean queryEnterpriseByMemberId(String memberId) throws BusinessEnterpriseException {
		if(StringUtil.isEmpty(memberId)){
			throw new BusinessEnterpriseException("BE0000");
		}
		com.zlebank.zplatform.member.merchant.bean.EnterpriseBean enterpris 
					= enterpriseService.getEnterpriseByMemberId(memberId);
		if(enterpris==null)return null;
		EnterpriseBean result = BeanCopyUtil.copyBean(EnterpriseBean.class, enterpris);
		result.setCardType(null==enterpris.getCardType()?null: enterpris.getCardType().getCode());
		result.setPrimaryBusiness(null==enterpris.getPrimaryBusiness()?null:enterpris.getPrimaryBusiness().getCode().toString().concat("-").concat(enterpris.getPrimaryBusiness().getMcc()));
		return result;
	}

}
