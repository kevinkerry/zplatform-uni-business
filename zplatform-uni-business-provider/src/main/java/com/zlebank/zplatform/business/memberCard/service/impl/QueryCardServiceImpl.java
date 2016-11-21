package com.zlebank.zplatform.business.memberCard.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.business.commons.bean.CardBin;
import com.zlebank.zplatform.business.commons.bean.PageVo;
import com.zlebank.zplatform.business.commons.dao.CardBinDAO;
import com.zlebank.zplatform.business.commons.dao.CashBankDAO;
import com.zlebank.zplatform.business.exception.BusinessCardException;
import com.zlebank.zplatform.business.memberCard.bean.Bank;
import com.zlebank.zplatform.business.memberCard.bean.CardBinResultBean;
import com.zlebank.zplatform.business.memberCard.bean.QueryCardBean;
import com.zlebank.zplatform.business.memberCard.bean.QueryCardResultBean;
import com.zlebank.zplatform.business.memberCard.bean.QueryCashBankResultBean;
import com.zlebank.zplatform.business.memberCard.bean.QuerySupporedCardBean;
import com.zlebank.zplatform.business.memberCard.bean.QuerySupportedCardResultBean;
import com.zlebank.zplatform.business.memberCard.service.QueryCardService;
import com.zlebank.zplatform.business.pojo.PojoCashBank;
import com.zlebank.zplatform.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.member.commons.bean.PagedResult;
import com.zlebank.zplatform.member.individual.bean.QuickpayCustBean;
import com.zlebank.zplatform.member.individual.service.MemberBankCardService;
@Service("busQueryCardService")
public class QueryCardServiceImpl implements QueryCardService {
	private final static Logger log = LoggerFactory.getLogger(QueryCardServiceImpl.class);
	
	@Autowired 
	private MemberBankCardService memberBankCardService;
	@Autowired
	private CardBinDAO cardBinDAO;
	@Autowired
	private CashBankDAO cashBankDAO;
	
	
	
	@Override
	public CardBinResultBean queryCardBin(String cardNo) throws BusinessCardException {
		if(StringUtil.isEmpty(cardNo)){
			throw new BusinessCardException("BD0000");
		}
		CardBin cardBin= this.cardBinDAO.getCard(cardNo);
		return BeanCopyUtil.copyBean(CardBinResultBean.class, cardBin);
	}

	@Override
	public PageVo<QuerySupportedCardResultBean> querySupportedCardList(QuerySupporedCardBean query, int page, int pageSize) {
		Map<String,Object> map =new HashMap<String,Object>();
		if(query != null){
			if(StringUtil.isEmpty(query.getBankcode())){
				map.put("bankCode", query.getBankcode());
			}
			if(StringUtil.isEmpty(query.getBusiCode())){
				map.put("busiCode", query.getBusiCode());
			}
			if(StringUtil.isEmpty(query.getCardtype())){
				map.put("cardtype", query.getCardtype());
			}
			if(StringUtil.isEmpty(query.getPaytype())){
				map.put("paytype", query.getPaytype());
			}
			if(StringUtil.isEmpty(query.getStatus())){
				map.put("status", query.getStatus());
			}
			
		}
		List<PojoCashBank>  bankList = this.cashBankDAO.getCardList(map, page, pageSize);
		int  total = this.cashBankDAO.getTotal(map, page, pageSize);
		List<QuerySupportedCardResultBean> supportBankList = new ArrayList<QuerySupportedCardResultBean>();
		for(PojoCashBank casebank:bankList){
			QuerySupportedCardResultBean supportedBankCardType = new QuerySupportedCardResultBean();
			supportedBankCardType.setCardType(casebank.getCardtype());
			Bank bank = new Bank();
			bank.setBankCode(casebank.getBankcode());
			bank.setBankName(casebank.getBankname());
			bank.setBankIcon(casebank.getIco());
			supportedBankCardType.setBank(bank);
			supportBankList.add(supportedBankCardType);
		}
		PageVo<QuerySupportedCardResultBean>  returnPage= new PageVo<QuerySupportedCardResultBean>();
		returnPage.setList(supportBankList);
		returnPage.setTotal(total);
		return returnPage;
	}

	@SuppressWarnings("null")
	@Override
	public PageVo<QueryCardResultBean> queryBindCardList(QueryCardBean query, int page, int pageSize) throws BusinessCardException {
		if(query==null){
			throw new BusinessCardException("BD0000");
		}
		PageVo<QueryCardResultBean> pageVoList =null;
		List<QueryCardResultBean> voList=new ArrayList<QueryCardResultBean>();
		if(query!=null){
			PagedResult<QuickpayCustBean> cardList= memberBankCardService.queryMemberBankCard(query.getMemberId(), query.getCardType(), query.getDevId(), page, pageSize);
			try {
				for (QuickpayCustBean item : cardList.getPagedResult()){
					voList.add(BeanCopyUtil.copyBean(QueryCardResultBean.class, item));
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				log.error(e.getMessage());
				throw new BusinessCardException("BD0001");
			}
		}
		pageVoList.setList(voList);
		pageVoList.setTotal(voList.size());
		return pageVoList;
	}

	@Override
	public QueryCashBankResultBean getBankICON(String bankCode) throws BusinessCardException {
		QueryCashBankResultBean result = null;
		if(StringUtil.isEmpty(bankCode)){
			throw new BusinessCardException("BD0000");
		}
		PojoCashBank bank =this.cashBankDAO.getBankICON(bankCode);
		if(bank!=null){
			result =BeanCopyUtil.copyBean(QueryCashBankResultBean.class, bank);
		}
		return result;
	}

}
