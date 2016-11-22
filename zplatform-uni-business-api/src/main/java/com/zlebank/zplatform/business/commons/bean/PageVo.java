package com.zlebank.zplatform.business.commons.bean;

import java.io.Serializable;
import java.util.List;

public class PageVo<T> implements Serializable {
	
	private static final long serialVersionUID = -6201007489473238659L;
	//列表
	public List<T> list;
	//总条数
	public int total;
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
