package me.zyb.framework.upms.configure;

import me.zyb.framework.upms.entity.SysUser;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.Objects;

/**
 * Shiro自定义密码规则
 * @author zhangyingbin
 */
public class CustomizedShiroCredentialsMatcher extends SimpleCredentialsMatcher {

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info){
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		SimpleAuthenticationInfo saInfo = (SimpleAuthenticationInfo) info;
		SysUser userEntity = (SysUser)saInfo.getPrincipals().getPrimaryPrincipal();

		//用户输入的明文密码
		String plaintext = String.valueOf(upToken.getPassword());
		//数据库中的密文密码
		String ciphertext = String.valueOf(info.getCredentials());
		//加密用户输入的明文密码
		String userPassword = encrypt(plaintext, userEntity.getSalt());

		//校验密码，返回结果
		return Objects.equals(userPassword, ciphertext);
	}

	/**
	 * 加密
	 * @return  String
	 */
	public static String encrypt(String plaintext, String salt){
		return new SimpleHash(Md5Hash.ALGORITHM_NAME, plaintext, salt, 1024).toString();
	}
}
