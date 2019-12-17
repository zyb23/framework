package me.zyb.framework.core.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 列表转树型工具类
 * @author zhangyingbin
 */
@Slf4j
public class ListToTreeUtil {
	private static final String GET_ID = "getId";
	private static final String GET_PARENT = "getParent";
	private static final String GET_PARENT_ID = "getParentId";
	private static final String GET_CHILDREN = "getChildren";
	private static final String SET_PARENT = "setParent";
	private static final String SET_PARENT_ID = "setParentId";

	/**
	 * List转Tree
	 * @param list  列表
	 * @param <T>   范型
	 * @return List<T>
	 */
	public static <T> List<T> listToTree(List<T> list, Class<T> clazz){
		try {
			Map<Object, T> mapTemp = new HashMap<Object, T>(16);
			Method getId = clazz.getMethod(GET_ID);
			Method getParent = clazz.getMethod(GET_PARENT);
			Method getParentId = clazz.getMethod(GET_PARENT_ID);
			Method getChildren = clazz.getMethod(GET_CHILDREN);
			Method setParent = clazz.getMethod(SET_PARENT, clazz);
			Method setParentId = clazz.getMethod(SET_PARENT_ID, Long.class);
			for (T current : list){
				mapTemp.put(getId.invoke(current), current);
			}

			List<T> top = new ArrayList<T>();
			mapTemp.forEach((k, v) -> {
				try {
					Object parentId = getParentId.invoke(v);
					if(null == parentId){
						T parent = (T)getParent.invoke(v);
						if(null != parent){
							parentId = getId.invoke(parent);
						}
					}
					if(null == parentId){
						top.add(v);
					}else {
						T parent = mapTemp.get(parentId);
						if(null == parent){
							top.add(v);
						}else {
							List<T> parentChildren = (List<T>) getChildren.invoke(parent);
							if(null == parentChildren){
								parentChildren = new ArrayList<T>();
							}
							if(!parentChildren.contains(v)){
								//断开父节点，只记录父节点ID，避免死循环
								setParent.invoke(v, (T)null);
								setParentId.invoke(v, parentId);
								parentChildren.add(v);
							}
						}
					}
				} catch (Exception e) {
					log.error("列表转树型异常", e);
				}
			});
			return top;
		} catch (Exception e) {
			log.error("列表转树型异常", e);
			return null;
		}
	}
}
