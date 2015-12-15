/**
 * 
 */
package com.kaoshidian.oa.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.support.WebApplicationObjectSupport;

import com.kaoshidian.oa.base.web.DateTypeEditor;
import com.kaoshidian.oa.permission.dao.UserDAO;
import com.kaoshidian.oa.permission.entity.User;


/**
 * @author <p>Innate Solitary 于 2012-6-5 下午4:28:31</p>
 *
 */
public class ActionExtend extends WebApplicationObjectSupport {
	
	
	@Autowired
	private UserDAO userDao;
	
	@InitBinder
	protected void initBind(HttpServletRequest request, ServletRequestDataBinder binder) {
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		CustomDateEditor editor = new CustomDateEditor(df, true);
		binder.registerCustomEditor(Date.class, editor);
		
		binder.registerCustomEditor(Date.class, new DateTypeEditor());
	}
	
	protected User getCurrentUser() {
		return userDao.findUniq("loginName", SecurityUtils.getSubject().getPrincipal());
	}
	
	@ModelAttribute("subject")
	public Subject getSubject() {
		return SecurityUtils.getSubject();	
	}
		
}


