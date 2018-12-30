package org.slsale.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.common.RedisAPI;
import org.slsale.pojo.Authority;
import org.slsale.pojo.Function;
import org.slsale.pojo.Menu;
import org.slsale.pojo.Role;
import org.slsale.pojo.RoleFunctions;
import org.slsale.pojo.User;
import org.slsale.service.authority.AuthorityService;
import org.slsale.service.function.FunctionService;
import org.slsale.service.role.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * AuthorityController
 * @author bdqn_hl
 * @date 2014-3-3
 */
@Controller
public class AuthorityController extends BaseController {
	private Logger logger = Logger.getLogger(AuthorityController.class);
	@Resource
	private RoleService roleService;
	@Resource
	private FunctionService functionService;
	@Resource
	private AuthorityService authorityService;
	@Resource
	private RedisAPI redisAPI;
	@Resource
	private LoginController loginController;
	
	@RequestMapping(value = "/backend/modifyAuthority.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public Object modifyAuthority(HttpSession session,@RequestParam String ids){
		
		String resultString = "nodata";
		try {
			if(null != ids){
				String[] idsArrayStrings = StringUtils.split(ids, "-");
				if(idsArrayStrings.length > 0){
					User user = (User)session.getAttribute(Constants.SESSION_USER);
					authorityService.hl_addAuthority(idsArrayStrings,user.getLoginCode());
					List<Menu> mList = null;
					mList = loginController.getFuncByCurrentUser(Integer.valueOf(idsArrayStrings[0]));
					JSONArray jsonArray = JSONArray.fromObject(mList);
					redisAPI.set("menuList"+idsArrayStrings[0], jsonArray.toString());
					
					//get all role url list to redis
					Authority authority = new Authority();
					authority.setRoleId(Integer.valueOf(idsArrayStrings[0]));
					List<Function> functionList = functionService.getFunctionListByRoId(authority);
					
					if(null != functionList || functionList.size() >= 0){
						StringBuffer sBuffer = new StringBuffer();
						for(Function f:functionList){
							sBuffer.append(f.getFuncUrl());
						}
						redisAPI.set("Role"+idsArrayStrings[0]+"UrlList", sBuffer.toString());
					}
					resultString = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}
	
	@RequestMapping(value = "/backend/getAuthorityDefault.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public Object getAuthorityDefault(@RequestParam Integer rid,@RequestParam Integer fid){
		String resultString = "nodata";
		try {
			Authority authority = new Authority();
			authority.setRoleId(rid);
			authority.setFunctionId(fid);
			if(authorityService.getAuthority(authority) != null){
				resultString =  "success";
			}
		} catch (Exception e) {
		}
		return resultString;
	}
	
	@RequestMapping(value = "/backend/functions.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public Object functions(){
		String resultString = "nodata";
		Function function = new Function();
		try {
			function.setId(0);
			List<Function> fList = functionService.getSubFuncList(function);
			List<RoleFunctions> rList = new ArrayList<RoleFunctions>();
			if(null != fList){
				
				for(Function func : fList){
					RoleFunctions rFunctions = new RoleFunctions();
					rFunctions.setMainFunction(func);
					rFunctions.setSubFunctions(functionService.getSubFuncList(func));
					rList.add(rFunctions);
				}
				resultString = JSONArray.fromObject(rList).toString();
			}
		} catch (Exception e) {
		}
		return resultString;
	}
	
	
	
	@RequestMapping("/backend/authoritymanage.html")
	public ModelAndView authorityManage(HttpSession session,Model model){
		
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			List<Role> roleList = null;
			try {
				roleList = roleService.getRoleIdAndNameList();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				roleList = null;
			}
			model.addAllAttributes(baseModel);
			model.addAttribute(roleList);
			return new ModelAndView("/backend/authoritymanage");
		}
	}
	
}
