package com.zlebank.zplatform.business.insteadPay.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.commons.dao.InsteadPayBatchDAO;
import com.zlebank.zplatform.business.exception.BusinessInsteadPayException;
import com.zlebank.zplatform.business.exception.BusinessMemberException;
import com.zlebank.zplatform.business.insteadPay.bean.InsteadPayQueryFile;
import com.zlebank.zplatform.business.insteadPay.bean.InsteadPayQuery_Request;
import com.zlebank.zplatform.business.insteadPay.bean.InsteadPayQuery_Response;
import com.zlebank.zplatform.business.insteadPay.service.QueryInsteadPayService;
import com.zlebank.zplatform.business.pojo.PojoInsteadPayBatch;
import com.zlebank.zplatform.business.pojo.PojoInsteadPayDetail;

import net.sf.json.JSONObject;
@Service("busQueryInsteadPayService")
public class QueryInsteadPayServiceImpl implements QueryInsteadPayService {
	private final static Logger log = LoggerFactory.getLogger(QueryInsteadPayServiceImpl.class);
	
	@Autowired
	private InsteadPayBatchDAO insteadPayBatchDAO;
	
	@Override
	public InsteadPayQuery_Response  batchInsteadPayQuery(InsteadPayQuery_Request requestBean) throws BusinessInsteadPayException {
        if (log.isDebugEnabled()) {
            log.debug("代付状态查询开始");
            log.debug(JSONObject.fromObject(requestBean)+"");
        }
        if(requestBean==null  ){
        	throw new BusinessInsteadPayException("EI0000");
        }
        InsteadPayQuery_Response responseBean = new InsteadPayQuery_Response();
        // 查询该批次
        PojoInsteadPayBatch batch = insteadPayBatchDAO.getByBatchNo(
                requestBean.getBatchNo(), requestBean.getTxnTime());
        if (batch == null) {
            responseBean.setRespCode("61");
            responseBean.setRespMsg("该批次不存在");
            return responseBean;
        }
        // 总笔数
        int totalQty = 0;
        // 总金额
        Long totalAmt = 0L;
        // 未处理笔数
        int waitQty = 0;
        // 未处理金额
        Long waitAmt = 0L;
        // 成功笔数
        int successQty = 0;
        // 成功金额
        Long successAmt = 0L;
        // 失败笔数
        int failQty = 0;
        // 失败金额
        Long failAmt = 0L;
        List<InsteadPayQueryFile> queryFiles = new ArrayList<InsteadPayQueryFile>();
        List<PojoInsteadPayDetail> detailList = batch.getDetails();
        for (PojoInsteadPayDetail detail : detailList) {
            totalQty++;
            totalAmt += detail.getAmt();
            if ("00".equals(detail.getRespCode())) {
                successQty++;
                successAmt = successAmt+ detail.getAmt();
            } else if ("01".equals(detail.getRespCode()) || "02".equals(detail.getRespCode()) || "03".equals(detail.getRespCode()) || "04".equals(detail.getRespCode())) {
                failQty++;
                failAmt = failAmt+ detail.getAmt();
            } else {
                waitQty++;
                waitAmt += detail.getAmt();
            }
            InsteadPayQueryFile queryFile = new InsteadPayQueryFile();
            queryFile.setMerId(detail.getMerId());
            queryFile.setOrderId(detail.getOrderId());
            queryFile.setCurrencyCode(detail.getCurrencyCode());
            queryFile.setAmt(String.valueOf(detail.getAmt()));
            queryFile.setBizType(detail.getBizType());
            queryFile.setAccType(detail.getAccType());
            queryFile.setAccNo(detail.getAccNo());
            queryFile.setAccName(detail.getAccName());
            queryFile.setBankCode(detail.getBankCode());
            queryFile.setIssInsProvince(detail.getIssInsProvince());
            queryFile.setIssInsCity(detail.getIssInsCity());
            queryFile.setIssInsName(detail.getIssInsName());
            queryFile.setCertifTp(detail.getCertifTp());
            queryFile.setCertifId(detail.getCertifId());
            queryFile.setPhoneNo(String.valueOf(detail.getPhoneNo()));
            queryFile.setBillType(detail.getBillType());
            queryFile.setNotes(detail.getNotes());
            // 00：划拨完成 09：划拨失败  11：未处理
            queryFile.setRespCode(detail.getRespCode());
            queryFile.setRespMsg(detail.getRespMsg());
            queryFile.setTxnFee(detail.getTxnfee() == null ? "0" : detail.getTxnfee().toString()); // 手续费
            queryFile.setInsteadPayDataSeqNo(detail.getInsteadPayDataSeqNo()); // 代付交易流水号
            queryFiles.add(queryFile);
        }
        // 应答报文
        responseBean.setFileContent(queryFiles);
        responseBean.setTotalQty(String.valueOf(totalQty));
        responseBean.setTotalAmt(String.valueOf(totalAmt));
        responseBean.setWaitTotalQty(String.valueOf(waitQty));
        responseBean.setWaitTotalAmt(String.valueOf(waitAmt));
        responseBean.setSuccTotalQty(String.valueOf(successQty));
        responseBean.setSuccTotalAmt(String.valueOf(successAmt));
        responseBean.setFailTotalQty(String.valueOf(failQty));
        responseBean.setFailTotalAmt(String.valueOf(failAmt));
        responseBean.setRespCode("00");
        responseBean.setRespMsg("成功！");
        if (log.isDebugEnabled()) {
            log.debug("代付状态查询结束");
        }
        return responseBean;

	}

}
