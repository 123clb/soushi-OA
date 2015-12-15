package com.kaoshidian.oa.assets.action;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.kaoshidian.oa.assets.dao.CatagoryDao;
import com.kaoshidian.oa.assets.dao.DeviceDao;
import com.kaoshidian.oa.assets.dao.DeviceDetailDao;
import com.kaoshidian.oa.assets.dao.SupplierDao;
import com.kaoshidian.oa.assets.entity.Device;
import com.kaoshidian.oa.assets.entity.DeviceCategory;
import com.kaoshidian.oa.assets.entity.DeviceDetail;
import com.kaoshidian.oa.assets.entity.Supplier;
import com.kaoshidian.oa.assets.mgr.CategoryMrg;
import com.kaoshidian.oa.assets.mgr.DeviceMgr;
import com.kaoshidian.oa.base.EntityView;
import com.kaoshidian.oa.base.page.PageContext;
import com.kaoshidian.oa.base.util.BeansWrapperUtil;
import com.kaoshidian.oa.base.util.DateUtils;
import com.kaoshidian.oa.log.mng.LogMgr;
import com.kaoshidian.oa.permission.dao.UserDAO;
import com.kaoshidian.oa.util.ActionExtend;
import com.kaoshidian.oa.util.DeviceStateEnum;
import com.kaoshidian.oa.util.JSONUtils;
import com.kaoshidian.oa.util.LogEntityEnum;
import com.kaoshidian.oa.util.LogOperationEnum;
import com.kaoshidian.oa.util.LogStatusEnum;
import com.kaoshidian.oa.util.Util;
/**
 * @author <p>123clb 于 2013-5-7 下午11:33:03</p>
 *
 */
@Controller
public class AssetsAction extends ActionExtend 
{
	@Autowired
	private DeviceDao deviceDao;
	@Autowired
	private CatagoryDao catagoryDao; 
	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private DeviceDetailDao deviceDetailDao;
	@Autowired 
	private CategoryMrg categoryMrg;
    @Autowired 
	private LogMgr logMgr;
    @Autowired
	private DeviceMgr deviceMgr;
    @Autowired
    private UserDAO userDao;
    
    @ModelAttribute("category")
	public DeviceCategory createCategory() 
    {
		return new DeviceCategory();
	} 
    @ModelAttribute("supplier")
 	public Supplier createSupplier() 
     {
 		return new Supplier();
 	}   
    @ModelAttribute("device")
    public Device createDevice()
    {
    	return new Device();    	
    }
  
    @ModelAttribute("deviceDetail")
    public DeviceDetail createDeviceDetail()
    {
    	return new DeviceDetail(); 	
    }  
    @RequestMapping("/device/category/add.do")
    public String beforeCategoryAdd(ModelMap model)
    {
    	return "/assets/categoryEdit";   	
    }
    
    @RequestMapping("/device/category/modify.do") 
    public String categoryEdit(ModelMap model, Integer categoryId)
    {
    	DeviceCategory category =catagoryDao.findById(categoryId);	
		model.addAttribute("category", category);
		return "/assets/categoryEdit";	
    }
    
	@RequestMapping("/device/category/save.do")
	@ResponseBody
	public JSONObject  dcategoryEdit(ModelMap model, DeviceCategory category, HttpServletRequest request, Integer categoryId) throws ParseException 
	{
		  Date currentTime=DateUtils.parseDatetime(DateUtils.currentDatetime()); 
		  category.setCreateDate(currentTime);
		  LogOperationEnum op=null;
		  
		  if(category.getCategoryId() == null)
		  {
			  op=LogOperationEnum.SAVE;
		  }
		  else {
			  
			  op=LogOperationEnum.MODIFY;
		}
		 categoryMrg.saveOrUpdateCategory(category);
		
		 logMgr.save(getCurrentUser(), Util.getIpAddr(request),category.getCategoryId(),LogEntityEnum.DEVICE_CATEGORY, op,
		  	      LogStatusEnum.SUCCESS, "设备类型信息:"+category.getCategoryName()+op+"成功");
         return JSONUtils.getJsonResult(null, null, "200", "保存成功","closeCurrent", "main");
	}
	@RequestMapping("/device/category/delete.do")
	@ResponseBody
	public JSONObject delete(HttpServletRequest request, ModelMap model,Integer categoryId) {
		catagoryDao.delete(new Integer[]{categoryId});
		logMgr.save(getCurrentUser(), Util.getIpAddr(request), categoryId, LogEntityEnum.DEVICE_CATEGORY, LogOperationEnum.DELETE, LogStatusEnum.SUCCESS, LogOperationEnum.DELETE.getLabel() + "分类ID:" +categoryId+"成功" );
		return JSONUtils.DELETE_SUCCESS;
	}
       @RequestMapping("/assets/dcategorylist.do")
		public String categoryList(ModelMap model)
		{
			List<DeviceCategory> categoryList=catagoryDao.findAll();
			model.addAttribute("categoryList", categoryList);
			return "/assets/dcategorylist";
		}
		
	   @RequestMapping("/device/supplier/add.do")
	    public String beforeSupplierAdd(ModelMap model)
	    {
	    	return "/assets/supplierEdit";   	
	    }
	    
	    @RequestMapping("/device/supplier/modify.do") 
	    public String supplierEdit(ModelMap model, Integer supplierId)
	    {
	    	Supplier supplier =supplierDao.findById(supplierId);	
			model.addAttribute("supplier", supplier);
			return "/assets/supplierEdit";	
	    }
		@RequestMapping("/device/supplier/save.do")
		@ResponseBody
		public JSONObject  supplierEdit(ModelMap model, Supplier supplier, HttpServletRequest request, Integer supplierId) throws ParseException 
		{
			  Date currentTime=DateUtils.parseDatetime(DateUtils.currentDatetime()); 
			  supplier.setCreateDate(currentTime);
			  LogOperationEnum op=null;
			  if(supplier.getSupplierId() == null)
			  {
				  op=LogOperationEnum.SAVE;
			  }
			  else {
				  op=LogOperationEnum.MODIFY;
			}
			supplierDao.saveOrUpdate(supplier);
			logMgr.save(getCurrentUser(), Util.getIpAddr(request),supplier.getSupplierId(),LogEntityEnum.DEVICE_SUPPLIER, op,
			  	      LogStatusEnum.SUCCESS, "供应商"+supplier.getSupplierName()+op+"成功");
	         return JSONUtils.getJsonResult(null, null, "200", "保存成功","closeCurrent", "main");
		}
		
		@RequestMapping("/device/supplier/delete.do")
		@ResponseBody
		public JSONObject sDelete(HttpServletRequest request, ModelMap model,Integer supplierId) {
			supplierDao.delete(new Integer[]{supplierId});
			logMgr.save(getCurrentUser(), Util.getIpAddr(request), supplierId, LogEntityEnum.DEVICE_SUPPLIER, LogOperationEnum.DELETE, LogStatusEnum.SUCCESS, LogOperationEnum.DELETE.getLabel() + "供应商ID:" +supplierId+"成功" );
			return JSONUtils.DELETE_SUCCESS;
		}	
		@RequestMapping("/assets/supplierlist.do")
		public String supplierList(ModelMap model)
		{
			List<Supplier> suppliersList=supplierDao.findAll();
			model.addAttribute("suppliersList", suppliersList);
			return "/assets/supplierlist";
		}
		
		@RequestMapping("/device/add.do")
		public String deviceAdd(ModelMap model)
		{	
			
			String loginName =this.getCurrentUser().getRealName();;
			Integer userId=this.getCurrentUser().getUserId();					
			model.addAttribute("createName",loginName);
			model.addAttribute("userId",userId);
			model.addAttribute("categotymap",deviceMgr.getCategoryMap());
			model.addAttribute("usermap",deviceMgr.getUserMap("RecordManager"));
			return "/assets/deviceEdit";			
		}
		
		@RequestMapping("/assets/devicelist.do")
		public String deviceList(ModelMap model,Integer pageNum, Integer numPerPage,HttpServletRequest request,DeviceStateEnum deviceState) throws Exception
		{
 		   EntityView ev=new EntityView();
 		    String searchDeviceName=request.getParameter("searchDeviceName");
 		    String searchPersonUseName=request.getParameter("searchPersonUseName");
 		    String orderField = request.getParameter("orderField");
 			String orderDirection = request.getParameter("orderDirection");
 		    Integer searchassigId = StringUtils.isEmpty(request
 					.getParameter("searchassigId")) ? null : Integer.valueOf(request
 					.getParameter("searchassigId"));
 		   
 		    if (searchDeviceName!=null && searchDeviceName.trim().isEmpty() ==false)
 			{
 				ev.add(Restrictions.like("deviceName", searchDeviceName,MatchMode.ANYWHERE));	
 				model.addAttribute("searchDeviceName", searchDeviceName);
 			}
 		    
 		    if(!StringUtils.isBlank(searchPersonUseName))
 		    {
 		    	ev.add(Restrictions.like("usePersonName", searchPersonUseName));	
 				model.addAttribute("usePersonName", searchPersonUseName);
 		    }
 		    
 			if (searchassigId!=null)
 			{
 				ev.add(Restrictions.eq("supervisePerson", searchassigId));
 				model.addAttribute("searchassigId", searchassigId);
 			}			
 			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
 			Date startTime = StringUtils.isEmpty(request
 					.getParameter("deviceStartTime")) ? null : df.parse(request
 					.getParameter("deviceStartTime"));
 			Date endTime = StringUtils.isEmpty(request
 					.getParameter("deviceEndTime")) ? null : df.parse(request
 					.getParameter("deviceEndTime"));
 			
 			if (startTime!=null)
 			{
 				ev.add(Restrictions.ge("createDate", startTime));	
 				model.addAttribute("deviceStartTime", startTime);
 			}
 			if (endTime!=null)
 			{
 				ev.add(Restrictions.le("createDate", endTime));	
 				model.addAttribute("deviceEndTime", endTime);
 			}
 			
 			if (deviceState!=null)
 			{
 				ev.add(Restrictions.eq("deviceState", deviceState));
 				model.addAttribute("deviceState", deviceState);
 			}
 		if (orderField == null) {
			ev.addOrder(Order.desc("createDate"));
			model.addAttribute("orderField", "createDate");
			model.addAttribute("orderDirection", "desc");
		} else if ("desc".equals(orderDirection)) {
			ev.addOrder(Order.desc(orderField));
			model.addAttribute("orderField", orderField);
			model.addAttribute("orderDirection", "desc");
		} else if ("asc".equals(orderDirection)) {
			ev.addOrder(Order.asc(orderField));
			model.addAttribute("orderField", orderField);
			model.addAttribute("orderDirection", "asc");
		}	
 		 model.addAttribute("assigners", deviceMgr.getUserMap("RecordManager"));
 		 PageContext<Device> pageCtx=deviceDao.queryUsePage(ev,pageNum, numPerPage);
 		 int pageCtxNum=pageCtx.getItemList().size();
 		 Double totalPrice=0.0;
 		 for (int i = 0; i < pageCtxNum; i++) {
 			totalPrice+=pageCtx.getItemList().get(i).getDevicePrice(); 
		}
 		 model.addAttribute("totalPrice",totalPrice);
 		 model.addAttribute("pageCtx",pageCtx);
 		 model.addAttribute("statusEnum", BeansWrapperUtil.wrapEnum(DeviceStateEnum.class));
		   return "/assets/devicelist";			
		}
		
		@RequestMapping("device/data/supplylist.do")
		public String getSupplyList (ModelMap model,HttpServletRequest request,Integer pageNum, Integer numPerPage) throws Exception
		{		
			EntityView ev=new EntityView();
			ev.addOrder(Order.desc("createDate"));
			model.addAttribute("pageCtx", supplierDao.queryUsePage(ev, pageNum, numPerPage));
			return "/assets/sylist";			
		}	
		@RequestMapping("/device/save.do")
		@ResponseBody
		public JSONObject deviceSave(ModelMap model, Device dv,  HttpServletRequest request,Integer deviceId) throws Exception
		{
			  Date currentTime=DateUtils.parseDatetime(DateUtils.currentDatetime()); 
			  String  dwid=request.getParameter("orgLookup1.id");
			  String uid=request.getParameter("orgLookup.id");	
			  String uname=request.getParameter("dwz.orgLookup.dtName");
			  Integer useId=StringUtils.isEmpty(uid)?null:Integer.valueOf(uid);
			  Integer spid=StringUtils.isEmpty(dwid)?null:Integer.valueOf(dwid);
			  dv.setCreateDate(currentTime);
			  dv.setSupplierID(spid);
			  dv.setUsePersonId(useId);
			  dv.setUsePersonName(uname);
			  LogOperationEnum op=null;			  
			  if(dv.getDeviceId()== null)
			  {
				  op=LogOperationEnum.SAVE;
			  }
			  else 
			  {
				  op=LogOperationEnum.MODIFY;
			  }	
			  if(!StringUtils.isBlank(uid))
			  {
				 dv.setDeviceState(DeviceStateEnum.ISUSEING);
				 deviceMgr.deviceTranse(dv);
			  }
			  else {
				  dv.setDeviceState(DeviceStateEnum.IDLE);
				  deviceDao.saveOrUpdate(dv);
			   }
			  
			  logMgr.save(getCurrentUser(), Util.getIpAddr(request),dv.getDeviceId(),LogEntityEnum.DEVICE, op,
			  	      LogStatusEnum.SUCCESS, "设备"+dv.getDeviceName()+"操作成功");
			
	         return JSONUtils.getJsonResult(null, null, "200", "保存成功","closeCurrent", "main");
		}
		@RequestMapping("/device/modify.do")
		public String beforeModify(ModelMap model,Integer deviceId)
		{
			Device device=deviceDao.findById(deviceId);
			model.addAttribute("device", device);
			model.addAttribute("categotymap",deviceMgr.getCategoryMap());
			model.addAttribute("usermap",deviceMgr.getUserMap("RecordManager"));
			model.addAttribute("syMap", deviceMgr.getSupplyMap());
			
			return "/assets/deviceEdit";	

		}
		@RequestMapping("/device/delete.do")
		@ResponseBody
		public JSONObject deviceDelete(HttpServletRequest request, Integer deviceId)
		{
			deviceDao.delete(new Integer[]{deviceId});	
			logMgr.save(getCurrentUser(), Util.getIpAddr(request), deviceId, LogEntityEnum.DEVICE, LogOperationEnum.DELETE, LogStatusEnum.SUCCESS, LogOperationEnum.DELETE.getLabel() + "设备ID:" +deviceId+"成功" );
			return JSONUtils.DELETE_SUCCESS;			
		}
		
		@RequestMapping("/device/allocation.do")
		public String  allocationAdd(ModelMap model, Integer deviceId)
		{
		   model.addAttribute("assigners", deviceMgr.getUserMap("RecordManager"));
		   model.addAttribute("devid", deviceId); 
		   return "assets/allocationEdit";	
		}
		
		@RequestMapping("/device/user/list.do")
		public String userList(ModelMap model) throws Exception
		{
			model.addAttribute("userList",deviceMgr.getAllUserList());			
			return "assets/userlist";
		}
		
		@RequestMapping("allocation/save.do")
		@ResponseBody
		 public JSONObject deviceDetailSave(HttpServletRequest request,DeviceDetail dv,Integer deviceId) throws Exception
		 {
			 Date currentTime=DateUtils.parseDatetime(DateUtils.currentDatetime());
			 String getUserId=request.getParameter("orgLookup.id");
			 String getUserName=request.getParameter("dwz.orgLookup.dtName");
			 Integer userId=StringUtils.isEmpty(getUserId)?null:Integer.valueOf(getUserId);  
			 dv.setUsePersonId(userId);		 
			 dv.setCreateDate(currentTime);
			 dv.setUsePersonName(getUserName);
			 LogOperationEnum op=null;
			 if(dv.getDetailId()== null)
			  {
				  op=LogOperationEnum.SAVE;
				  dv.setDeviceId(deviceId);
			  }
			  else {				  
				  op=LogOperationEnum.MODIFY;
			}			 
		    boolean isTrue=deviceDetailDao.saveOrUpdate(dv);
			  if (isTrue)
			  {
				 String sql="update oa_assets_device set deviceState='"+DeviceStateEnum.ISUSEING+"',usePersonName='"+getUserName+"',usePersonId="+getUserId+",supervisePerson="+dv.getAssignPersonId()+"  where deviceId="+deviceId;
				 deviceDao.update(sql);
			  }
			  Device dev=deviceDao.findById(deviceId);
			  String deviceName=dev.getDeviceName();
			  String assignName=userDao.findById(dv.getAssignPersonId()).getRealName();
			  
			  String delInfo="设备  "+deviceName+ " 由 "+assignName+"分配给"+dv.getUsePersonName()+"使用";    
			  logMgr.save(getCurrentUser(), Util.getIpAddr(request),dv.getDetailId(),LogEntityEnum.DEVICE_DETAIL, op,
			  	      LogStatusEnum.SUCCESS, delInfo);
	         return JSONUtils.getJsonResult(null, null, "200", "保存成功","closeCurrent", "main");		 
		}
						  
		@ RequestMapping("/device/detaillist.do")
		public  String getDeviceDetail(ModelMap model,Integer deviceId)
		{
			  EntityView ev=new EntityView();
			  ev.add(Restrictions.eq("deviceId", deviceId));
			  List<DeviceDetail> detailsList=deviceDetailDao.findByEntityView(ev);
			  model.addAttribute("detailsList", detailsList);			  
			  model.addAttribute("assigners", deviceMgr.getUserMap("RecordManager"));
			  return "assets/detaillist";		
		}
		
		@RequestMapping("/device/detail/modify.do")
		public String detailUpdate(ModelMap model,Integer detailId)
		{			
		    if (detailId!=null)
		    {
		    	DeviceDetail deviceDetail=deviceDetailDao.findById(detailId);
		    	model.addAttribute("deviceDetail", deviceDetail);
		    	model.addAttribute("assigners", deviceMgr.getUserMap("RecordManager"));	
		    	model.addAttribute("devid", deviceDetail.getDetailId()); 
		    }
			return "assets/allocationEdit";
		}
		@RequestMapping("/device/detail/delete.do")
		@ResponseBody
		public JSONObject detailDelete(Integer detailId,HttpServletRequest request)
		{
			deviceDetailDao.delete(new Integer[]{detailId});	
			logMgr.save(getCurrentUser(), Util.getIpAddr(request), detailId, LogEntityEnum.DEVICE_DETAIL, LogOperationEnum.DELETE, LogStatusEnum.SUCCESS, LogOperationEnum.DELETE.getLabel() + "使用明细ID:" +detailId+"成功");
			return JSONUtils.DELETE_SUCCESS;			
		}
		
		@RequestMapping("/device/scrap.do")
		public String  beforeSetState(ModelMap model,Integer deviceId)
		{
			 String currentName=getCurrentUser().getRealName();
			 model.addAttribute("sName", currentName);
			 model.addAttribute("aPid", getCurrentUser().getUserId());
			 model.addAttribute("deviceId", deviceId);
			 return "assets/deviceState";
		}
		
		@RequestMapping("/device/setState.do")
		@ResponseBody
		public JSONObject  saveState(Integer deviceId, String scrapPic,String deviceState,Integer assignPersonId ) throws ParseException
		{
			if (!StringUtils.isBlank(deviceState))
			{
			   DeviceStateEnum  dvState=DeviceStateEnum.IDLE;
			   String stateString="闲置";
				  if (deviceState.equals("SCRAP"))
				  {
					  dvState=DeviceStateEnum.SCRAP;
					  stateString="报废";
				  }
				String strSql="update oa_assets_device set deviceState='"+dvState+"',usePersonName='',usePersonId=null,scrapPic='"+scrapPic+"'  where deviceId="+deviceId;
				deviceDao.update(strSql);
			   DeviceDetail  detail=new DeviceDetail();
			   
			   detail.setDeviceId(deviceId);
			   detail.setAssignPersonId(assignPersonId);			   
			   Date createDate=DateUtils.parseDatetime(DateUtils.currentDatetime());
			   detail.setCreateDate(createDate);
			   detail.setDescription(stateString);
			   deviceDetailDao.addNew(detail);
			}
			
			return JSONUtils.getJsonResult(null, null, "200", "设置成功","closeCurrent", "main");		 
		}
		
} 
