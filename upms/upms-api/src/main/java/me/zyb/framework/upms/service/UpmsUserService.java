package me.zyb.framework.upms.service;

import me.zyb.framework.upms.condition.UpmsUserCondition;
import me.zyb.framework.upms.model.UpmsPermissionModel;
import me.zyb.framework.upms.model.UpmsRoleModel;
import me.zyb.framework.upms.model.UpmsUserModel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

/**
 * @author zhangyingbin
 */
public interface UpmsUserService {

	/**
	 * <p>新增/修改用户</p>
	 * <p>编辑用户时，登录密码不能编辑，需调用单独的修改登录密码接口</p>
	 * <p>每个用户有单独修改自己的登录密码的默认权限及接口</p>
	 * @param model 数据模型
	 */
	public UpmsUserModel save(UpmsUserModel model);

	/**
	 * <p>删除用户</p>
	 * <p>用户角色关系：用户是关系维护端，JPA会同时将用户角色中间表的关系删除</p>
	 * @param userId    用户ID
	 */
	public void delete(Long userId);

	/**
	 * 修改/重置用户登录密码
	 * @param userId        用户ID
	 * @param loginPassword 新密码
	 */
	public void updateLoginPassword(Long userId, String loginPassword);

	/**
	 * 查询所有用户
	 * @return List<UpmsUserModel>
	 */
	public List<UpmsUserModel> queryAll();

	/**
	 * 根据条件查询分页数据
	 * @param condition 查询条件
	 * @return Page<UpmsUserModel>
	 */
	public Page<UpmsUserModel> queryByCondition(UpmsUserCondition condition);

	/**
	 * 根据登录名查询用户
	 * @param loginName 登录名
	 * @return UpmsUserModel
	 */
	public UpmsUserModel queryByLoginName(String loginName);

	/**
	 * <pre>
	 *     用户保存角色（中间表）
	 *     根据roleIdSet判断
	 *     1：用户已有roleIdSet中的角色，不作任何操作
	 *     2：用户没有roleIdSet中的角色，新增
	 *     3：用户现有角色不在roleIdSet中，删除
	 * </pre>
	 * @param userId        用户ID
	 * @param roleIdSet     角色ID列表
	 */
	public void saveRole(Long userId, Set<Long> roleIdSet);

	/**
	 * 查询用户的角色列表
	 * @param userId    用户ID
	 * @return List<UpmsRoleModel>
	 */
	public List<UpmsRoleModel> queryRole(Long userId);

	/**
	 * 删除用户角色（中间表）
	 * @param userId        用户ID
	 * @param roleIdSet     角色ID列表
	 */
	public void deleteUpmsUserRole(Long userId, Set<Long> roleIdSet);

	/**
	 * 查询用户的权限列表
	 * @param userId                用户ID
	 * @param parentPermissionId    父级权限ID
	 * @return List<UpmsPermissionModel>
	 */
	public List<UpmsPermissionModel> queryPermission(Long userId, Long parentPermissionId);

	/**
	 * 查询用户顶级菜单权限
	 * @param userId    用户ID
	 * @return List<UpmsPermissionModel>
	 */
	public List<UpmsPermissionModel> queryTopPermission(Long userId);

	/**
	 * 查询用户所有权限
	 * @param userId    用户ID
	 * @return List<UpmsPermissionModel>
	 */
	public List<UpmsPermissionModel> queryAllPermission(Long userId);

	/**
	 * 查询用户的权限列表，树形展示
	 * @param userId                用户ID
	 * @return List<UpmsPermissionModel>
	 */
	public List<UpmsPermissionModel> queryPermissionTree(Long userId);

	/**
	 * 用户登录，并返回用户相关信息（根据登录名和登录密码查询用户）
	 * @param loginName     登录名
	 * @param loginPassword 登录密码
	 * @return UpmsUserModel
	 */
	public UpmsUserModel login(String loginName, String loginPassword);

	/**
	 * 根据token（sessionId）获取自己的信息
	 * @return UpmsUserModel
	 */
	public UpmsUserModel getSelfInfo(String token);

	/**
	 * 用户登出
	 */
	public void logout();

	/**
	 * 根据角色ID查询拥有该角色的所有用户
	 * @param roleId    角色ID
	 * @return List<UpmsUserModel>
	 */
	public List<UpmsUserModel> queryByRoleId(Long roleId);

	/**
	 * 通过ID列表获取用户
	 * @param idSet     ID列表
	 * @return List<UpmsUserModel>
	 */
	public List<UpmsUserModel> queryByIdSet(Set<Long> idSet);

	/**
	 * <p>根据ID锁定用户</p>
	 * <p>密码输入错误次数等均有可能锁定用户</p>
	 * <p>锁定有时效性，不操作数据库，只操作缓存（如Redis）</p>
	 * @param id    用户ID
	 */
	public void lockById(Long id);

	/**
	 * <p>根据ID解锁用户</p>
	 * <p>密码输入错误次数等均有可能锁定用户</p>
	 * <p>锁定有时效性，不操作数据库，只操作缓存（如Redis）</p>
	 * @param id    用户ID
	 */
	public void unlockById(Long id);

	/**
	 * 根据ID冻结用户
	 * @param id    用户ID
	 * @return UpmsUserModel
	 */
	public void freezeById(Long id);

	/**
	 * 根据ID解冻用户
	 * @param id    用户ID
	 * @return UpmsUserModel
	 */
	public void unfreezeById(Long id);

	/**
	 * 冻结或解冻用户
	 * @param userId    用户ID
	 * @param isEnable  false：冻结，true：解冻
	 */
	public void freezeUnfreezeById(Long userId, boolean isEnable);
}
