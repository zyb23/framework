package me.zyb.framework.upms.dict;

import lombok.Getter;
import me.zyb.framework.core.base.BaseEnum;
import me.zyb.framework.core.convert.JpaEnumConverter;

/**
 * 权限类型
 * @author zhangyingbin
 */
@Getter
public enum PermissionType implements BaseEnum<Integer> {
	/** 目录（导航项） */
	CATALOG(0, "catalog", "目录"),
	/** 菜单	 */
	MENU(1, "menu", "菜单"),
	/** 按钮 */
	BUTTON(2, "button", "按钮/操作");

	private Integer value;
	private String code;
	private String name;

	PermissionType(Integer value, String code, String name){
		this.value = value;
		this.code = code;
		this.name = name;
	}

	public static PermissionType getByValue(Integer value) {
		for(PermissionType en : PermissionType.values()) {
			if(en.getValue().equals(value)) {
				return en;
			}
		}

		return null;
	}

	/**
	 * 数据库实体转换器
	 */
	public static class Converter extends JpaEnumConverter<PermissionType, Integer> {}
}
