package com.thinkgem.jeesite.common.utils;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class DoubleUtil implements Serializable {
	private static final long serialVersionUID = -3345205828566485102L;
	// 默认除法运算精度
	private static final Integer DEF_DIV_SCALE = 2;

	/**
	 * 提供精确的加法运算。
	 * @param value1 被加数
	 * @param value2 加数
	 * @return 两个参数的和
	 */
	public Double add(Number value1, Number value2) {
		BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
		BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param value1
	 *            被减数
	 * @param value2
	 *            减数
	 * @return 两个参数的差
	 */
	public static double sub(Number value1, Number value2) {
		BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
		BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param value1
	 *            被乘数
	 * @param value2
	 *            乘数
	 * @return 两个参数的积
	 */
	public Double mul(Number value1, Number value2) {
		BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
		BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时， 精确到小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param dividend
	 *            被除数
	 * @param divisor
	 *            除数
	 * @return 两个参数的商
	 */
	public Double div(Double dividend, Double divisor) {
		return div(dividend, divisor, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
	 * 
	 * @param dividend
	 *            被除数
	 * @param divisor
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public Double div(Double dividend, Double divisor, Integer scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(dividend));
		BigDecimal b2 = new BigDecimal(Double.toString(divisor));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param value
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static Double round(Double value, Integer scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(value));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 比较
	 * return -1, 0, or 1
	 */
	
	public int compare(Double value1, Double value2){
		BigDecimal   data1   =   new   BigDecimal(value1); 
		BigDecimal   data2   =   new   BigDecimal(value2); 
		return data1.compareTo(data2);
	}
	
	/*
	 * 四舍五入之后，后有0去掉，无0,则保留
	 */
	public static String doubleToString(Double value, Integer scale){
		double a = round(value,scale);
		String str = Double.valueOf(a).toString();
		
		return str;
	}
}

