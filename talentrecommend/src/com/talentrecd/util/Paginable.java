package com.talentrecd.util;

public interface Paginable {

	/**
	 * 总记录数
	 */
	public int getTotalCount();

	/**
	 * 总页
	 */
	public int getTotalPage();

	/**
	 * 每页记录
	 */
	public int getPageSize();

	/**
	 * 当前页号
	 */
	public int getPageNo();

	/**
	 * 是否第一
	 */
	public boolean isFirstPage();

	/**
	 * 是否�?��
	 */
	public boolean isLastPage();

	/**
	 * 返回下页的页�?	 */
	public int getNextPage();

	/**
	 * 返回上页的页�?	 */
	public int getPrePage();
}
