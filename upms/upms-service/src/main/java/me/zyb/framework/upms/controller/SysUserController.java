package me.zyb.framework.upms.controller;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.base.BaseController;
import me.zyb.framework.core.ReturnCode;
import me.zyb.framework.core.util.regex.StringRegex;
import me.zyb.framework.upms.UpmsException;
import me.zyb.framework.upms.condition.SysUserCondition;
import me.zyb.framework.upms.configure.ShiroAuthHelper;
import me.zyb.framework.upms.configure.UpmsProperties;
import me.zyb.framework.upms.dict.SysPermissionCode;
import me.zyb.framework.upms.model.SysPermissionModel;
import me.zyb.framework.upms.model.SysUserModel;
import me.zyb.framework.upms.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author zhangyingbin
 */
@Slf4j
@RequestMapping("/sysUser")
@RestController
public class SysUserController extends BaseController {
	@Autowired
	private UpmsProperties upmsProperties;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 分页数据
     * @param condition 查询条件
     * @return Object
     */
    @RequiresPermissions(SysPermissionCode.USER_QUERY)
    @RequestMapping("/queryByCondition")
    public Object queryByCondition(@RequestBody SysUserCondition condition) {
        Page<SysUserModel> page = sysUserService.queryByCondition(condition);
        return rtSuccess(page);
    }

    /**
     * 新增用户
     * @param model 用户信息
     * @return Object
     */
    @RequiresPermissions(SysPermissionCode.USER_ADD)
    @PostMapping("/add")
    public Object add(@Valid @RequestBody SysUserModel model) {
    	String message;
        if (StringUtils.isBlank(model.getLoginPassword())) {
	        message = "loginPassword不能为空";
            log.warn(message);
            return rtParameterError(message);
        }
	    validatePassword(model);

        SysUserModel userModel = sysUserService.save(model);

        return rtSuccess(userModel);
    }

	/**
	 * 校验前端密码参数
	 * @param model 前端参数
	 */
	private void validatePassword(SysUserModel model){
		//是否需要输入确认密码
		if(upmsProperties.getSwitchConfirmPassword() && StringUtils.isBlank(model.getConfirmPassword())){
			log.warn("confirmPassword不能为空");
			throw new UpmsException("confirmPassword不能为空");
		}
		//密码强度策略
		if(upmsProperties.getSwitchValidatePasswordPolicy()){
			if (!StringRegex.isAlphabetAndNumber(model.getLoginPassword())) {
				log.warn("密码至少是数字和字母组合");
				throw new UpmsException("密码至少是数字和字母组合");
			}
			int shortest = upmsProperties.getPasswordPolicyShortest();
			int longest = upmsProperties.getPasswordPolicyLongest();
			if (!StringRegex.checkLength(shortest, longest, model.getLoginPassword())) {
				String message = "密码长度必须是" + shortest + "-" + longest + "位";
				log.warn(message);
				throw new UpmsException(message);
			}
		}
	}

    /**
     * 编辑用户
     * @param model 用户信息
     * @return Object
     */
    @PostMapping("/edit")
    @RequiresPermissions(SysPermissionCode.USER_EDIT)
    public Object edit(@RequestBody SysUserModel model) {
    	String message;
        if (null == model.getId()) {
	        message = "id不能为空";
        	log.error(message);
            return rtParameterError(message);
        }
        SysUserModel userModel = sysUserService.save(model);

        return rtSuccess(userModel);
    }

    /**
     * 用户修改自己的信息
     * @param model 用户信息
     * @return Object
     */
    @PostMapping("/editSelf")
    public Object editSelf(@RequestBody SysUserModel model) {
	    String message;
	    Long userId = model.getId();
	    if (null == userId) {
		    message = "id不能为空";
		    log.error(message);
		    return rtParameterError(message);
	    }
        if (!userId.equals(ShiroAuthHelper.getCurrentSysUserId())) {
        	message = "本操作只能修改自己的信息";
            log.error(message);
            return rtFailure(message);
        }
        SysUserModel userModel = sysUserService.save(model);

        return rtSuccess(userModel);
    }

    /**
     * 修改/重置用户登录密码
     * @param model 用户信息
     * @return Object
     */
    @RequiresPermissions(SysPermissionCode.USER_UPDATE_LOGIN_PASSWORD)
    @PostMapping("/updateLoginPassword")
    public Object updateLoginPassword(@RequestBody SysUserModel model) {
        if (null == model.getId() || StringUtils.isBlank(model.getLoginPassword())) {
            return rtParameterError();
        }
	    if(upmsProperties.getSwitchConfirmPassword() && StringUtils.isBlank(model.getConfirmPassword())){
		    log.warn("confirmPassword不能为空");
		    return rtParameterError("confirmPassword不能为空");
	    }
	    validatePassword(model);
        sysUserService.updateLoginPassword(model.getId(), model.getLoginPassword());

        return rtSuccess();
    }

    /**
     * 修改用户自己的登录密码
     * @param model 用户信息
     * @return Object
     */
    @PostMapping("/updateSelfLoginPassword")
    public Object updateSelfLoginPassword(@RequestBody SysUserModel model) {
        if (!ShiroAuthHelper.getCurrentSysUserId().equals(model.getId())) {
            log.error("本操作只能修改自己的密码");
            return rtFailure("本操作只能修改自己的登录密码");
        }
	    validatePassword(model);
        sysUserService.updateLoginPassword(model.getId(), model.getLoginPassword());
	    sysUserService.logout();

        return rtSuccess();
    }

    /**
     * 删除用户
     * @param model 用户信息
     * @return Object
     */
    @RequiresPermissions(SysPermissionCode.USER_DELETE)
    @PostMapping("/delete")
    public Object delete(@RequestBody SysUserModel model) {
	    sysUserService.delete(model.getId());
        return rtSuccess();
    }

	/**
	 * 查询锁定用户
	 * @param condition 查询条件
	 * @return Object
	 */
	@RequiresPermissions(value = {SysPermissionCode.USER_LOCK, SysPermissionCode.USER_UNLOCK}, logical = Logical.OR)
    @RequestMapping("/queryLockUser")
    public Object queryLockUser(@RequestBody SysUserCondition condition){
		return rt(ReturnCode.FUNCTION_NOT_ONLINE);
    }

	/**
	 * 锁定用户
	 * @param model     用户信息
	 * @return Object
	 */
	@RequiresPermissions(SysPermissionCode.USER_LOCK)
	@PostMapping("/lockById")
	public Object lockById(@RequestBody SysUserModel model) {
		//sysUserService.lockById(model.getId()); TODO
		return rt(ReturnCode.FUNCTION_NOT_ONLINE);
	}

	/**
	 * 解锁用户
	 * @param model     用户信息
	 * @return Object
	 */
	@RequiresPermissions(SysPermissionCode.USER_UNLOCK)
	@PostMapping("/unlockById")
	public Object unlockById(@RequestBody SysUserModel model) {
		//sysUserService.unlockById(model.getId()); TODO
		return rt(ReturnCode.FUNCTION_NOT_ONLINE);
	}

    /**
     * 冻结用户
     * @param model     用户信息
     * @return Object
     */
    @RequiresPermissions(SysPermissionCode.USER_FREEZE)
    @PostMapping("/freeze")
    public Object freeze(@RequestBody SysUserModel model){
        sysUserService.freezeById(model.getId());
        return rtSuccess();
    }

    /**
     * 用户解冻
     * @param model     用户信息
     * @return Object
     */
    @RequiresPermissions(SysPermissionCode.USER_UNFREEZE)
    @PostMapping("/unfreeze")
    public Object unfreeze(@RequestBody SysUserModel model) {
	    sysUserService.unfreezeById(model.getId());
        return rtSuccess();
    }

	/**
	 * 冻结用户与解冻用户和一
	 * @param model     用户信息
	 * @return Object
	 */
	@RequiresPermissions({SysPermissionCode.USER_FREEZE, SysPermissionCode.USER_UNFREEZE})
	@PostMapping("/freezeUnfreeze")
	public Object freezeUnfreeze(@RequestBody SysUserModel model) {
		sysUserService.freezeUnfreezeById(model.getId(), model.getIsEnable());
		return rtSuccess();
	}

    /**
     * 用户保存角色
     * @param model     用户信息
     * @return Object
     */
    @RequiresPermissions(SysPermissionCode.USER_SAVE_ROLE)
    @PostMapping("/saveRole")
    public Object saveRole(@RequestBody SysUserModel model) {
	    sysUserService.saveRole(model.getId(), model.getRoleIdList());
        return rtSuccess();
    }

    /**
     * 用户删除角色
     * @param model     用户信息
     * @return Object
     */
    @RequiresPermissions(SysPermissionCode.USER_DELETE_ROLE)
    @PostMapping("/deleteRole")
    public Object deleteRole(@RequestBody SysUserModel model) {
	    sysUserService.deleteSysUserRole(model.getId(), model.getRoleIdList());
        return rtSuccess();
    }

    /**
     * 查询用户子级权限
     * @param model     权限信息
     * @return Object
     */
    @RequiresPermissions(SysPermissionCode.USER_QUERY)
    @PostMapping("/childPermission")
    public Object childPermission(@RequestBody SysPermissionModel model) {
        List<SysPermissionModel> result = sysUserService.queryPermission(ShiroAuthHelper.getCurrentSysUserId(), model.getId());
        return rtSuccess(result);
    }
}