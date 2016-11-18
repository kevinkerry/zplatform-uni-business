package com.zlebank.zplatform.business.merch.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.business.commons.dao.MerchWhiteListDAO;
import com.zlebank.zplatform.business.exception.BusinessMerchException;
import com.zlebank.zplatform.business.merch.bean.MerchWhiteBean;
import com.zlebank.zplatform.business.merch.service.WhiteListService;
import com.zlebank.zplatform.business.pojo.PojoMerchWhiteList;
import com.zlebank.zplatform.commons.utils.BeanCopyUtil;
@Service("busWhiteListService")
public class WhiteListServiceImpl implements WhiteListService {
	
	private final static Logger log = LoggerFactory.getLogger(WhiteListServiceImpl.class);
	@Autowired
	private MerchWhiteListDAO merchWhiteListDAO;
	@Override
	@Transactional()
	public boolean saveWhiteList(MerchWhiteBean merchWhiteList) throws BusinessMerchException {
		if(merchWhiteList==null){
			throw new BusinessMerchException("BT0000");//参数不能为空
		}
		try {
			PojoMerchWhiteList bean = BeanCopyUtil.copyBean(PojoMerchWhiteList.class, merchWhiteList);
			this.merchWhiteListDAO.merge(bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BusinessMerchException("BT0005");//保存白名单异常
		}
		return true;
	}

	@Override
	public boolean updateWhiteList(MerchWhiteBean merchWhiteList) throws BusinessMerchException {
		if(merchWhiteList==null){
			throw new BusinessMerchException("BT0000");//参数不能为空
		}
		PojoMerchWhiteList merchWhite = merchWhiteListDAO.getMerchWhiteListById(merchWhiteList.getId());
		if(merchWhite==null){
			throw new BusinessMerchException("BT0006");//"商户白名单不存在"
		}
		try {
			merchWhite.setAccName(merchWhiteList.getAccName());
			merchWhite.setCardNo(merchWhiteList.getCardNo());
			merchWhite.setId(merchWhiteList.getId());
			merchWhite.setMerchId(merchWhiteList.getMerchId());
			merchWhite.setNotes(merchWhiteList.getNotes());
			merchWhite.setStatus(merchWhiteList.getStatus());
			merchWhite.setUptime(merchWhiteList.getUptime());
			merchWhite.setUpuser(merchWhiteList.getUpuser());
			merchWhiteListDAO.merge(merchWhite);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BusinessMerchException("BT0007");//修改白名单异常
		}
		return true;
	}

	@Override
	public boolean deleteWhiteList(Long id) throws BusinessMerchException {
		if(id==null){
			throw new BusinessMerchException("BT0000");//参数不能为空
		}
		PojoMerchWhiteList merchWhiteList = merchWhiteListDAO.getMerchWhiteListById(id);
		if(merchWhiteList==null){
			throw new BusinessMerchException("BT0006");//"商户白名单不存在"
		}
		merchWhiteList.setStatus("9");
		try {
			merchWhiteListDAO.merge(merchWhiteList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BusinessMerchException("BT0008");//删除白名单异常
		}
		return true;
	}

	

}
