package me.zyb.framework.upms.repository;

import me.zyb.framework.upms.entity.UpmsDept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhangyingbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface UpmsDeptRepository extends JpaRepository<UpmsDept, Long>, JpaSpecificationExecutor<UpmsDept> {
	/**
	 * 根据部门名称查询
	 * @param name 部门名称
	 * @return UpmsDept
	 */
	public UpmsDept findByName(String name);

	/**
	 * 根据上级部门ID查询
	 * @param parentId  上级部门ID
	 * @return List<UpmsDept>
	 */
	public List<UpmsDept> findByParent_Id(Long parentId);
}
