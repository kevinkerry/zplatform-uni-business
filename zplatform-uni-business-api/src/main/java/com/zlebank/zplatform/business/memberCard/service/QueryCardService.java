package com.zlebank.zplatform.business.memberCard.service;

import java.util.List;

import com.zlebank.zplatform.business.memberCard.bean.BindCardResultBean;
import com.zlebank.zplatform.business.memberCard.bean.CardBinResultBean;
import com.zlebank.zplatform.business.memberCard.bean.QueryBindCardBean;
import com.zlebank.zplatform.business.memberCard.bean.QuerySupporedCardBean;
import com.zlebank.zplatform.business.memberCard.bean.SupportedCardResultBean;

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
    public CardBinResultBean queryCardBin(String cardNo);
    /***
     * 查询支持的银行卡
     * @param query
     * @param page
     * @param pageSize
     * @return
     */
    public List<SupportedCardResultBean> querySupportedCardList(QuerySupporedCardBean query,int page,int pageSize);
    /****
     * 查询已绑卡列表
     * @param query
     * @param page
     * @param pageSize
     * @return
     */
    public List<BindCardResultBean> queryBindCardedList(QueryBindCardBean query,int page,int pageSize);
}
