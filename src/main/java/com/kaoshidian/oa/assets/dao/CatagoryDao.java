package com.kaoshidian.oa.assets.dao;
import org.springframework.stereotype.Repository;

import com.kaoshidian.oa.assets.entity.DeviceCategory;
import com.kaoshidian.oa.base.HibernateBaseDao;

/**
 * @author <p>123clb 于 2013-5-8 下午5:13:33</p>
 *
 */
@Repository
public class CatagoryDao extends HibernateBaseDao<DeviceCategory, Integer> {
	
	@Override
	 public Class<DeviceCategory> getEntityClass() {
	    return DeviceCategory.class;
   }

}
