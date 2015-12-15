package com.kaoshidian.oa.attend.entity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.hibernate.criterion.Restrictions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.tags.EvalTag;
import org.slf4j.Logger;
import com.kaoshidian.oa.attend.dao.AttendDataDao;
import com.kaoshidian.oa.attend.dao.AttendForLeaveDao;
import com.kaoshidian.oa.attend.dao.AttendInfoDao;
import com.kaoshidian.oa.attend.dao.AttendRulesDao;
import com.kaoshidian.oa.attend.mgr.AttendMrg;
import com.kaoshidian.oa.base.EntityView;
import com.kaoshidian.oa.base.util.DateUtils;
import com.kaoshidian.oa.permission.dao.UserDAO;
import com.kaoshidian.oa.permission.entity.User;
import com.kaoshidian.oa.util.CommonConstant;
import com.kaoshidian.tool.rtx.IRTXServer;
import com.kaoshidian.tool.rtx.RTXMessage;
@Service
public class AttendNoticTask
{
	private static final Logger logger = LoggerFactory
			.getLogger(AttendNoticTask.class);
	@Autowired
    private AttendDataDao attendDataDao;
	@Autowired
	private AttendInfoDao attendInfoDao;
	@Autowired
	private AttendMrg attendMrg;
	@Autowired
	private AttendForLeaveDao attendForLeaveDao;
	@Autowired
	private UserDAO userDao;
	@Autowired
	private IRTXServer rtx;
	@Autowired
	private AttendRulesDao attendRulesDao;
	private int sendDaysNum=1;
	public void execute() throws ParseException 
	{
		sendUrl();
		if (DateUtils.dayOfWeek()==2)
		{
			sendDaysNum=2;	
		}
		else {
			
			sendDaysNum=1;	
		}
		
		if(!DateUtils.isWeekend(DateUtils.currentDatetime()))
		{
		    if (CommonConstant.SendNum)
		      {
				isCard(0);
		      	isCard(1);
		      }
		}
	}
	//模拟登陆
   public  void sendUrl() throws ParseException 
	{		 
	   try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI("http://192.168.1.202"));
			CookieStore cookieStore = httpClient.getCookieStore();      
			DefaultHttpClient httpClient2 = new DefaultHttpClient();
			httpClient2.setCookieStore(cookieStore);
			HttpPost httppost1 = new HttpPost("http://192.168.1.202/form/Download");	
			Date currentTime=DateUtils.parseDate(DateUtils.currentDate());
			Date getDataDay=DateUtils.getDSpecifiedDayBefore(currentTime, 1);
			String sendDate=DateUtils.formatDate(getDataDay);
			String pers="&period=1&";
			for (int i = 1; i <301; i++)
			{
				pers+="uid="+i+"&";
			}
			String urlParas="sdate="+sendDate+"&edate="+sendDate+pers; 
			//String urlParas="sdate=2013-07-18&edate=2013-07-31"+pers;
			urlParas=urlParas.substring(0, urlParas.length()-1);
			StringEntity reqEntity = new StringEntity(urlParas);
			//设置类型
			reqEntity.setContentType("application/x-www-form-urlencoded");
			//设置请求的数据
			httppost1.setEntity(reqEntity);
			//执行   
			HttpResponse response1 = httpClient2.execute(httppost1);
			int infoCount=0;
			if (response1.getStatusLine().getStatusCode() == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						response1.getEntity().getContent(), "gbk"));		
				while (true) {
					String line = br.readLine();
					if (line == null) 
					{
						break; 
					}
					String [] lines=line.split("\t");
					    AttendData attendData=new AttendData();
		  			    attendData.setWorkerId(lines[0]);
		  			    attendData.setWorkerName(lines[1]);		  			    
		  			    Date workTime=DateUtils.parseDatetime(lines[2]);		  			    		    
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
						
					    attendData.setWorkTime(workTime);
					    EntityView ev=new EntityView();
					    ev.add(Restrictions.eq("workerId", lines[0]));
					    ev.add(Restrictions.eq("workTime", workTime));
					    int count=attendDataDao.count(ev);
					   if(!DateUtils.isWeekend(getDataDay)) 
					   {
						    if (count == 0)
						    {
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
					   infoCount+=1;
				}
				br.close();	
			} else {
				logger.error("网络数据交互失败。");
				
			}
			sendRtxMessage("clb","信息更新","今天早上共更新了"+infoCount+"条数据。");	
		} 
	    catch (ClientProtocolException e)
	    {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    catch (IOException e) 
		{
			// TODO Auto-generated catch block
			String info="连接网络失败";
			logger.error(info, e);
		} 
	    catch (URISyntaxException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	   
	}
   //判断是否打卡或迟到
public void isCard( int flag) throws ParseException 
{
			 String sql="select * from oa_perm_user where state='ACTIVITY' and isSend=true";		
			 List<Map> dataList=userDao.findBySql(sql);
			 String [] isSender=new String[100];
			 int y=0;
			 Date currentTime=DateUtils.parseDate(DateUtils.currentDate());
			 String sendDate=DateUtils.formatDate(DateUtils.getDSpecifiedDayBefore(currentTime, sendDaysNum));	
			 Date startTime=DateUtils.parseDatetime(sendDate+" 03:00:00");			 
		     Date endTime=DateUtils.parseDatetime(sendDate+" 12:00:00");		     
		     Date startLaterTime=DateUtils.parseDatetime(sendDate+" 08:30:59");
		     String rflag="m";
		     String dayName="早上";
		     if(flag==1)
		     {
		    	  sendDate=DateUtils.formatDate(DateUtils.getDSpecifiedDayBefore(currentTime, sendDaysNum));	
		    	  startTime=DateUtils.parseDatetime(sendDate+" 18:01:00");
			      endTime=DateUtils.parseDatetime(sendDate+" 23:59:59");
			      rflag="n";
			      dayName="下午";
		     }
		     EntityView cev=new EntityView();			 
		     cev.add(Restrictions.between("workTime", startTime, DateUtils.parseDatetime(sendDate+" 23:59:59")));
		     if(attendDataDao.count(cev)>50)
		     {
			    for (int i = 0; i < dataList.size(); i++) 
			    {
				 Object o = dataList.get(i).get("jobno");
				 if(o == null) {
					 continue; 
				 }  
				 if (flag==0)
				 {
					     EntityView lev=new EntityView();			 
					     lev.add(Restrictions.between("workTime", startLaterTime, endTime));
					     lev.add(Restrictions.eq("workerId", o));
					      if (attendDataDao.count(lev)>0)
					      {
					    	  SimpleDateFormat	 datetimeFormat = new SimpleDateFormat("yyyy-MM");
					    	  EntityView nev=new EntityView();
					    	  nev.add(Restrictions.eq("rtxNo", o));
					    	  nev.add(Restrictions.eq("infoTime", datetimeFormat.format(startLaterTime)));
					    	  AttendInfo attendInfo=attendInfoDao.findUniq(nev);			    	  
					    	  Integer laterNum=attendInfo.getLaterNum();
					    	  String txtSting="";				    	  		    	 
					    	   if (laterNum>=1&&laterNum<3)
					    	   {
					    		   txtSting="，请注意按时休息。                         可登陆[OA系统|http://oa.isoushi.cn]查看个人考勤记录，自己的RTX账号密码登陆即可，推荐Chrome浏览器。";
					    	   }   
					    	   if (laterNum==3)
					    	   {
					    		   txtSting="。  注意：您本月迟到已经3次了，请严格要求自己。";
					    	   }
					    	   if (laterNum>3&&laterNum<=8)
					    	   {
					    		   txtSting="。  迟到3次以上是要扣薪的，请多多注意。";
					    	   } 					    	   
					    	   if (laterNum>8)
					    	   {
					    		   txtSting="。您上班时间可能比较特殊，系统暂时无法特殊处理（如需关闭提示信息，请与曹力博同学联系，谢谢）。";
					    	   } 
					    	 sendRtxMessage(dataList.get(i).get("loginName").toString(),"提  示 ","同学,您"+sendDate+"日早上迟到了，这是您本月第"+laterNum+"次迟到"+txtSting);
					      }					     
				  }
				 String workerid = o.toString();		
				 EntityView ev=new EntityView(); 
				 ev.add(Restrictions.eq("workerId", workerid));
				 ev.add(Restrictions.between("workTime", startTime, endTime));
				 int count=attendDataDao.count(ev); 
				 if(count==0)
				 {
					  //请假 
					  EntityView evu=new EntityView();
					  evu.add(Restrictions.eq("applyId", dataList.get(i).get("userId")));
					  evu.add(Restrictions.between("leaveEndTime", startTime, endTime));  				 			  
					  int cnum=attendForLeaveDao.count(evu);
					  if (cnum==0)
					  {
						  if(!attendMrg.IsCardRecord(rflag, dataList.get(i).get("realName").toString(), Integer.valueOf(dataList.get(i).get("userId").toString()),   DateUtils.formatDate(startTime)))
						  {
							  attendMrg.insertRecord(rflag, dataList.get(i).get("realName").toString(), Integer.valueOf(dataList.get(i).get("userId").toString()),   DateUtils.formatDate(startTime));
						  }
						  isSender[y]=dataList.get(i).get("loginName").toString();
						  y=y+1;
					}
				 }		 
			}
		  }
		     
		  sendRtxMessage(isSender,"提 示 ","同学,您"+sendDate+"日"+dayName+"可能没有打卡，未打卡一次按迟到一次算（已签到和请假的除外），请及时到前台补签，谢谢! (如提示信息有误，请与技术部曹力博联系进行核查。)");
}
   
	//RTX发送信息
	public void  sendRtxMessage(String rtxUid, String title, String message) 
		{
			if(StringUtils.isNotEmpty(rtxUid)) {
				RTXMessage msg = new RTXMessage();
				msg.setTitle(title);
				msg.setMessage(message);
				msg.setErrUrl("test");
				msg.setOkUrl("test");
				msg.setDelayTime(RTXMessage.DELAY_TIME_PERMANENT);
				msg.setReceivers(new String[] {rtxUid});
				rtx.sendNotice(msg);
			}	
		}
	//RTX发送信息
	public void  sendRtxMessage(String [] rtxUidAry, String title, String message)
			{	
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
