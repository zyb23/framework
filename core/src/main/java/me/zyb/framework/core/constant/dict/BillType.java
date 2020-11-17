package me.zyb.framework.core.constant.dict;

import lombok.Getter;
import me.zyb.framework.core.autoconfigure.MessageSourceHelper;
import me.zyb.framework.core.base.BaseEnum;
import me.zyb.framework.core.convert.JpaEnumConverter;

/**
 * 账单类型
 * @author Administrator
 *
 */
@Getter
public enum BillType implements BaseEnum<Integer> {
	/** 充值 */
	RECHARGE(1, "recharge", "充值"),
	/** 提现 */
	WITHDRAW(2, "withdraw", "提现"),
	/** 调整 */
	ADJUST(3, "adjust", "调整"),
	/** 转入 */
	ROLL_IN(4, "roll.in", "转入"),
	/** 转出 */
	ROLL_OUT(5, "roll.out", "转出"),
	/** 结息 */
	INTEREST(6, "interest", "结息"),
	/** 手续费 */
	FEE(7, "commission", "手续费"),
	/** 佣金 */
	COMMISSION(8, "commission", "佣金"),
	/** 奖金 */
	REWARD(9, "reward", "奖金"),
	;

	private Integer value;
	private String code;
	private String name;

	BillType(Integer value, String code, String name) {
		this.value = value;
		this.code = code;
		this.name = name;
	}

	@Override
	public String getName(){
		return MessageSourceHelper.getMessage(code, null, name, null);
	}

	/**
	 * 数据库实体转换器
	 */
	public static class Converter extends JpaEnumConverter<BillType, Integer> {}
}
