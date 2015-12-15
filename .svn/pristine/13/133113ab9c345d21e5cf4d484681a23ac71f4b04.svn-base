/**
 * 
 */
package com.kaoshidian.oa.log.mng;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.kaoshidian.oa.base.EntityView;
import com.kaoshidian.oa.base.page.PageContext;
import com.kaoshidian.oa.base.util.DateUtils;
import com.kaoshidian.oa.log.dao.LogDao;
import com.kaoshidian.oa.log.entity.BizLog;
import com.kaoshidian.oa.permission.entity.User;
import com.kaoshidian.oa.util.LogEntityEnum;
import com.kaoshidian.oa.util.LogOperationEnum;
import com.kaoshidian.oa.util.LogStatusEnum;

/**
 * @author <p>Innate Solitary 于 2012-8-25 下午3:39:07</p>
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class LogMgr {
	
	@Autowired
	private LogDao logDao;
	
	
	public void save(User user, String remoteIp, Integer entityId, LogEntityEnum entity, LogOperationEnum operation, LogStatusEnum status, String description) {
		this.save(user.getUserId(), user.getLoginName(), remoteIp, entityId, entity, operation, status, description);
	}
	
	public void save(Integer userId, String loginName, String remoteIp, Integer entityId, LogEntityEnum entity, LogOperationEnum operation, LogStatusEnum status, String description) {
		BizLog log = new BizLog(userId, loginName, remoteIp, entityId, description, entity, operation, status);
		logDao.saveOrUpdate(log);
	}
	
	@SuppressWarnings("unchecked")
	public PageContext<BizLog> findLog(ModelMap model, HttpServletRequest request, Integer pageNum, Integer numPerPage) throws Exception {
		String userName = request.getParameter("userName");
		String entity = request.getParameter("entity");
		Integer entityId = StringUtils.isEmpty(request.getParameter("entityId")) ? null : Integer.valueOf(request.getParameter("entityId"));
		String operation = request.getParameter("operation");
		String status = request.getParameter("status");
		String remoteIp = request.getParameter("remoteIp");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		
		EntityView ev = new EntityView();
		
		if(StringUtils.isNotEmpty(userName)) {
			ev.add(Restrictions.like("userName", userName));
			model.addAttribute("userName", userName);
		}
		if(StringUtils.isNotEmpty(entity)) {
			ev.add(Restrictions.eq("entity", LogEntityEnum.valueOf(entity)));
			model.addAttribute("entity", entity);
		}
		if(entityId != null) {
			ev.add(Restrictions.eq("entityId", entityId));
			model.addAttribute("entityId", entityId);
		}
		if(StringUtils.isNotEmpty(operation)) {
			ev.add(Restrictions.eq("operation", LogOperationEnum.valueOf(operation)));
			model.addAttribute("operation", operation);
		}
		if(StringUtils.isNotEmpty(status)) {
			ev.add(Restrictions.eq("status", LogStatusEnum.valueOf(status)));
			model.addAttribute("status", status);
		}
		if(StringUtils.isNotEmpty(remoteIp)) {
			ev.add(Restrictions.eq("remoteIp", remoteIp));
			model.addAttribute("remoteIp", remoteIp);
		}
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		if(StringUtils.isNotEmpty(startDate)) {
			Calendar c = Calendar.getInstance();
			c.setTime(df.parse(startDate));
			DateUtils.setCalendar(c, null, null, null, 0, 0, 0, 0);
			ev.add(Restrictions.ge("createDate", c.getTime()));
			model.addAttribute("startDate", startDate);
		}
		if(StringUtils.isNotEmpty(endDate)) {
			Calendar c = Calendar.getInstance();
			c.setTime(df.parse(endDate));
			DateUtils.setCalendar(c, null, null, null, 23, 59, 59, 999);
			ev.add(Restrictions.le("createDate", c.getTime()));
			model.addAttribute("endDate", endDate);
		}
		if(orderField == null) {
			ev.addOrder(Order.desc("createDate"));
			model.addAttribute("orderField", "createDate");
			model.addAttribute("orderDirection", "desc");
		} else if("desc".equals(orderDirection)) {
			ev.addOrder(Order.desc(orderField));
			model.addAttribute("orderField", orderField);
			model.addAttribute("orderDirection", "desc");
		} else if("asc".equals(orderDirection)) {
			ev.addOrder(Order.asc(orderField));
			model.addAttribute("orderField", orderField);
			model.addAttribute("orderDirection", "asc");
		}
		
		return logDao.queryUsePage(ev, pageNum, numPerPage);
	}
	
	@SuppressWarnings("unchecked")
	public PageContext<BizLog> findDelLog(ModelMap model, HttpServletRequest request, Integer pageNum, Integer numPerPage) throws Exception {
		String userName = request.getParameter("productor");
		String entity = request.getParameter("entity");
		Integer entityId = StringUtils.isEmpty(request.getParameter("entityId")) ? null : Integer.valueOf(request.getParameter("entityId"));
		String operation = request.getParameter("operation");
		String status = request.getParameter("status");
		String remoteIp = request.getParameter("remoteIp");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		
		EntityView ev = new EntityView();
		
		if(StringUtils.isNotEmpty(userName)) {
			ev.add(Restrictions.like("description", userName,MatchMode.ANYWHERE));
			model.addAttribute("searcheproName", userName);
		}
		
	    ev.add(Restrictions.eq("entity", LogEntityEnum.valueOf("RECORD_VIDEO"))); 
	    model.addAttribute("entity", entity);
			
		if(entityId != null) {
			ev.add(Restrictions.eq("entityId", entityId));
			model.addAttribute("entityId", entityId);
		}
		
	    ev.add(Restrictions.eq("operation", LogOperationEnum.valueOf("DELETE")));
	     model.addAttribute("operation", operation);
	     
		if(StringUtils.isNotEmpty(status)) {
			ev.add(Restrictions.eq("status", LogStatusEnum.valueOf(status)));
			model.addAttribute("status", status);
		}
		if(StringUtils.isNotEmpty(remoteIp)) {
			ev.add(Restrictions.eq("remoteIp", remoteIp));
			model.addAttribute("remoteIp", remoteIp);
		}
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		if(StringUtils.isNotEmpty(startDate)) {
			Calendar c = Calendar.getInstance();
			c.setTime(df.parse(startDate));
			DateUtils.setCalendar(c, null, null, null, 0, 0, 0, 0);
			ev.add(Restrictions.ge("createDate", c.getTime()));
			model.addAttribute("startDate", startDate);
		}
		if(StringUtils.isNotEmpty(endDate)) {
			Calendar c = Calendar.getInstance();
			c.setTime(df.parse(endDate));
			DateUtils.setCalendar(c, null, null, null, 23, 59, 59, 999);
			ev.add(Restrictions.le("createDate", c.getTime()));
			model.addAttribute("endDate", endDate);
		}
		if(orderField == null) {
			ev.addOrder(Order.desc("createDate"));
			model.addAttribute("orderField", "createDate");
			model.addAttribute("orderDirection", "desc");
		} else if("desc".equals(orderDirection)) {
			ev.addOrder(Order.desc(orderField));
			model.addAttribute("orderField", orderField);
			model.addAttribute("orderDirection", "desc");
		} else if("asc".equals(orderDirection)) {
			ev.addOrder(Order.asc(orderField));
			model.addAttribute("orderField", orderField);
			model.addAttribute("orderDirection", "asc");
		}
		
		return logDao.queryUsePage(ev, pageNum, numPerPage);
	}
}
