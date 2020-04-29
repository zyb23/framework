package me.zyb.framework.upms.repository;

import me.zyb.framework.upms.entity.UpmsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author zhangyingbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface UpmsLogRepository extends JpaRepository<UpmsLog, Long>, JpaSpecificationExecutor<UpmsLog> {

	/**
	 * 根据ID批量删除日志
	 * @param logIdSet  日志ID列表
	 */
	public void deleteAllByIdIn(Set<Long> logIdSet);

	/**
	 * 查询某个实体的日志记录
	 * @param entityId      实体ID
	 * @param entityName    实体名称
	 * @return List<UpmsLog>
	 */
	public List<UpmsLog> findByEntityIdAndEntityName(Long entityId, String entityName);
}
