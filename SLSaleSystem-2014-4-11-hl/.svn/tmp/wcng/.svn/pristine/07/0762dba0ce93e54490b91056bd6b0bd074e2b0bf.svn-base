package org.slsale.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.common.JsonDateValueProcessor;
import org.slsale.common.PageSupport;
import org.slsale.common.SQLTools;
import org.slsale.pojo.GoodsInfo;
import org.slsale.pojo.User;
import org.slsale.service.goodsinfo.GoodsInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.StringUtils;
/**
 * GoodsInfoController
 * @author bdqn_hl
 * @date 2014-3-5
 */
@Controller
public class GoodsInfoController extends BaseController{
	private Logger logger = Logger.getLogger(GoodsInfoController.class);
	
	@Resource
	private GoodsInfoService goodsInfoService;
	
	
	
	@RequestMapping("/backend/goodslist.html")
	public ModelAndView goodsList(HttpSession session,Model model,
								 @RequestParam(value="s_goodsName",required=false) String s_goodsName){
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			GoodsInfo goodsInfo = new GoodsInfo();
			if(null != s_goodsName)
				goodsInfo.setGoodsName("%"+SQLTools.transfer(s_goodsName)+"%");
			goodsInfo.setState(1);
			goodsInfo.setStarNum(0);
			goodsInfo.setPageSize(10000);
			List<GoodsInfo> goodsInfoList = null;
				try {
					goodsInfoList = goodsInfoService.getGoodsInfoList(goodsInfo);
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					goodsInfoList = null;
				}
			model.addAllAttributes(baseModel);
			model.addAttribute("goodsInfoList", goodsInfoList);
			model.addAttribute("s_goodsName", s_goodsName);
			return new ModelAndView("/backend/goodslist");
		}
	}
	
	
	
	/**
	 * 获取列表（分页）
	 * @return
	 */
	@RequestMapping("/backend/goodsinfolist.html")
	public ModelAndView goodsInfoList(HttpSession session,Model model,
								@RequestParam(value="currentpage",required=false)Integer currentpage ,
								@RequestParam(value="s_goodsName",required=false) String s_goodsName, 
								@RequestParam(value="s_state",required=false) String s_state
								){
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			GoodsInfo goodsInfo = new GoodsInfo();
			if(null != s_goodsName)
				goodsInfo.setGoodsName("%"+SQLTools.transfer(s_goodsName)+"%");
			if(!StringUtils.isNullOrEmpty(s_state))
				goodsInfo.setState(Integer.valueOf(s_state));
			else 
				goodsInfo.setState(null);
			PageSupport page = new PageSupport();
			
			try{
				page.setTotalCount(goodsInfoService.count(goodsInfo));
			}catch (Exception e1) {
				// TODO: handle exception
				e1.printStackTrace();
				page.setTotalCount(0);
			}
			if(page.getTotalCount() > 0){
				if(currentpage != null)
					page.setPage(currentpage);
				if(page.getPage() <= 0)
					page.setPage(1);
				if(page.getPage() > page.getPageCount())
					page.setPage(page.getPageCount());
				goodsInfo.setStarNum((page.getPage() - 1) * page.getPageSize());
				goodsInfo.setPageSize(page.getPageSize());
				List<GoodsInfo> goodsInfoList = null;
				try {
					goodsInfoList = goodsInfoService.getGoodsInfoList(goodsInfo);
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					goodsInfoList = null;
					if(page == null){
						page = new PageSupport();
						page.setItems(null);
					}
				}
				page.setItems(goodsInfoList);
			}else{
				page.setItems(null);
			}
			model.addAllAttributes(baseModel);
			model.addAttribute("page", page);
			model.addAttribute("s_goodsName", s_goodsName);
			model.addAttribute("s_state", s_state);
			return new ModelAndView("/backend/goodsinfolist");
		}
	}
	
	@RequestMapping(value = "/backend/addgoodsinfo.html",method=RequestMethod.POST)
	public ModelAndView addGoodsInfo(HttpSession session,@ModelAttribute("addGoodsInfo") GoodsInfo addGoodsInfo){
		if(session.getAttribute(Constants.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			try {
				addGoodsInfo.setCreateTime(new Date());
				addGoodsInfo.setCreatedBy(((User)session.getAttribute(Constants.SESSION_USER)).getLoginCode());
				addGoodsInfo.setLastUpdateTime(new Date());
				goodsInfoService.addGoodsInfo(addGoodsInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ModelAndView("redirect:/backend/goodsinfolist.html");
		}
	}
	
	@RequestMapping(value = "/backend/modifygoodsinfo.html",method=RequestMethod.POST)
	public ModelAndView modifyGoodsInfo(HttpSession session,@ModelAttribute("modifyGoodsInfo") GoodsInfo modifyGoodsInfo){
		if(session.getAttribute(Constants.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			try {
				modifyGoodsInfo.setLastUpdateTime(new Date());
				goodsInfoService.modifyGoodsInfo(modifyGoodsInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ModelAndView("redirect:/backend/goodsinfolist.html");
		}
	}
	
	@RequestMapping(value = "/backend/delgoodsinfo.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String delGoodsInfo(@RequestParam(value="delId",required=false) String delId){
		
		String result= "false" ;
		GoodsInfo delGoodsInfo = new GoodsInfo();
		delGoodsInfo.setId(Integer.valueOf(delId));
		try {
			if(goodsInfoService.isExitInPack(delGoodsInfo) > 0){
				result = "isused";
			}else{
				if(goodsInfoService.deleteGoodsInfo(delGoodsInfo) > 0)
					result = "success";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value = "/backend/goodsSNisexit.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String loginCodeIsExit(@RequestParam(value="goodsSN",required=false) String goodsSN,
								  @RequestParam(value="id",required=false) String id){
		String result = "failed";
		GoodsInfo _goodsInfo = new GoodsInfo();
		_goodsInfo.setGoodsSN(goodsSN);
		if(!id.equals("-1"))
			_goodsInfo.setId(Integer.valueOf(id));
		try {
			if(goodsInfoService.goodsSNIsExit(_goodsInfo) == 0)
				result = "only";
			else 
				result = "repeat";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return result;
		}
		return result;
	}
	
	@RequestMapping(value = "/backend/getgoodsinfo.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public Object getGoodsInfo(@RequestParam(value="id",required=false) String id){
		String cjson = "";
		if(null == id || "".equals(id)){
			return "nodata";
		}else{
			try {
				GoodsInfo goodsInfo = new GoodsInfo();
				goodsInfo.setId(Integer.valueOf(id));
				goodsInfo = goodsInfoService.getGoodsInfoById(goodsInfo);
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject jo = JSONObject.fromObject(goodsInfo,jsonConfig);
				cjson = jo.toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "failed";
			}
				return cjson;
		}
	}
	
	@RequestMapping("/backend/modifystate.html")
	@ResponseBody
	public Object modifyState(HttpSession session,@RequestParam String state){
		
		if(null == state || "".equals(state)){
			return "nodata";
		}else{
			JSONObject goodsInfoObject = JSONObject.fromObject(state);
			GoodsInfo stateObjGoodsInfo =  (GoodsInfo)JSONObject.toBean(goodsInfoObject, GoodsInfo.class);
			try {
				goodsInfoService.modifyGoodsInfo(stateObjGoodsInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return "failed";
			}
			return "success";
		}
		
	}
	
}
