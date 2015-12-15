/**
 * 
 */
package com.kaoshidian.oa.base;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.kaoshidian.oa.base.entity.BaseEntity;

/**
 * @author <p>Innate Solitary 于 2012-5-24 下午2:51:41</p>
 *
 */
public class EntityPersistInterceptor extends EmptyInterceptor {

	/**
     * 
     */
    private static final long serialVersionUID = 8387713433754766374L;
    
    
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
    	if (!(entity instanceof BaseEntity)) {
    		return true;
        }
    	BaseEntity baseEntity = (BaseEntity) entity;
    	if(baseEntity.getCreateDate() == null) {
    		baseEntity.setCreateDate(new Date());
    	}
        return false;
    }

}
