package me.zyb.framework.upms.repository;

import me.zyb.framework.upms.entity.UpmsRole;
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
public interface UpmsRoleRepository extends JpaRepository<UpmsRole, Long>, JpaSpecificationExecutor<UpmsRole> {
	/**
	 * 根据名称查询
	 * @param name  角色名称
	 * @return UpmsRole
	 */
	public UpmsRole findByName(String name);

	/**
	 * 根据用户ID查询
	 * @param userId    用户ID
	 * @return List<UpmsRole>
	 */
	public List<UpmsRole> findByUserList_Id(Long userId);

	/**
	 * 根据权限ID查询
	 * @param permissionId  权限ID
	 * @return List<UpmsRole>
	 */
	public List<UpmsRole> findByPermissionList_Id(Long permissionId);
}
