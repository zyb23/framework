package me.zyb.framework.core.query;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * 分页查询返回结果对象
 * @author zhangyingbin
 * @param <T>
 */
@Data
public class Page<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 当前页面的结果集 */
	protected Collection<T> content = Collections.emptyList();
	
    /** 当前页码 */
	protected long pageNo = 1;
	/** 页面容量 */
	protected long pageSize = 20;
	
	/** 总条数 */
	protected long totalCount = -1L;
	/** 总页数 */
	protected long totalPages = -1L;
	
	/** 是否第一个数据 */
	protected Boolean isFirst = false;
	
	/** 是否最后一个数据 */
	protected Boolean isLast = false;

	public Page(){}
	
	public Page(Collection<T> content, int pageNo, int pageSize, long totalCount, long totalPages, Boolean isFirst, Boolean isLast){
		this.content = content;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.totalPages = totalPages;
		this.isFirst = isFirst;
		this.isLast = isLast;
	}
	
	/** ------------------------------ get、set ------------------------------*/
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		if(pageSize < 0) {
            this.pageSize = 1;
        }
	}
	
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
		
		if(this.totalCount < 0) {
			totalPages = -1L;
        }
		else {
			long count = this.totalCount / (long)this.pageSize;
            if(this.totalCount % (long)this.pageSize > 0L) {
                ++count;
            }

            totalPages = count;
        }
	}
	
	public long getTotalPages() {
		if(this.totalCount < 0L) {
            totalPages = -1L;
        }
		else {
			long count = this.totalCount / (long)this.pageSize;
            if(this.totalCount % (long)this.pageSize > 0L) {
                ++count;
            }

            totalPages = count;
        }
		
		return totalPages;
	}

	/**
	 * 获取当前页面第一条数据的排列
	 * @return
	 */
	public long getFirst() {
        return (this.pageNo - 1) * this.pageSize + 1;
    }
	
	/**
     * 是否还有下一页
     * @return
     */
    public boolean isHasNext() {
        return (this.pageNo + 1) <= this.getTotalPages();
    }

    /**
     * 得到下一页的页码
     * @return
     */
    public long getNextPage() {
        return this.isHasNext() ? this.pageNo + 1 : this.pageNo;
    }

    /**
     * 是否有上一页
     * @return
     */
    public boolean isHasPre() {
        return this.pageNo - 1 >= 1;
    }

    /**
     * 得到上一页页码
     * @return
     */
    public long getPrePage() {
        return this.isHasPre() ? this.pageNo - 1 : this.pageNo;
    }
}
