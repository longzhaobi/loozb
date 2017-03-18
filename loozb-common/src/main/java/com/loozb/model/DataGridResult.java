package com.loozb.model;

import java.util.List;

public class DataGridResult {

	private Page page;
	
	private List<?> data;
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	public class Page {
		private long total;
		
		private long currentPage;

		public long getTotal() {
			return total;
		}

		public void setTotal(long total) {
			this.total = total;
		}

		public long getCurrentPage() {
			return currentPage;
		}

		public void setCurrentPage(long currentPage) {
			this.currentPage = currentPage;
		}
	}

	public static DataGridResult page(int pageNum, long total, List<?> list) {
		DataGridResult result = new DataGridResult();
		DataGridResult.Page page = new DataGridResult().new Page();
		page.setCurrentPage(pageNum);
		page.setTotal(total);
		result.setData(list);
		result.setPage(page);
		return result;
	}
	

}
