package me.zyb.framework.upms.repository;

import me.zyb.framework.upms.entity.SysRole;
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
public interface SysRoleRepository extends JpaRepository<SysRole, Long>, JpaSpecificationExecutor<SysRole> {
	/**
	 * 根据名称查询
	 * @param name  角色名称
	 * @return SysRole
	 */
	public SysRole findByName(String name);

	/**
	 * 根据用户ID查询
	 * @param userId    用户ID
	 * @return List<SysRole>
	 */
	public List<SysRole> findByUserList_Id(Long userId);

	/**
	 * 根据权限ID查询
	 * @param permissionId  权限ID
	 * @return List<SysRole>
	 */
	public List<SysRole> findByPermissionList_Id(Long permissionId);
}
