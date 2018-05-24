package com.tdhy.web.result;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/** 
 * @ClassName WebError 
 * @Description 
 * @author yuxiaolong@tiandihengye.com
 * @date 2017年11月6日 下午4:27:08 
 */
public class WebError implements Serializable {
	/** 
	* @Fields serialVersionUID : TODO() 
	*/
	private static final long serialVersionUID = 1L;

	private Integer code;

	private String message;

	private Map<String, Object> data = new HashMap<>(8);

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

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public WebError(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public WebError addData(String key, Object val) {
		this.data.put(key, val);
		return this;
	}

	public static WebError onSuccess() {
		return new WebError(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage());
	}

	public static WebError onError(ResultEnum enums) {
		return new WebError(enums.getCode(), enums.getMessage());
	}

	public static WebError onSuccess(ResultEnum enums) {
		return new WebError(enums.getCode(), enums.getMessage());
	}
}
