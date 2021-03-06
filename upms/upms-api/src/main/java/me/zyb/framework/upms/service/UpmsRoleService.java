package me.zyb.framework.upms.service;

import me.zyb.framework.upms.condition.UpmsRoleCondition;
import me.zyb.framework.upms.model.UpmsPermissionModel;
import me.zyb.framework.upms.model.UpmsRoleModel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

/**
 * @author zhangyingbin
 */
public interface UpmsRoleService {

	/**
	 * 新增/修改权限
	 * @param model 数据模型
	 */
	public UpmsRoleModel save(UpmsRoleModel model);

	/**
	 * <p>删除角色</p>
	 * <p>用户角色关系：角色不是关系维护端，手动删除关系</p>
	 * <p>角色权限关系：角色是关系维护端，JPA会同时将角色权限中间表的关系删除</p>
	 */
	public void delete(Long roleId);

	/**
	 * 查询所有角色
	 */
	public List<UpmsRoleModel> queryAll();

	/**
	 * 根据条件查询分页数据
	 * @param condition  查询条件
	 * @return Page<UpmsRoleModel>
	 */
	public Page<UpmsRoleModel> queryByCondition(UpmsRoleCondition condition);

	/**
	 * 根据ID列表查询
	 * @param idSet     ID列表
	 * @return List<UpmsRoleModel>
	 */
	public List<UpmsRoleModel> queryByIdSet(Set<Long> idSet);
	/**
	 * 根据用户ID查询用户的角色
	 * @param userId   用户ID
	 * @return List<UpmsRoleModel>
	 */
	public List<UpmsRoleModel> queryByUserId(Long userId);

	/**
	 * 根据权限ID查询拥有该权限的所有角色
	 * @param permissionId   权限ID
	 * @return List<UpmsRoleModel>
	 */
	public List<UpmsRoleModel> queryByPermissionId(Long permissionId);

	/**
	 * <pre>
	 *     角色保存权限（中间有）
	 *     根据permissionIdSet判断
	 *     1：角色已有permissionIdSet中的权限，不作任何操作
	 *     2：角色没有permissionIdSet中的权限，新增
	 *     3：角色现有权限不在permissionIdSet中，删除
	 * </pre>
	 */
	public void savePermission(Long roleId, Set<Long> permissionIdSet);

	/**
	 * 查询角色拥有的所有权限
	 * @param roleId    角色ID
	 * @return List<UpmsPermissionModel>
	 */
	public List<UpmsPermissionModel> queryPermission(Long roleId);

	/**
	 * 删除角色权限（中间表）
	 * @param roleId            角色ID
	 * @param permissionIdSet   权限ID列表
	 */
	public void deleteUpmsRolePermission(Long roleId, Set<Long> permissionIdSet);

	/**
	 * 查询角色详情，树形展示所有权限，并标识角色已有权限
	 * @param id    角色ID
	 * @return UpmsRoleModel
	 */
	public UpmsRoleModel queryDetail(Long id);
}
