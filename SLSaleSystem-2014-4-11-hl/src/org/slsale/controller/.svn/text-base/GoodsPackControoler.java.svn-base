package org.slsale.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.common.PageSupport;
import org.slsale.common.SQLTools;
import org.slsale.pojo.DataDictionary;
import org.slsale.pojo.GoodsInfo;
import org.slsale.pojo.GoodsPack;
import org.slsale.pojo.GoodsPackAffiliated;
import org.slsale.pojo.User;
import org.slsale.service.dataDictionary.DataDictionaryService;
import org.slsale.service.goodspack.GoodsPackService;
import org.slsale.service.goodspackaffiliated.GoodsPackAffiliatedService;
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
 * GoodsPackControoler
 * @author bdqn_hl
 * @date 2014-3-5
 */
@Controller
public class GoodsPackControoler extends BaseController{
	private Logger logger = Logger.getLogger(GoodsPackControoler.class);
	
	@Resource
	private GoodsPackService goodsPackService;
	@Resource
	private DataDictionaryService dataDictionaryService;
	@Resource
	private GoodsPackAffiliatedService goodsPackAffiliatedService;
	
	/**
	 * 获取列表（分页）
	 * @return
	 */
	@RequestMapping("/backend/goodspacklist.html")
	public ModelAndView goodsPackList(HttpSession session,Model model,
								@RequestParam(value="currentpage",required=false)Integer currentpage ,
								@RequestParam(value="s_goodsPackName",required=false) String s_goodsPackName, 
								@RequestParam(value="s_typeId",required=false) String s_typeId, 
								@RequestParam(value="s_state",required=false) String s_state
								){
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			DataDictionary dataDictionary = new DataDictionary();
			dataDictionary.setTypeCode("PACK_TYPE");
			List<DataDictionary> packTypeList = null;
			try {
				packTypeList = dataDictionaryService.getDataDictionaries(dataDictionary);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			GoodsPack goodsPack = new GoodsPack();
			if(null != s_goodsPackName)
				goodsPack.setGoodsPackName("%"+SQLTools.transfer(s_goodsPackName)+"%");
			if(!StringUtils.isNullOrEmpty(s_state))
				goodsPack.setState(Integer.valueOf(s_state));
			else 
				goodsPack.setState(null);
			if(!StringUtils.isNullOrEmpty(s_typeId))
				goodsPack.setTypeId(Integer.valueOf(s_typeId));
			else 
				goodsPack.setTypeId(null);
			//pages 
			PageSupport page = new PageSupport();
			
			try{
				page.setTotalCount(goodsPackService.count(goodsPack));
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
				goodsPack.setStarNum((page.getPage() - 1) * page.getPageSize());
				goodsPack.setPageSize(page.getPageSize());
				List<GoodsPack> goodsPackList = null;
				try {
					goodsPackList = goodsPackService.getGoodsPackList(goodsPack);
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					goodsPackList = null;
					if(page == null){
						page = new PageSupport();
						page.setItems(null);
					}
				}
				page.setItems(goodsPackList);
			}else{
				page.setItems(null);
			}
			model.addAllAttributes(baseModel);
			model.addAttribute("page", page);
			model.addAttribute("packTypeList", packTypeList);
			model.addAttribute("s_goodsPackName", s_goodsPackName);
			model.addAttribute("s_typeId", s_typeId);
			model.addAttribute("s_state", s_state);
			return new ModelAndView("/backend/goodspacklist");
		}
	}
	
	
	
	@RequestMapping(value = "/backend/addgoodspack.html",method=RequestMethod.GET)
	public ModelAndView addGoodsPack(Model model,HttpSession session){
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			DataDictionary dataDictionary = new DataDictionary();
			dataDictionary.setTypeCode("PACK_TYPE");
			List<DataDictionary> packTypeList = null;
			try {
				packTypeList = dataDictionaryService.getDataDictionaries(dataDictionary);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			model.addAllAttributes(baseModel);
			model.addAttribute("packTypeList", packTypeList);
			return new ModelAndView("/backend/addgoodspack");
		}
	}
	
	@RequestMapping(value = "/backend/modifygoodspack.html",method=RequestMethod.GET)
	public ModelAndView modifyGoodsPack(Model model,HttpSession session,@RequestParam(value="id",required=false) String id){
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			if(null == id || id.equals("")){
				return new ModelAndView("redirect:/backend/goodspacklist.html");
			}
			DataDictionary dataDictionary = new DataDictionary();
			dataDictionary.setTypeCode("PACK_TYPE");
			List<DataDictionary> packTypeList = null;
			List<GoodsPackAffiliated> goodsList = null;
			GoodsPackAffiliated goodsPackAffiliated = new GoodsPackAffiliated();
			goodsPackAffiliated.setGoodsPackId(Integer.valueOf(id));
			try {
				packTypeList = dataDictionaryService.getDataDictionaries(dataDictionary);
				goodsList = goodsPackAffiliatedService.getGoodsPackAffiliatedListById(goodsPackAffiliated);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			GoodsPack goodsPack = new GoodsPack();
			goodsPack.setId(Integer.valueOf(id));
			try {
				goodsPack = goodsPackService.getGoodsPackById(goodsPack);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.addAllAttributes(baseModel);
			model.addAttribute("packTypeList", packTypeList);
			model.addAttribute("goodsList", goodsList);
			model.addAttribute("goodsPack", goodsPack);
			return new ModelAndView("/backend/modifygoodspack");
		}
	}
	
	@RequestMapping(value = "/backend/viewgoodspack.html",method=RequestMethod.GET)
	public ModelAndView viewGoodsPack(Model model,HttpSession session,@RequestParam(value="id",required=false) String id){
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		logger.debug("hanlu============= viewGoodsPack========="+ id);
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			if(null == id || id.equals("")){
				return new ModelAndView("redirect:/backend/goodspacklist.html");
			}
			DataDictionary dataDictionary = new DataDictionary();
			dataDictionary.setTypeCode("PACK_TYPE");
			List<DataDictionary> packTypeList = null;
			List<GoodsPackAffiliated> goodsList = null;
			GoodsPackAffiliated goodsPackAffiliated = new GoodsPackAffiliated();
			goodsPackAffiliated.setGoodsPackId(Integer.valueOf(id));
			try {
				packTypeList = dataDictionaryService.getDataDictionaries(dataDictionary);
				goodsList = goodsPackAffiliatedService.getGoodsPackAffiliatedListById(goodsPackAffiliated);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			GoodsPack goodsPack = new GoodsPack();
			goodsPack.setId(Integer.valueOf(id));
			try {
				goodsPack = goodsPackService.getGoodsPackById(goodsPack);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.addAllAttributes(baseModel);
			model.addAttribute("packTypeList", packTypeList);
			model.addAttribute("goodsPack", goodsPack);
			model.addAttribute("goodsList", goodsList);
			return new ModelAndView("/backend/viewgoodspack");
		}
	}
	
	
	/**
     * 封装将json对象转换为java集合对象
     * 
     * @param <T>
     * @param clazz
     * @param jsons 
     * @return
     */
    private <T> List<T> getJavaCollection(T clazz, String jsons) {
        List<T> objs=null;
        JSONArray jsonArray=(JSONArray)JSONSerializer.toJSON(jsons);
        if(jsonArray.size() > 1){
            objs=new ArrayList<T>();
            List list=(List)JSONSerializer.toJava(jsonArray);
            for(int i = 0; i < list.size()-1; i++){
            	JSONObject jsonObject=JSONObject.fromObject(list.get(i));
            	T obj=(T)JSONObject.toBean(jsonObject, clazz.getClass());
                objs.add(obj);
            }
        }
        return objs;
    }
	
	@RequestMapping(value = "/backend/saveaddgoodspack.html",method=RequestMethod.POST)
	public ModelAndView saveAddGoodsPack(HttpSession session,@ModelAttribute("addGoodsPack") GoodsPack addGoodsPack){
		if(session.getAttribute(Constants.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			try {
				User user = (User)session.getAttribute(Constants.SESSION_USER);
				List<GoodsPackAffiliated> GPAList = null;
				GPAList = getJavaCollection(new GoodsPackAffiliated(),addGoodsPack.getGoodsJson());
				addGoodsPack.setCreateTime(new Date());
				addGoodsPack.setCreatedBy(user.getLoginCode());
				addGoodsPack.setLastUpdateTime(new Date());
				goodsPackService.hl_addGoodsPack(addGoodsPack,GPAList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ModelAndView("redirect:/backend/goodspacklist.html");
		}
	}
	
	@RequestMapping(value = "/backend/savemodifygoodspack.html",method=RequestMethod.POST)
	public ModelAndView saveModifyGoodsPack(HttpSession session,@ModelAttribute("modifyGoodsPack") GoodsPack modifyGoodsPack){
		if(session.getAttribute(Constants.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			try {
				User user = (User)session.getAttribute(Constants.SESSION_USER);
				List<GoodsPackAffiliated> GPAList = null;
				GPAList = getJavaCollection(new GoodsPackAffiliated(),modifyGoodsPack.getGoodsJson());
				modifyGoodsPack.setLastUpdateTime(new Date());
				goodsPackService.hl_modifyGoodsPack(modifyGoodsPack,GPAList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ModelAndView("redirect:/backend/goodspacklist.html");
		}
	}
	
	
	@RequestMapping(value = "/backend/goodspackcodeisexit.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String goodsPackCodeIsExit(@RequestParam(value="goodsPackCode",required=false) String goodsPackCode,
								  @RequestParam(value="id",required=false) String id){
		logger.debug("hanlu goodsPackCodeIsExit goodsPackCode ===================== "+goodsPackCode);
		logger.debug("hanlu goodsPackCodeIsExit id ===================== "+id);
		String result = "failed";
		GoodsPack _goodsPack = new GoodsPack();
		_goodsPack.setGoodsPackCode(goodsPackCode);
		if(!id.equals("-1"))
			_goodsPack.setId(Integer.valueOf(id));
		try {
			if(goodsPackService.goodsPackCodeIsExit(_goodsPack) == 0)
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
	
	@RequestMapping("/backend/modifygoodspackstate.html")
	@ResponseBody
	public Object modifyGoodsPackState(HttpSession session,@RequestParam String state){
		if(null == state || "".equals(state)){
			return "nodata";
		}else{
			JSONObject goodsPackObject = JSONObject.fromObject(state);
			GoodsPack stateObjGoodsPack =  (GoodsPack)JSONObject.toBean(goodsPackObject, GoodsPack.class);
			try {
				goodsPackService.modifyGoodsPack(stateObjGoodsPack);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return "failed";
			}
			return "success";
		}
	}
	
	@RequestMapping(value = "/backend/delgoodspack.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String delGoodsPack(@RequestParam(value="delId",required=false) String delId){
		
		String result= "false" ;
		GoodsPack delGoodsPack = new GoodsPack();
		delGoodsPack.setId(Integer.valueOf(delId));
		try {
			if(goodsPackService.deleteGoodsPack(delGoodsPack) > 0){
				GoodsPackAffiliated goodsPackAffiliated = new GoodsPackAffiliated();
				goodsPackAffiliated.setGoodsPackId(Integer.valueOf(delId));
				goodsPackAffiliatedService.deleteGoodsPackAffiliated(goodsPackAffiliated);
				result = "success";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
}

