package com.tdhy.exception;

import com.tdhy.web.result.ResultEnum;

/** 
 * 根异常
 * 
 * @author yuxiaolong@tiandihengye.com
 * @date 2017年10月20日
 */
public class TdhyRuntimeException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private ResultEnum resultEnum;

	private ExceptionEnum flag;

	public TdhyRuntimeException(ResultEnum resultEnum) {
		super(resultEnum.getMessage());
		this.resultEnum = resultEnum;
	}

	public TdhyRuntimeException(ResultEnum resultEnum,ExceptionEnum flag) {
		super(resultEnum.getMessage());
		this.resultEnum = resultEnum;
		this.flag = flag;
	}

	public TdhyRuntimeException(String message) {
		super(message);
	}

	public ResultEnum getResultEnum() {
		return resultEnum;
	}

	public void setResultEnum(ResultEnum resultEnum) {
		this.resultEnum = resultEnum;
	}

	public ExceptionEnum getFlag() {
		return flag;
	}

	public void setFlag(ExceptionEnum flag) {
		this.flag = flag;
	}

}

