package com.zlebank.zplatform.business.memberCard.service;

import java.util.List;

import com.zlebank.zplatform.business.memberCard.bean.QueryCardResultBean;
import com.zlebank.zplatform.business.memberCard.bean.QueryCashBankResultBean;
import com.zlebank.zplatform.business.commons.bean.PageVo;
import com.zlebank.zplatform.business.exception.BusinessCardException;
import com.zlebank.zplatform.business.memberCard.bean.CardBinResultBean;
import com.zlebank.zplatform.business.memberCard.bean.QueryCardBean;
import com.zlebank.zplatform.business.memberCard.bean.QuerySupporedCardBean;
import com.zlebank.zplatform.business.memberCard.bean.QuerySupportedCardResultBean;

/***
 * 查询卡相关
 * @author liusm
 *
 */
public interface QueryCardService {
	/****
	 * 根据卡号查询卡BIN信息
	 * @param cardNo
	 * @return
	 */
    public CardBinResultBean queryCardBin(String cardNo) throws BusinessCardException;
    /***
     * 查询支持的银行卡
     * @param query
     * @param page
     * @param pageSize
     * @return
     */
    public PageVo<QuerySupportedCardResultBean> querySupportedCardList(QuerySupporedCardBean query,int page,int pageSize)throws BusinessCardException;
    /****
     * 查询已绑卡列表
     * @param query
     * @param page
     * @param pageSize
     * @return
     */
    public PageVo<QueryCardResultBean> queryBindCardList(QueryCardBean query,int page,int pageSize)throws BusinessCardException;
    /***
     * 根据卡号查询ICON
     * @param bankCode
     * @return
     */
    public QueryCashBankResultBean getBankICON(String bankCode) throws BusinessCardException;
}
