package com.tdhy.web.result;

/** 
 * @ClassName ResultEnum 
 * @Description webJSON 数据状态码枚举
 * @author yuxiaolong@tiandihengye.com
 * @date 2017年11月13日  
 */
public enum ResultEnum {
	/**
	 * 200 成功
	 */
	SUCCESS(20000, "操作成功"),
	
	
	NOT_WX_LOGIN(20010,"未登录"),
	
	
	NOT_PWD(2000, "原密码错误,请重新输入！"),
	/**
	 * 
	 */
	REPLACE_NAME(10001, "名字已存在,请更换其他名称"),
	
	/**
	 * 参数错误
	 */
	DATA_ERROR(400,"参数错误"),
	/**
	 * 500 系统错误
	 */
	ERROR(500, "系统错误");
	/**
	 * 
	 */
	private Integer code;

	private String message;

	private ResultEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
