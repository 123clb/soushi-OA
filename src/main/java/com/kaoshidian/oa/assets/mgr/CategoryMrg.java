package com.kaoshidian.oa.assets.mgr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaoshidian.oa.assets.dao.CatagoryDao;
import com.kaoshidian.oa.assets.entity.DeviceCategory;
/**
 * @author <p>123clb 于 2013-5-8 下午5:08:35</p>
 *
 */
@Service
@SuppressWarnings("unchecked")
public class CategoryMrg {	
	
	@Autowired
	private CatagoryDao catagoryDao;
	
	public void saveOrUpdateCategory(DeviceCategory dcg)
	{
		catagoryDao.saveOrUpdate(dcg);
		
	}

}
