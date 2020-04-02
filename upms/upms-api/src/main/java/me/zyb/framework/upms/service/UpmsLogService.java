package me.zyb.framework.upms.service;

import me.zyb.framework.upms.condition.UpmsLogCondition;
import me.zyb.framework.upms.model.UpmsLogModel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

/**
 * @author zhangyingbin
 */
public interface UpmsLogService {

	/**
	 * <p>新增/修改日志</p>
	 * @param model 数据模型
	 */
	public UpmsLogModel save(UpmsLogModel model);

	/**
	 * <p>删除日志</p>
	 * @param logId    日志ID
	 */
	public void delete(Long logId);

	/**
	 * 批量删除
	 * @param logIdSet  日志ID列表
	 */
	public void delete(Set<Long> logIdSet);

	/**
	 * 查询所有日志
	 * @return List<UpmsLogModel>
	 */
	public List<UpmsLogModel> queryAll();

	/**
	 * 根据条件查询分页数据
	 * @param condition 查询条件
	 * @return Page<UpmsLogModel>
	 */
	public Page<UpmsLogModel> queryByCondition(UpmsLogCondition condition);
}
