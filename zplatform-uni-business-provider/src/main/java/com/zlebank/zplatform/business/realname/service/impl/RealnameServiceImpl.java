package com.zlebank.zplatform.business.realname.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.exception.BusinessRealnameException;
import com.zlebank.zplatform.business.realname.bean.RealNameBean;
import com.zlebank.zplatform.business.realname.service.RealnameService;
import com.zlebank.zplatform.member.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.payment.exception.PaymentRealNameException;
import com.zlebank.zplatform.payment.realname.bean.RealNameCardBean;
import com.zlebank.zplatform.payment.realname.service.RealNameCardService;
@Service("busRealnameService")
public class RealnameServiceImpl implements RealnameService {
	
	private final static Logger log = LoggerFactory.getLogger(RealnameServiceImpl.class);
	@Autowired
	private RealNameCardService realNameCardService;
	
	@SuppressWarnings("unused")
	public ResultBean realname(RealNameBean bean) throws BusinessRealnameException{
		RealNameCardBean realnameBean= null;
		ResultBean  result =null;
		try {
			if(bean ==null){
				throw new BusinessRealnameException("BR0000");
			}
			realnameBean = BeanCopyUtil.copyBean(RealNameCardBean.class, bean);
			com.zlebank.zplatform.payment.commons.bean.ResultBean resultBean =this.realNameCardService.realName(realnameBean);
			if(resultBean!=null){
				result = BeanCopyUtil.copyBean(ResultBean.class, resultBean);
			}else{
				result= new ResultBean("BR0001", "实名认证失败");
			}
		}catch (PaymentRealNameException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			result = new ResultBean("BR0001", "实名认证失败："+e.getMessage()); 
		}catch (Exception  e) {
			e.printStackTrace();
			log.error(e.getMessage());
			result = new ResultBean("BR0002", "实名认证异常"); 
		}	
		
		return result;
	}

}
