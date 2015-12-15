package com.kaoshidian.oa.attend.mgr;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaoshidian.oa.attend.dao.AttendDataDao;
import com.kaoshidian.oa.attend.dao.AttendInfoDao;
import com.kaoshidian.oa.attend.dao.AttendRecordDao;
import com.kaoshidian.oa.attend.entity.AttendInfo;
import com.kaoshidian.oa.attend.entity.AttendRecord;
import com.kaoshidian.oa.base.EntityView;
import com.kaoshidian.oa.base.util.DateUtils;

/**
 * @author <p>123clb 于 2013-7-12 下午5:11:56</p>
 *
 */

@Service
public class AttendMrg {
	
@Autowired
private AttendInfoDao attendInfoDao;

@Autowired
private AttendRecordDao attendRecordDao;

@Autowired
private AttendDataDao attendDataDao;
	
 public void isInfoExist(String rtxNo,String infoTime,String personName)
 {
		  EntityView ev=new EntityView();
		  ev.add(Restrictions.eq("rtxNo", rtxNo));
		  ev.add(Restrictions.eq("infoTime", infoTime));
		 int count=attendInfoDao.count(ev);	 
		 if(count==0)
		 {		 	 
			 AttendInfo attendInfo=new AttendInfo();
			 attendInfo.setInfoTime(infoTime);
			 attendInfo.setRtxNo(rtxNo);
			 attendInfo.setLaterNum(0);
			 attendInfo.setLeaveNum(0.00);
			 attendInfo.setPersonName(personName);
			 
			 attendInfoDao.addNew(attendInfo);	
		 }
 }
 
	 //记录未打卡信息
public void insertRecord(String flag, String userName, Integer uid, String time) throws ParseException
	 {
		 AttendRecord attendRecord=new AttendRecord();	 
		 attendRecord.setrName(userName);
		 attendRecord.setrUid(uid);
		 attendRecord.setrFlag(flag);
		 attendRecord.setrTime(time);
		 attendRecord.setCreateDate(DateUtils.parseDatetime(DateUtils.currentDatetime()));
		 attendRecordDao.addNew(attendRecord);		  
	 }
 
//判断是否一已经记录过为打卡时间了
 public Boolean IsCardRecord(String flag, String userName, Integer uid, String time)
 {
	  Boolean isExit=false;
	  EntityView ev=new EntityView();
	  ev.add(Restrictions.eq("rFlag",flag));
	  ev.add(Restrictions.eq("rName", userName));
	  ev.add(Restrictions.eq("rTime", time));
	  
	 if (attendRecordDao.count(ev)>0)
	 {
		 isExit=true;
		 
	 }
	 return isExit;
 }

 //public String getRuleTime()
  
	public ArrayNode getDateChartJson(String rtxNo,Date amTime,Date pmTime) throws ParseException 
	{
		     String sql="select DATE_FORMAT(workTime, '%Y-%m-%d')AS month, workTime  from  oa_attend_data where  workerId=? and workTime between ? and ?  GROUP BY month ";
			 List<Map> dmap=attendDataDao.findBySql(sql, new Object[]{rtxNo,amTime,pmTime});	 
		     ObjectMapper om = new ObjectMapper();
		     ArrayNode chartDataJson = om.createArrayNode();
				for (Map m : dmap) 
				{
			       ObjectNode node = om.createObjectNode();
			       node.put("month", m.get("month").toString());  
			       Calendar cal=Calendar.getInstance();
			       cal.setTime((Date)m.get("workTime"));
			       int hour=cal.get(Calendar.HOUR_OF_DAY);
			       if(hour<12)
			       {
				       cal.getTimeInMillis();			       
				       String baseTime=DateUtils.formatDate(DateUtils.parseDate(m.get("workTime").toString()))+" 08:30:00";			       
				       Date  bDate=DateUtils.parseDatetime(baseTime);
				       Calendar cal1=Calendar.getInstance();
				       cal1.setTime(bDate);
				       cal1.getTimeInMillis();
		
				       double dtime =(cal.getTimeInMillis()-cal1.getTimeInMillis())/1000/60;
				       node.put("workTime", -dtime);
				       chartDataJson.add(node);
			       }
			    }			
	          return chartDataJson;
	}


}
