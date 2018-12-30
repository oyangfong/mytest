package org.slsale.controller;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slsale.common.Constants;
import org.slsale.pojo.DataDictionary;
import org.slsale.pojo.User;
import org.slsale.service.dataDictionary.DataDictionaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * DicDataController
 * @author bdqn_hl
 * @date 2014-3-4
 */
@Controller
public class DicDataController extends BaseController {
	private Logger logger = Logger.getLogger(DicDataController.class);
	@Resource
	private DataDictionaryService dataDictionaryService;
	
	
	@RequestMapping("/backend/delMainDic.html")
	@ResponseBody
	public Object delDic(HttpSession session,@RequestParam String dic){
		
		if(null == dic || "".equals(dic)){
			return "nodata";
		}else{
			JSONObject roleObject = JSONObject.fromObject(dic);
			DataDictionary dataDictionary =  (DataDictionary)JSONObject.toBean(roleObject, DataDictionary.class);
			
			try {
						dataDictionaryService.deleteDataDictionary(dataDictionary);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "failed";
			}
			return "success";
		}
		
	}
	
	
	@RequestMapping(value = "/backend/delDic.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public Object delDic(@RequestParam Integer id){
		
		if(null == id || id<=0){
			return "nodata";
		}else{
			try {
				
				DataDictionary dataDictionary = new DataDictionary();
				dataDictionary.setId(id);
				System.out.println("hanlu======dataDictionary.getId()============ +" + dataDictionary.getId());
				dataDictionaryService.deleteDataDictionary(dataDictionary);
				return "success";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "failed";
			}
		}
	}
	@RequestMapping(value = "/backend/modifyDic.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public Object modifyDic(@RequestParam String dicJson){
		
		if(null == dicJson || "".equals(dicJson)){
			return "nodata";
		}else{
			try {
				
				JSONObject dicObject = JSONObject.fromObject(dicJson);
				DataDictionary dataDictionary =  (DataDictionary)JSONObject.toBean(dicObject, DataDictionary.class);
				if(dataDictionaryService.typeCodeOrValueIdIsExit(dataDictionary) > 0){//valueId重名
			    	return "rename";
			    }else
			    	dataDictionaryService.modifyDataDictionary(dataDictionary);
				return "success";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "failed";
			}
		}
	}
	@RequestMapping(value = "/backend/getJsonDic.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public Object getJsonDic(@RequestParam String tcode){
		
		if(null == tcode || "".equals(tcode)){
			return "nodata";
		}else{
			try {
				
				List<DataDictionary> ddList = null;
				DataDictionary dataDictionary = new DataDictionary();
				dataDictionary.setTypeCode(tcode);
				ddList = dataDictionaryService.getDataDictionaries(dataDictionary);
				if(null != ddList){
					return JSONArray.fromObject(ddList).toString();
				}else{
					return "nodata";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "failed";
			}
		}
	}
	
	@RequestMapping("/backend/modifylDic.html")
	@ResponseBody
	public Object modifylDic(HttpSession session,@RequestParam String olddic,@RequestParam String newdic){
		
		if(null == newdic || "".equals(newdic) || "".equals(olddic) || "".equals(olddic)){
			return "nodata";
		}else{
			JSONObject newDicObject = JSONObject.fromObject(newdic);
			JSONObject oldDicObject = JSONObject.fromObject(olddic);
			DataDictionary newDataDictionary =  (DataDictionary)JSONObject.toBean(newDicObject, DataDictionary.class);
			DataDictionary oldDataDictionary =  (DataDictionary)JSONObject.toBean(oldDicObject, DataDictionary.class);
			
			try {
				
				List<DataDictionary> ddList = null;
				//第一个typeName是新的typeCode 第二个typeCode是旧的typeCode
				DataDictionary _ddDataDictionary  = new DataDictionary();
				_ddDataDictionary.setTypeName(newDataDictionary.getTypeCode());
				_ddDataDictionary.setTypeCode(oldDataDictionary.getTypeCode());
				ddList = dataDictionaryService.getDataDictionariesNotIn(_ddDataDictionary);
				if(ddList !=null && ddList.size() > 0){//有重名
					return "rename";
				}else{
					newDataDictionary.setValueName(oldDataDictionary.getTypeCode());
					dataDictionaryService.modifyDataDictionarys(newDataDictionary);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "failed";
			}
			return "success";
		}
		
	}
	
	
	@RequestMapping("/backend/addDicSub.html")
	@ResponseBody
	public Object addDicSub(HttpSession session,@RequestParam String dic){
		
		if(null == dic || "".equals(dic)){
			return "nodata";
		}else{
			JSONObject roleObject = JSONObject.fromObject(dic);
			DataDictionary dataDictionary =  (DataDictionary)JSONObject.toBean(roleObject, DataDictionary.class);
			try {
				    if(dataDictionaryService.typeCodeOrValueIdIsExit(dataDictionary) > 0){//valueId重名
				    	return "rename";
				    }else{
				    	int valueId = dataDictionaryService.maxValueId(dataDictionary)+1;
				    	dataDictionary.setValueId(valueId);
				    	dataDictionaryService.addDataDictionary(dataDictionary);
				    }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "failed";
			}
			return "success";
		}
		
	}
	
	
	@RequestMapping("/backend/addDic.html")
	@ResponseBody
	public Object addDic(HttpSession session,@RequestParam String dic){
		
		if(null == dic || "".equals(dic)){
			return "nodata";
		}else{
			JSONObject roleObject = JSONObject.fromObject(dic);
			DataDictionary dataDictionary =  (DataDictionary)JSONObject.toBean(roleObject, DataDictionary.class);
			
			try {
				DataDictionary _dataDictionarynew = new DataDictionary();
				_dataDictionarynew.setTypeCode(dataDictionary.getTypeCode());
				
				List<DataDictionary> ddList = dataDictionaryService.getDataDictionaries(_dataDictionarynew);
				//typeCode 不能重复
				if(null != ddList && ddList.size() > 0){
					return "rename";
				}else{
					_dataDictionarynew.setTypeCode(null);
					_dataDictionarynew.setTypeName(dataDictionary.getTypeName());
					ddList = null;
					ddList = dataDictionaryService.getDataDictionaries(_dataDictionarynew);
					if(null != ddList  && ddList.size() > 0){
						return "rename";
					}else
						dataDictionaryService.addDataDictionary(dataDictionary);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "failed";
			}
			return "success";
		}
		
	}
	
	@RequestMapping("/backend/dicmanage.html")
	public ModelAndView dataDic(HttpSession session,Model model){
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			List<DataDictionary> dataList = null;
			try {
				dataList = dataDictionaryService.getDataDictionariesCategory();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				dataList = null;
			}
			baseModel.put("dataList", dataList);
			model.addAllAttributes(baseModel);
			return new ModelAndView("/backend/dicmanage");
		}
	}
	
	@RequestMapping(value = "/backend/typecodeisexit.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String typeCodeIsExit(@RequestParam(value="typeCode",required=false) String typeCode,
								  @RequestParam(value="id",required=false) String id){
		String result = "failed";
		DataDictionary _dataDictionary = new DataDictionary();
		_dataDictionary.setTypeCode(typeCode);
		if(!id.equals("-1"))
			_dataDictionary.setId(Integer.valueOf(id));
		try {
			if(dataDictionaryService.typeCodeOrValueIdIsExit(_dataDictionary) == 0)
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
	
	
	
}
