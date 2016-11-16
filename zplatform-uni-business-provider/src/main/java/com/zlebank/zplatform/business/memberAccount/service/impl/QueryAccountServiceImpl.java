package com.zlebank.zplatform.business.memberAccount.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.acc.bean.enums.Usage;
import com.zlebank.zplatform.business.commons.bean.PageVo;
import com.zlebank.zplatform.business.exception.BusinessAccountException;
import com.zlebank.zplatform.business.memberAccount.bean.MemInAndExDetailBean;
import com.zlebank.zplatform.business.memberAccount.bean.MemberAccountBean;
import com.zlebank.zplatform.business.memberAccount.service.QueryAccountService;
import com.zlebank.zplatform.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.member.individual.bean.MemberBalanceDetailBean;
import com.zlebank.zplatform.member.individual.bean.MemberBean;
import com.zlebank.zplatform.member.individual.bean.enums.MemberType;
import com.zlebank.zplatform.member.individual.exception.GetAccountFailedException;
import com.zlebank.zplatform.member.individual.service.MemberAccountService;
@Service("queryAccountService")
public class QueryAccountServiceImpl implements QueryAccountService {
	
	private final static Logger log = LoggerFactory.getLogger(QueryAccountServiceImpl.class);
	@Autowired 
    private MemberAccountService memberAccountService;
	
	@Override
	public MemberAccountBean queryMemberFuns(String memberId) throws BusinessAccountException {
		if(memberId == null){
			throw new BusinessAccountException("BA0000");//参数不能为空
		}
		MemberBean member = new MemberBean();
        member.setMemberId(memberId);
        MemberAccountBean memberAccount = null;
        try {
        	com.zlebank.zplatform.member.individual.bean.MemberAccountBean account = 
        			memberAccountService.queryBalance(MemberType.INDIVIDUAL, member, Usage.BASICPAY);
        	if(account!=null){
        		BeanCopyUtil.copyBean(MemberAccountBean.class, account);
        	}
        }catch (GetAccountFailedException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BusinessAccountException("BA0001");//查询余额异常
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BusinessAccountException("BA0002");//查询余额异常
		}
        return memberAccount;
	}

	@Override
	public PageVo<MemInAndExDetailBean> queryAccInAndExDetail(String memberId, int page, int pageSize)
			throws BusinessAccountException {
		PageVo<MemInAndExDetailBean> pageVo = new PageVo<MemInAndExDetailBean>();
		if(memberId == null ){
			throw new BusinessAccountException("BA0000");//参数不能为空
		}
		 MemberBean member = new MemberBean();
        member.setMemberId(memberId);
        com.zlebank.zplatform.member.commons.bean.PagedResult<MemberBalanceDetailBean> entrys = null;
        try {
			entrys = memberAccountService.queryBalanceDetail(
			        MemberType.INDIVIDUAL, member, page, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (entrys == null) {
            return null;
        }
        List<MemInAndExDetailBean> memInAndExDetailList = new ArrayList<MemInAndExDetailBean>();
        try {
			for (MemberBalanceDetailBean memberBalanceDetailBean : entrys
			        .getPagedResult()) {
			    MemInAndExDetailBean memInAndExDetail = new MemInAndExDetailBean();
			    BeanUtils.copyProperties(memberBalanceDetailBean, memInAndExDetail);
			    memInAndExDetailList.add(memInAndExDetail);
			}
		} catch (BeansException | IllegalAccessException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BusinessAccountException("BA0003");//查询余额异常
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BusinessAccountException("BA0004");//查询余额异常
		}
        pageVo.setList(memInAndExDetailList);
        pageVo.setTotal((int) entrys.getTotal());
        return pageVo;
	}

}
