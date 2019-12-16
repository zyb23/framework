package me.zyb.framework.core.util;



public class JdbcUtil {
	
//	public static Criteria createCriteria(Criteria criteria, Class<?> entityClass, Map<String, Object> param){
//		String alias = entityClass.getSimpleName();
//		
//		for(Map.Entry<String, Object> entry : param.entrySet()){
//			if(entry.getValue() != null && StringUtil.isNotBlank((String)entry.getValue())){
//				Field field = BeanUtil.getDeclaredField(entityClass, entry.getKey());//获取属性字段
//				if(field != null){
//					Type type = field.getGenericType();//获取字段类型
//					if(type instanceof Class<?>){
//						Class<?> cls = (Class<?>)type;
//						/** 判断类型	*/
//						if(String.class.isAssignableFrom(cls)){
//							criteria.add(Restrictions.like(entry.getKey(), entry.getValue()));
//						}else if(Integer.class.isAssignableFrom(cls)){
//							criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
//						}else if(Long.class.isAssignableFrom(cls)){
//							criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
//						}else{
//							String referencedColumnName = field.getAnnotation(JoinColumn.class).referencedColumnName();
//							if("".equals(referencedColumnName)){
//								criteria.add(Restrictions.eq(alias + "." + entry.getKey() + ".id", Long.parseLong((String)entry.getValue())));
//							}else{
//								criteria.add(Restrictions.eq(alias + "." + entry.getKey() + "." + referencedColumnName, entry.getValue()));
//							}
//						}
//					}
//				}
//			}
//		}
//		
//		return criteria;
//	}
}
