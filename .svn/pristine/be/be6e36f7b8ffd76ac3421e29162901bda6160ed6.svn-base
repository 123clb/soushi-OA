package com.kaoshidian.oa.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.subject.Subject;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaoshidian.oa.base.entity.BaseEntity;
import com.kaoshidian.oa.permission.entity.User;

/**
 * @classDescription :实体视图，查询、排序条件的容器，用来存储查询、排序条件
 * @date 2009-7-20 
 * @author 王渊博
 */
public class EntityView  {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(EntityView.class);

	private List<Criterion> criterionList = new ArrayList<Criterion>();
	private Class<?> entityClass;
	private List<Order> orderList = new ArrayList<Order>();
    public EntityView(){
		super();
		//add_dataperm();
	}
    
    public EntityView(Class<?> entityClass) {
    	super();
    	this.entityClass = entityClass;
    	//add_dataperm();
    	
    }
	public EntityView(Map<String,Object> map){
		this.add(map);
		//add_dataperm();
	}
	public EntityView(BaseEntity entity) throws Exception{
		Map<String, Object> map = EntityUtils.bean2Map(entity);
		this.add(map);
		this.entityClass = entity.getClass();
		//add_dataperm();
	}
	public EntityView(Map<String,Object> map,boolean isFuzzyQuery){
		this.add(map,isFuzzyQuery);
	}
	
	/**
	 * 数据权限初始化
	 */
	private void add_dataperm() {
		
		if (entityClass == null) {
			if (logger.isDebugEnabled()) {
	            // 如果entityClass不存在，则返回，认为此次查询不附带数据权限
	            logger.warn("没有设置EntityClass，此次查询不附加数据权限");
            }
			return;
		}

		User user = (User) SecurityUtils.getSubject().getPrincipal();
		if (user == null) {
			throw new AccountException("登录已失效");
		}
		Subject subject = SecurityUtils.getSubject();
		if (subject.hasRole("admin") || subject.hasRole("ProductCharge")) {
			// 如果是管理員，則可以看到所有的數據
			return;
		}

		Collection<?> coll = null;
		coll = (Collection<?>) user.getPermissions().get(entityClass.getName());

		if (coll != null) {
			criterionList.add(Restrictions.in("id", coll));
		} else {
			// 如果数据权限中没有定义此用户的数据权限，则加入一个false条件，造成无查询结果
			criterionList.add(Restrictions.lt("id", 0));
		}
	}
	
	public EntityView add(Criterion criterion){
		this.criterionList.add(criterion);
		return this;
	}

	public EntityView addOrder(Order order){
		this.orderList.add(order);
		return this;
	}
	
	public List<Criterion> getCriterionList() {
		return criterionList;
	}

	public List<Order> getOrderList() {
		return orderList;
	}
	/**
	 * @functionDescription :将Map转化为entityView,翻译为sql相当于 where key1=value1 and key2=value2 and key3 like '%value3%'
	 * @param map
	 * @param isFuzzyQuery 是否为模糊查询，模糊查询时，运算符翻译为like
	 * @return
	 */
	public EntityView add(Map<String,Object> map,boolean isFuzzyQuery){
		for(Iterator<Entry<String, Object>> ite = map.entrySet().iterator();ite.hasNext();){
			Entry<String, Object> entry = ite.next();
			Object value = entry.getValue();
			if (isFuzzyQuery && value instanceof String) {
				this.criterionList.add(Restrictions.like(entry.getKey(), "%"+value.toString().trim()+"%"));
			}else{
				this.criterionList.add(Restrictions.eq(entry.getKey(), value));
			}
			
		}
		return this;
	}
	public EntityView add(Map<String,Object> map){
		return this.add(map, false);
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if(criterionList.size()>0){
			sb.append("where 1=1");
			for(Criterion criterion:criterionList){
				sb.append(" and ").append(criterion);
			}
		}
		if(orderList.size()>0){
			sb.append(" order by ");
			for(Order order:orderList){
				sb.append(order).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.length()>0?sb.toString():super.toString();
	}
	
	public static void main(String[] args){
		EntityView ev = new EntityView();
		ev.add(Restrictions.le("score", 60));
		ev.add(Restrictions.between("date", new Date(), new Date()));
		ev.add(Restrictions.like("id", 60));
		ev.addOrder(Order.asc("number"));
		ev.addOrder(Order.desc("name"));
		System.out.print(ev.toString());
	}
	public Class<?> getEntityClass() {
		return entityClass;
	}
	/**
	 * @functionDescription :设置该EntityView对应的实体
	 * @param entityClass
	 */
	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
//		String propName = EntityUtils.getPropName(entityClass, TbRmLearncenter.class);
//		if(propName!=null){
//			this.addLearnCenterRestriction(propName);
//		}
	}
}
