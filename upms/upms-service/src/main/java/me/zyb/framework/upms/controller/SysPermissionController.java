package me.zyb.framework.upms.controller;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.base.BaseController;
import me.zyb.framework.upms.condition.SysPermissionCondition;
import me.zyb.framework.upms.dict.PermissionType;
import me.zyb.framework.upms.dict.SysPermissionCode;
import me.zyb.framework.upms.entity.SysPermission;
import me.zyb.framework.upms.model.SysPermissionModel;
import me.zyb.framework.upms.service.SysPermissionService;
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
@RequestMapping("/sysPermission")
public class SysPermissionController extends BaseController {

	@Autowired
	private SysPermissionService sysPermissionService;

	/**
	 * 分页数据
	 * @param condition 查询条件
	 * @return Object
	 */
	@RequiresPermissions(SysPermissionCode.PERMISSION_QUERY)
	@RequestMapping("/queryByCondition")
	public Object queryByCondition(@RequestBody SysPermissionCondition condition) {
		Page<SysPermissionModel> page = sysPermissionService.queryByCondition(condition);
		return rtSuccess(page);
	}

	/**
	 * 新增权限
	 * @param model 权限信息
	 * @return Object
	 */
	@RequiresPermissions(SysPermissionCode.PERMISSION_ADD)
	@PostMapping("/add")
	public Object add(@Valid @RequestBody SysPermissionModel model){
		SysPermissionModel permissionModel = sysPermissionService.save(model);
		return rtSuccess(permissionModel);
	}

	/**
	 * 编辑权限
	 * @param model 权限信息
	 * @return Object
	 */
	@RequiresPermissions(SysPermissionCode.PERMISSION_EDIT)
	@PostMapping("/edit")
	public Object edit(@Valid @RequestBody SysPermissionModel model){
		if(null == model.getId()){
			log.error("id不能为空");
			return rtParameterError("id不能为空");
		}
		SysPermissionModel permissionModel = sysPermissionService.save(model);
		return rtSuccess(permissionModel);
	}

	/**
	 * 删除权限
	 * @param model   权限信息
	 * @return Object
	 */
	@RequiresPermissions(SysPermissionCode.PERMISSION_DELETE)
	@PostMapping("delete")
	public Object delete(@RequestBody SysPermissionModel model){
		sysPermissionService.delete(model.getId());
		return rtSuccess();
	}

	/**
	 * 查询顶级权限
	 * @return Object
	 */
	@RequiresPermissions(SysPermissionCode.PERMISSION_QUERY)
	@PostMapping("top")
	public Object top(){
		List<SysPermissionModel> permissionModelList = sysPermissionService.queryByParentId(SysPermission.TOP_PARENT_ID);
		return rtSuccess(permissionModelList);
	}

	/**
	 * 根据父级权限查询下一级权限
	 * @param condition  查询条件
	 * @return Object
	 */
	@RequiresPermissions(SysPermissionCode.PERMISSION_QUERY)
	@PostMapping("children")
	public Object children(@RequestBody SysPermissionCondition condition){
		List<SysPermissionModel> permissionModelList = sysPermissionService.queryByParentId(condition.getId());
		return rtSuccess(permissionModelList);
	}

	/**
	 * 树形展示所有权限
	 * @return Object
	 */
	@RequiresPermissions(SysPermissionCode.PERMISSION_QUERY)
	@PostMapping("/tree")
	public Object tree(@RequestBody SysPermissionCondition condition){
		List<SysPermissionModel> permissionModelTree = sysPermissionService.queryTree(condition.getId(), true, true);
		return rtSuccess(permissionModelTree);
	}

	/**
	 * 权限类型
	 * @return Object
	 */
	@RequestMapping("/permissionType")
	public Object permissionType() {
		return rtSuccess(PermissionType.values());
	}
}
