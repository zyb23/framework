package me.zyb.framework.usoa.repository;

import me.zyb.framework.usoa.entity.UsoaAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zhangyingbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface UsoaAccountRepository extends JpaRepository<UsoaAccount, Long>, JpaSpecificationExecutor<UsoaAccount> {
	/**
	 * 根据手机号查询账号
	 * @param mobile    手机号
	 * @return UsoaAccount
	 */
	public UsoaAccount findByMobile(String mobile);
}
