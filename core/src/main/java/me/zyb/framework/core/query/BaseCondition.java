package me.zyb.framework.core.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import me.zyb.framework.core.dict.ConstNumber;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 基础查询条件
 * @author zhangyingbin
 */
@Data
public class BaseCondition implements Serializable {
	/** ID主键 */
	private Long id;
	/** 编码 */
	private String code;
	/** 名称 */
	private String name;

	/** 创建人ID */
	private Long creatorId;
	/** 创建时间开始 */
	private Date createTimeStart;
	/** 创建时间结束 */
	private Date createTimeEnd;
	/** 修改人ID */
	private Long editorId;
	/** 修改时间开始 */
	private Date editTimeStart;
	/** 修改时间结束 */
	private Date editTimeEnd;

	/** 页码 */
	@NotNull(message = "页码不能为空")
	private Integer pageNo = ConstNumber.DEFAULT_PAGE_NO;
	/** 每页条数 */
	@NotNull(message = "每页条数不能为空")
	private Integer pageSize = ConstNumber.DEFAULT_PAGE_SIZE;
	/**
	 * <p>排序条件</p>
	 * <p>例：{"code":"asc", "name": "desc"}</p>
	 */
	private LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();

	/** 排序Sort对象 */
	@JsonIgnore
	private Sort sort;

	/**
	 * <p>获取分页对象</p>
	 * <p>存在排序条件时，分页对象内设置排序条件的Sort</p>
	 * @return Pageable
	 */
	public Pageable getPageable(){
		if(null == sort){
			return PageRequest.of(this.pageNo - 1, this.pageSize);
		}else {
			return PageRequest.of(this.pageNo - 1, this.pageSize, sort);
		}
	}

	/**
	 * 设置排序参数
	 * @param orderBy 排序参数
	 */
	public void setOrderBy(LinkedHashMap<String, String> orderBy){
		this.orderBy = orderBy;
		List<Sort.Order> orderList = new ArrayList<Sort.Order>();
		orderBy.forEach((k, v) -> orderList.add(new Sort.Order(Sort.Direction.fromString(v), k)));
		this.sort = Sort.by(orderList);
	}
}
