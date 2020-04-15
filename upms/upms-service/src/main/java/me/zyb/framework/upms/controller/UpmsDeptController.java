package me.zyb.framework.upms.controller;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.base.BaseController;
import me.zyb.framework.upms.condition.UpmsDeptCondition;
import me.zyb.framework.upms.dict.UpmsPermissionCode;
import me.zyb.framework.upms.model.UpmsDeptModel;
import me.zyb.framework.upms.service.UpmsDeptService;
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
@RequestMapping("/upmsDept")
public class UpmsDeptController extends BaseController {

	@Autowired
	private UpmsDeptService upmsDeptService;

	/**
	 * 分页数据
	 * @param condition 查询条件
	 * @return Object
	 */
	@RequiresPermissions(UpmsPermissionCode.DEPT_QUERY)
	@RequestMapping("/queryByCondition")
	public Object queryByCondition(@RequestBody UpmsDeptCondition condition) {
		Page<UpmsDeptModel> page = upmsDeptService.queryByCondition(condition);
		return rtSuccess(page);
	}

	/**
	 * 新增部门
	 * @param model 部门信息
	 * @return Object
	 */
	@RequiresPermissions(UpmsPermissionCode.DEPT_ADD)
	@PostMapping("/add")
	public Object add(@Valid @RequestBody UpmsDeptModel model){
		UpmsDeptModel deptModel = upmsDeptService.save(model);
		return rtSuccess(deptModel);
	}

	/**
	 * 编辑部门
	 * @param model 部门信息
	 * @return Object
	 */
	@RequiresPermissions(UpmsPermissionCode.DEPT_EDIT)
	@PostMapping("/edit")
	public Object edit(@Valid @RequestBody UpmsDeptModel model){
		if(null == model.getId()){
			log.error("id不能为空");
			return rtParameterError("id不能为空");
		}
		UpmsDeptModel deptModel = upmsDeptService.save(model);
		return rtSuccess(deptModel);
	}

	/**
	 * 删除部门
	 * @param model   部门信息
	 * @return Object
	 */
	@RequiresPermissions(UpmsPermissionCode.DEPT_DELETE)
	@PostMapping("/delete")
	public Object delete(@RequestBody UpmsDeptModel model){
		upmsDeptService.delete(model.getId());
		return rtSuccess();
	}

	/**
	 * 根据父级部门查询下一级部门
	 * @param condition  查询条件
	 * @return Object
	 */
	@RequiresPermissions(UpmsPermissionCode.DEPT_QUERY)
	@PostMapping("/children")
	public Object children(@RequestBody UpmsDeptCondition condition){
		List<UpmsDeptModel> deptModelList = upmsDeptService.queryByParentId(condition.getId());
		return rtSuccess(deptModelList);
	}

	/**
	 * 树形展示所有部门
	 * @return Object
	 */
	@RequiresPermissions(UpmsPermissionCode.DEPT_QUERY)
	@PostMapping("/tree")
	public Object tree(@RequestBody UpmsDeptCondition condition){
		List<UpmsDeptModel> deptModelTree = upmsDeptService.queryTree(condition.getParentId(), true, true);
		return rtSuccess(deptModelTree);
	}
}
