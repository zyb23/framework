package me.zyb.framework.upms.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.util.StringUtil;
import me.zyb.framework.upms.EntityToModelUtil;
import me.zyb.framework.upms.UpmsException;
import me.zyb.framework.upms.condition.UpmsDeptCondition;
import me.zyb.framework.upms.entity.UpmsDept;
import me.zyb.framework.upms.model.UpmsDeptModel;
import me.zyb.framework.upms.repository.UpmsDeptRepository;
import me.zyb.framework.upms.service.UpmsDeptService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author zhangyingbin
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UpmsDeptServiceImpl implements UpmsDeptService {
	@Autowired
	private UpmsDeptRepository upmsDeptRepository;

	@Override
	public UpmsDeptModel save(UpmsDeptModel model) {
		UpmsDept entity;
		if(null == model.getId()){
			//新增
			entity = upmsDeptRepository.findByName(model.getName());
			if(null != entity){
				throw new UpmsException("部门名称存在");
			}else {
				entity = new UpmsDept();
			}
		}else {
			//修改
			Optional<UpmsDept> optional = upmsDeptRepository.findById(model.getId());
			if(optional.isPresent()){
				entity = optional.get();
			}else {
				throw new UpmsException("部门不存在");
			}
		}
		if(null != model.getParentId()) {
			Optional<UpmsDept> parent = upmsDeptRepository.findById(model.getParentId());
			if(parent.isPresent()) {
				entity.setParent(parent.get());
				entity.setLevel(parent.get().getLevel() + 1);
			}
		}
		entity.setName(model.getName());
		entity.setPrincipal(model.getPrincipal());

		upmsDeptRepository.save(entity);

		model = EntityToModelUtil.entityToModel(entity);

		return model;
	}

	@Override
	public void delete(Long DeptId) {
		//是否有子级
		List<UpmsDept> children = upmsDeptRepository.findByParent_Id(DeptId);
		if(null != children && children.size() > 0){
			throw new UpmsException("拥有子级的部门不能删除");
		}else {
			upmsDeptRepository.deleteById(DeptId);
		}
	}

	@Override
	public List<UpmsDeptModel> queryByParentId(Long parentId) {
		List<UpmsDept> entityList = upmsDeptRepository.findByParent_Id(parentId);
		return EntityToModelUtil.entityToModel4Dept(entityList);
	}

	@Override
	public List<UpmsDeptModel> queryAll(){
		List<UpmsDept> entityList = upmsDeptRepository.findAll();
		return EntityToModelUtil.entityToModel4Dept(entityList);
	}

	/**
	 * 构造查询规则
	 * @param condition 查询条件
	 * @return Specification<UpmsDept>
	 */
	private Specification<UpmsDept> buildSpecification(UpmsDeptCondition condition){
		return (Specification<UpmsDept>) (root, query, criteriaBuilder) -> {
			List<Predicate> predicateList = new ArrayList<Predicate>();
			if(null != condition.getId()){
				predicateList.add(criteriaBuilder.equal(root.get("id").as(Long.class), condition.getId()));
			}
			if(StringUtils.isNotBlank(condition.getName())){
				predicateList.add(criteriaBuilder.like(root.get("name").as(String.class), StringUtil.like(condition.getName())));
			}
			if (null != condition.getPrincipal()) {
				predicateList.add(criteriaBuilder.like(root.get("principal").as(String.class), StringUtil.like(condition.getPrincipal())));
			}
			if (null != condition.getLevel()){
				predicateList.add(criteriaBuilder.equal(root.get("level").as(Integer.class), condition.getLevel()));
			}
			if (null != condition.getParentId()){
				Join<UpmsDept, UpmsDept> parent = root.join("parent");
				predicateList.add(criteriaBuilder.equal(parent.get("id").as(Long.class), condition.getParentId()));
			}
			query.where(predicateList.toArray(new Predicate[predicateList.size()]));
			return query.getRestriction();
		};
	}

	private Page<UpmsDept> findByCondition(UpmsDeptCondition condition){
		return upmsDeptRepository.findAll(buildSpecification(condition), condition.getPageable());
	}

	@Override
	public Page<UpmsDeptModel> queryByCondition(UpmsDeptCondition condition) {
		Page<UpmsDept> entityPage = findByCondition(condition);
		List<UpmsDept> entityList = entityPage.getContent();
		List<UpmsDeptModel> modelList = EntityToModelUtil.entityToModel4Dept(entityList, condition.getNeedParent(), condition.getNeedChildren());
		return new PageImpl<UpmsDeptModel>(modelList, entityPage.getPageable(), entityPage.getTotalElements());
	}

	@Override
	public List<UpmsDeptModel> queryByIdSet(Set<Long> idSet) {
		List<UpmsDept> entityList = upmsDeptRepository.findAllById(idSet);
		return EntityToModelUtil.entityToModel4Dept(entityList);
	}

	@Override
	public List<UpmsDeptModel> queryTree(Long parentId, boolean needParent, boolean needChildren) {
		List<UpmsDept> upmsDeptList = upmsDeptRepository.findByParent_Id(parentId);
		return EntityToModelUtil.entityToModel4Dept(upmsDeptList, needParent, needChildren);
	}
}
