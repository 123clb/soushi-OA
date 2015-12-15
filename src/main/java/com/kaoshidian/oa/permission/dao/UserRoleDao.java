/**
 * 
 */
package com.kaoshidian.oa.permission.dao;

import org.springframework.stereotype.Repository;

import com.kaoshidian.oa.base.HibernateBaseDao;
import com.kaoshidian.oa.permission.entity.UserRole;

/**
 * @author <p>Innate Solitary 于 2012-5-31 上午11:01:12</p>
 *
 */
@Repository
public class UserRoleDao extends HibernateBaseDao<UserRole, Integer> {

	@Override
    public Class<UserRole> getEntityClass() {
	    return UserRole.class;
    }

}
