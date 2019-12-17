package me.zyb.framework.upms.repository;

import me.zyb.framework.upms.entity.SysPermission;
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
public interface SysPermissionRepository extends JpaRepository<SysPermission, Long>, JpaSpecificationExecutor<SysPermission> {
	/**
	 * 根据权限编码查询权限
	 * @param code 权限编码
	 */
	public SysPermission findByCode(String code);

	/**
	 * 根据父级权限查询下一级权限
	 * @param parentId  父级权限ID
	 */
	public List<SysPermission> findByParent_Id(Long parentId);

	/**
	 * 根据父级权限ID、用户ID查询
	 * @param parentId  父级权限ID
	 * @param userId    用户ID
	 * @return List<SysPermission>
	 */
	public List<SysPermission> findByParent_IdAndRoleList_UserList_Id(Long parentId, Long userId);

	/**
	 * 根据父级权限查询下一级权限（排序）
	 * @param parentId  父级权限ID
	 */
	public List<SysPermission> findByParent_IdOrderBySortAsc(Long parentId);

	/**
	 * 根据角色ID查询
	 * @param roleId    角色ID
	 * @return List<SysPermission>
	 */
	public List<SysPermission> findByRoleList_Id(Long roleId);

	/**
	 * 根据用户ID查询
	 * @param userId    用户ID
	 * @return List<SysPermission>
	 */
	public List<SysPermission> findByRoleList_UserList_Id(Long userId);
}
