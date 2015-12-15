package com.kaoshidian.oa.attend.dao;
import org.springframework.stereotype.Repository;
import com.kaoshidian.oa.attend.entity.AttendInfo;
import com.kaoshidian.oa.base.HibernateBaseDao;
/**
 * @author <p>123clb 于 2013-7-12 下午2:23:40</p>
 *
 */
@Repository
public class AttendInfoDao extends HibernateBaseDao<AttendInfo, Integer> 
{
	@Override
	public Class<AttendInfo> getEntityClass() {
	    return  AttendInfo.class;
	}
	
}
