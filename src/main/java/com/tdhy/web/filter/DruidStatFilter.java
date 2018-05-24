package com.tdhy.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.WebStatFilter;

@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*",
initParams = {
        @WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")// 忽略资源
}
)
public class DruidStatFilter extends WebStatFilter  {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
//		HttpServletRequest req = (HttpServletRequest)request;
//		HttpSession session = req.getSession();
//		if(req.getRequestURI().indexOf("druid")>-1){
//			Admin admin = (Admin)session.getAttribute(BaseSystemConstants.MANAGE_SESSION_KEY);
//			if(admin == null){
//				return ;
//			}
//		}
			 super.doFilter(request, response, chain);
	}
}
