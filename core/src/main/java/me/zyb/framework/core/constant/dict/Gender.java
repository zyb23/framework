package me.zyb.framework.core.constant.dict;

import lombok.Getter;
import me.zyb.framework.core.base.BaseEnum;
import me.zyb.framework.core.convert.JpaEnumConverter;

/**
 * 性别
 * @author zhangyingbin
 */
@Getter
public enum Gender implements BaseEnum<Integer> {
	/** 女 */
	WOMAN(0, "woman", "女"),
    /** 男 */
    MAN(1, "man", "男"),
	/** 其他 */
	OTHER(2, "other", "其他")
    ;

    private Integer value;
    private String code;
    private String name;

    Gender(Integer value, String code, String name){
        this.value = value;
        this.code = code;
        this.name = name;
    }

    /**
     * 数据库实体转换器
     */
    public static class Converter extends JpaEnumConverter<Gender, Integer> {}
}
