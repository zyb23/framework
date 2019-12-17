package me.zyb.framework.upms.repository;

import me.zyb.framework.upms.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhangyingbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface SysUserRepository extends JpaRepository<SysUser, Long>, JpaSpecificationExecutor<SysUser> {
	/**
	 * 根据登录名查询用户
	 * @param loginName 登录名
	 * @return SysUser
	 */
	public SysUser findByLoginName(String loginName);

	/**
	 * 根据角色ID查询
	 * @param roleId    角色ID
	 * @return List<SysUser>
	 */
	public List<SysUser> findByRoleList_Id(Long roleId);

	/**
	 * 修改登录密码
	 * @param loginPassword 新密码
	 * @param id    用户ID
	 */
	@Modifying
	@Query("update SysUser user set user.loginPassword = ?1 where user.id = ?2")
	public void updateLoginPasswordById(String loginPassword, Long id);

	/**
	 * 冻结用户
	 * @param id    用户ID
	 */
	@Modifying
	@Query("update SysUser user set user.isEnable = false")
	public void freezeById(Long id);

	/**
	 * 解冻用户
	 * @param id    用户ID
	 */
	@Modifying
	@Query("update SysUser user set user.isEnable = true")
	public void unfreezeById(Long id);
}
