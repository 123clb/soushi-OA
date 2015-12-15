package com.kaoshidian.oa.assets.mgr;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kaoshidian.oa.assets.dao.CatagoryDao;
import com.kaoshidian.oa.assets.dao.DeviceDao;
import com.kaoshidian.oa.assets.dao.DeviceDetailDao;
import com.kaoshidian.oa.assets.dao.SupplierDao;
import com.kaoshidian.oa.assets.entity.Device;
import com.kaoshidian.oa.assets.entity.DeviceCategory;
import com.kaoshidian.oa.assets.entity.DeviceDetail;
import com.kaoshidian.oa.assets.entity.Supplier;
import com.kaoshidian.oa.base.EntityView;
import com.kaoshidian.oa.permission.dao.RoleDAO;
import com.kaoshidian.oa.permission.dao.UserDAO;
import com.kaoshidian.oa.permission.dao.UserRoleDao;
import com.kaoshidian.oa.permission.entity.Role;
import com.kaoshidian.oa.permission.entity.User;
import com.kaoshidian.oa.permission.entity.UserRole;
import com.kaoshidian.oa.permission.mgr.PermissionMgr;
/**
 * @author <p>123clb 于 2013-5-8 下午4:57:24</p>
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@SuppressWarnings("unchecked")

public class DeviceMgr
{
	@Autowired
	private DeviceDao deviceDao;
	@Autowired
	private DeviceDetailDao deviceDetailDao;
	@Autowired 
	private CatagoryDao catagoryDao;
	@Autowired 
	private UserDAO userDao;
	@Autowired
	private RoleDAO roleDao;
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private PermissionMgr permissionMgr;
	
	public void saveOrUpdateRecordVideo(Device dv) 
	{
		deviceDao.saveOrUpdate(dv);
	}
	
	//返回类别的MAP
	public Map<String,String>getCategoryMap()
		{
			List<DeviceCategory> dcList=catagoryDao.findAll();
			Map<String,String> CatgoryMap = new LinkedHashMap<String,String>();
			for (int i = 0; i < dcList.size(); i++) {
				CatgoryMap.put(dcList.get(i).getCategoryId().toString(),dcList.get(i).getCategoryName());			
			}	
			return CatgoryMap;
		}
		
	//返回供应商的MAP
	public Map<String,String>getSupplyMap()
		{
			List<Supplier> dcList=supplierDao.findAll();
			Map<String,String> syMap = new LinkedHashMap<String,String>();
			for (int i = 0; i < dcList.size(); i++) {
				syMap.put(dcList.get(i).getSupplierId() .toString(),dcList.get(i).getSupplierName());			
			}	
			return syMap;
		}
	   //返回指定角色的监管人MAP
		public Map<String,String>getUserMap(String rolename)
		{
			  EntityView ev=new EntityView();
			  ev.add(Restrictions.eq("roleName", rolename));	
			  Role role =roleDao.findUniq(ev);			
		      List <UserRole> urList=(List<UserRole>) userRoleDao.findByProperty("roleId", role.getRoleId());
			  Map<String,String> userMap = new LinkedHashMap<String,String>();
			  List<User> ulist=userDao.findAll();
			  for (int i = 0; i < urList.size(); i++) {
				  	 for (int j = 0; j < ulist.size(); j++) {
				  		 
				  		 if(urList.get(i).getUserId().equals(ulist.get(j).getUserId())&&ulist.get(j).getState().toString().equals("ACTIVITY"))
				  		 {
				  			userMap.put(urList.get(i).getUserId().toString(),ulist.get(j).getRealName());
				  		 }
					}		
				}
				return userMap;
		}
		
	    public List<User> getAllUserList()
	    {
	    	EntityView userev = new EntityView();
			userev.addOrder(Order.asc("state"));
			userev.addOrder(Order.asc("userId"));
			List<User> userList = userDao.findByEntityView(userev);
			for (User user : userList) {
				user.setRoles(permissionMgr.getRolesByGrantUser(user.getUserId()));
	        }	    	
	       return userList;
	    }
	    
	    public  void  deviceTranse(Device dv)
	    {
	    	 try {
	    		   	 deviceDao.saveOrUpdate(dv);
	    		   	 DeviceDetail deviceDetail=new DeviceDetail(); 
					 deviceDetail.setDeviceId(dv.getDeviceId());
					 deviceDetail.setUsePersonId(dv.getUsePersonId());
					 deviceDetail.setUsePersonName(dv.getUsePersonName());
					 deviceDetail.setAssignPersonId(dv.getSupervisePerson());
					 deviceDetail.setCreateDate(dv.getCreateDate());
					 deviceDetailDao.saveOrUpdate(deviceDetail);	
	    	 	} 
	    	 catch (Exception e) 
				{
					// TODO: handle exception
				}
	    	 
	    }
}
