package com.zlebank.zplatform.business.realname.service.impl;

import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.exception.BusinessRealnameException;
import com.zlebank.zplatform.business.realname.bean.RealNameBean;
import com.zlebank.zplatform.business.realname.service.RealnameService;
import com.zlebank.zplatform.payment.exception.PaymentRealNameException;
import com.zlebank.zplatform.payment.realname.bean.RealNameCardBean;
import com.zlebank.zplatform.payment.realname.service.RealNameCardService;
@Service("realnameService")
public class RealnameServiceImpl implements RealnameService {
	
	private final static Logger log = LoggerFactory.getLogger(RealnameServiceImpl.class);
	@Autowired
	private RealNameCardService realNameCardService;
	
	@SuppressWarnings("unused")
	public ResultBean realname(RealNameBean bean) throws BusinessRealnameException {
		RealNameCardBean realnameBean= null;
		ResultBean  result =null;
		try {
			BeanUtils.copyProperties(bean,realnameBean);
			com.zlebank.zplatform.payment.commons.bean.ResultBean resultBean =this.realNameCardService.realName(realnameBean);
			if(result!=null){
				BeanUtils.copyProperties(resultBean,result);
			}
		}catch (PaymentRealNameException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BusinessRealnameException("BR0000");
		}	
		return result;
	}

}
