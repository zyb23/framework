package me.zyb.framework.upms.repository;

import me.zyb.framework.upms.entity.UpmsUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户角色关联
 * @author zhangyingbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface UpmsUserRoleRepository extends JpaRepository<UpmsUserRole, Long>, JpaSpecificationExecutor<UpmsUserRole> {
	/**
	 * 根据用户ID查询
	 * @param userId    用户ID
	 * @return List<AdminUserRole>
	 */
	public List<UpmsUserRole> findByUserId(Long userId);

	/**
	 * 根据用户ID查询
	 * @param userIdList    用户ID
	 * @return List<AdminUserRole>
	 */
	public List<UpmsUserRole> findByUserIdIn(List<Long> userIdList);

	/**
	 * 根据用户ID、角色ID列表查询
	 * @param userId        用户ID
	 * @param roleIdList    角色ID列表
	 * @return List<UpmsUserRole>
	 */
	public List<UpmsUserRole> findByUserIdAndRoleIdIn(Long userId, List<Long> roleIdList);

	/**
	 * 根据角色ID查询
	 * @param roleId    角色ID
	 * @return List<AdminUserRole>
	 */
	public List<UpmsUserRole> findByRoleId(Long roleId);

	/**
	 * 根据角色ID查询
	 * @param roleIdList    角色ID列表
	 * @return List<UpmsUserRole>
	 */
	public List<UpmsUserRole> findByRoleIdIn(List<Long> roleIdList);

	/**
	 * 根据角色ID、用户ID列表查询
	 * @param roleId        角色ID
	 * @param userIdList    用户ID列表
	 * @return List<UpmsUserRole>
	 */
	public List<UpmsUserRole> findByRoleIdAndUserIdIn(Long roleId, List<Long> userIdList);

	/**
	 * 根据用户ID删除
	 * @param userId    用户ID
	 */
	public void deleteByUserId(Long userId);

	/**
	 * 根据用户ID列表删除
	 * @param userIdList    用户ID列表
	 */
	public void deleteByUserIdIn(List<Long> userIdList);

	/**
	 * 根据用户ID、角色ID删除
	 * @param userId    用户ID
	 * @param roleId    角色ID
	 */
	public void deleteByUserIdAndRoleId(Long userId, Long roleId);

	/**
	 * 根据用户ID、角色ID列表删除
	 * @param userId        用户ID
	 * @param roleIdList    角色ID列表
	 */
	public void deleteByUserIdAndRoleIdIn(Long userId, List<Long> roleIdList);

	/**
	 * 根据角色ID删除
	 * @param roleId    角色ID
	 */
	public void deleteByRoleId(Long roleId);

	/**
	 * 根据角色ID列表删除
	 * @param roleIdList    角色ID列表
	 */
	public void deleteByRoleIdIn(List<Long> roleIdList);

	/**
	 * 根据角色ID、用户ID列表删除
	 * @param roleId        角色ID
	 * @param userIdList    用户ID列表
	 */
	public void deleteByRoleIdAndUserIdIn(Long roleId, List<Long> userIdList);
}
