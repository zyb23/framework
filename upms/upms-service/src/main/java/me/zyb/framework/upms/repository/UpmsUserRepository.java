package me.zyb.framework.upms.repository;

import me.zyb.framework.upms.entity.UpmsUser;
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
public interface UpmsUserRepository extends JpaRepository<UpmsUser, Long>, JpaSpecificationExecutor<UpmsUser> {
	/**
	 * 根据登录名查询用户
	 * @param loginName 登录名
	 * @return UpmsUser
	 */
	public UpmsUser findByLoginName(String loginName);

	/**
	 * 根据角色ID查询
	 * @param roleId    角色ID
	 * @return List<UpmsUser>
	 */
	public List<UpmsUser> findByRoleList_Id(Long roleId);

	/**
	 * 修改登录密码
	 * @param loginPassword 新密码
	 * @param id    用户ID
	 */
	@Modifying
	@Query("update UpmsUser user set user.loginPassword = ?1 where user.id = ?2")
	public void updateLoginPasswordById(String loginPassword, Long id);

	/**
	 * 冻结用户
	 * @param id    用户ID
	 */
	@Modifying
	@Query("update UpmsUser user set user.isEnable = false")
	public void freezeById(Long id);

	/**
	 * 解冻用户
	 * @param id    用户ID
	 */
	@Modifying
	@Query("update UpmsUser user set user.isEnable = true")
	public void unfreezeById(Long id);
}
