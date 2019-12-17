package me.zyb.framework.upms.controller;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.base.BaseController;
import me.zyb.framework.upms.condition.SysRoleCondition;
import me.zyb.framework.upms.dict.SysPermissionCode;
import me.zyb.framework.upms.model.SysPermissionModel;
import me.zyb.framework.upms.model.SysRoleModel;
import me.zyb.framework.upms.service.SysRoleService;
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
@RestController
@RequestMapping("/sysRole")
public class SysRoleController extends BaseController {

	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * 分页数据
	 * @param condition 查询条件
	 * @return Object
	 */
	@RequiresPermissions(SysPermissionCode.ROLE_QUERY)
	@RequestMapping("/queryByCondition")
	public Object queryByCondition(@Valid @RequestBody SysRoleCondition condition) {
		Page<SysRoleModel> page = sysRoleService.queryByCondition(condition);
		return rtSuccess(page);
	}


	/**
	 * 新增角色
	 * @param model 角色信息
	 * @return Object
	 */
	@RequiresPermissions(SysPermissionCode.ROLE_ADD)
	@PostMapping("/add")
	public Object add(@Valid @RequestBody SysRoleModel model){
		SysRoleModel roleModel = sysRoleService.save(model);
		return rtSuccess(roleModel);
	}

	/**
	 * 编辑角色
	 * @param model 角色信息
	 * @return Object
	 */
	@RequiresPermissions(SysPermissionCode.ROLE_EDIT)
	@PostMapping("/edit")
	public Object edit(@Valid @RequestBody SysRoleModel model){
		if(null == model.getId()){
			log.warn("id不能为空");
			return rtParameterError("id不能为空");
		}
		SysRoleModel roleModel = sysRoleService.save(model);
		return rtSuccess(roleModel);
	}

	/**
	 * 删除角色
	 * @param model    角色信息
	 * @return Object
	 */
	@RequiresPermissions(SysPermissionCode.ROLE_DELETE)
	@PostMapping("delete")
	public Object delete(@RequestBody SysRoleModel model){
		sysRoleService.delete(model.getId());
		return rtSuccess();
	}

	/**
	 * 角色保存权限
	 * @param model   角色信息
	 * @return Object
	 */
	@RequiresPermissions(SysPermissionCode.ROLE_SAVE_PERMISSION)
	@PostMapping("/savePermission")
	public Object savePermission(@RequestBody SysRoleModel model){
		sysRoleService.savePermission(model.getId(), model.getPermissionIdList());
		return rtSuccess();
	}

	/**
	 * 角色删除权限
	 * @param model   角色信息
	 * @return Object
	 */
	@RequiresPermissions(SysPermissionCode.ROLE_DELETE_PERMISSION)
	@PostMapping("/deletePermission")
	public Object deletePermission(@RequestBody SysRoleModel model){
		sysRoleService.deleteSysRolePermission(model.getId(), model.getPermissionIdList());
		return rtSuccess();
	}

	/**
	 * 查询角色的所有权限
	 * @param model  角色信息
	 * @return Object
	 */
	@RequiresPermissions(SysPermissionCode.ROLE_QUERY)
	@RequestMapping("/queryPermission")
	public Object queryPermission(@RequestBody SysRoleModel model){
		List<SysPermissionModel> permissionModelList = sysRoleService.queryPermission(model.getId());
		return rtSuccess(permissionModelList);
	}

	/**
	 * 获取角色详情
	 * @param model   角色信息
	 * @return Object
	 */
	@RequiresPermissions(SysPermissionCode.ROLE_QUERY)
	@RequestMapping("/queryDetail")
	public Object queryDetail(@RequestBody SysRoleModel model){
		SysRoleModel roleModel = sysRoleService.queryDetail(model.getId());
		return rtSuccess(roleModel);
	}
}
