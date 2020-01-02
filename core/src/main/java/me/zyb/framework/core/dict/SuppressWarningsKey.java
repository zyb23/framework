package me.zyb.framework.core.dict;

/**
 * 抑制警告的关键字
 * @author zhangyingbin
 */
public class SuppressWarningsKey {
	/** 抑制所有警告 */
	public static final String ALL = "all";
	/** 抑制装箱、拆箱操作时候的警告 */
	public static final String BOXING  = "boxing";
	/** 抑制映射相关的警告 */
	public static final String CAST = "cast";
	/** 抑制启用注释的警告 */
	public static final String DEP_ANN = "dep-ann";
	/** 抑制过期方法警告 */
	public static final String DEPRECATION  = "deprecation";
	/** 抑制确在switch中缺失breaks的警告 */
	public static final String FALLTHROUGH = "fallthrough";
	/** 抑制finally模块没有返回的警告 */
	public static final String FINALLY = "finally";
	/** 抑制变量的局部变量相关的警告 */
	public static final String HIDING = "hiding";
	/** 忽略没有完整的switch语句 */
	public static final String INCOMPLETE_SWITCH = "incomplete-switch";
	/** 忽略非nls格式的字符 */
	public static final String NLS = "nls";
	/** 忽略对null的操作 */
	public static final String NULL = "null";
	/** 抑制使用generics时忽略没有指定相应的类型 */
	public static final String RAWTYPES = "rawtypes";
	/** 抑制指示应该在注释元素（以及包含在该注释元素中的所有程序元素）中取消显示指定的编译器警告 */
	public static final String RESTRICTION = "restriction";
	/** 忽略在serializable类中没有声明serialVersionUID变量 */
	public static final String SERIAL = "serial";
	/** 抑制不正确的静态访问方式警告 */
	public static final String STATIC_ACCESS = "static-access";
	/** 抑制子类没有按最优方法访问内部类的警告 */
	public static final String SYNTHETIC_ACCESS = "synthetic-access";
	/** 抑制没有进行类型检查操作的警告 */
	public static final String UNCHECKED = "unchecked";
	/** 抑制没有权限访问的域的警告 */
	public static final String UNQUALIFIED_FIELD_ACCESS = "unqualified-field-access";
	/** 抑制没被使用过的代码的警告 */
	public static final String UNUSED = "unused";
}
