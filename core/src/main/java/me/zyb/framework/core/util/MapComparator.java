package me.zyb.framework.core.util;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;

/**
 * 适用Collection<Map<K, V>>类型的list排序<br>
 * 使用方式：Collections.sort(list, new MapComparator(...))
 * @author zhangyingbin
 *
 * @param <K>
 * @param <V>
 */
public class MapComparator<K extends Object, V extends Object> implements Comparator<Map<K, V>> {
	/** 是否降序（默认升序） */
    private boolean[] isDesc;
    /** 排序字段 */
    private K[] orderKey;

	/***
	 * 所有key都按升序排序<br>
	 * 按orderKeys的顺序比较
	 * @param orderKeys 要排序的keys
	 */
	public MapComparator(K[] orderKeys){
		this.orderKey = orderKeys;
		this.isDesc = new boolean[orderKeys.length];
		Arrays.fill(this.isDesc, false);
	}

    /***
     * 所有key都按此isDesc排序<br>
     * 按orderKeys的顺序比较
     * @param isDesc    是否降序
     * @param orderKey  要排序的key
     */
	public MapComparator(boolean isDesc, K[] orderKey){
        this.orderKey = orderKey;
        this.isDesc = new boolean[orderKey.length];
        Arrays.fill(this.isDesc, isDesc);
    }
    
	/**
	 * 支持不同的key，不同的排序规则<br>
	 * 按orderKeys的顺序比较<br>
	 * isDescs与orderKeys一一对应
	 * @param isDesc
	 * @param orderKey
	 */
	public MapComparator(boolean[] isDesc, K[] orderKey){
		this.orderKey = orderKey;
        this.isDesc = isDesc;
	}

    @Override
    public int compare(Map<K, V> o1, Map<K, V> o2) {
    	int result = 0;
    	for(int i = 0; i < orderKey.length; i++){
    		//根据orderKeys[i]的比较结果
    		Object v1 = getValueByKey(o1, orderKey[i]);
            Object v2 = getValueByKey(o2, orderKey[i]);

            if (v1 == null && v2 == null) {
            	result = 0;
            }
            else if (v1 == null) {
            	result = -1;
            }
            else if (v2 == null) {
            	result = 1;
            }

            if (v1 instanceof Number) {
            	result = compare((Number) v1, (Number) v2);
            }
            else if (v1 instanceof String) {
            	result = compare((String) v1, (String) v2);
            }
            else if (v1 instanceof Date) {
            	result = compare((Date) v1, (Date) v2);
            }
            
            //排序方式
        	if (isDesc[i]) {
        		result = -result;
            }
        	
        	//比较结果不相等时，中断key循环返回比较结果；相等则继续循环后面的orderKeys
        	if(0 != result){
        		break;
        	}
    	}
    	
        return result;
    }
    
    private int compare(Number o1, Number o2) {
        return (int) (o1.doubleValue() - o2.doubleValue());
    }

    private int compare(String o1, String o2) {
        return Collator.getInstance().compare(o1, o2);
    }

    private int compare(Date o1, Date o2) {
        return (int) (o1.getTime() - o2.getTime());
    }

    private Object getValueByKey(Map<K, V> map, K key) {
        return map.get(key);
    }
}
