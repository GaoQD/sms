package com.sms.model;



public class Paging {

	private int currentPage;// ��ǰҳ
	private int pageStart;// ��ʼҳ
	private int pageSize;// ÿҳ��ʾ����
	
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
