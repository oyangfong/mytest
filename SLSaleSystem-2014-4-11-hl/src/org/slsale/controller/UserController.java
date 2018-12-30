package org.slsale.controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.slsale.common.SQLTools;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.common.JsonDateValueProcessor;
import org.slsale.common.PageSupport;
import org.slsale.pojo.DataDictionary;
import org.slsale.pojo.Role;
import org.slsale.pojo.User;
import org.slsale.service.dataDictionary.DataDictionaryService;
import org.slsale.service.role.RoleService;
import org.slsale.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.StringUtils;

/**
 * UserController
 * @author bdqn_hl
 * @date 2014-2-21
 */

@Controller
public class UserController extends BaseController{
	
	private Logger logger = Logger.getLogger(UserController.class);
	
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	@Resource
	private DataDictionaryService dataDictionaryService;
	
	/**
	 * 获取用户列表（分页）
	 * @return
	 */
	@RequestMapping("/backend/userlist.html")
	public ModelAndView userList(HttpSession session,Model model,
								@RequestParam(value="currentpage",required=false)Integer currentpage ,
								@RequestParam(value="s_referCode",required=false) String s_referCode, 
								@RequestParam(value="s_loginCode",required=false) String s_loginCode,
								@RequestParam(value="s_roleId",required=false) String s_roleId,
								@RequestParam(value="s_isStart",required=false) String s_isStart
								){
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			DataDictionary dataDictionary = new DataDictionary();
			dataDictionary.setTypeCode("CARD_TYPE");
			List<Role> roleList = null;
			List<DataDictionary> cardTypeList = null;
			try {
				roleList = roleService.getRoleIdAndNameList();
				cardTypeList = dataDictionaryService.getDataDictionaries(dataDictionary);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			User user = new User();
			if(null != s_loginCode)
				user.setLoginCode("%"+SQLTools.transfer(s_loginCode)+"%");
			if(null != s_referCode)
				user.setReferCode("%"+SQLTools.transfer(s_referCode)+"%");
			if(!StringUtils.isNullOrEmpty(s_isStart))
				user.setIsStart(Integer.valueOf(s_isStart));
			else 
				user.setIsStart(null);
			if(!StringUtils.isNullOrEmpty(s_roleId))
				user.setRoleId(Integer.valueOf(s_roleId));
			else
				user.setRoleId(null);
			
			//pages 
			PageSupport page = new PageSupport();
			
			try{
				page.setTotalCount(userService.count(user));
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
				user.setStarNum((page.getPage() - 1) * page.getPageSize());
				user.setPageSize(page.getPageSize());
				List<User> userList = null;
				try {
					userList = userService.getUserList(user);
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					userList = null;
					if(page == null){
						page = new PageSupport();
						page.setItems(null);
					}
				}
				page.setItems(userList);
			}else{
				page.setItems(null);
			}
			model.addAllAttributes(baseModel);
			model.addAttribute("page", page);
			model.addAttribute("s_loginCode", s_loginCode);
			model.addAttribute("s_referCode", s_referCode);
			model.addAttribute("s_isStart", s_isStart);
			model.addAttribute("s_roleId", s_roleId);
			model.addAttribute("roleList",roleList);
			model.addAttribute("cardTypeList",cardTypeList);
			return new ModelAndView("/backend/userlist");
		}
	}
	
	//modify password start
	
	@RequestMapping("/backend/modifyPwd.html")
	@ResponseBody
	public Object modifyPwd(HttpSession session,@RequestParam String userJson){
		User sessionUser =  ((User)session.getAttribute(Constants.SESSION_USER));
		if(null == userJson || "".equals(userJson)){
			return "nodata";
		}else{
			JSONObject roleObject = JSONObject.fromObject(userJson);
			User user =  (User)JSONObject.toBean(roleObject, User.class);
			user.setId(sessionUser.getId());
			user.setLoginCode(sessionUser.getLoginCode());
			try {
				if(userService.getLoginUser(user) != null){
					user.setPassword(user.getPassword2());
					user.setPassword2(null);
					userService.modifyUser(user);
				}else{
					return "oldpwdwrong";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return "failed";
			}
		}
		return "success";
	}
	//modify password end
	
	@RequestMapping("/member/modifypersonalpwd.html")
	public ModelAndView modifyPersonalPwd(HttpSession session,Model model){
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			model.addAllAttributes(baseModel);
			return new ModelAndView("/member/modifypersonalpwd");
		}
	}
	
	@RequestMapping("/member/savesecondpwd.html")
	@ResponseBody
	public Object saveSecondPwd(HttpSession session,@RequestParam String userJson){
		User sessionUser =  ((User)session.getAttribute(Constants.SESSION_USER));
		if(null == userJson || "".equals(userJson)){
			return "nodata";
		}else{
			JSONObject userObject = JSONObject.fromObject(userJson);
			User user =  (User)JSONObject.toBean(userObject, User.class);
			User _user = new User();
			_user.setId(sessionUser.getId());
			try {
				if(userService.getUserById(_user).getPassword2().equals(user.getPassword())){
					_user.setPassword2(user.getPassword2());
					_user.setPassword(null);
					userService.modifyUser(_user);
				}else{
					return "oldpwdwrong";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return "failed";
			}
		}
		return "success";
	}
	
	
	
	@RequestMapping(value = "/backend/adduser.html",method=RequestMethod.POST)
	public ModelAndView addUser(HttpSession session,@ModelAttribute("addUser") User addUser){
		if(session.getAttribute(Constants.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			try {
				String idCard = addUser.getIdCard();
				String ps = idCard.substring(idCard.length()-6); 
				addUser.setPassword(ps);
				addUser.setPassword2(ps);
				addUser.setCreateTime(new Date());
				addUser.setReferId(((User)session.getAttribute(Constants.SESSION_USER)).getId());
				addUser.setReferCode(((User)session.getAttribute(Constants.SESSION_USER)).getLoginCode());
				addUser.setLastUpdateTime(new Date());
				
				userService.addUser(addUser);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ModelAndView("redirect:/backend/userlist.html");
		}
	}
	
	@RequestMapping(value = "/backend/modifyuser.html",method=RequestMethod.POST)
	public ModelAndView modifyUser(HttpSession session,@ModelAttribute("modifyUser") User modifyUser){
		if(session.getAttribute(Constants.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			try {
				modifyUser.setLastUpdateTime(new Date());
				userService.modifyUser(modifyUser);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ModelAndView("redirect:/backend/userlist.html");
		}
	}
	
	
	@RequestMapping(value = "/member/modifymember.html",method=RequestMethod.POST)
	public ModelAndView modifyMember(HttpSession session,@ModelAttribute("modifyUser") User modifyUser){
		if(session.getAttribute(Constants.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			try {
				modifyUser.setLastUpdateTime(new Date());
				userService.modifyUser(modifyUser);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ModelAndView("redirect:/member/memberlist.html");
		}
	}
	
	/**
	 * 保存修改后的个人信息
	 * @param session
	 * @param modifyUser
	 * @return
	 */
	@RequestMapping(value = "/member/savepersonalinfo.html",method=RequestMethod.POST)
	public ModelAndView savePersonalInfo(HttpSession session,@ModelAttribute("modifyUser") User modifyUser){
		if(session.getAttribute(Constants.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			try {
				modifyUser.setLastUpdateTime(new Date());
				userService.modifyUser(modifyUser);
				session.setAttribute(Constants.SESSION_USER,userService.getUserById(modifyUser));
				this.setCurrentUser((User)session.getAttribute(Constants.SESSION_USER));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ModelAndView("redirect:/member/modifypersonalinfo.html");
		}
	}
	
	@RequestMapping(value = "/backend/loadUserTypeList.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public Object loadUserTypeList(@RequestParam(value="s_roleId",required=false) String s_roleId){
		String cjson = "";
		try {
			DataDictionary dataDictionary = new DataDictionary();
			dataDictionary.setTypeCode("USER_TYPE");
			List<DataDictionary> userTypeList = dataDictionaryService.getDataDictionaries(dataDictionary);
			JSONArray jo = JSONArray.fromObject(userTypeList);
			cjson = jo.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return cjson;
	}
	
	@RequestMapping(value = "/backend/upload.html", produces = {"text/html;charset=UTF-8"})  
	@ResponseBody
    public Object upload(@RequestParam(value = "a_fileInputID", required = false) MultipartFile cardFile, 
    		             @RequestParam(value = "a_fileInputBank", required = false) MultipartFile bankFile, 
    		             @RequestParam(value = "m_fileInputID", required = false) MultipartFile mCardFile, 
    		             @RequestParam(value = "m_fileInputBank", required = false) MultipartFile mBankFile, 
    		             @RequestParam(value = "loginCode", required = false) String loginCode, 
    					 HttpServletRequest request,HttpSession session) {  
  
        logger.debug("开始....");
        String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");  
        logger.debug("hanlu path======== " + path);
        List<DataDictionary> list = null;
        DataDictionary dataDictionary = new DataDictionary();
        dataDictionary.setTypeCode("PERSONALFILE_SIZE");
        try {
			list = dataDictionaryService.getDataDictionaries(dataDictionary);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        int filesize = 50000;
        if(null != list){
        	 if(list.size() == 1){
             	filesize = Integer.valueOf(list.get(0).getValueName());
             }
        }
       
        if(cardFile != null){
        	String oldFileName = cardFile.getOriginalFilename();
            String prefix=FilenameUtils.getExtension(oldFileName);     
            logger.debug("hanlu bankFile prefix======== " + prefix);
            if(cardFile.getSize() >  filesize){//上传大小不得超过 50k
            	return "1";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
            		|| prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式不正确
            	String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_IDcard.jpg";  
                logger.debug("hanlu new fileName======== " + cardFile.getName());
                File targetFile = new File(path, fileName);  
                if(!targetFile.exists()){  
                    targetFile.mkdirs();  
                }  
                //保存  
                try {  
                	cardFile.transferTo(targetFile);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
                String url = request.getContextPath()+"/statics/uploadfiles/"+fileName;
                return url;  
            }else{
            	return "2";
            }
        }
        if(bankFile != null){
        	String oldFileName = bankFile.getOriginalFilename();
            logger.debug("hanlu bankFile oldFileName======== " + oldFileName);
            String prefix=FilenameUtils.getExtension(oldFileName);     
            if(bankFile.getSize() >  filesize){//上传大小不得超过 50k
            	return "1";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
            		|| prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")){
            	String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_bank.jpg";  
                logger.debug("hanlu bankFile new fileName======== " + bankFile.getName());
                File targetFile = new File(path, fileName);  
                if(!targetFile.exists()){  
                    targetFile.mkdirs();  
                }  
                //保存  
                try {  
                	bankFile.transferTo(targetFile);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
                String url = request.getContextPath()+"/statics/uploadfiles/"+fileName;
                return url;  
            }else{//上传图片格式不正确
            	return "2";
            }
        }
        if(mCardFile != null){
        	String oldFileName = mCardFile.getOriginalFilename();
            String prefix=FilenameUtils.getExtension(oldFileName);     
            if(mCardFile.getSize() >  filesize){//上传大小不得超过 50k
            	return "1";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
            		|| prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式不正确
            	String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_IDcard.jpg";  
                logger.debug("hanlu new fileName======== " + mCardFile.getName());
                File targetFile = new File(path, fileName);  
                if(!targetFile.exists()){  
                    targetFile.mkdirs();  
                }  
                //保存  
                try {  
                	mCardFile.transferTo(targetFile);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
                String url = request.getContextPath()+"/statics/uploadfiles/"+fileName;
                return url;  
            }else{
            	return "2";
            }
        }
        if(mBankFile != null){
        	String oldFileName = mBankFile.getOriginalFilename();
            logger.debug("hanlu bankFile oldFileName======== " + oldFileName);
            String prefix=FilenameUtils.getExtension(oldFileName);     
            if(mBankFile.getSize() >  filesize){//上传大小不得超过 50k
            	return "1";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
            		|| prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式不正确
            	String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_bank.jpg";  
                logger.debug("hanlu bankFile new fileName======== " + mBankFile.getName());
                File targetFile = new File(path, fileName);  
                if(!targetFile.exists()){  
                    targetFile.mkdirs();  
                }  
                //保存  
                try {  
                	mBankFile.transferTo(targetFile);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
                String url = request.getContextPath()+"/statics/uploadfiles/"+fileName;
                return url;  
            }else{
            	return "2";
            }
        }
        return null;
    }  
	
	@RequestMapping(value = "/backend/delpic.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String delPic(@RequestParam(value="picpath",required=false) String picpath,
						 @RequestParam(value="id",required=false) String id,
						HttpServletRequest request,HttpSession session){
		String result= "failed" ;
		if(picpath == null || picpath.equals("")){
			result = "success"; 
		}else{
			String[] paths = picpath.split("/");
			String path = request.getSession().getServletContext().getRealPath(paths[1]+File.separator+paths[2]+File.separator+paths[3]);  
			File file = new File(path);
		    
		    if(file.exists())
		     if(file.delete()){
		    	 if(id.equals("0")){//添加用户时，删除上传的图片
		    		 result = "success";
		    	 }else{//修改用户时，删除上传的图片
		    		 User _user = new User();
			    	 _user.setId(Integer.valueOf(id));
			    	 if(picpath.indexOf("_IDcard.jpg") != -1)
			    		 _user.setIdCardPicPath(picpath);
			    	 else if(picpath.indexOf("_bank.jpg") != -1)
			    		 _user.setBankPicPath(picpath);
			    	 try {
						if(userService.delUserPic(_user) > 0){
							logger.debug("hanlu modify----userService.delUserPic======== " );
							result = "success";
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return result;
					}
		    	 }
		    }
		}
		return result;
	}

	@RequestMapping(value = "/backend/deluser.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String delUser(@RequestParam(value="delId",required=false) String delId,
						  @RequestParam(value="delIdCardPicPath",required=false) String delIdCardPicPath,			  
						  @RequestParam(value="delBankPicPath",required=false) String delBankPicPath,			  
						  @RequestParam(value="delUserType",required=false) String delUserType,			  
						  HttpServletRequest request,HttpSession session){
		
		String result= "false" ;
		User delUser = new User();
		delUser.setId(Integer.valueOf(delId));
		try {
			//若被删除的用户为：普通消费会员、VIP会员、加盟店  则不可被删除
			if(delUserType.equals("2") || delUserType.equals("3") || delUserType.equals("4")){
				result = "noallow";
			}else{
				if(this.delPic(delIdCardPicPath,delId,request,session).equals("success") && this.delPic(delBankPicPath,delId,request,session).equals("success")){
					if(userService.deleteUser(delUser) > 0)
						result = "success";
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value = "/backend/logincodeisexit.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String loginCodeIsExit(@RequestParam(value="loginCode",required=false) String loginCode,
								  @RequestParam(value="id",required=false) String id){
		logger.debug("hanlu loginCodeIsExit loginCode===================== "+loginCode);
		logger.debug("hanlu loginCodeIsExit id===================== "+id);
		String result = "failed";
		User _user = new User();
		_user.setLoginCode(loginCode);
		if(!id.equals("-1"))
			_user.setId(Integer.valueOf(id));
		try {
			if(userService.loginCodeIsExit(_user) == 0)
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
	/**
	 * 获取用户列表（分页）
	 * @return
	 */
	@RequestMapping("/member/memberlist.html")
	public ModelAndView memberList(HttpSession session,Model model,
			@RequestParam(value="currentpage",required=false)Integer currentpage ,
			@RequestParam(value="s_userName",required=false) String s_userName, 
			@RequestParam(value="s_loginCode",required=false) String s_loginCode){
		
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			DataDictionary dataDictionary = new DataDictionary();
			List<DataDictionary> cardTypeList = null;
			List<DataDictionary> userTypeList = null;
			try {
				dataDictionary.setTypeCode("CARD_TYPE");
				cardTypeList = dataDictionaryService.getDataDictionaries(dataDictionary);
				dataDictionary.setTypeCode("USER_TYPE");
				userTypeList = dataDictionaryService.getDataDictionaries(dataDictionary);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			User user = new User();
			if(null != s_loginCode)
				user.setLoginCode("%"+SQLTools.transfer(s_loginCode)+"%");
			if(null != s_userName)
				user.setUserName("%"+SQLTools.transfer(s_userName)+"%");
			user.setReferId(((User)session.getAttribute(Constants.SESSION_USER)).getId());
			user.setUserType("1");
			//pages 
			PageSupport page = new PageSupport();
			try{
				page.setTotalCount(userService.count(user));
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
				user.setStarNum((page.getPage() - 1) * page.getPageSize());
				user.setPageSize(page.getPageSize());
				List<User> userList = null;
				try {
					userList = userService.getUserList(user);
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					userList = null;
					if(page == null){
						page = new PageSupport();
						page.setItems(null);
					}
				}
				page.setItems(userList);
			}else{
				page.setItems(null);
			}
			model.addAllAttributes(baseModel);
			model.addAttribute("page", page);
			model.addAttribute("s_loginCode", s_loginCode);
			model.addAttribute("s_userName", s_userName);
			model.addAttribute("cardTypeList",cardTypeList);
			model.addAttribute("userTypeList",userTypeList);
			return new ModelAndView("/member/memberlist");
		}
	}
	
	@RequestMapping("/member/modifypersonalinfo.html")
	public ModelAndView modifyPersonalInfo(HttpSession session,Model model){
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			User currentUser = (User)session.getAttribute(Constants.SESSION_USER);

			DataDictionary dataDictionary = new DataDictionary();
			List<DataDictionary> cardTypeList = null;
			try {
				dataDictionary.setTypeCode("CARD_TYPE");
				cardTypeList = dataDictionaryService.getDataDictionaries(dataDictionary);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			model.addAllAttributes(baseModel);
			model.addAttribute("currentUser", currentUser);
			model.addAttribute("cardTypeList",cardTypeList);
			return new ModelAndView("/member/modifypersonalinfo");
		}
	}
	
	@RequestMapping(value = "/backend/getuser.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public Object getUser(@RequestParam(value="id",required=false) String id){
		String cjson = "";
		if(null == id || "".equals(id)){
			return "nodata";
		}else{
			try {
				User user = new User();
				user.setId(Integer.valueOf(id));
				user = userService.getUserById(user);
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject jo = JSONObject.fromObject(user,jsonConfig);
				cjson = jo.toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "failed";
			}
				return cjson;
		}
		
	}
	
	@RequestMapping("/member/registrule.html")
	public ModelAndView registRule(HttpSession session,Model model){
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			model.addAllAttributes(baseModel);
			return new ModelAndView("/member/registrule");
		}
	}
	
	@RequestMapping("/member/registmember.html")
	public ModelAndView registMember(HttpSession session,Model model){
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{

			DataDictionary dataDictionary = new DataDictionary();
			List<DataDictionary> cardTypeList = null;
			try {
				dataDictionary.setTypeCode("CARD_TYPE");
				cardTypeList = dataDictionaryService.getDataDictionaries(dataDictionary);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			model.addAllAttributes(baseModel);
			model.addAttribute("cardTypeList",cardTypeList);
			return new ModelAndView("/member/registmember");
		}
	}
	
	
	
	@RequestMapping(value = "/member/saveregistmember.html",method=RequestMethod.POST)
	public ModelAndView saveRegistMember(HttpSession session,@ModelAttribute("registMember") User registMember){
		if(session.getAttribute(Constants.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			try {
				registMember.setRoleId(2);
				registMember.setRoleName("会员");
				registMember.setUserType("1");
				registMember.setUserTypeName("注册会员");
				String idCard = registMember.getIdCard();
				String ps = idCard.substring(idCard.length()-6); 
				registMember.setPassword(ps);
				registMember.setPassword2(ps);
				registMember.setCreateTime(new Date());
				registMember.setReferId(((User)session.getAttribute(Constants.SESSION_USER)).getId());
				registMember.setReferCode(((User)session.getAttribute(Constants.SESSION_USER)).getLoginCode());
				registMember.setLastUpdateTime(new Date());
				
				userService.addUser(registMember);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ModelAndView("redirect:/member/registmember.html");
			}
			return new ModelAndView("redirect:/member/registok.html?loginCode="+registMember.getLoginCode());
		}
	}
	
	@RequestMapping("/member/registok.html")
	public ModelAndView registOk(HttpSession session,Model model,
								@RequestParam(value="loginCode",required=false)String loginCode){
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			User registUser = new User();
			registUser.setLoginCode(loginCode);
			User _user = new User();
			try {
				_user = userService.getUserListBySearch(registUser).get(0);
				registUser.setAccountHolder(_user.getAccountHolder());
				registUser.setBankAccount(_user.getBankAccount());
				registUser.setBankName(_user.getBankName());
				registUser.setIdCard(_user.getIdCard());
				registUser.setLoginCode(loginCode);
				registUser.setUserName(_user.getUserName());
				registUser.setMobile(_user.getMobile());
				model.addAllAttributes(baseModel);
				model.addAttribute("registUser",registUser);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ModelAndView("/member/registok");
		}
	}
	

	 
}