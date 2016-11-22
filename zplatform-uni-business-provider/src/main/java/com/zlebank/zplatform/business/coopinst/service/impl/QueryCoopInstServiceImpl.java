package com.zlebank.zplatform.business.coopinst.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.business.coopinst.bean.CoopInstMkBean;
import com.zlebank.zplatform.business.coopinst.service.QueryCoopInstService;
import com.zlebank.zplatform.business.exception.BusinessCoopInstException;
import com.zlebank.zplatform.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.member.commons.utils.StringUtil;
import com.zlebank.zplatform.member.coopinsti.bean.CoopInsti;
import com.zlebank.zplatform.member.coopinsti.bean.CoopInstiMK;
import com.zlebank.zplatform.member.coopinsti.bean.enums.TerminalAccessType;
import com.zlebank.zplatform.member.coopinsti.service.CoopInstiService;
@Service("busQueryCoopInstService")
public class QueryCoopInstServiceImpl implements QueryCoopInstService {
    @Autowired
	private CoopInstiService  coopInstiService;
	@Override
	public CoopInstMkBean queryCoopInstMk(String coopInstCode, String terminalAccessType ) throws BusinessCoopInstException {
		if(StringUtil.isEmpty(coopInstCode)||StringUtil.isEmpty(terminalAccessType)){
			throw new BusinessCoopInstException("BI0000");
		}
		TerminalAccessType  term= TerminalAccessType.fromValue(Integer.valueOf(terminalAccessType));
		if(term.equals(TerminalAccessType.UNKNOW)){
			throw new BusinessCoopInstException("BI0001");
		}
		CoopInsti coopInst= this.coopInstiService.getInstiByInstiCode(coopInstCode);
		if(coopInst==null){
			throw new BusinessCoopInstException("BI0002");
		}
		CoopInstiMK mk = this.coopInstiService.getCoopInstiMK(coopInstCode, term);
		if(mk==null)return null;
		
		CoopInstMkBean result = BeanCopyUtil.copyBean(CoopInstMkBean.class, mk);
		result.setTerminalAccessType(String.valueOf(mk.getTerminalAccessType().getCode()));
		result.setEncryptAlgorithm(mk.getEncryptAlgorithm().getAlgName());
		return result;
	}

}
