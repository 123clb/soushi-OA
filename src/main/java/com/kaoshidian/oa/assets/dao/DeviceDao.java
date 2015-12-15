package com.kaoshidian.oa.assets.dao;

import org.springframework.stereotype.Repository;

import com.kaoshidian.oa.assets.entity.Device;
import com.kaoshidian.oa.base.HibernateBaseDao;


@Repository
public class DeviceDao extends HibernateBaseDao<Device, Integer> {
	
	@Override
	 public Class<Device> getEntityClass() {
	    return Device.class;
    }
	

}
