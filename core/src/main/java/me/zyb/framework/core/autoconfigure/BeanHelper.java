package me.zyb.framework.core.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 
 * @author zhangyingbin
 *
 */
@Slf4j
public class BeanHelper {

	private static ApplicationContext applicationContext;

	public BeanHelper(ApplicationContext applicationContext){
		BeanHelper.applicationContext = applicationContext;
	}

	/**
	 * 获取指定名称的bean
	 * @param beanName  bean的名称
	 * @return bean的类型
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName){
		if(applicationContext.containsBean(beanName)){
			return (T) applicationContext.getBean(beanName);
		} else {
			return null;
		}
	}
   
	/**
    * 获取父类的第一个范型参数类型
    * @param clazz  类型
    * @return Class<?>
    */
   public static Class<?> getSuperClassGenericType(Class<?> clazz){
       return getSuperClassGenericType(clazz, 0);
   }
   
   /**
    * 获取父类的第index(index从0开始)个范型参数类型
    * @author zhangyingbin
    *
    * @param clazz  类型
    * @param index  索引
    * @return Class<?>
    */
   public static Class<?> getSuperClassGenericType(Class<?> clazz, int index) throws IndexOutOfBoundsException{
	   Class<?> resultType = null;
       Type superType = clazz.getGenericSuperclass();
       
       if(superType instanceof ParameterizedType){
    	   Type[] paramTypes = ((ParameterizedType)superType).getActualTypeArguments();
    	   if(index >= paramTypes.length || index < 0) {
    		   log.warn("Index is out of GenericSuperclass params for class [" + clazz.getSimpleName() + "].");
           }
    	   if(paramTypes.length > 0){
    		   resultType = (Class<?>)paramTypes[index];
    	   }
    	   else{
    		   log.warn("Can not determine EntityType for class [" + clazz.getSimpleName() + "].");
    	   }
       }
       else{
    	   log.error("The superclass [" + superType + "] of [" + clazz + "] is not a parameterized type.");
       }
       
       return resultType;
   }

   /**
    * 获取类定义的所有属性（递归获取超类中定义的属性）
    * @param clazz  类型
    * @return Field[]
    */
   public static Field[] getDeclaredFields(Class<?> clazz){
	   Field[] fields = clazz.getDeclaredFields();

       if(clazz.getSuperclass() != null){
           Class<?> clazzSuper = clazz.getSuperclass();
           Field[] fieldsSuper = getDeclaredFields(clazzSuper);
           fields = ArrayUtils.addAll(fields, fieldsSuper);
       }

       return fields;
   }
   
   /**
    * 根据名称获取类定义的属性（递归追踪超类中定义的属性）
    * @param clazz  类型
    * @param name   属性名
    * @return Field
    */
   public static Field getDeclaredField(Class<?> clazz, String name){
	   Field field = null;
		try {
			field = clazz.getDeclaredField(name);
		} catch (NoSuchFieldException e) {
			if(clazz.getSuperclass() != null){
				Class<?> clazzSuper = clazz.getSuperclass();
				field = getDeclaredField(clazzSuper, name);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	   
		return field;
   }
   
   /**
    * 比较两个对象是否相等
    * @param obj1   对象1
    * @param obj2   对象2
    * @return boolean
    */
   public static boolean equals(Object obj1, Object obj2, String... ignores){
	   Class<?> classObj1 = obj1.getClass();
	   Field[] fields = getDeclaredFields(classObj1);
	   for(Field field : fields){
		   String fieldName = field.getName();
		   //是否忽略
		   boolean ig = false;
		   for(String ignore : ignores){
			   if(fieldName.equals(ignore)){
				   ig = true;
				   break;
			   }
		   }
		   if(ig){
			   continue;
		   }
		   
		   String value1 = getValue(obj1, fieldName) == null ? "" : getValue(obj1, fieldName).toString();
		   String value2 = getValue(obj2, fieldName) == null ? "" : getValue(obj2, fieldName).toString();
		   if(!value1.equals(value2)){
			   log.info(fieldName + "值不同，obj1：" + value1 + "，obj2：" + value2);
			   return false;
		   }
	   }
	   
	   return true;
   }
   
   /**
    * 根据字段名取值
    * @author zhangyingbin
    * @param obj        取值对象
    * @param fieldName  取值字段
    * @return Object
    */
   public static Object getValue(Object obj, String fieldName){
	   if(null == obj){
		   return null;
	   }
	   try{
		   Class<?> clazz = obj.getClass();
		   Method[] methods = clazz.getMethods();
		   for(Method method : methods){
			   //非get方法
			   if(!method.getName().startsWith("get")){
				   continue;
			   }
			   if(method.getName().toUpperCase().equals(fieldName.toUpperCase())
					   || method.getName().substring(3).toUpperCase().equals(fieldName.toUpperCase())){
				   return method.invoke(obj);
			   }
		   }
	   }catch (Exception e) {
		   log.error("取值出错！", e);
	   }
	   
	   return null;
   }

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		BeanHelper.applicationContext = applicationContext;
	}
}
