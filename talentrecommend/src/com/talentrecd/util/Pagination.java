package com.talentrecd.util;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Pagination<E> extends SimplePage implements Serializable, Paginable {
 
	public Pagination() {
	}

	public Pagination(int pageNo, int pageSize, int totalCount) {
		super(pageNo, pageSize, totalCount);
	}

	
	public Pagination(int pageNo, int pageSize, int totalCount, List<E> list) {
		super(pageNo, pageSize, totalCount);
		this.list = list;
	}

	public int getFirstResult() {
		return (pageNo - 1) * pageSize;
	}

	/**
	 * 当前页的数据
	 */
	private List<E> list;

	public List<E> getList() {
		return list;
	}

	public void setList(List<E> list) {
		this.list = list;
	}
}