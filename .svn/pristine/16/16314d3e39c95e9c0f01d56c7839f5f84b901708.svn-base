/**
 * 
 */
package com.kaoshidian.oa.common.action;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.kaoshidian.oa.util.SystemConfig;
import com.kaoshidian.tool.rtx.IRTXServer;
import com.kaoshidian.tool.rtx.RTXMessage;

/**
 * @author <p>Innate Solitary 于 2013-3-12 下午4:54:15</p>
 *
 */
public class ExceptionFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionFilter.class);

	private ApplicationContext context = null;
	private SystemConfig sysCfg;
	private IRTXServer rtx;
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		context = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
		rtx = (IRTXServer) context.getBean("rtxServer");
		sysCfg = (SystemConfig) context.getBean("systemConfig");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
	        chain.doFilter(request, response);
        } catch (Exception e) {
        	Throwable cause = e;
        	do {
        		if(cause.getCause() != null) {
        			cause = cause.getCause();
        		} else {
        			break;
        		}
        	} while (true);
        	        	
    		if (sysCfg.getRtxCanSend() != null && sysCfg.getRtxCanSend()) {
	            StringBuffer esb = new StringBuffer();
	            esb.append(cause.toString()).append("\r\n");
	            StackTraceElement[] stes = cause.getStackTrace();
	            for (StackTraceElement ste : stes) {
		            esb.append("    at ").append(ste.toString()).append("\r\n");
	            }
	            RTXMessage msg = new RTXMessage();
	            msg.setDelayTime(RTXMessage.DELAY_TIME_PERMANENT);
	            msg.setReceivers(new String[] { "clb" });
	            msg.setTitle("OA异常");
	            msg.setMessage("OA抛出异常：" + cause.toString() + "  at  " + stes[0]);
	            msg.setType(RTXMessage.TYPE_EMERGENCE);
	            msg.setOkUrl("test");
	            msg.setErrUrl("test");
	            rtx.sendNotice(msg);
            }
			throw new ServletException(e);
        }
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		context = null;
		rtx = null;
		sysCfg = null;
	}

}
