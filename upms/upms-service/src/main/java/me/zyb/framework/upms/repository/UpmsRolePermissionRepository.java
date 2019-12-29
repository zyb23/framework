package me.zyb.framework.upms.repository;

import me.zyb.framework.upms.entity.UpmsRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhangyingbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface UpmsRolePermissionRepository extends JpaRepository<UpmsRolePermission, Long>, JpaSpecificationExecutor<UpmsRolePermission> {
	/**
	 * 根据角色ID查询
	 * @param roleId    角色ID
	 * @return List<UpmsRolePermission>
	 */
	public List<UpmsRolePermission> findByRoleId(Long roleId);

	/**
	 * 根据角色ID列表查询
	 * @param roleIdList    角色ID列表
	 * @return List<UpmsRolePermission>
	 */
	public List<UpmsRolePermission> findByRoleIdIn(List<Long> roleIdList);

	/**
	 * 根据角色ID、权限ID列表查询
	 * @param roleId            角色ID
	 * @param permissionIdList  权限ID列表
	 * @return List<UpmsRolePermission>
	 */
	public List<UpmsRolePermission> findByRoleIdAndPermissionIdIn(Long roleId, List<Long> permissionIdList);

	/**
	 * 根据权限ID查询
	 * @param permissionId    权限ID
	 * @return List<UpmsRolePermission>
	 */
	public List<UpmsRolePermission> findByPermissionId(Long permissionId);

	/**
	 * 根据权限ID列表查询
	 * @param permissionIdList   权限ID列表
	 * @return List<UpmsRolePermission>
	 */
	public List<UpmsRolePermission> findByPermissionIdIn(List<Long> permissionIdList);

	/**
	 * 根据权限ID、角色ID列表查询
	 * @param permissionId  权限ID
	 * @param roleIdList    角色ID列表
	 * @return List<UpmsRolePermission>
	 */
	public List<UpmsRolePermission> findByPermissionIdAndRoleIdIn(Long permissionId, List<Long> roleIdList);

	/**
	 * 根据角色ID删除
	 * @param roleId    角色ID
	 */
	public void deleteByRoleId(Long roleId);

	/**
	 * 根据角色ID列表删除
	 * @param roleIdList    角色ID
	 */
	public void deleteByRoleIdIn(List<Long> roleIdList);

	/**
	 * 根据角色ID、权限ID删除
	 * @param roleId        角色ID
	 * @param permissionId  权限ID
	 */
	public void deleteByRoleIdAndPermissionId(Long roleId, Long permissionId);

	/**
	 * 根据角色ID、权限ID列表删除
	 * @param roleId            角色ID
	 * @param permissionIdList  权限ID列表
	 */
	public void deleteByRoleIdAndPermissionIdIn(Long roleId, List<Long> permissionIdList);

	/**
	 * 根据权限ID删除
	 * @param permissionId  权限ID
	 */
	public void deleteByPermissionId(Long permissionId);

	/**
	 * 根据权限ID列表删除
	 * @param permissionIdList  权限ID列表
	 */
	public void deleteByPermissionIdIn(List<Long> permissionIdList);

	/**
	 * 根据权限ID、角色ID列表删除
	 * @param permissionId  权限ID
	 * @param roleIdList    角色ID列表
	 */
	public void deleteByPermissionIdAndRoleIdIn(Long permissionId, List<Long> roleIdList);
}
