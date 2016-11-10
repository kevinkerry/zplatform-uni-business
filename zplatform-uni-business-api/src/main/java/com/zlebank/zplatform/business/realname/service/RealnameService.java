package com.zlebank.zplatform.business.realname.service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.exception.BusinessRealnameException;
import com.zlebank.zplatform.business.realname.bean.RealNameBean;

/****
 * 实名认证相关
 * @author liusm
 *
 */
public interface RealnameService {
	/****
	 * 实名认证
	 * @param bean
	 * @return
	 */
	public ResultBean realname (RealNameBean bean) throws  BusinessRealnameException;
}
