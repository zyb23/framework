package me.zyb.framework.core.convert;

import me.zyb.framework.core.base.BaseEnum;

import javax.persistence.AttributeConverter;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * 数据库-实体基础枚举转换类
 * @param <X> 枚举类
 * @param <Y> 数据库存储的Java类型（value）
 * @author zhangyingbin
 */
public abstract class JpaEnumConverter<X extends BaseEnum<Y>, Y> implements AttributeConverter<BaseEnum<Y>, Y> {
	/**
	 * 枚举类的Class对象
	 */
	private Class<X> xclazz;

	/**
	 * 枚举类的values静态方法
	 */
	private Method valuesMethod;

	@SuppressWarnings("unchecked")
	public JpaEnumConverter() {
		this.xclazz = (Class<X>)(((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments())[0];
		try {
			valuesMethod = xclazz.getMethod("values");
		} catch (Exception e) {
			throw new RuntimeException("can't get values method from " + xclazz);
	}
	}

	@Override
	public Y convertToDatabaseColumn(BaseEnum<Y> attribute) {
		return attribute == null ? null : attribute.getValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public X convertToEntityAttribute(Y dbData) {
		try {
			X[] values = (X[]) valuesMethod.invoke(null);
			for (X x : values) {
				if (x.getValue().equals(dbData)) {
					return x;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("can't convertToEntityAttribute" + e.getMessage());
		}
		if(null == dbData){
			return null;
		}
		throw new RuntimeException("unknown dbData " + dbData);
	}
}
