package me.zyb.framework.upms.service;

import me.zyb.framework.upms.model.SysPermissionModel;
import me.zyb.framework.upms.condition.SysPermissionCondition;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author zhangyingbin
 */
public interface SysPermissionService {

	/**
	 * 新增/修改权限
	 * @param model 数据模型
	 * @return SysPermissionModel
	 */
	public SysPermissionModel save(SysPermissionModel model);

	/**
	 * <p>删除权限</p>
	 * <p>有子级权限的情况下不能删除</p>
	 */
	public void delete(Long permissionId);

	/**
	 * 根据父级权限查询子级权限
	 * @param parentId  父级权限ID
	 * @return List<SysPermissionModel>
	 */
	public List<SysPermissionModel> queryByParentId(Long parentId);

	/**
	 * 查询所有权限
	 * @return List<SysPermissionModel>
	 */
	public List<SysPermissionModel> queryAll();

	/**
	 * 根据条件查询分页数据
	 * @param condition 查询条件
	 * @return Page<SysPermissionModel>
	 */
	public Page<SysPermissionModel> queryByCondition(SysPermissionCondition condition);

	/**
	 * 根据ID查询所有权限
	 * @param idList   权限ID列表
	 * @return List<SysPermissionModel>
	 */
	public List<SysPermissionModel> queryByIdList(List<Long> idList);

	/**
	 * 根据角色ID查询该角色的所有权限
	 * @param roleId    角色ID
	 * @return List<SysPermissionModel>
	 */
	public List<SysPermissionModel> queryByRoleId(Long roleId);

	/**
	 * 树形查询所有子级权限（附带parentId）
	 * @param parentId      父级权限ID（为null时，从顶级权限开始查询）
	 * @param needParent    是否要包含父级权限
	 * @param needChildren  是事要包含子级权限列表
	 * @return List<SysPermissionModel>
	 */
	public List<SysPermissionModel> queryTree(Long parentId, boolean needParent, boolean needChildren);
}
