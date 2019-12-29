package me.zyb.framework.upms.controller;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.base.BaseController;
import me.zyb.framework.upms.condition.UpmsRoleCondition;
import me.zyb.framework.upms.dict.UpmsPermissionCode;
import me.zyb.framework.upms.model.UpmsPermissionModel;
import me.zyb.framework.upms.model.UpmsRoleModel;
import me.zyb.framework.upms.service.UpmsRoleService;
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
@RequestMapping("/upmsRole")
public class UpmsRoleController extends BaseController {

	@Autowired
	private UpmsRoleService upmsRoleService;

	/**
	 * 分页数据
	 * @param condition 查询条件
	 * @return Object
	 */
	@RequiresPermissions(UpmsPermissionCode.ROLE_QUERY)
	@RequestMapping("/queryByCondition")
	public Object queryByCondition(@Valid @RequestBody UpmsRoleCondition condition) {
		Page<UpmsRoleModel> page = upmsRoleService.queryByCondition(condition);
		return rtSuccess(page);
	}


	/**
	 * 新增角色
	 * @param model 角色信息
	 * @return Object
	 */
	@RequiresPermissions(UpmsPermissionCode.ROLE_ADD)
	@PostMapping("/add")
	public Object add(@Valid @RequestBody UpmsRoleModel model){
		UpmsRoleModel roleModel = upmsRoleService.save(model);
		return rtSuccess(roleModel);
	}

	/**
	 * 编辑角色
	 * @param model 角色信息
	 * @return Object
	 */
	@RequiresPermissions(UpmsPermissionCode.ROLE_EDIT)
	@PostMapping("/edit")
	public Object edit(@Valid @RequestBody UpmsRoleModel model){
		if(null == model.getId()){
			log.warn("id不能为空");
			return rtParameterError("id不能为空");
		}
		UpmsRoleModel roleModel = upmsRoleService.save(model);
		return rtSuccess(roleModel);
	}

	/**
	 * 删除角色
	 * @param model    角色信息
	 * @return Object
	 */
	@RequiresPermissions(UpmsPermissionCode.ROLE_DELETE)
	@PostMapping("delete")
	public Object delete(@RequestBody UpmsRoleModel model){
		upmsRoleService.delete(model.getId());
		return rtSuccess();
	}

	/**
	 * 角色保存权限
	 * @param model   角色信息
	 * @return Object
	 */
	@RequiresPermissions(UpmsPermissionCode.ROLE_SAVE_PERMISSION)
	@PostMapping("/savePermission")
	public Object savePermission(@RequestBody UpmsRoleModel model){
		upmsRoleService.savePermission(model.getId(), model.getPermissionIdList());
		return rtSuccess();
	}

	/**
	 * 角色删除权限
	 * @param model   角色信息
	 * @return Object
	 */
	@RequiresPermissions(UpmsPermissionCode.ROLE_DELETE_PERMISSION)
	@PostMapping("/deletePermission")
	public Object deletePermission(@RequestBody UpmsRoleModel model){
		upmsRoleService.deleteUpmsRolePermission(model.getId(), model.getPermissionIdList());
		return rtSuccess();
	}

	/**s
	 * 查询角色的所有权限
	 * @param model  角色信息
	 * @return Object
	 */
	@RequiresPermissions(UpmsPermissionCode.ROLE_QUERY)
	@RequestMapping("/queryPermission")
	public Object queryPermission(@RequestBody UpmsRoleModel model){
		List<UpmsPermissionModel> permissionModelList = upmsRoleService.queryPermission(model.getId());
		return rtSuccess(permissionModelList);
	}

	/**
	 * 获取角色详情
	 * @param model   角色信息
	 * @return Object
	 */
	@RequiresPermissions(UpmsPermissionCode.ROLE_QUERY)
	@RequestMapping("/queryDetail")
	public Object queryDetail(@RequestBody UpmsRoleModel model){
		UpmsRoleModel roleModel = upmsRoleService.queryDetail(model.getId());
		return rtSuccess(roleModel);
	}
}
