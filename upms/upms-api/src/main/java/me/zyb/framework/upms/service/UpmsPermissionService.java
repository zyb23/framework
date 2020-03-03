package me.zyb.framework.upms.service;

import me.zyb.framework.upms.condition.UpmsPermissionCondition;
import me.zyb.framework.upms.model.UpmsPermissionModel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

/**
 * @author zhangyingbin
 */
public interface UpmsPermissionService {

	/**
	 * 新增/修改权限
	 * @param model 数据模型
	 * @return UpmsPermissionModel
	 */
	public UpmsPermissionModel save(UpmsPermissionModel model);

	/**
	 * <p>删除权限</p>
	 * <p>有子级权限的情况下不能删除</p>
	 */
	public void delete(Long permissionId);

	/**
	 * 根据父级权限查询子级权限
	 * @param parentId  父级权限ID
	 * @return List<UpmsPermissionModel>
	 */
	public List<UpmsPermissionModel> queryByParentId(Long parentId);

	/**
	 * 查询所有权限
	 * @return List<UpmsPermissionModel>
	 */
	public List<UpmsPermissionModel> queryAll();

	/**
	 * 根据条件查询分页数据
	 * @param condition 查询条件
	 * @return Page<UpmsPermissionModel>
	 */
	public Page<UpmsPermissionModel> queryByCondition(UpmsPermissionCondition condition);

	/**
	 * 根据ID查询所有权限
	 * @param idSet     权限ID列表
	 * @return List<UpmsPermissionModel>
	 */
	public List<UpmsPermissionModel> queryByIdSet(Set<Long> idSet);

	/**
	 * 根据角色ID查询该角色的所有权限
	 * @param roleId    角色ID
	 * @return List<UpmsPermissionModel>
	 */
	public List<UpmsPermissionModel> queryByRoleId(Long roleId);

	/**
	 * 树形查询所有子级权限（附带parentId）
	 * @param parentId      父级权限ID（为null时，从顶级权限开始查询）
	 * @param needParent    是否要包含父级权限
	 * @param needChildren  是事要包含子级权限列表
	 * @return List<UpmsPermissionModel>
	 */
	public List<UpmsPermissionModel> queryTree(Long parentId, boolean needParent, boolean needChildren);
}
