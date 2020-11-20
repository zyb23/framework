package me.zyb.framework.core.base;

import me.zyb.framework.core.query.BaseCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangyingbin
 */
public abstract class BaseService<T extends BaseEntity> {
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected HttpServletResponse response;

	/**
	 * 分页数据
	 * @param condition 条件
	 * @return Page<T>
	 */
	public abstract <E extends BaseCondition>  Page<T> findPage(E condition);

	/**
	 * 构造条件
	 * @param condition 条件
	 * @return Specification<T>
	 */
	public abstract <E extends BaseCondition> Specification<T> buildSpecification(E condition);

	/**
	 * 根据ID查询
	 * @param id    主键
	 * @return T
	 */
	public abstract T findById(Long id);

	/**
	 * 统计
	 * @param condition 条件
	 * @return Long
	 */
	public abstract <E extends BaseCondition> Long count(E condition);
}
