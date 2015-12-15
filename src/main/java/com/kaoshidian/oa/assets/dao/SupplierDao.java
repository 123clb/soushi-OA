package com.kaoshidian.oa.assets.dao;
import org.springframework.stereotype.Repository;

import com.kaoshidian.oa.assets.entity.Supplier;
import com.kaoshidian.oa.base.HibernateBaseDao;

/**
 * @author <p>123clb 于 2013-5-9 下午4:43:41</p>
 *
 */
@Repository
public class SupplierDao extends HibernateBaseDao<Supplier, Integer> {
	 @Override
	 public Class<Supplier> getEntityClass() {
	    return Supplier.class;
   }
}
