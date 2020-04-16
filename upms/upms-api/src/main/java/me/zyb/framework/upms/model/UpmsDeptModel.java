package me.zyb.framework.upms.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 部门实体-数据表
 * @author zhangyingbin
 */
@Data
public class UpmsDeptModel implements Serializable {
	private static final long serialVersionUID = 1L;

	/** ID主键 */
	private Long id;

	/** 部门名称 */
	private String name;

	/** 部门级别 */
	private Integer level = 1;

	/** 部门负责人 */
	private String principal;

	/** 创建人ID */
	protected Long creatorId;

	/** 创建时间 */
	protected Date createTime;

	/** 修改人ID */
	protected Long editorId;

	/** 修改时间 */
	protected Date editTime;

	/** 父级ID */
	private Long parentId;

	/** 上级部门 */
	private UpmsDeptModel parent;

	/** 下级部门列表 */
	private List<UpmsDeptModel> children = new ArrayList<>();

	/** 部门成员列表 */
	private List<UpmsUserModel> userList = new ArrayList<>();
}