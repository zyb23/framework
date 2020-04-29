package me.zyb.framework.upms.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.util.StringUtil;
import me.zyb.framework.upms.EntityToModelUtil;
import me.zyb.framework.upms.UpmsException;
import me.zyb.framework.upms.condition.UpmsLogCondition;
import me.zyb.framework.upms.dict.LogType;
import me.zyb.framework.upms.entity.UpmsLog;
import me.zyb.framework.upms.model.UpmsLogModel;
import me.zyb.framework.upms.repository.UpmsLogRepository;
import me.zyb.framework.upms.service.UpmsLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

;
;

/**
 * @author zhangyingbin
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UpmsLogServiceImpl implements UpmsLogService {
	@Autowired
	private UpmsLogRepository upmsLogRepository;

	@Override
	public UpmsLogModel save(UpmsLogModel model) {
		UpmsLog entity = null;
		if(null == model.getId()){
			//新增
			entity = new UpmsLog();
			entity.setCreatorName(model.getCreatorName());
		}else {
			//修改
			Optional<UpmsLog> optional = upmsLogRepository.findById(model.getId());
			if(optional.isPresent()){
				entity = optional.get();
			}else {
				log.error("日志不存在，id：{}", model.getId());
				throw new UpmsException("日志不存在");
			}
		}
		entity.setContent(model.getContent());
		entity.setIp(model.getIp());
		entity.setType(model.getType());

		upmsLogRepository.save(entity);

		model = EntityToModelUtil.entityToModel(entity);

		return model;
	}

	@Override
	public void delete(Long logId) {
		upmsLogRepository.deleteById(logId);
	}

	@Override
	public void delete(Set<Long> logIdSet) {
		upmsLogRepository.deleteAllByIdIn(logIdSet);
	}

	@Override
	public List<UpmsLogModel> queryAll() {
		List<UpmsLog> entityList = upmsLogRepository.findAll();
		return EntityToModelUtil.entityToModel4Log(entityList);
	}

	@Override
	public Page<UpmsLogModel> queryByCondition(UpmsLogCondition condition) {
		Page<UpmsLog> entityPage = findByCondition(condition);
		List<UpmsLog> entityList = entityPage.getContent();
		List<UpmsLogModel> modelList = EntityToModelUtil.entityToModel4Log(entityList);
		return new PageImpl<>(modelList, entityPage.getPageable(), entityPage.getTotalElements());
	}

	/**
	 * 构造查询规则
	 * @param condition 查询条件
	 * @return Specification<UpmsUser>
	 */
	private Specification<UpmsLog> buildSpecification(UpmsLogCondition condition){
		return (Specification<UpmsLog>) (root, query, criteriaBuilder) -> {
			List<Predicate> predicateList = new ArrayList<Predicate>();
			if(null != condition.getId()){
				predicateList.add(criteriaBuilder.equal(root.get("id").as(Long.class), condition.getId()));
			}
			if(StringUtils.isNotBlank(condition.getCreatorName())){
				predicateList.add(criteriaBuilder.like(root.get("loginName").as(String.class), StringUtil.like(condition.getCreatorName())));
			}
			if(null != condition.getType()){
				predicateList.add(criteriaBuilder.equal(root.get("type").as(LogType.class), condition.getType()));
			}
			if(null != condition.getDescription()) {
				predicateList.add(criteriaBuilder.like(root.get("description").as(String.class), StringUtil.like(condition.getDescription())));
			}
			if(null != condition.getEntityId()) {
				predicateList.add(criteriaBuilder.equal(root.get("entityId").as(Long.class), condition.getEntityId()));
			}
			if(null != condition.getEntityName()) {
				predicateList.add(criteriaBuilder.like(root.get("entityName").as(String.class), StringUtil.like(condition.getEntityName())));
			}
			if(null != condition.getCreateTimeStart()){
				predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Date.class), condition.getCreateTimeStart()));
			}
			if(null != condition.getCreateTimeEnd()){
				predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(Date.class), condition.getCreateTimeEnd()));
			}
			query.where(predicateList.toArray(new Predicate[predicateList.size()]));
			return query.getRestriction();
		};
	}

	private Page<UpmsLog> findByCondition(UpmsLogCondition condition) {
		return upmsLogRepository.findAll(buildSpecification(condition), condition.getPageable());
	}
}
