package org.slsale.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.common.HtmlEncode;
import org.slsale.common.JsonDateValueProcessor;
import org.slsale.common.PageSupport;
import org.slsale.pojo.GoodsInfo;
import org.slsale.pojo.LeaveMessage;
import org.slsale.pojo.MessageReply;
import org.slsale.pojo.Reply;
import org.slsale.pojo.User;
import org.slsale.service.leavemessage.LeaveMessageService;
import org.slsale.service.reply.ReplyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * MessageController
 * @author bdqn_hl
 * @date 2014-3-7
 */
@Controller
public class MessageController extends BaseController{
	private Logger logger = Logger.getLogger(MessageController.class);
	
	@Resource
	private LeaveMessageService leaveMessageService;
	@Resource
	private ReplyService replyService;
	
	
	@RequestMapping("/message/messagelist.html")
	public ModelAndView messageList(HttpSession session,Model model,@RequestParam(value="currentpage",required=false)Integer currentpage,@RequestParam(value="createdBy",required=false) String createdBy){
		
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			LeaveMessage leaveMessage = new LeaveMessage();
			//pages 
			PageSupport page = new PageSupport();
		
			try{
				if(null != createdBy && !"".equals(createdBy)){
					leaveMessage.setCreatedBy(createdBy);
				}
				page.setTotalCount(leaveMessageService.count(leaveMessage));
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
				leaveMessage.setStarNum((page.getPage() - 1) * page.getPageSize());
				leaveMessage.setPageSize(page.getPageSize());
				List<LeaveMessage> leaveMessageList = null;
			try {
				leaveMessageList = leaveMessageService.getLeaveMessageList(leaveMessage);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				leaveMessageList = null;
				if(page == null){
					page = new PageSupport();
					page.setItems(null);
				}
			}
				page.setItems(leaveMessageList);
			}else{
				page.setItems(null);
			}
			model.addAllAttributes(baseModel);
			model.addAttribute("page", page);
			model.addAttribute("currentUser", ((User)session.getAttribute(Constants.SESSION_USER)).getLoginCode());
			return new ModelAndView("/message/messagelist");
		}
	}
	
	@RequestMapping(value = "/message/getmessage.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public Object getMessage(@RequestParam(value="id",required=false) String id){
		String cjson = "";
		if(null == id || "".equals(id)){
			return "nodata";
		}else{
			try {
				LeaveMessage _leaveMessage = new LeaveMessage();
				_leaveMessage.setId(Integer.valueOf(id));
				_leaveMessage = leaveMessageService.getLeaveMessage(_leaveMessage);
				
				Reply reply = new Reply();
				reply.setMessageId(Integer.valueOf(id));
				List<Reply> _replyList = replyService.getReplyList(reply);
				
				MessageReply messageReply =  new MessageReply();
				messageReply.setLeaveMessage(_leaveMessage);
				messageReply.setReplyList(_replyList);
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject jo = JSONObject.fromObject(messageReply,jsonConfig);
				cjson = jo.toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "failed";
			}
				return cjson;
		}
		
	}
	
	///member/replymessage.html
	@RequestMapping(value = "/message/replymessage.html",method=RequestMethod.POST)
	public ModelAndView replyMessage(HttpSession session,@ModelAttribute("addReply") Reply addReply){
		if(session.getAttribute(Constants.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			try {
				addReply.setCreateTime(new Date());
				addReply.setCreatedBy(((User)session.getAttribute(Constants.SESSION_USER)).getLoginCode());
				if(null != addReply.getReplyContent() && !addReply.getReplyContent().equals("")){
					addReply.setReplyContent(HtmlEncode.htmlEncode(addReply.getReplyContent()));
				}
				if(replyService.addReply(addReply) > 0){
					LeaveMessage leaveMessage = new LeaveMessage(); 
					leaveMessage.setId(addReply.getMessageId());
					leaveMessage.setState(1);
					leaveMessageService.modifyLeaveMessage(leaveMessage);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ModelAndView("redirect:/message/messagelist.html");
		}
	}
	
	@RequestMapping(value = "/backend/delmessage.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String delMessage(@RequestParam(value="delId",required=false) String delId){
		
		String result= "false" ;
		LeaveMessage delLeaveMessage = new LeaveMessage();
		delLeaveMessage.setId(Integer.valueOf(delId));
		try {
			if(leaveMessageService.deleteLeaveMessage(delLeaveMessage) > 0){
				Reply delReply = new Reply();
				delReply.setMessageId(Integer.valueOf(delId));
				replyService.delete(delReply);
				result = "success";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	//add reply
		@RequestMapping("/message/mymessage.html")
		public ModelAndView myMessage(HttpSession session,Model model,@RequestParam(value="currentpage",required=false)Integer currentpage){
			Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
			if(baseModel == null){
				return new ModelAndView("redirect:/");
			}else{
				User sessionUser =  ((User)session.getAttribute(Constants.SESSION_USER));
				messageList(session,model,currentpage,sessionUser.getLoginCode());
			}
			return new ModelAndView("message/mymessage");
		}
		
		@RequestMapping("/message/addmessage.html")
		public ModelAndView addMessage(@ModelAttribute("addMessage") LeaveMessage message,HttpSession session){
			
			if(session.getAttribute(Constants.SESSION_BASE_MODEL) == null){
				return new ModelAndView("redirect:/");
			}else{
				try {
					User sessionUser =  ((User)session.getAttribute(Constants.SESSION_USER));
					message.setCreatedBy(sessionUser.getLoginCode());
					message.setState(0);
					message.setCreateTime(new Date());
					if(null != message.getMessageContent() && !message.getMessageContent().equals("")){
						message.setMessageContent(HtmlEncode.htmlEncode(message.getMessageContent()));
					}
					leaveMessageService.addLeaveMessage(message);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			return new ModelAndView("redirect:/message/mymessage.html");
		}
		
		
		
		@RequestMapping(value="/message/reply.html", produces = {"text/html;charset=UTF-8"})
		@ResponseBody
		public Object getReply( HttpServletRequest request,HttpSession session,@RequestParam Integer id){
			String result = "";
			if(null == id || "".equals(id)){
				result = "nodata";
			}else{
				try {
					Reply reply = new Reply();
					reply.setMessageId(id);
					List<Reply> rList = replyService.getReplyList(reply);
					if(rList == null){
						result = "noreply";
					}else{
						result = JSONArray.fromObject(rList).toString();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					result = "failed";
				}
			}
			return result;
		}
	
}
