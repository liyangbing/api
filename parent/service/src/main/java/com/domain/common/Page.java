package com.domain.common;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 页面modelWW
 * @param <E>
 */
public class Page<E> {
	
	/**
	 * 升序标识, asc
	 */
	public static final String ASC = "asc";
	
	/**
	 * 降序标识, desc
	 */
	public static final String DESC = "desc";
	
	/**
	 * 最小页面大小
	 */
	public static final int MIN_PAGESIZE = 5;
	
	/**
	 * 最大页面大小
	 */
	public static final int MAX_PAGESIZE = 200;
	
	/**
	 * 页码，默认为1
	 */
	protected int pageNo = 1;
	
	/**
	 * 页面大小，默认为5
	 */
	protected int pageSize = 3;
	
	/**
	 * 排序字段，如果有多个排序字段，字段之间用,分割
	 * 例如 id, name...
	 */
	protected String orderBy = null;
	
	/**
	 * 排序字段的排序顺序，如果有多个字段排序，顺序字段之间
	 * 用,分割，例如asc,desc....
	 */
	protected String order = null;
	
	/**
	 * 是否计算总的记录数，默认为true
	 */
	protected boolean autoCount = true;
	
	/**
	 * 记录游标的开始位置,默认为0
	 */
	protected Integer offset = 0;
	
	/**
	 * 结果集,list集合
	 */
	protected List<E> result = new ArrayList<E>();
	
	/**
	 * 记录总数,默认为-1
	 */
	protected long totalCount = -1;
	
	/**
	 * 总页数
	 */
	protected int pages;

	public Page() {
	}

	public Page(int pageSize) {
		setPageSize(pageSize);
	}

	public Page(int pageSize, boolean autoCount) {
		setPageSize(pageSize);
		this.autoCount = autoCount;
	}

	public int getPageNo() {
		return this.pageNo;
	}

	/**
	 * 设置页码，当参数小于1时，设置pageNo=1
	 * @param pageNo
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1)
			this.pageNo = 1;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	/**
	 * 设置页面大小，当页面大小最小值时，设置为最小值
	 * 当页面大小值时，设置为最大值
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < 5) {
			this.pageSize = 5;
		}
		if (pageSize > 200)
			this.pageSize = 200;
	}

	/**
	 * 得到页面记录的开始游标位置
	 * @return
	 */
	public int getFirst() {
		return (this.pageNo - 1) * this.pageSize;
	}

	public String getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * 查看页面是否实现了排序
	 * @return
	 */
	public boolean isOrderBySetted() {
		return StringUtils.isNotBlank(this.orderBy);
	}

	public String getOrder() {
		return this.order;
	}

	/**
	 * 设置排序关键字，排序有两种，asc代表升序，desc代表降序
	 * 当传过来关键字，首先进行分割，判断
	 * @param order
	 */
	public void setOrder(String order) {
		String[] orders = StringUtils.split(StringUtils.lowerCase(order), ',');
		for (String orderStr : orders) {
			if ((!StringUtils.equals("desc", orderStr))
					&& (!StringUtils.equals("asc", orderStr))) {
				throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
			}
		}
		this.order = StringUtils.lowerCase(order);
	}

	public boolean isAutoCount() {
		return this.autoCount;
	}

	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}

	public List<E> getResult() {
		return this.result;
	}

	public void setResult(List<E> result) {
		this.result = result;
	}

	public long getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 返回页面总数
	 * @return
	 */
	public long getTotalPages() {
		if (this.totalCount < 0) {
			return -1;
		}
		long count = this.totalCount / this.pageSize;
		if (this.totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}

	/**
	 * 判断是否有后页
	 * @return
	 */
	public boolean isHasNext() {
		return this.pageNo + 1 <= getTotalPages();
	}

	public int getNextPage() {
		if (isHasNext()) {
			return this.pageNo + 1;
		}
		return this.pageNo;
	}

	/**
	 * 判断是否还有前页
	 * @return
	 */
	public boolean isHasPre() {
		return this.pageNo - 1 >= 1;
	}

	public int getPrePage() {
		if (isHasPre()) {
			return this.pageNo - 1;
		}
		return this.pageNo;
	}

	public long getPages() {
		return (totalCount + pageSize- 1) / pageSize;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}
	
	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	
}
