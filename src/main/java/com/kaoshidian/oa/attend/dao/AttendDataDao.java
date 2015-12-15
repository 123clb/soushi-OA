package com.kaoshidian.oa.attend.dao;
import org.springframework.stereotype.Repository;

import com.kaoshidian.oa.attend.entity.AttendData;
import com.kaoshidian.oa.base.HibernateBaseDao;
/**
 * @author <p>123clb 于 2013-5-21 下午5:43:51</p>
 *
 */
@Repository
public class AttendDataDao extends HibernateBaseDao<AttendData, Integer> {

	@Override
	 public Class<AttendData> getEntityClass() {
	    return AttendData.class;
  }
	
}
