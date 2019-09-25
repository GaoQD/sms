package com.sms.model;



public class Paging {

	private int currentPage;// 当前页
	private int pageStart;// 起始页
	private int pageSize;// 每页显示数量
	
	public Paging(int currentPage,int pageSize) {
		pageStart = (currentPage - 1) * pageSize;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageStart() {
		return pageStart;
	}
	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
