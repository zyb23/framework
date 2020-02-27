package me.zyb.framework.usoa.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.usoa.model.UsoaAccountModel;
import me.zyb.framework.usoa.service.UsoaAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhangyingbin
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UsoaAccountServiceImpl implements UsoaAccountService {
	@Override
	public UsoaAccountModel loginBySmsCode(String mobile, String smsCode) {
		//TODO
		return null;
	}
}
