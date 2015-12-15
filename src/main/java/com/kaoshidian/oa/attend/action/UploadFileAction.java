package com.kaoshidian.oa.attend.action;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kaoshidian.oa.attend.dao.AttendDataDao;
import com.kaoshidian.oa.attend.entity.AttendData;
import com.kaoshidian.oa.permission.dao.UserDAO;
import com.kaoshidian.oa.util.ActionExtend;
import com.kaoshidian.oa.util.SystemConfig;
/**
 * @author <p>123clb 于 2013-5-22 上午10:38:22</p>
 *
 */
@Controller
public class UploadFileAction extends ActionExtend {   
	
	@Autowired
	private UserDAO userDao;	
	@Autowired
	private SystemConfig systemConfig;
    @Autowired
    private AttendDataDao attendDataDao;
	
	@RequestMapping("/record/upload.do")
	@ResponseBody
    public String fileUpload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        //String savePath =System.getProperty("user.dir")+"/src/main/webapp/resources/upload/";	
        String savePath=systemConfig.getUploadDir();
        File f1 = new File(savePath);
        if (!f1.exists())
        {
             f1.mkdirs();
        }
        DiskFileItemFactory fac = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(fac);
        upload.setHeaderEncoding("utf-8");
        List fileList = null;
        try {
              fileList = upload.parseRequest(request);
        } 
        catch (FileUploadException ex) {
            return ex.toString();
        }
        Iterator<FileItem> it = fileList.iterator();
        String name = "";
        String extName = "";
        while (it.hasNext()) {
            FileItem item = it.next();
            if (!item.isFormField())
            {
                name = item.getName();
                long size = item.getSize();
                String type = item.getContentType();
                System.out.println(size + " " + type);
                if (name == null || name.trim().equals("")) {
                    continue;
                }
                //扩展名格式： 
                if (name.lastIndexOf(".") >= 0) {
                    extName = name.substring(name.lastIndexOf("."));
                }
                File file = null;
                do {
                    //生成文件名：
                    name = UUID.randomUUID().toString();
                    file = new File(savePath + name + extName);
                } while (file.exists());
                File saveFile = new File(savePath + name + extName);
                try {
                     item.write(saveFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
       String newNameString=name+extName; 
       return newNameString;
	}
		
	//读取excel中的数据并进行处理
	@RequestMapping("/record/readDataExcefdfdl.do")
	@ResponseBody
	public String ReadXLS( HttpServletRequest request)
	{   
	   try  
		{   	
	      String aPath=systemConfig.getUploadDir();
    	  String Path =aPath+request.getParameter("fname");
    	  int month=Integer.parseInt(request.getParameter("md"));
	       //请注意月份是从0-11   
          Calendar c = Calendar.getInstance();
           //设置为该月，例如08年1月，日期随意
           c.set(2013,month-1,1);
          //输出的excel的路径   
           String filePath=systemConfig.getUploadDir()+"/jdata.xls";
          //创建Excel工作薄     
          Workbook book=Workbook.getWorkbook(new File(Path));
		  Sheet sheet=book.getSheet(0);//获得第一个工作表对象      
          int count= sheet.getRows();
          for (int k = 0; k < count; k++) 
  	       {   	  
  	    	    int ew=Integer.parseInt(sheet.getCell(2,k+1).getContents());  //得到第一列第一行的单元格  	    
  			    String ce=sheet.getCell(3,k+1).getContents();	    
  			    AttendData attendData=new AttendData();
  			   // attendData.setUserId(ew);
  			   // attendData.setPunched(DateUtils.parseDatetime(ce));
  			    attendDataDao.addNew(attendData);
  			    
  	       }
		}
	 catch(IOException e) 
	 {
         e.printStackTrace();
	 }
	 finally
	 	{
		    return "resources/upload/jlv.xls";
	 	}
	}  
	
}