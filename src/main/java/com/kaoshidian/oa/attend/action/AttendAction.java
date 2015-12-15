package com.kaoshidian.oa.attend.action;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.shiro.SecurityUtils;
import org.codehaus.jackson.node.ArrayNode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kaoshidian.oa.attend.dao.AttendAgainDao;
import com.kaoshidian.oa.attend.dao.AttendDataDao;
import com.kaoshidian.oa.attend.dao.AttendForLeaveDao;
import com.kaoshidian.oa.attend.dao.AttendInfoDao;
import com.kaoshidian.oa.attend.dao.AttendRecordDao;
import com.kaoshidian.oa.attend.dao.AttendRulesDao;
import com.kaoshidian.oa.attend.entity.AttendAgainSign;
import com.kaoshidian.oa.attend.entity.AttendData;
import com.kaoshidian.oa.attend.entity.AttendForLeave;
import com.kaoshidian.oa.attend.entity.AttendInfo;
import com.kaoshidian.oa.attend.entity.AttendRules;
import com.kaoshidian.oa.attend.mgr.AttendMrg;
import com.kaoshidian.oa.base.EntityView;
import com.kaoshidian.oa.base.util.BeansWrapperUtil;
import com.kaoshidian.oa.base.util.DateUtils;
import com.kaoshidian.oa.log.mng.LogMgr;
import com.kaoshidian.oa.permission.dao.OrgDao;
import com.kaoshidian.oa.permission.dao.UserDAO;
import com.kaoshidian.oa.permission.entity.Org;
import com.kaoshidian.oa.permission.entity.User;
import com.kaoshidian.oa.permission.entity.UserStateEnum;
import com.kaoshidian.oa.permission.mgr.PermissionMgr;
import com.kaoshidian.oa.util.ActionExtend;
import com.kaoshidian.oa.util.AttendLeaveEnum;
import com.kaoshidian.oa.util.CommonConstant;
import com.kaoshidian.oa.util.JSONUtils;
import com.kaoshidian.oa.util.LogEntityEnum;
import com.kaoshidian.oa.util.LogOperationEnum;
import com.kaoshidian.oa.util.LogStatusEnum;
import com.kaoshidian.oa.util.SystemConfig;
import com.kaoshidian.oa.util.Util;
import com.kaoshidian.tool.rtx.IRTXServer;
import com.kaoshidian.tool.rtx.RTXMessage;

/**
 * @author <p>
 *         123clb 于 2013-5-22 下午5:26:46
 *         </p>
 * 
 */
@Controller
public class AttendAction extends ActionExtend {
	@Autowired
	private AttendForLeaveDao attendForLeaveDao;
	@Autowired
	private LogMgr logMgr;
	@Autowired
	private PermissionMgr permissionMgr;
	@Autowired
	private UserDAO userDao;
	@Autowired
	private AttendDataDao attendDataDao;	
	@Autowired
	private AttendRulesDao attendRulesDao;
	@Autowired
	private IRTXServer rtx;
	@Autowired
	private AttendAgainDao attendAgainDao;
	@Autowired
	private AttendInfoDao attendInfoDao;
	@Autowired
	private AttendMrg attendMrg;
	@Autowired
	private AttendRecordDao attendRecordDao;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private OrgDao orgDao;
	
	@ModelAttribute("leave")
	public AttendForLeave createLeave() {
		return new AttendForLeave();
	}
	@ModelAttribute("rule")
	public AttendRules createRules()
	{
		return new AttendRules();	
	}
	@RequestMapping("/attend/leave/save.do")
	@ResponseBody
	public JSONObject attendLeaveSave(ModelMap model,
			HttpServletRequest request, AttendForLeave attendForLeave,
			Integer leaveId, Integer chargeId, Double leaveNum)
			throws ParseException {
		LogOperationEnum op = null;
		if (leaveId == null) {
			Integer userId = getCurrentUser().getUserId();
			String rtxNo = getCurrentUser().getJobno();
			attendForLeave.setApplyId(userId);
			String currentTime = DateUtils.currentDatetime();
			attendForLeave.setCreateDate(DateUtils.parseDatetime(currentTime));
			String leaveStartDate = request.getParameter("leaveStartDate");
			String leaveEndDate = request.getParameter("leaveEndDate");
			String sTime = request.getParameter("sTime");
			String eTime = request.getParameter("eTime");
			Date begin = DateUtils.parseDate(leaveStartDate);
			Date end = DateUtils.parseDate(leaveEndDate);
			Double num = 0.0;

			if (leaveStartDate.equals(leaveEndDate)) {
				if (sTime.equals("8:30") && eTime.equals("18:00")) {
					num = 1.0;
				} else {
					num = 0.5;
				}
			} else {
				double between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
				double day = between / (24 * 3600);

				num = day;
				if (sTime.equals("8:30") && eTime.equals("12:30")) {
					num = day + 0.5;
				} else if (sTime.equals("12:30") && eTime.equals("18:00")) {
					num = day + 0.5;
				} else {
					num = day + 1.0;
				}

			}
			if (leaveNum.doubleValue() != num.doubleValue()) {
				return JSONUtils.getJsonResult(null, null, "300",
						"对不起，你输入的天数有误！", "closeCurrent", "main");
			}
			attendForLeave.setRtxNum(rtxNo);
			attendForLeave.setLeaveStartTime(DateUtils
					.parseDatetime(leaveStartDate + " " + sTime + ":00"));
			attendForLeave.setLeaveEndTime(DateUtils.parseDatetime(leaveEndDate
					+ " " + eTime + ":00"));
			op = LogOperationEnum.SAVE;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
			String infoTime = df.format(begin);
			attendMrg.isInfoExist(rtxNo, infoTime, getCurrentUser()
					.getRealName());
			String wsql = "update oa_attend_info set leaveNum=leaveNum+" + num
					+ "  where rtxNo=" + rtxNo + " and infoTime='" + infoTime
					+ "' ";
			attendInfoDao.update(wsql);
		} else {
			op = LogOperationEnum.MODIFY;
		}
		attendForLeaveDao.saveOrUpdate(attendForLeave);
		logMgr.save(getCurrentUser(), Util.getIpAddr(request),
				attendForLeave.getLeaveId(), LogEntityEnum.ATTEND_LEAVE, op,
				LogStatusEnum.SUCCESS, "请假申请:" + attendForLeave.getLeaveId());
		User user = userDao.findById(chargeId);
		sendRtxMessage(user.getLoginName(), "领导您好", this.getCurrentUser()
				.getRealName() + "同学刚提交了请假申请信息，需要您的签字 ，请您登陆OA系统进行操作，谢谢。");
		return JSONUtils.getJsonResult(null, null, "200", "保存成功", null, "main");
	}

	@RequestMapping("/attend/leave/modify.do")
	public String beaforeModify(ModelMap model, Integer leaveId) {
		if (leaveId != null) {
			AttendForLeave af = attendForLeaveDao.findById(leaveId);
			model.addAttribute("leave", af);
			model.addAttribute("leaveEnum",
					BeansWrapperUtil.wrapEnum(AttendLeaveEnum.class));
			model.addAttribute("usermap",
					permissionMgr.getUserMap("RecordManager"));
		}
		return "attend/leaveEdit";
	}

	@RequestMapping("/attend/leave/list.do")
	public String attendLeaveList(ModelMap model, Integer pageNum,
			Integer numPerPage, HttpServletRequest request,
			AttendLeaveEnum attendLeaveEnum) throws Exception {
		EntityView ev = new EntityView();
		String searchName = request.getParameter("searchLeaveName");
		if (SecurityUtils.getSubject().hasRole("Employee")) {
			searchName = getCurrentUser().getRealName();
			model.addAttribute("isPersonnel", 1);
		}
		String startTime = request.getParameter("searchStartTime");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		String endTime = request.getParameter("searchEndTime");
		Date searchStartTime = StringUtils.isEmpty(startTime) ? null
				: DateUtils.parseDatetime(startTime);
		Date searchEndTime = StringUtils.isEmpty(endTime) ? null : DateUtils
				.parseDatetime(endTime);
		if (!StringUtils.isEmpty(searchName)) {
			ev.add(Restrictions.like("applyName", searchName,
					MatchMode.ANYWHERE));
			model.addAttribute("searchName", searchName);
		}
		if (searchStartTime != null) {
			ev.add(Restrictions.ge("leaveStartTime", searchStartTime));
			model.addAttribute("searchStartTime", searchStartTime);
		}

		if (searchEndTime != null) {
			ev.add(Restrictions.le("leaveEndTime", searchEndTime));
			model.addAttribute("searchEndTime", searchEndTime);
		}

		if (attendLeaveEnum != null) {
			ev.add(Restrictions.eq("attendLeaveEnum", attendLeaveEnum));
			model.addAttribute("leaveEnum", attendLeaveEnum);
		}

		if (SecurityUtils.getSubject().hasRole("PersonManage")
				|| SecurityUtils.getSubject().hasRole("admin")) {

		} else {

			if (SecurityUtils.getSubject().hasRole("RecordManager")) {
				ev.add(Restrictions
						.eq("chargeId", getCurrentUser().getUserId()));
			} else {
				ev.add(Restrictions.eq("applyId", getCurrentUser().getUserId()));
			}

		}

		if (orderField == null) {
			ev.addOrder(Order.desc("createDate"));
			model.addAttribute("orderField", "createDate");
			model.addAttribute("orderDirection", "desc");
		} else if ("desc".equals(orderDirection)) {
			ev.addOrder(Order.desc(orderField));
			model.addAttribute("orderField", orderField);
			model.addAttribute("orderDirection", "desc");
		} else if ("asc".equals(orderDirection)) {
			ev.addOrder(Order.asc(orderField));
			model.addAttribute("orderField", orderField);
			model.addAttribute("orderDirection", "asc");
		}

		model.addAttribute("pageCtx",
				attendForLeaveDao.queryUsePage(ev, pageNum, numPerPage));
		model.addAttribute("leaveEnum",
				BeansWrapperUtil.wrapEnum(AttendLeaveEnum.class));
		Map<String, String> chargeMap = permissionMgr
				.getUserMap("RecordManager");
		model.addAttribute("chargeMap", chargeMap);

		return "attend/leavelist";
	}

	@RequestMapping("/attend/leave/add.do")
	public String attendLeaveAdd(ModelMap model) {
		String applyName = getCurrentUser().getRealName();
		model.addAttribute("applyName", applyName);
		model.addAttribute("leaveEnum",
				BeansWrapperUtil.wrapEnum(AttendLeaveEnum.class));
		model.addAttribute("usermap", permissionMgr.getUserMap("RecordManager"));
		return "attend/leaveEdit";
	}

	@RequestMapping("/attend/leave/beforesign.do")
	public String beforeAgree(ModelMap model, Integer leaveId, String f) {
		if (leaveId != null) {
			AttendForLeave af = attendForLeaveDao.findById(leaveId);
			Map<String, String> chargeMap = permissionMgr
					.getUserMap("RecordManager");
			model.addAttribute("leave", af);
			model.addAttribute("flag", f);
			if (f.equals("c")) {
				model.addAttribute("currentTime", DateUtils.currentDatetime());
			}
			model.addAttribute("charegeMap", chargeMap);
			model.addAttribute("leaveEnum",
					BeansWrapperUtil.wrapEnum(AttendLeaveEnum.class));
		}
		return "/attend/leavesign";
	}

	@RequestMapping("/attend/leave/delete.do")
	@ResponseBody
	public JSONObject LeaveInfoDel(HttpServletRequest request, Integer leaveId) {
		attendForLeaveDao.delete(new Integer[] { leaveId });
		logMgr.save(getCurrentUser(), Util.getIpAddr(request), leaveId,
				LogEntityEnum.ATTEND_LEAVE, LogOperationEnum.DELETE,
				LogStatusEnum.SUCCESS, LogOperationEnum.DELETE.getLabel()
						+ "请假信息:" + leaveId);
		return JSONUtils.getJsonResult(null, null, "200", "删除成功", null, "main");
	}

	@RequestMapping("/attend/leave/sign.do")
	@ResponseBody
	public JSONObject greeSign(ModelMap model, Integer leaveId, String f,
			String g, HttpServletRequest request) {
		AttendForLeave leave = attendForLeaveDao.findById(leaveId);
		Integer cuurentUserId = getCurrentUser().getUserId();
		String message = "";
		String whereSql = "";
		if (!StringUtils.isEmpty(f)) {
			if (f.equals("a")) {
				if (cuurentUserId.intValue() != leave.getChargeId().intValue()) {
					return JSONUtils.getJsonResult(null, null, "300",
							"您无权限操作此项", "closeCurrent", "main");
				}
				if (g.equals("y")) {
					whereSql = "isAgree=true";
					message = this.getCurrentUser().getRealName()
							+ "主管已同意您提交的请假申请，请等候人事审核,谢谢。";
				} else {
					whereSql = "isAgree=false";
					message = this.getCurrentUser().getRealName()
							+ "主管未同意您提交的请假申请。";
				}
			}
			if (f.equals("v")) {
				if (SecurityUtils.getSubject().hasRole("PersonManage")) {
					if (g.equals("y")) {
						whereSql = "isVerify=true ";
						message = "   您的请假申请已被人事审核通过。";
					} else {
						whereSql = "isVerify=false";
						message = "   您的请假申请未被人事审核通过。";
					}
				} else {
					return JSONUtils.getJsonResult(null, null, "300",
							"您无权限操作此项", "closeCurrent", "main");
				}
			}
			if (f.equals("p")) {
				if (SecurityUtils.getSubject().hasRole("Boss")) {
					if (g.equals("y")) {
						whereSql = "isApproval=true";

						message = " BOSS已同意你的请假请求。                系统提醒：外出请注意安全，期盼您早日回归组织 。 ";
					} else {
						whereSql = "isApproval=false";
						message = " 抱歉, BOSS 未同意你的请假请求。 ";
					}
				} else {
					return JSONUtils.getJsonResult(null, null, "300",
							"您无权限操作此项", "closeCurrent", "main");
				}
			}
		}

		if (leaveId != null) {
			String sql = "update oa_attend_forleave set " + whereSql
					+ " where leaveId=" + leaveId;
			attendForLeaveDao.update(sql);
			logMgr.save(getCurrentUser(), Util.getIpAddr(request), leaveId,
					LogEntityEnum.ATTEND_LEAVE, LogOperationEnum.MODIFY,
					LogStatusEnum.SUCCESS, message);
			User user = userDao.findById(leave.getApplyId());
			sendRtxMessage(user.getLoginName(), "您好:", message);
		}
		if (f.equals("a")) {
			Map<String, String> userMap = permissionMgr
					.getUserMap("PersonManage");
			for (String key : userMap.keySet()) {
				User user = userDao.findById(Integer.valueOf(key));
				sendRtxMessage(
						user.getLoginName(),
						"人事您好",
						this.getCurrentUser().getRealName() + "主管已同意"
								+ leave.getApplyName()
								+ " 同学提交的请假申请，请您登陆OA系统进行核实，谢谢。");
			}
		}
		if (f.equals("v")) {
			if (leave.getLeaveNum() >= 2) {
				sendRtxMessage(permissionMgr.getUser("Boss").getLoginName(),
						"领导您好", this.getCurrentUser().getRealName()
								+ " 提交了超过2天的 请假申请信息， 需要您的审批，请您登陆OA系统进行操作，谢谢。");
			}
		}

		if (f.equals("p")) {
			User user = userDao.findById(leave.getChargeId());
			sendRtxMessage(user.getLoginName(), "主管您好:",
					"总经理 已同意" + leave.getApplyName() + "同学的请假申请。");
			Map<String, String> userMap = permissionMgr
					.getUserMap("PersonManage");
			for (String key : userMap.keySet()) {
				User userXz = userDao.findById(Integer.valueOf(key));
				sendRtxMessage(userXz.getLoginName(), "人事您好", "总经理  已同意"
						+ leave.getApplyName() + " 同学提交的请假申请。");
			}
		}
		return JSONUtils.getJsonResult(null, null, "200", "操作成功", null, "main");
	}

	@RequestMapping("/attend/leave/close.do")
	@ResponseBody
	public JSONObject closeHoliday(ModelMap model, Integer leaveId,
			HttpServletRequest request, AttendForLeave attendForLeave)
			throws Exception {
		AttendForLeave leave = attendForLeaveDao.findById(leaveId);
		Date closeTime = attendForLeave.getCloseTime();
		if (leaveId != null) {

			if (leave.getIsAgree() == null || leave.getIsVerify() == null) {
				return JSONUtils.getJsonResult(null, null, "300",
						"对不起，你的申请还未审批，不能操作此项！", "closeCurrent", "main");
			}

			if (leave.getIsAgree() && leave.getIsVerify()) {

				String sql = "update oa_attend_forleave set  closeTime='"
						+ closeTime + "' where leaveId=" + leaveId;
				attendForLeaveDao.update(sql);
				Map<String, String> userMap = permissionMgr
						.getUserMap("PersonManage");
				for (String key : userMap.keySet()) {
					User user = userDao.findById(Integer.valueOf(key));
					sendRtxMessage(user.getLoginName(), "人事您好",
							leave.getApplyName() + " 同学已销假，请您登陆OA系统进行核实，谢谢。");
				}
			} else {

				return JSONUtils.getJsonResult(null, null, "300",
						"对不起，你的申请还未生效，不能操作此项！", "closeCurrent", "main");
			}
		}
		return JSONUtils.getJsonResult(null, null, "200", "销假成功",
				"closeCurrent", "main");
	}

	// 同步打卡机上的打卡记录
	@RequestMapping("/record/realcheck.do")
	@ResponseBody
	public String sendUrl(HttpServletRequest request) throws ParseException {
		try {
			int max =0;
			int year=Integer.parseInt(request.getParameter("yd"));
			int month = Integer.parseInt(request.getParameter("md"));
			Calendar c = Calendar.getInstance();
			// 设置为该月，例如08年1月，日期随意
			 c.set(year, month - 1, 1);
			// 获得该月的日期
		     max = c.getActualMaximum(Calendar.DATE);
			// 获得该月的日期
			String tbTime = year + "-" + month;
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpRequest = new HttpGet();
			httpRequest.setURI(new URI("http://192.168.1.202"));
			
			CookieStore cookieStore = httpClient.getCookieStore();
			DefaultHttpClient httpClient2 = new DefaultHttpClient();
			httpClient2.setCookieStore(cookieStore);
			HttpPost httppost1 = new HttpPost(
					"http://192.168.1.202/form/Download");
			Date currentTime = DateUtils.parseDate(DateUtils.currentDate());
			Date getDataDay = DateUtils.getDSpecifiedDayBefore(currentTime, 1);

			String sendDate = DateUtils.formatDate(getDataDay);
			String pers = "&period=1&";
			for (int i = 1; i < 201; i++) {
				pers += "uid=" + i + "&";
			}

			String startSendTime = tbTime + "-01";
			String endSendTime = tbTime + "-" + max;
			String urlParas = "sdate=" + startSendTime + "&edate="
					+ endSendTime + "" + pers;
			// String urlParas="sdate=2013-09-16&edate=2013-09-16"+pers;
			urlParas = urlParas.substring(0, urlParas.length() - 1);
			StringEntity reqEntity = new StringEntity(urlParas);
			// 设置类型
			reqEntity.setContentType("application/x-www-form-urlencoded");
			// 设置请求的数据
			httppost1.setEntity(reqEntity);
			// 执行
			HttpResponse response1 = httpClient2.execute(httppost1);
			
			
			if (response1.getStatusLine().getStatusCode() == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						response1.getEntity().getContent(), "gbk"));
				while (true) {
					String line = br.readLine();
					if (line == null) {
						break;
					}
					String[] lines = line.split("\t");
					AttendData attendData = new AttendData();
					attendData.setWorkerId(lines[0]);
					attendData.setWorkerName(lines[1]);
					Date workTime = DateUtils.parseDatetime(lines[2]);
					attendData.setWorkTime(workTime);	
					 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
					 String infoTime=df.format(workTime);
					 String drg=DateUtils.formatDatetime(workTime, "yyyy-MM-dd");
					 
					    String morningTime="08:30:59";
					    String afterTime="12:30:00";
					     EntityView numEv=new EntityView();
					     numEv.add(Restrictions.eq("jobno", lines[0]));
					     
					    User user=userDao.findUniq(numEv);
					    if (user.getRuleId()>0)
					    {
				          AttendRules rules=attendRulesDao.findById(user.getRuleId());				          
				          morningTime=rules.getMorningTime();
					    }
					    
					 Date amDrg=DateUtils.parseDatetime(drg+" "+morningTime);
					 Date noonDrg=DateUtils.parseDatetime(drg+" "+afterTime);
					 
					EntityView ev = new EntityView();
					ev.add(Restrictions.eq("workerId", lines[0]));
					ev.add(Restrictions.eq("workTime", workTime));
					int count = attendDataDao.count(ev);
					if (count == 0) {
						//计算迟到次数
						attendMrg.isInfoExist(lines[0], infoTime,lines[1]);   
						   if (workTime.before(noonDrg) && workTime.after(amDrg)) 
							{
								String wsql = "update oa_attend_info set laterNum=laterNum+1 where rtxNo='"+ lines[0]+"' and infoTime='"
										+ infoTime + "'";
								attendInfoDao.update(wsql);
							}
						attendDataDao.addNew(attendData);
					}
				}
				br.close();
			} else {
				System.out.print("error:http璇锋眰閿欒");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "1";
	}
	// 群发信息
	@RequestMapping("/record/all.do")
	public void sendAllMessage() throws ParseException {
		String sql = "select distinct(workerId) as workerid from oa_attend_data ";
		List<Map> dataList = attendDataDao.findBySql(sql);
		Date currentTime = DateUtils.parseDate(DateUtils.currentDate());
		String sendDate = DateUtils.formatDate(DateUtils
				.getDSpecifiedDayBefore(currentTime, 1));
		String[] isSender = new String[300];
		int y = 0;
		for (int i = 0; i < dataList.size(); i++) {
			Object o = dataList.get(i).get("workerid");
			if (o == null) {
				continue;
			}
			String workerid = o.toString();

			EntityView evu = new EntityView();
			evu.add(Restrictions.eq("jobno", workerid));
			User user = userDao.findUniq(evu);
			isSender[y] = user.getLoginName();
			y++;
		}
		sendRtxMessage(isSender, "测  试 ", "刚在测试...，发送失误！");
	}

	// 生成月考勤报表
	@SuppressWarnings("finally")
	@RequestMapping("/record/createExcel.do")
	@ResponseBody
	public String makeAttend(Integer num, HttpServletRequest request) {

		try {
			String aPath = systemConfig.getUploadDir();
			// String Path = aPath + request.getParameter("fname");
			int month = Integer.parseInt(request.getParameter("md"));
			int year = Integer.parseInt(request.getParameter("yd"));

			// 请注意月份是从0-11
			Calendar c = Calendar.getInstance();
			// 设置为该月，例如08年1月，日期随意
			c.set(year, month - 1, 1);
			// 获得该月的日期
			int max = c.getActualMaximum(Calendar.DATE);

			// sendUrl(year,month,max);
			// 输出的excel的路径
			String filePath = systemConfig.getUploadDir() + "/jlv.xls";
			// 创建Excel工作薄
			WritableWorkbook wwb;
			// 新建立一个jxl文件,即在C盘下生成test.xls
			OutputStream os;
			os = new FileOutputStream(filePath);
			wwb = Workbook.createWorkbook(os);
			// 添加第一个工作表并设置第一个Sheet的名字
			WritableSheet sheetd = wwb.createSheet("考勤记录", 0);
			String[] title = { "序号", "部门", "姓名", "指纹卡号", "应出勤天数", "实出勤天数",
					"换休", "事假", "迟到次数", "迟到罚款", "备注" };
			Label label;
			Integer j = 1;
			int i = 0;
			for (i = 0; i < title.length; i++) {
				if (i > 3) {
					for (j = 1; j <= max; j++) {
						label = new Label(3 + j, 2, j.toString());
						sheetd.addCell(label);
					}
				}
				label = new Label(i + j - 1, 2, title[i]);
				sheetd.addCell(label);
			}
			sheetd.mergeCells(0, 0, i + j, 1);
			label = new Label(0, 0, "搜视"+year+"年" + month + "月份考勤表");
			sheetd.addCell(label);
			Integer t = 3, cn = 1, ft = 1;
			String drg = DateUtils.formatDatetime(c.getTime(), "yyyy-MM");
			// 应出勤天数
			int maxWorkday = max;
			String sql = "select * from oa_perm_user where jobno is not NULL and state=?    order by userId ";
			List<Map> dataList = attendDataDao.findBySql(sql,
					new Object[] { UserStateEnum.ACTIVITY.name() });
			for (Integer x = 0; x < dataList.size(); x++) {
				// 迟到次数
				Integer laterNum = 0;
				String workerid = dataList.get(x).get("jobno").toString();
				String workName = dataList.get(x).get("realName").toString();
				EntityView infoev = new EntityView();
				infoev.add(Restrictions.eq("rtxNo", workerid));
				String monthsString = "0";
				int monthNum = c.get(Calendar.MONTH) + 1;
				if (monthNum < 10) {
					monthsString = "0" + monthNum;
				}
				
				String ym = c.get(Calendar.YEAR) + "-" + monthsString;
				infoev.add(Restrictions.eq("infoTime", ym));// 得到年
				AttendInfo info = attendInfoDao.findUniq(infoev);
				if (info != null) {
					label = new Label(7 + max, t, info.getLeaveNum().toString());
					sheetd.addCell(label); // 请假天数

					// label = new Label(8+max,t,
					// info.getLaterNum().toString());
					// sheetd.addCell(label); //迟到次数
				}
				Integer xx = x + 1;

				label = new Label(0, t, xx.toString());
				sheetd.addCell(label); // 生成编号

				Org org = orgDao.findById(Integer.valueOf(dataList.get(x)
						.get("orgId").toString()));

				if (org != null) {
					label = new Label(1, t, org.getOrgName());
					sheetd.addCell(label); // 部门
				}

				label = new Label(2, t, workName);
				sheetd.addCell(label); // 姓名
				label = new Label(3, t, workerid);
				sheetd.addCell(label); // RTX编号

				for (Integer l = 1; l <= max; l++) {
					String dayS = "";
					String str = "";
					boolean isTrue = false;
					if (l < 10) {
						dayS = "0" + l;
					} else {
						dayS = l.toString();
					}
					Date mdrg = DateUtils.parseDatetime(drg + "-" + dayS + " "
							+ "08:31:00");
					Date drg1 = DateUtils.parseDatetime(drg + "-" + dayS + " "
							+ "08:30:00");
					Date drg2 = DateUtils.parseDatetime(drg + "-" + dayS + " "
							+ "12:30:00");
					Date drg3 = DateUtils.parseDatetime(drg + "-" + dayS + " "
							+ "14:00:00");
					Date drg4 = DateUtils.parseDatetime(drg + "-" + dayS + " "
							+ "18:00:00");
					Date startTime = DateUtils.parseDatetime(drg + "-" + dayS
							+ " " + "00:00:00");
					Date endTime = DateUtils.parseDatetime(drg + "-" + dayS
							+ " " + "23:59:59");

					if (DateUtils
							.isWeekend(drg + "-" + dayS + " " + "08:31:00")) {
						maxWorkday -= 1;
						label = new Label(3 + l, t, "\\");
						WritableCellFormat format1 = new WritableCellFormat();
						format1.setBackground(Colour.YELLOW);
						label.setCellFormat(format1);
						sheetd.addCell(label);
					}

					EntityView ev = new EntityView();
					ev.add(Restrictions.eq("workerId", workerid));
					ev.add(Restrictions.between("workTime", startTime, drg2));
					AttendData listAttendData;
					listAttendData = attendDataDao.findUniq(ev);
					if (listAttendData != null) {
						Date clockTime = listAttendData.getWorkTime();
						if (clockTime.before(mdrg)) {
							str = "√";
							isTrue = true;
						} else if (clockTime.before(drg2)) {
							long atTime = DateUtils.getDistanceTime(
									DateUtils.formatDatetime(drg1),
									DateUtils.formatDatetime(clockTime));
							str = "c" + atTime;
							laterNum = laterNum + 1;
						}
					} else {
						EntityView ev1 = new EntityView();
						ev1.add(Restrictions.eq("rtxNum", workerid));
						ev1.add(Restrictions.eq("leaveStartTime", drg1));
						ev1.add(Restrictions.eq("leaveEndTime", drg2));
						if (attendForLeaveDao.count(ev1) > 0) {
							str = "上午请假";
						} else {
							str = "N上";
						}
					}
					
					EntityView ev2 = new EntityView();
					ev2.add(Restrictions.eq("workerId", workerid));
					ev2.add(Restrictions.between("workTime", drg4, endTime));
					listAttendData = attendDataDao.findUniq(ev2);
					if (listAttendData == null) {
						EntityView ev4 = new EntityView();
						ev4.add(Restrictions.eq("rtxNum", workerid));
						ev4.add(Restrictions.eq("leaveStartTime", drg3));
						ev4.add(Restrictions.eq("leaveEndTime", drg4));
						if (attendForLeaveDao.count(ev4) > 0) {
							if (isTrue) {
								str = "下午请假";
							} else {
								str = str + "+下午请假";
							}

						} else {
							if (isTrue) {
								str = "N下";
							} else {
								str = str + "+N下";
							}
						}
					}
					EntityView ev5 = new EntityView();
					ev5.add(Restrictions.eq("rtxNum", workerid));
					ev5.add(Restrictions.eq("leaveStartTime", drg1));
					ev5.add(Restrictions.eq("leaveEndTime", drg4));
					if (attendForLeaveDao.count(ev5) > 0) {
						str = "请  假";
					}
					if (!str.equals("N上+N下")) {
						label = new Label(3 + l, t, str);
						sheetd.addCell(label);
					}
				}

				label = new Label(4 + max, t, String.valueOf(maxWorkday));
				sheetd.addCell(label);
				label = new Label(8 + max, t, laterNum.toString());
				sheetd.addCell(label); // 迟到次数

				maxWorkday = max;
				t = t + 1;
			}
			wwb.write();
			wwb.close();
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "resources/upload/jlv.xls";
	}

	// 补 签
	@RequestMapping("/attend/again/add.do")
	public String againBefore(ModelMap model) {

		String againName = getCurrentUser().getRealName();
		model.addAttribute("againName", againName);
		model.addAttribute("usermap",
				permissionMgr.getUserNameMap("RecordManager"));
		return "attend/againsign";
	}
	
	@RequestMapping("/attend/again/save.do")
	@ResponseBody
	public Object againSave(AttendAgainSign againSign,
			HttpServletRequest request) {

		try {
			attendAgainDao.addNew(againSign);
			logMgr.save(getCurrentUser(), Util.getIpAddr(request),
					againSign.getAgainId(), LogEntityEnum.ATTEND_AGAINSIGN,
					LogOperationEnum.SAVE, LogStatusEnum.SUCCESS, "补 签:"
							+ againSign.getAgainId());
			sendRtxMessage(againSign.getAgainLoginName(), "您 好", this
					.getCurrentUser().getRealName()
					+ "同学刚提交了补签信息，需要您签字 ，请您登陆OA系统进行操作，谢谢。");
		} catch (RuntimeException e) {
			throw e;
		}
		return JSONUtils.getJsonResult(null, null, "200", "保存成功","closeCurrent", "main");
	}

	@RequestMapping("attend/again/list.do")
	public String againList(ModelMap model, HttpServletRequest request,
			Integer pageNum, Integer numPerPage) throws Exception {
		EntityView ev = new EntityView();
		String searchName = request.getParameter("searchLeaveName");
		if (SecurityUtils.getSubject().hasRole("Employee")) {
			searchName = getCurrentUser().getRealName();
			model.addAttribute("isPersonnel", 1);
		}
		String startTime = request.getParameter("searchStartTime");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		String endTime = request.getParameter("searchEndTime");
		String chargeName = request.getParameter("chargeName");
		Date searchStartTime = StringUtils.isEmpty(startTime) ? null
				: DateUtils.parseDatetime(startTime);
		Date searchEndTime = StringUtils.isEmpty(endTime) ? null : DateUtils
				.parseDatetime(endTime);
		if (!StringUtils.isEmpty(searchName)) {
			ev.add(Restrictions.like("againName", searchName,
					MatchMode.ANYWHERE));
			model.addAttribute("searchName", searchName);

		}
		if (searchStartTime != null) {
			ev.add(Restrictions.ge("againTime", searchStartTime));
			model.addAttribute("searchStartTime", searchStartTime);
		}

		if (searchEndTime != null) {
			ev.add(Restrictions.le("againTime", searchEndTime));
			model.addAttribute("searchEndTime", searchEndTime);
		}

		if (!StringUtils.isEmpty(chargeName)) {
			ev.add(Restrictions.le("chargeName", chargeName));
			model.addAttribute("chargeName", chargeName);
		}

		if (orderField == null) {
			ev.addOrder(Order.desc("createDate"));
			model.addAttribute("orderField", "createDate");
			model.addAttribute("orderDirection", "desc");
		} else if ("desc".equals(orderDirection)) {
			ev.addOrder(Order.desc(orderField));
			model.addAttribute("orderField", orderField);
			model.addAttribute("orderDirection", "desc");
		} else if ("asc".equals(orderDirection)) {
			ev.addOrder(Order.asc(orderField));
			model.addAttribute("orderField", orderField);
			model.addAttribute("orderDirection", "asc");
		}
		model.addAttribute("pageCtx",attendAgainDao.queryUsePage(ev, pageNum, numPerPage));
		model.addAttribute("usermap",
				permissionMgr.getUserNameMap("RecordManager"));
		return "attend/againsignlist";
	}

	@RequestMapping("attend/again/beforesign.do")
	public String againIscheck(ModelMap model, Integer againId) {
		if (againId != null) {
			AttendAgainSign attendAgainSign = attendAgainDao.findById(againId);
			model.addAttribute("usermap",
					permissionMgr.getUserNameMap("RecordManager"));
			model.addAttribute("attendAgain", attendAgainSign);
		}
		return "attend/againcheck";
	}
	
	@RequestMapping("/attend/uploadExcel.do")
	public String reUpload(ModelMap model, Integer fid) {
		Boolean isCreate = false;
		if (fid != null) {
			
			isCreate = true;
		}
		model.addAttribute("IsCreate", isCreate);
		
		if(CommonConstant.SendNum)
		{
			model.addAttribute("IsSend", true);
		}
		else {
			
			model.addAttribute("IsSend", false);
		}
		
		return "attend/uploadExcel";
	}
	
	@RequestMapping("/attend/again/sign.do")
	@ResponseBody
	public JSONObject isCheck(ModelMap model, Integer againId, String f,
			HttpServletRequest request) {
		String whereSql = "";
		String message = "";
		AttendAgainSign againSign = attendAgainDao.findById(againId);
		if (!StringUtils.isEmpty(f)) {
			// if(cuurentUserId.intValue()!=leave.getChargeId().intValue())
			// {
			// return JSONUtils.getJsonResult(null, null, "300",
			// "您无权限操作此项","closeCurrent", "main");
			// }
			if (f.equals("y")) {
				whereSql = "isCheck=true";
				message = this.getCurrentUser().getRealName()
						+ "主管已同意您提交的补签申请。";
			} else {
				whereSql = "isCheck=false";
				message = this.getCurrentUser().getRealName()
						+ "主管未同意您提交的补签申请。";
			}
              			
			String sql = "update oa_attend_againsign set " + whereSql
					+ " where againId=" + againId;
			attendAgainDao.update(sql);
			logMgr.save(getCurrentUser(), Util.getIpAddr(request), againId,
					LogEntityEnum.ATTEND_AGAINSIGN, LogOperationEnum.MODIFY,
					LogStatusEnum.SUCCESS, message);
			sendRtxMessage(againSign.getChargeLoginName(), "您好:", message);

		}
		return JSONUtils.getJsonResult(null, null, "200", "操作成功",
				"closeCurrent", "main");
	}

	@RequestMapping("/attend/info/sName.do")
	@ResponseBody
	public JSONObject  SearchName(ModelMap model)
	{		    
		    EntityView ev=new EntityView();		    
		    ev.add(Restrictions.eq("personName",""));
			List<AttendInfo> infoList=attendInfoDao.findByEntityView(ev);
			
			for (int i = 0; i < infoList.size(); i++) {	
				EntityView uev=new EntityView();
				uev.add(Restrictions.eq("jobno", infoList.get(i).getRtxNo()));				
				User user=userDao.findUniq(uev);

				String strSql="update oa_attend_info set personName='"+user.getRealName()+"' where infoId="+infoList.get(i).getInfoId()+"";
				attendInfoDao.update(strSql);
			}
			return JSONUtils.getJsonResult(null, null, "200", "更新成功",null, "main");
			//return JSONUtils.getJsonResult(null, null, "200", "更新成功", null, "main").toString();
	}
	
	@RequestMapping("/attend/info/list.do")
	public String getInfoList(ModelMap model, HttpServletRequest request,
			Integer pageNum, Integer numPerPage) throws Exception {
		EntityView ev = new EntityView();
		String searchName = request.getParameter("searchName");
		String searchTime = request.getParameter("searchTime");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");

		if (SecurityUtils.getSubject().hasRole("RecordManager")) 
		{
			model.addAttribute("rManager", 1);
		}
		else {
			searchName=getCurrentUser().getRealName();
		}
		
		if (!StringUtils.isEmpty(searchName)) {
			ev.add(Restrictions.like("personName", searchName,
					MatchMode.ANYWHERE));
			model.addAttribute("searchName", searchName);
		}
		if (!StringUtils.isEmpty(searchTime)) {
			ev.add(Restrictions.eq("infoTime", searchTime));
			model.addAttribute("searchTime", searchTime);
		}
		if (orderField == null) {
			ev.addOrder(Order.desc("infoTime"));
			model.addAttribute("orderField", "infoTime");
			model.addAttribute("orderDirection", "desc");
		} else if ("desc".equals(orderDirection)) {
			ev.addOrder(Order.desc(orderField));
			model.addAttribute("orderField", orderField);
			model.addAttribute("orderDirection", "desc");
		} else if ("asc".equals(orderDirection)) {
			ev.addOrder(Order.asc(orderField));
			model.addAttribute("orderField", orderField);
			model.addAttribute("orderDirection", "asc");
		}
		model.addAttribute("pageCtx",
				attendInfoDao.queryUsePage(ev, pageNum, numPerPage));
		return "attend/infolist";
	}

	@RequestMapping("/attend/info/amDetail.do")
	public String getLaterData(ModelMap model, HttpServletRequest request)
			throws ParseException {
		String rtxNo = request.getParameter("r");
		String infoTime = request.getParameter("t");
		String flag = request.getParameter("f");
		Date amTime = DateUtils.parseDatetime(infoTime + "-01 00:00:00");
		Calendar c = Calendar.getInstance();
	    c.setTime(amTime);
		// 获得该月的天数
		
		int max = c.getActualMaximum(Calendar.DATE);
		Date pmTime = DateUtils.parseDatetime(infoTime + "-" + max
				+ " 23:59:59");
		EntityView ev = new EntityView();
		ev.add(Restrictions.eq("workerId", rtxNo));
		ev.add(Restrictions.between("workTime", amTime, pmTime));
		List<AttendData> infoList = attendDataDao.findByEntityView(ev);
		String startString = " 00:00:00";
		String endString = " 12:30:00";
		if (flag.equals("n")) {
			startString = " 14:00:00";
			endString = " 23:59:59";
		}
		Map<String, Date> infoMap = new LinkedHashMap<String, Date>();
		for (Integer i = 1; i < max + 1; i++) {
			Date startTime = DateUtils.parseDatetime(infoTime + "-" + i
					+ startString);
			Date endTime = DateUtils.parseDatetime(infoTime + "-" + i
					+ endString);
			for (int j = 0; j < infoList.size(); j++) {

				Date operTime = infoList.get(j).getWorkTime();
				if (operTime.before(endTime) && operTime.after(startTime)) {
					infoMap.put(i.toString(), operTime);
				}
			}
		}
		model.addAttribute("infoMap", infoMap);
		return "attend/infodetail";
	}

	@RequestMapping("/attend/record/list.do")
	public String getRecordList(ModelMap model, HttpServletRequest request,
			Integer pageNum, Integer numPerPage) throws Exception {
		EntityView ev = new EntityView();
		String searchName = request.getParameter("searchName");
		String searchTime = request.getParameter("searchTime");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		String searchStartTime = StringUtils.isEmpty(searchTime) ? null
				: searchTime;
		if (!StringUtils.isEmpty(searchName)) {
			ev.add(Restrictions.like("rName", searchName, MatchMode.ANYWHERE));
			model.addAttribute("searchName", searchName);

		}
		if (searchStartTime != null) {
			ev.add(Restrictions.eq("rTime", searchStartTime));
			model.addAttribute("searchTime", searchStartTime);
		}
		if (orderField == null) {
			ev.addOrder(Order.desc("rTime"));
			model.addAttribute("orderField", "rTime");
			model.addAttribute("orderDirection", "desc");
		} else if ("desc".equals(orderDirection)) {
			ev.addOrder(Order.desc(orderField));
			model.addAttribute("orderField", orderField);
			model.addAttribute("orderDirection", "desc");
		} else if ("asc".equals(orderDirection)) {
			ev.addOrder(Order.asc(orderField));
			model.addAttribute("orderField", orderField);
			model.addAttribute("orderDirection", "asc");
		}

		model.addAttribute("pageCtx",
				attendRecordDao.queryUsePage(ev, pageNum, numPerPage));
		return "attend/recordlist";
	}

	@RequestMapping("/attend/amcharts.do")
	public String createChart(ModelMap model, HttpServletRequest request)
			throws ParseException {
		String rtxNo = request.getParameter("r");
		String infoTime = request.getParameter("t");
		String flag = request.getParameter("f");
		Date amTime = DateUtils.parseDatetime(infoTime + "-01 00:00:00");
		Calendar c = Calendar.getInstance();
		// c.setTime(amTime);
		// 获得该月的天数
		int max = c.getActualMaximum(Calendar.DATE);
		Date pmTime = DateUtils.parseDatetime(infoTime + "-" + max
				+ " 11:59:59");
		ArrayNode chartArrayJson = attendMrg.getDateChartJson(rtxNo, amTime,
				pmTime);
		model.addAttribute("chartData", chartArrayJson);
		return "attend/chartline";
	}

	@RequestMapping("attend/setIsMsg.do")
	@ResponseBody
	public int setSendMsg(ModelMap model,HttpServletRequest request) throws ParseException
	{
	 Integer result=1;
	 String n=StringUtils.isEmpty(request.getParameter("cgn"))?"1":request.getParameter("cgn");		
	 if (n.equals("1"))
	 {
		 CommonConstant.SendNum=false;
		 result=0;	 
	 }
	 else 
	 {
		 CommonConstant.SendNum=true;
	 }
     	return result;	
	}
	
	
	@RequestMapping("/attend/rule/rulesedit.do")
	public  String  editRules(ModelMap model,Integer ruleId)
	{
		if (ruleId!=null)
		{
			AttendRules attendRules=attendRulesDao.findById(ruleId);
			model.addAttribute("rule", attendRules);
		}
		//model.addAttribute("addUser", getCurrentUser());
		return "/permission/user/rulesedit";
	}

	@RequestMapping("/attend/rule/save.do")
	@ResponseBody
	public  JSONObject  saveRules(HttpServletRequest request,Integer ruleId,AttendRules attendRules)
	{
		LogOperationEnum op = null;
		if (ruleId==null)
		{
			op=LogOperationEnum.SAVE;
		}
		else {
			
			attendRules.setRuleId(ruleId);
			op=LogOperationEnum.MODIFY;
		}	
		attendRules.setAddUser(getCurrentUser().getLoginName());
		attendRulesDao.saveOrUpdate(attendRules);	
		logMgr.save(getCurrentUser(), Util.getIpAddr(request), attendRules.getRuleId(), LogEntityEnum.ATTEND_RULES, op, LogStatusEnum.SUCCESS, op.getLabel()+ getCurrentUser().getRealName()+attendRules.getRuleName());
		return JSONUtils.SAVE_SUCCESS;
	}
	
	@RequestMapping("/attend/rule/list.do")
	public String  ruleList(ModelMap model,HttpServletRequest request,Integer pageNum, Integer numPerPage) throws Exception{
		
		 EntityView ev=new EntityView();
		 model.addAttribute("pageCtx",attendRulesDao.queryUsePage(ev, pageNum, numPerPage));
		 
		return "/permission/user/ruleslist";
	}
	
	@RequestMapping("/attend/rule/delete.do")
	@ResponseBody
	public JSONObject ruleDel(ModelMap model,Integer  ruleId,HttpServletRequest request)
	{
		AttendRules attendRules=attendRulesDao.findById(ruleId);
		attendRulesDao.delete(new Integer[] {ruleId});
		logMgr.save(this.getCurrentUser(), Util.getIpAddr(request), attendRules.getRuleId(), LogEntityEnum.ATTEND_RULES, LogOperationEnum.DELETE, LogStatusEnum.SUCCESS, LogOperationEnum.DELETE.getLabel() + "规则：" + attendRules.getRuleName()+attendRules.getRuleId());
		return JSONUtils.DELETE_SUCCESS;	
	}

	// RTX发送信息
	public void sendRtxMessage(String rtxUid, String title, String message) {
		if (StringUtils.isNotEmpty(rtxUid)) {
			RTXMessage msg = new RTXMessage();
			msg.setTitle(title);
			msg.setMessage(message);
			msg.setErrUrl("test");
			msg.setOkUrl("test");
			msg.setDelayTime(RTXMessage.DELAY_TIME_PERMANENT);
			msg.setReceivers(new String[] { rtxUid });
			rtx.sendNotice(msg);
		}
	}

	// RTX发送信息
	public void sendRtxMessage(String[] rtxUidAry, String title, String message) {
		RTXMessage msg = new RTXMessage();
		msg.setTitle(title);
		msg.setMessage(message);
		msg.setErrUrl("test");
		msg.setOkUrl("test");
		msg.setDelayTime(RTXMessage.DELAY_TIME_PERMANENT);
		msg.setReceivers(rtxUidAry);
		rtx.sendNotice(msg);
	}
}
