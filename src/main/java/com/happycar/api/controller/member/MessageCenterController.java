package com.happycar.api.controller.member;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.annotation.Authentication;
import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.MessageCenterDao;
import com.happycar.api.model.HcMember;
import com.happycar.api.model.HcMessageCenter;
import com.happycar.api.utils.BeanUtil;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.vo.HcMessageCenterVO;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;


@Api(value = "消息中心管理")
@RestController
@RequestMapping("/api/member/messageCenter")	
@Authentication
public class MessageCenterController extends BaseController{
	
	private Logger logger = Logger.getLogger(MessageCenterController.class);
	@Resource
	private MessageCenterDao messageCenterDao;
	
	@ApiOperation(value = "消息列表", httpMethod = "GET", notes = "消息列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "accessToken", value = "token", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	@Transactional
	public ResponseModel list(
			HttpServletRequest request,
			@ApiIgnore Pageable pageable){
		ResponseModel model = new ResponseModel();
		HcMember member = getLoginMember(request);
		Page<HcMessageCenter> page = messageCenterDao.findByMemberIdAndIsDeleted(member.getId(), 0,pageable);
		model.addAttribute("page", page);
		MessageUtil.success("获取成功", model);
		return model;
	}
	
	@ApiOperation(value = "未读消息列表", httpMethod = "GET", notes = "未读消息列表")
	@RequestMapping(value = "/unreadMessage", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "accessToken", value = "token", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	@Transactional
	public ResponseModel unreadMessage(
			HttpServletRequest request,
			@ApiIgnore Pageable pageable){
		ResponseModel model = new ResponseModel();
		HcMember member = getLoginMember(request);
		List<HcMessageCenter> unreadMessages = messageCenterDao.findByMemberIdAndIsReadAndIsDeleted(member.getId(), 0, 0);
		messageCenterDao.updateUnread(member.getId());
		List<HcMessageCenterVO> unreadMessageVOs = new ArrayList<>();  
		for (HcMessageCenter hcMessageCenter : unreadMessages) {
			HcMessageCenterVO messageCenterVO = new HcMessageCenterVO();
			BeanUtil.copyProperties(hcMessageCenter, messageCenterVO);
			if(hcMessageCenter.getContent().length()>50){
				messageCenterVO.setShortContent(hcMessageCenter.getContent().substring(0, 50)+"...");
			}
			messageCenterVO.setShortContent(hcMessageCenter.getContent());
		}
		model.addAttribute("unreadMessages", unreadMessages);
		MessageUtil.success("获取成功", model);
		return model;
	}
	
	@ApiOperation(value = "未读消息数", httpMethod = "GET", notes = "未读消息数")
	@RequestMapping(value = "/unreadMessageNum", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "accessToken", value = "token", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel isUnreadMessage(
			HttpServletRequest request,
			@ApiIgnore Pageable pageable){
		ResponseModel model = new ResponseModel();
		HcMember member = getLoginMember(request);
		Integer unreadMessageNum = messageCenterDao.isUnreadMessage(member.getId());
		if(unreadMessageNum==null) unreadMessageNum = 0;
		model.addAttribute("unreadMessageNum", unreadMessageNum);
		MessageUtil.success("获取成功", model);
		return model;
	}


}
