package com.tdhy.util;

import java.util.Random;

/** 
 * @ClassName RandomUtil 
 * @Description 数字工具类
 * @author yuxiaolong@tiandihengye.com
 * @date 2017年11月17日  
 */
public class RandomUtil {
	/** 
	 * @Title randomMinToMax 
	 * @Description 取之间随机数 
	 * @param min 最小数
	 * @param max 最大数
	 * @return   返回最小数到最大数之间的随机整数
	 */
	public static int randomMinToMax(int min, int max) {
		if(max == min && max ==0){
			return 0;
		}
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return s;
	}
}
