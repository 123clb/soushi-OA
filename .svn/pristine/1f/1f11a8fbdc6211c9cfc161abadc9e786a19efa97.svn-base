package com.kaoshidian.oa;

import org.junit.BeforeClass;
import org.springframework.mock.web.MockServletContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;
@Transactional  
public class JUnitBase {
	protected static HandlerMapping handlerMapping;
	protected static HandlerAdapter handlerAdapter;
	protected static XmlWebApplicationContext context;

	/**
	 * 读取spring3 MVC配置文件
	 */
	@BeforeClass
	public static void setUp() {
		if (handlerMapping == null) {
			String[] configs = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
					"file:src/main/webapp/WEB-INF/spring/application-context.xml",
					"file:src/main/webapp/WEB-INF/spring/application-context-shiro.xml",
					"file:src/main/webapp/WEB-INF/spring/application-context-schedule.xml",
					"file:src/main/webapp/WEB-INF/spring/root-context.xml"};
			context = new XmlWebApplicationContext();
			context.setConfigLocations(configs);
			MockServletContext msc = new MockServletContext();
			context.setServletContext(msc);
			context.refresh();
			msc.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, context);
			handlerMapping = (HandlerMapping) context.getBean(DefaultAnnotationHandlerMapping.class);
			handlerAdapter = (HandlerAdapter) context.getBean(context.getBeanNamesForType(AnnotationMethodHandlerAdapter.class)[0]);   
		}
	}

	public JUnitBase() {
		super();
	}

}