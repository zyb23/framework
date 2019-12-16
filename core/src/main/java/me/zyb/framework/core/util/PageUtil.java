package me.zyb.framework.core.util;

import me.zyb.framework.core.query.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * 分页工具类
 * @author zhangyingbin
 *
 */
public class PageUtil {

	/**
	 * 创建分页请求
	 * @param pageNo    页码
	 * @param pageSize  每页条数
	 * @param sort      排序规则
	 * @return org.springframework.data.domain.PageRequest
	 */
    public static PageRequest buildPageRequest(int pageNo, int pageSize, Sort sort) {
        return PageRequest.of(pageNo - 1, pageSize, sort);
    }
    
    /**
     * 返回分页数据对象
     * @param springPage    spring-data分页对象
     * @return Page<T>      自己定义分页对象
     */
    public static <E> Page<E> convertPage(org.springframework.data.domain.Page<E> springPage){
    	Page<E> page = new Page<E>();
		page.setContent(springPage.getContent());
		page.setPageNo(springPage.getNumber() + 1);
		page.setPageSize(springPage.getSize());
		page.setTotalCount(springPage.getTotalElements());
		page.setTotalPages(springPage.getTotalPages());
		page.setIsFirst(springPage.isFirst());
		page.setIsLast(springPage.isLast());
		
		return page;
    }
}
