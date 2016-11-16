package com.zlebank.zplatform.business.memberAccount.service;

import com.zlebank.zplatform.business.commons.bean.PageVo;
import com.zlebank.zplatform.business.exception.BusinessAccountException;
import com.zlebank.zplatform.business.memberAccount.bean.MemInAndExDetailBean;
import com.zlebank.zplatform.business.memberAccount.bean.MemberAccountBean;

public interface QueryAccountService {
  /***
   * 查询会员基本账户余额
   * @param memberId
   * @return
   * @throws CommonException
   */
   public MemberAccountBean queryMemberFuns(String memberId)
            throws BusinessAccountException;
    /**
     * 查询账户明细
     * 
     * @param memberId
     * @param page
     * @param pageSize
     * @return
     * @throws AbstractTradeDescribeException
     * @throws IllegalAccessException
     */
    public PageVo<MemInAndExDetailBean> queryAccInAndExDetail(String memberId,int page,int pageSize) throws BusinessAccountException;
}
