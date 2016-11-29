package com.zlebank.zplatform.business.insteadPay.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.acc.bean.TradeInfo;
import com.zlebank.zplatform.acc.bean.enums.EntryEvent;
import com.zlebank.zplatform.acc.bean.enums.Usage;
import com.zlebank.zplatform.acc.exception.AbstractBusiAcctException;
import com.zlebank.zplatform.acc.exception.AccBussinessException;
import com.zlebank.zplatform.acc.exception.IllegalEntryRequestException;
import com.zlebank.zplatform.acc.service.AccEntryService;
import com.zlebank.zplatform.business.commons.bean.CardBin;
import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.commons.dao.CardBinDAO;
import com.zlebank.zplatform.business.commons.dao.ConfigInfoDAO;
import com.zlebank.zplatform.business.commons.dao.InsteadPayBatchDAO;
import com.zlebank.zplatform.business.commons.dao.InsteadPayDetailDAO;
import com.zlebank.zplatform.business.commons.dao.MerchWhiteListDAO;
import com.zlebank.zplatform.business.commons.dao.RouteConfigDAO;
import com.zlebank.zplatform.business.commons.enums.BusinessEnum;
import com.zlebank.zplatform.business.commons.enums.InsteadPayImportTypeEnum;
import com.zlebank.zplatform.business.commons.enums.SeqNoEnum;
import com.zlebank.zplatform.business.commons.utils.OrderNumber;
import com.zlebank.zplatform.business.insteadPay.bean.InsteadPayFile;
import com.zlebank.zplatform.business.insteadPay.bean.InsteadPayInterfaceParamBean;
import com.zlebank.zplatform.business.insteadPay.bean.InsteadPay_Request;
import com.zlebank.zplatform.business.insteadPay.service.InsteadPayService;
import com.zlebank.zplatform.business.pojo.ConfigInfoModel;
import com.zlebank.zplatform.business.pojo.PojoInsteadPayBatch;
import com.zlebank.zplatform.business.pojo.PojoInsteadPayDetail;
import com.zlebank.zplatform.commons.enums.BusinessCodeEnum;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.fee.bean.FeeBean;
import com.zlebank.zplatform.fee.exception.TradeFeeException;
import com.zlebank.zplatform.fee.service.TradeFeeService;
import com.zlebank.zplatform.member.coopinsti.bean.CoopInsti;
import com.zlebank.zplatform.member.coopinsti.service.CoopInstiService;
import com.zlebank.zplatform.member.individual.bean.MemberAccountBean;
import com.zlebank.zplatform.member.individual.bean.MemberBean;
import com.zlebank.zplatform.member.individual.bean.PoMemberBean;
import com.zlebank.zplatform.member.individual.bean.enums.MemberType;
import com.zlebank.zplatform.member.individual.service.MemberAccountService;
import com.zlebank.zplatform.member.individual.service.MemberService;
import com.zlebank.zplatform.member.merchant.bean.MerchantBean;
import com.zlebank.zplatform.member.merchant.service.MerchService;

import net.sf.json.JSONObject;
@Service("busInsteadPayService")
public class InsteadPayServiceImpl implements InsteadPayService {
	
	private final static Logger log = LoggerFactory.getLogger(InsteadPayServiceImpl.class);
	@Autowired
	private InsteadPayBatchDAO insteadPayBatchDAO;
	@Autowired
	private ConfigInfoDAO configInfoDAO;
	@Autowired
	private InsteadPayDetailDAO insteadPayDetailDAO;
	@Autowired
    private MerchWhiteListDAO merchWhiteListDAO;
	@Autowired
	private RouteConfigDAO  routeConfigDAO;
	@Autowired
	private MemberAccountService memberAccountService;
	@Autowired
	private MerchService merchService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private CoopInstiService coopInstiService;
	@Autowired
	private AccEntryService accEntryService;
	@Autowired
	private TradeFeeService tradeFeeService;
	@Autowired
	private CardBinDAO cardBinDAO;
	
	@Override
	 @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Throwable.class)
	public ResultBean batchInsteadPay(InsteadPay_Request request, InsteadPayImportTypeEnum type,
			InsteadPayInterfaceParamBean param) {
		if (log.isDebugEnabled()) {
            log.debug("代付处理开始");
            log.debug(JSONObject.fromObject(request)+"");
        }
        if(request==null || type==null || param==null){
        	return  new ResultBean("EI0000", "参数不能为空");
        }
        // 校验报文bean数据
        /*String errorMsg = HibernateValidatorUtil.validateBeans(request);
        if (StringUtil.isNotEmpty(errorMsg)) {
        	throw new DataAccessResourceFailureException("报文内容校验失败："+errorMsg);
        }*/
        BigDecimal  totalAmtBig = new BigDecimal(request.getTotalAmt());
        BigDecimal totalQtyBig = new BigDecimal(request.getTotalQty());
        if(totalAmtBig.compareTo(BigDecimal.ZERO)<=0 ){
        	return new ResultBean("EB0001", "报文内容校验失败：totalAmt:必输大于0");
        }
        if(totalQtyBig.compareTo(BigDecimal.ZERO)<=0 ){
        	return new ResultBean("EB0002", "报文内容校验失败：totalQty:必输大于0");
        }
        // 是否是批处理时间范围内
        Date currentTime;
        try {
            ConfigInfoModel startTime = configInfoDAO.getConfigByParaName("INSTEAD_PAY_START_TIME");
            ConfigInfoModel endTime = configInfoDAO.getConfigByParaName("INSTEAD_PAY_END_TIME");
            currentTime = DateUtil.convertToDate(DateUtil.getCurrentTime(),
                    "HHmmss");
            Date insteadStartTime = DateUtil.convertToDate(startTime.getPara(),
                    "HHmmss");
            Date insteadEndTime = DateUtil.convertToDate(endTime.getPara(),
                    "HHmmss");
            if (currentTime.before(insteadEndTime)
                    && currentTime.after(insteadStartTime)) {
            } else {
            	return new ResultBean("EB0003", "非代付工作时间");
            }
        } catch (ParseException e) {
        	e.printStackTrace();
            log.error(e.getMessage(),e);
            return new ResultBean("EB0003", "非代付工作时间");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResultBean("EB0003", "非代付工作时间");
        }
        if (log.isDebugEnabled()) {
            log.debug("批处理时间范围检查【通过】");
        }
        // 代付明细
        List<InsteadPayFile> fileContent = request.getFileContent();
        BigDecimal total = BigDecimal.ZERO;
        // 明细检查
        for (InsteadPayFile file : fileContent) {
            total = total.add(new BigDecimal(file.getAmt()));
            if (!file.getMerId().equals(request.getMerId())) {
            	return new ResultBean("EB0004", "代付文件商户信息有误");
            }
        }
        if (Integer.valueOf(request.getTotalQty()) != fileContent.size()) {
        	return new ResultBean("EB0005", "代付明细文件笔数和代付总笔数不相符");
        }
        BigDecimal batchAmt = new BigDecimal(request.getTotalAmt());
        if (batchAmt.compareTo(total) != 0) {
        	return new ResultBean("EB0005", "代付明细文件笔数和代付总笔数不相符");
        }
        // 是否要判断白名单
        StringBuilder totalError = new StringBuilder();
        StringBuffer errorMsgDetail= new StringBuffer();
//        if (isCheckWhiteList(request.getMerId())) {
            for (InsteadPayFile file : fileContent) {
            	 // 校验报文bean数据
                /* errorMsg = HibernateValidatorUtil.validateBeans(file);
                if (StringUtil.isNotEmpty(errorMsg)) {
                	 errorMsgDetail.append(errorMsg).append("|");
                }*/
                BigDecimal  amtBig = new BigDecimal(file.getAmt());
                if(amtBig.compareTo(BigDecimal.ZERO)<=0 ){
                	 errorMsgDetail.append("amt:必输大于0"+file.getAccNo()).append("|");
                }
                if (StringUtil.isEmpty(file.getBankCode())) {
                	if(file.getAccType().equals(InsteadPayFile.ACC_TYPE_PRI)){
                		  //如查银行卡号没有，则通过银行卡号获取联行号
                  	   Map<String,Object> map =this.routeConfigDAO.getCardPBCCode(file.getAccNo());
                  	   if(map==null || map.get("PBC_BANKCODE")==null){
                  		 errorMsgDetail.append("查询联行号失败!请检查卡号"+file.getAccNo()).append("|");
                  	   }else{
                  		   String bankNumber = map.get("PBC_BANKCODE")+"";
                      	   file.setBankCode(bankNumber);
                  	   }
                	}else{
                		 errorMsgDetail.append("对公账户bankCode不能为空"+file.getAccNo()).append("|");
                	}
                }
                // 如果是对公账户，跳过实名认证和白名单。
                if (InsteadPayFile.ACC_TYPE_PUB.equals(file.getAccType())) 
                    continue;
                String error = merchWhiteListDAO.checkMerchWhiteList(file.getMerId(), file.getAccName(), file.getAccNo());
                if (StringUtil.isNotEmpty(error)) {
                    totalError.append(error);
                }
            }
//        }
        if(errorMsgDetail.toString().length()>0){
        	return new ResultBean("EB0006", errorMsgDetail.toString());
        }
        if (totalError.length() != 0) {
        	return new ResultBean("EB0007", totalError.toString()+"不在白名单内");
        }
        // 商户信息
        MerchantBean merchPojo = this.merchService.getMerchBymemberId(request.getMerId());
        if(merchPojo==null){
        	return new ResultBean("EI0008", "获取商户账户信息失败");
        }
        // 商户余额是否足够
        BigDecimal payBalance = new BigDecimal(request.getTotalAmt());
        MemberBean member = new MemberBean();
        member.setMemberId(request.getMerId());
        MemberAccountBean resultBalance = null;
        try {
            resultBalance = memberAccountService.queryBalance(MemberType.ENTERPRISE, member, Usage.BASICPAY);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResultBean("EB0008", "获取商户账户信息失败");
        }
        BigDecimal merBalance = resultBalance != null ? resultBalance.getBalance() : BigDecimal.ZERO;
        if (merBalance.compareTo(payBalance) < 0) {
        	return new ResultBean("EB0009", "余额不足");
        }
        if (log.isDebugEnabled()) {
            log.debug("商户本金余额是否足够检查【通过】");
        }
        /**  手续费计算 */
        BigDecimal feeAmt= BigDecimal.ZERO; 

        // 插入批次表
        PojoInsteadPayBatch batch = convertToPojoInsteadPayBatch(request, param);
        // 设定代付接入类型
        batch.setType(type.getCode()); 
        if (type == InsteadPayImportTypeEnum.FILE) {
            batch.setFilePath(param.getFtpFileName());
            batch.setOriginalFileName(param.getOriginalFileName());    
        }
        batch = insteadPayBatchDAO.merge(batch);
        // 插入明细表
        List<PojoInsteadPayDetail> pojoInsteadPayDetails = convertToPojoInsteadPayDetail(
                request, batch);
        for (PojoInsteadPayDetail detail : pojoInsteadPayDetails) {
            // 插入交易流水号
            detail.setTxnseqno(OrderNumber.getInstance().generateTxnseqno(BusinessCodeEnum.INSTEADPAY_APPLY.getBusiCode()));
            // 取手续费
            Long txnfee = 0L;
			try {
				FeeBean feeBean = new FeeBean();
				feeBean.setBusiCode(BusinessEnum.INSTEADPAY.getBusiCode());
				feeBean.setFeeVer(merchPojo.getFeeVer());
				feeBean.setMerchNo(detail.getMerId());
				feeBean.setTxnAmt(detail.getAmt()+"");
				feeBean.setTxnseqno(detail.getTxnseqno());
				CardBin card = cardBinDAO.getCard(detail.getAccNo());
		        if (card != null) {
		            feeBean.setCardType(card.getType());
		        }
				txnfee = this.tradeFeeService.getCommonFee(feeBean);
			} catch (TradeFeeException e) {
				e.printStackTrace();
				log.error(e.getMessage());
				return new ResultBean("EB0010", "计算手续费异常");
			}
            feeAmt = feeAmt.add(new BigDecimal(txnfee));
            detail.setTxnfee(txnfee);
            detail = insteadPayDetailDAO.merge(detail);
            // 将资金划拨到中间账户
            TradeInfo tradeInfo = new TradeInfo();
            tradeInfo.setTxnseqno(detail.getTxnseqno());
            tradeInfo.setAmount(new BigDecimal(detail.getAmt()));
            tradeInfo.setBusiCode(BusinessCodeEnum.INSTEADPAY_APPLY.getBusiCode());
            tradeInfo.setCharge(new BigDecimal(detail.getTxnfee()));
            tradeInfo.setCommission(BigDecimal.ZERO);
            tradeInfo.setPayMemberId(detail.getMerId());
            tradeInfo.setPayToMemberId(detail.getMerId());
            tradeInfo.setPayToParentMemberId(detail.getMerId());
            String instiCode = null;
            // 取合作机构号
            if (StringUtil.isNotEmpty(request.getCoopInstiId())) {
                instiCode = request.getCoopInstiId();
            } else {
            	PoMemberBean memberMerch = memberService.getMbmberByMemberId(detail.getMerId(), MemberType.ENTERPRISE);
                CoopInsti insti = coopInstiService.getInstiByInstiID(memberMerch.getInstiId());
                instiCode = insti.getInstiCode();
            }
            tradeInfo.setCoopInstCode(instiCode);
            try {
                accEntryService.accEntryProcess(tradeInfo,EntryEvent.AUDIT_APPLY);
                
            } catch (AccBussinessException e) {
                log.error(e.getMessage(),e);
                if ("E000019".equals(e.getCode()))
                	return new ResultBean("EB0009", "余额不足");
                else 
                	return new ResultBean("EB0011", "记账时出现异常");
            } catch (AbstractBusiAcctException e) {
                log.error(e.getMessage(),e);
                return new ResultBean("EB0011", "记账时出现异常");
            } catch (NumberFormatException e) {
                log.error(e.getMessage(),e);
                return new ResultBean("EB0011", "记账时出现异常");
            } catch (IllegalEntryRequestException e) {
				e.printStackTrace();
				log.error(e.getMessage(),e);
				return new ResultBean("EB0011", "记账时出现异常");
			}
        }
        // 商户余额是否能够支付手续费
        if (merBalance.compareTo(payBalance.add(feeAmt))<0) {
        	return new ResultBean("EB0009", "余额不足");
        }
        if (log.isDebugEnabled()) {
            log.debug("代付处理结束");
        }
		return new ResultBean(true);
	}

	/**
     * 转换为代付批次表POJO
     * 
     * @param request
     * @return
     */
    private PojoInsteadPayBatch convertToPojoInsteadPayBatch(InsteadPay_Request request,InsteadPayInterfaceParamBean param) {
        PojoInsteadPayBatch batch = new PojoInsteadPayBatch();
        batch.setInsteadPayBatchSeqNo(getBatchNo(SeqNoEnum.INSTEAD_PAY_BATCH_NO));
        batch.setBatchNo(request.getBatchNo());// 批次号
        batch.setMerId(request.getMerId());// 商户号
        batch.setTxnTime(request.getTxnTime());// 订单发送时间(yyyyMMddhhmmss)
        batch.setTotalQty(Long.parseLong(request.getTotalQty()));// 总笔数
        batch.setTotalAmt(Long.parseLong(request.getTotalAmt()));// 总金额
        batch.setStatus("01");// 状态(00:已处理01:未处理)
       if(param==null){
        batch.setInuser(0L);// 创建人
       }else{
           batch.setInuser(param.getUserId());// 创建人 
       }
        batch.setIntime(new Date());// 创建时间
        batch.setUpuser(0L);// 修改人
        batch.setUptime(new Date());// 修改时间
        batch.setNotes("");// 备注
        batch.setType("00");// 接入类型（01：文件导入00：接口）
        batch.setUnapproveCount(batch.getTotalQty());// 未审核笔数
        batch.setUnapproveAmt(batch.getTotalAmt());// 未审核金额
        batch.setRefuseCount(0L);
        batch.setRefuseAmt(0L);
        batch.setApproveCount(0L);
        batch.setApproveAmt(0L);
        batch.setApplyTime(new Date());// 申请时间
        batch.setNotifyUrl(request.getBackUrl());
        return batch;
    }
    /**
     * 转换为代付批次明细表POJO
     * 
     * @param request
     * @return
     */
    private List<PojoInsteadPayDetail> convertToPojoInsteadPayDetail(InsteadPay_Request request,
            PojoInsteadPayBatch batch) {
        List<PojoInsteadPayDetail> result = new ArrayList<PojoInsteadPayDetail>();
        List<InsteadPayFile> fileContent = request.getFileContent();
        for (InsteadPayFile file : fileContent) {
            PojoInsteadPayDetail detail = new PojoInsteadPayDetail();
            detail.setInsteadPayDataSeqNo(getBatchNo(SeqNoEnum.INSTEAD_PAY_DATA_NO));
            detail.setInsteadPayBatch(batch);
            detail.setMerId(file.getMerId());
            detail.setOrderId(file.getOrderId());
            detail.setCurrencyCode(file.getCurrencyCode());
            detail.setAmt(Long.parseLong(file.getAmt()));
            detail.setBizType(file.getBizType());
            detail.setAccType(file.getAccType());
            detail.setAccNo(file.getAccNo());
            detail.setAccName(file.getAccName());
            detail.setBankCode(file.getBankCode());
            detail.setIssInsProvince(file.getIssInsProvince());
            detail.setIssInsCity(file.getIssInsCity());
            detail.setIssInsName(file.getIssInsName());
            detail.setCertifTp(file.getCertifTp());
            detail.setCertifId(file.getCertifId());
            detail.setPhoneNo(Long.parseLong(file.getPhoneNo()));
            detail.setBillType(file.getBillType());
            detail.setNotes(file.getNotes());
            detail.setRespCode("11");
            detail.setRespMsg("未处理");
            detail.setStatus("01");
            detail.setInuser(0L);
            detail.setIntime(new Date());
            result.add(detail);
        }
        return result;
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public String getBatchNo(SeqNoEnum type) {
        String rtnNo = null;
        switch (type) {
            case INSTEAD_PAY_BATCH_NO:
                rtnNo = "W01"+DateUtil.getCurrentDate()+String.format("%05d", configInfoDAO.getSequence("SEQ_T_INSTEAD_PAY_BATCH_NO"));
                break;
            case WITHDRAW_BATCH_NO:
                rtnNo = "W02"+DateUtil.getCurrentDate()+String.format("%05d", configInfoDAO.getSequence("SEQ_T_TXNS_WITHDRAW_BATCH_NO"));
                break;
            case REFUND_BATCH_NO:
                rtnNo = "W03"+DateUtil.getCurrentDate()+String.format("%05d", configInfoDAO.getSequence("SEQ_T_TXNS_REFUND_BATCH_NO"));
                break;
            case TRAN_BATCH_NO:
                rtnNo = "T00"+DateUtil.getCurrentDate()+String.format("%05d", configInfoDAO.getSequence("SEQ_T_TRAN_BATCH_NO"));
                break;
            case BANK_TRAN_BATCH_NO:
                rtnNo = "B00"+DateUtil.getCurrentDate()+String.format("%05d", configInfoDAO.getSequence("SEQ_T_BANK_TRAN_BATCH_NO"));
                break;
            case INSTEAD_PAY_DATA_NO:
                rtnNo = "W01"+DateUtil.getCurrentDateTime()+String.format("%07d", configInfoDAO.getSequence("SEQ_DETAIL_NO"));
                break;
            case WITHDRAW_DATA_NO:
                rtnNo = "W02"+DateUtil.getCurrentDateTime()+String.format("%07d", configInfoDAO.getSequence("SEQ_DETAIL_NO"));
                break;
            case REFUND_DATA_NO:
                rtnNo = "W03"+DateUtil.getCurrentDateTime()+String.format("%07d", configInfoDAO.getSequence("SEQ_DETAIL_NO"));
                break;
            case TRAN_DATA_NO:
                rtnNo = "T00"+DateUtil.getCurrentDateTime()+String.format("%07d", configInfoDAO.getSequence("SEQ_DETAIL_NO"));
                break;
            case BANK_TRAN_DATA_NO:
                rtnNo = "B00"+DateUtil.getCurrentDateTime()+String.format("%07d", configInfoDAO.getSequence("SEQ_DETAIL_NO"));
                break;
            default :
                break;
        }
        return rtnNo;
    }

	

}
