/**
 * 
 */
package com.kaoshidian.oa.permission.dao;

import org.springframework.stereotype.Repository;

import com.kaoshidian.oa.base.HibernateBaseDao;
import com.kaoshidian.oa.permission.entity.Org;

/**
 * @author <p>Turbidsoul Chen 于 2013年6月29日 上午11:57:17</p>
 *
 */
@Repository
public class OrgDao extends HibernateBaseDao<Org, Integer> {

	@Override
    public Class<Org> getEntityClass() {
	    return Org.class;
    }

}
