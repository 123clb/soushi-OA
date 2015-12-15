package com.kaoshidian.oa.base;

import com.kaoshidian.oa.util.CommonConstant;

public class PageBean {
	private int currentPage;
	private int totalCount;
	private int pageSize = CommonConstant.PAGE_SIZE;
	private int currTotalCount;
	
	public int getCurrTotalCount() {
		return currTotalCount;
	}

	public void setCurrTotalCount(int currTotalCount) {
		this.currTotalCount += currTotalCount;
	}

	public PageBean(int currentPage, int totalCount) {
		this.currentPage = currentPage;
		this.totalCount = totalCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
