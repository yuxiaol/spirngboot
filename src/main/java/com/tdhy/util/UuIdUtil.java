package com.tdhy.util;

import java.util.UUID;

/** 
 * 获取uuid
 * 
 * @author yuxiaolong@tiandihengye.com
 * @date 2017年10月19日
 */
public class UuIdUtil {
	
	public static String getUuid(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
