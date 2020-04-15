package me.zyb.framework.upms.service;

import me.zyb.framework.upms.condition.UpmsDeptCondition;
import me.zyb.framework.upms.model.UpmsDeptModel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

/**
 * 部门服务
 * @author zhangyingbin
 */
public interface UpmsDeptService {

	/**
	 * 新增/修改部门
	 * @param model 数据模型
	 * @return UpmsDeptModel
	 */
	public UpmsDeptModel save(UpmsDeptModel model);

	/**
	 * <p>删除部门</p>
	 * <p>有子级部门的情况下不能删除</p>
	 */
	public void delete(Long deptId);

	/**
	 * 根据上级部门查询下级部门
	 * @param parentId  上级部门ID
	 * @return List<UpmsDeptModel>
	 */
	public List<UpmsDeptModel> queryByParentId(Long parentId);

	/**
	 * 查询所有部门
	 * @return List<UpmsDeptModel>
	 */
	public List<UpmsDeptModel> queryAll();

	/**
	 * 根据条件查询分页数据
	 * @param condition 查询条件
	 * @return Page<UpmsDeptModel>
	 */
	public Page<UpmsDeptModel> queryByCondition(UpmsDeptCondition condition);

	/**
	 * 根据ID查询所有权限
	 * @param idSet     权限ID列表
	 * @return List<UpmsPermissionModel>
	 */
	public List<UpmsDeptModel> queryByIdSet(Set<Long> idSet);

	/**
	 * 树形查询所有子级部门（附带parentId）
	 * @param parentId          父级部门ID（为null时，从顶级部门开始查询）
	 * @param needParent        是否要包含父级
	 * @param needChildren      是否要包含子级列表
	 * @return List<UpmsPermissionModel>
	 */
	public List<UpmsDeptModel> queryTree(Long parentId, boolean needParent, boolean needChildren);
}
