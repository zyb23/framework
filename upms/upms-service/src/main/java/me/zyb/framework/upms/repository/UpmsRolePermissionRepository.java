package me.zyb.framework.upms.repository;

import me.zyb.framework.upms.entity.UpmsRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

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
	 * @param roleIdSet     角色ID列表
	 * @return List<UpmsRolePermission>
	 */
	public List<UpmsRolePermission> findByRoleIdIn(Set<Long> roleIdSet);

	/**
	 * 根据角色ID、权限ID列表查询
	 * @param roleId            角色ID
	 * @param permissionIdSet   权限ID列表
	 * @return List<UpmsRolePermission>
	 */
	public List<UpmsRolePermission> findByRoleIdAndPermissionIdIn(Long roleId, Set<Long> permissionIdSet);

	/**
	 * 根据权限ID查询
	 * @param permissionId    权限ID
	 * @return List<UpmsRolePermission>
	 */
	public List<UpmsRolePermission> findByPermissionId(Long permissionId);

	/**
	 * 根据权限ID列表查询
	 * @param permissionIdSet   权限ID列表
	 * @return List<UpmsRolePermission>
	 */
	public List<UpmsRolePermission> findByPermissionIdIn(Set<Long> permissionIdSet);

	/**
	 * 根据权限ID、角色ID列表查询
	 * @param permissionId  权限ID
	 * @param roleIdSet     角色ID列表
	 * @return List<UpmsRolePermission>
	 */
	public List<UpmsRolePermission> findByPermissionIdAndRoleIdIn(Long permissionId, Set<Long> roleIdSet);

	/**
	 * 根据角色ID删除
	 * @param roleId    角色ID
	 */
	public void deleteByRoleId(Long roleId);

	/**
	 * 根据角色ID列表删除
	 * @param roleIdSet     角色ID
	 */
	public void deleteByRoleIdIn(Set<Long> roleIdSet);

	/**
	 * 根据角色ID、权限ID删除
	 * @param roleId        角色ID
	 * @param permissionId  权限ID
	 */
	public void deleteByRoleIdAndPermissionId(Long roleId, Long permissionId);

	/**
	 * 根据角色ID、权限ID列表删除
	 * @param roleId            角色ID
	 * @param permissionIdSet   权限ID列表
	 */
	public void deleteByRoleIdAndPermissionIdIn(Long roleId, Set<Long> permissionIdSet);

	/**
	 * 根据权限ID删除
	 * @param permissionId  权限ID
	 */
	public void deleteByPermissionId(Long permissionId);

	/**
	 * 根据权限ID列表删除
	 * @param permissionIdSet   权限ID列表
	 */
	public void deleteByPermissionIdIn(Set<Long> permissionIdSet);

	/**
	 * 根据权限ID、角色ID列表删除
	 * @param permissionId  权限ID
	 * @param roleIdSet     角色ID列表
	 */
	public void deleteByPermissionIdAndRoleIdIn(Long permissionId, Set<Long> roleIdSet);
}
