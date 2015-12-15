package com.kaoshidian.oa.permission;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import com.kaoshidian.oa.util.JSONUtils;

/**
 * @author <p>Innate Solitary 于 2012-7-28 下午3:26:46</p>
 *
 */
public class MrmsAuthenticationFilter extends AuthorizationFilter {

	@Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		
		Subject subject = SecurityUtils.getSubject();
		String[] roles = (String[]) mappedValue;
		if(ArrayUtils.isEmpty(roles)) {
			return false;
		}
		
		for (String role : roles) {
	        if(subject.hasRole(role)) {
	        	return true;
	        }
        }
	    return false;
    }

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		
		boolean isAjax = "XMLHttpRequest".equals(((HttpServletRequest) request).getHeader("X-Requested-With"));
		
		if(!isAjax) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write("<h1 align='center'>无权限</h1>");
			out.close();
			return false;
		}
		
		response.setContentType("application/json;charset=UTF-8");  
		PrintWriter out = response.getWriter();
		out.write(JSONUtils.getJsonResult(null, null, "300", "您没有权限进行此操作，请联系管理员", "closeCurrent", null).toString());
		out.close();
		
	    return false;
	}
}
