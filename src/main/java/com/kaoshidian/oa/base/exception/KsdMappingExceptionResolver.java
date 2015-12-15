/**
 * 
 */
package com.kaoshidian.oa.base.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * 单独实现的异常处理器，继承自{@link SimpleMappingExceptionResolver} 当请求是一个ajax请求时，取出异常的错误消息，并写入PrintWriter流至前台
 * @author <p>Innate Solitary 于 2012-2-8 下午4:05:15</p>
 *
 */
public class KsdMappingExceptionResolver extends SimpleMappingExceptionResolver {
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView mav = super.doResolveException(request, response, handler, ex);
		
		if(mav == null) {
			return null;
		}
		
		
		String xrequestedwith = request.getHeader("X-Requested-With");
		if("XMLHttpRequest".equals(xrequestedwith)) {
			// 如果当次请求是ajax请求，则只返回一个错误信息在responseText中
			mav = null;
			String message = "";
			if(ex instanceof KsdBizException) {
				message = ex.getMessage();
			} else {
				message = "系统内部错误";
			}
			try {
				response.setContentType("text/plain;charset=utf-8");
				response.getWriter().write(message);
				response.getWriter().flush();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				throw new KsdBizSystemErrorException(e.getMessage());
			}
		}
		
		return mav;
	}

}
