package me.zyb.framework.upms.service;

import me.zyb.framework.upms.condition.SysRoleCondition;
import me.zyb.framework.upms.model.SysPermissionModel;
import me.zyb.framework.upms.model.SysRoleModel;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author zhangyingbin
 */
public interface SysRoleService {

	/**
	 * 新增/修改权限
	 * @param model 数据模型
	 */
	public SysRoleModel save(SysRoleModel model);

	/**
	 * <p>删除角色</p>
	 * <p>用户角色关系：角色不是关系维护端，手动删除关系</p>
	 * <p>角色权限关系：角色是关系维护端，JPA会同时将角色权限中间表的关系删除</p>
	 */
	public void delete(Long roleId);

	/**
	 * 查询所有角色
	 */
	public List<SysRoleModel> queryAll();

	/**
	 * 根据条件查询分页数据
	 * @param condition  查询条件
	 * @return Page<SysRoleModel>
	 */
	public Page<SysRoleModel> queryByCondition(SysRoleCondition condition);

	/**
	 * 根据ID列表查询
	 * @param idList    ID列表
	 * @return List<SysRoleModel>
	 */
	public List<SysRoleModel> queryByIdList(List<Long> idList);
	/**
	 * 根据用户ID查询用户的角色
	 * @param userId   用户ID
	 * @return List<SysRoleModel>
	 */
	public List<SysRoleModel> queryByUserId(Long userId);

	/**
	 * 根据权限ID查询拥有该权限的所有角色
	 * @param permissionId   权限ID
	 * @return List<SysRoleModel>
	 */
	public List<SysRoleModel> queryByPermissionId(Long permissionId);

	/**
	 * <p>角色保存权限（中间有）</p>
	 * <p>根据permissionIdList判断</p>
	 * <p>1：角色已有permissionIdList中的权限，不作任何操作</p>
	 * <p>2：角色没有permissionIdList中的权限，新增</p>
	 * <p>3：角色现有权限不在permissionIdList中，删除</p>
	 * @param roleId        角色ID
	 * @param permissionIdList   权限ID列表
	 */
	public void savePermission(Long roleId, List<Long> permissionIdList);

	/**
	 * 查询角色拥有的所有权限
	 * @param roleId    角色ID
	 * @return List<SysPermissionModel>
	 */
	public List<SysPermissionModel> queryPermission(Long roleId);

	/**
	 * 删除角色权限（中间表）
	 * @param roleId            角色ID
	 * @param permissionIdList  权限ID列表
	 */
	public void deleteSysRolePermission(Long roleId, List<Long> permissionIdList);

	/**
	 * 查询角色详情，树形展示所有权限，并标识角色已有权限
	 * @param id    角色ID
	 * @return SysRoleModel
	 */
	public SysRoleModel queryDetail(Long id);
}
