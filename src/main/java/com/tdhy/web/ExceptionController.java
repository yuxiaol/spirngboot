package com.tdhy.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tdhy.exception.ExceptionEnum;
import com.tdhy.exception.TdhyRuntimeException;
import com.tdhy.web.result.WebError;

import net.sf.json.JSONObject;

/** 
 * @ClassName: ExceptionController 
 * @Description: 统一简单的异常处理
 * @author: yuxiaolong@tiandihengye.com
 * @date: 2017年10月12日 下午2:26:57 
 */
@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler({ TdhyRuntimeException.class })
	public void handlerExceptionTdhy(HttpServletRequest request, HttpServletResponse response, Exception e) {
		if (e instanceof TdhyRuntimeException) {
			TdhyRuntimeException tre = (TdhyRuntimeException) e;
			ExceptionEnum flag = tre.getFlag();
			switch (flag) {
			case CLIENT:
				PrintWriter out = null;
				try {
					out = response.getWriter();
					response.setCharacterEncoding("UTF-8");
					response.setContentType("application/json; charset=utf-8");
					JSONObject responseJSONObject = JSONObject.fromObject(WebError.onError(tre.getResultEnum()));
					out.append(responseJSONObject.toString());
				} catch (IOException e1) {
					e.printStackTrace();
				} finally {
					if (null != out) {
						out.close();
					}
				}
				break;
			case MANAGE:
				try {
					response.sendRedirect(request.getContextPath() + "/error/detail?code=" + tre.getResultEnum().getCode());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
	}

}
