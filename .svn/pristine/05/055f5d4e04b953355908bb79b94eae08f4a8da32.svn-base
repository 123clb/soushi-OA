/**
 * 
 */
package com.kaoshidian.oa.log.action;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.kaoshidian.oa.base.page.PageContext;
import com.kaoshidian.oa.base.util.BeansWrapperUtil;
import com.kaoshidian.oa.log.entity.BizLog;
import com.kaoshidian.oa.log.mng.LogMgr;
import com.kaoshidian.oa.util.ActionExtend;
import com.kaoshidian.oa.util.LogEntityEnum;
import com.kaoshidian.oa.util.LogOperationEnum;
import com.kaoshidian.oa.util.LogStatusEnum;

/**
 * @author <p>Innate Solitary 于 2012-8-25 下午5:00:19</p>
 *
 */
@Controller
public class LogAction extends ActionExtend {
	@Autowired
	private LogMgr logMng;

	@RequestMapping("/log/list.do")
	public String list(ModelMap model, HttpServletRequest request, Integer pageNum, Integer numPerPage) throws Exception {
		PageContext<BizLog> pc = logMng.findLog(model, request, pageNum, numPerPage);
		model.addAttribute("pageCtx", pc);
		model.addAttribute("entityEnum", BeansWrapperUtil.wrapEnum(LogEntityEnum.class));
		model.addAttribute("operationEnum", BeansWrapperUtil.wrapEnum(LogOperationEnum.class));
		model.addAttribute("statusEnum", BeansWrapperUtil.wrapEnum(LogStatusEnum.class));
		return "/sys/log/loglist";
	}
	

}
