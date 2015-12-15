package com.kaoshidian.oa.attend.dao;
import org.springframework.stereotype.Repository;
import com.kaoshidian.oa.attend.entity.AttendRecord;
import com.kaoshidian.oa.base.HibernateBaseDao;

/**
 * @author <p>123clb 于 2013-7-20 下午12:02:13</p>
 *
 */
@Repository
public class AttendRecordDao extends HibernateBaseDao<AttendRecord, Integer> {
	@Override
	 public Class<AttendRecord> getEntityClass()
	 {
	    return AttendRecord.class;
	 }

}
