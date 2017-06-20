package com.happycar.api.controller.member;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.annotation.Authentication;
import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.MemberDao;
import com.happycar.api.dao.MemberRelationDao;
import com.happycar.api.model.HcMember;
import com.happycar.api.model.HcMemberRelation;
import com.happycar.api.service.MemberRelationService;
import com.happycar.api.utils.BeanUtil;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.utils.StringUtil;
import com.happycar.api.vo.HcMemberRelationVO;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "我的成员管理")
@RestController
@RequestMapping("/api/member")	
@Authentication
public class MemberRelationController extends BaseController{
	
	private Logger logger = Logger.getLogger(MemberRelationController.class);
	@Resource
	private MemberDao memberDao;
	@Resource
	private MemberRelationDao relationDao;
	@Resource
	private MemberRelationService relationService;
	
	@ApiOperation(value = "我的成员", httpMethod = "GET", notes = "我的成员")
	@RequestMapping(value = "/relation", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel relation(HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		HcMember member = getLoginMember(request);
		List<HcMemberRelation> oneLevelRelations = relationDao.findByPidAndLevel(member.getId(), 1);
		List<HcMemberRelation> twoLevelRelations = relationDao.findByPidAndLevel(member.getId(), 2);
		List<HcMemberRelation> threeLevelRelations = relationDao.findByPidAndLevel(member.getId(), 3);
		List<HcMemberRelationVO> oneLevelRelationsVO = new ArrayList<>();
		List<HcMemberRelationVO> twoLevelRelationsVO = new ArrayList<>();
		List<HcMemberRelationVO> threeLevelRelationsVO = new ArrayList<>();
		for (HcMemberRelation hcMemberRelation : oneLevelRelations) {
			HcMemberRelationVO relationVO = new HcMemberRelationVO();
			BeanUtil.copyProperties(hcMemberRelation, relationVO);
			relationVO.setChildName(hideName(hcMemberRelation.getChildMember().getName()));
			relationVO.setChildPic(hcMemberRelation.getChildMember().getPic());
			relationVO.setParentName(hideName(hcMemberRelation.getParentMember().getName()));
			relationVO.setParentPic(hcMemberRelation.getParentMember().getPic());
			oneLevelRelationsVO.add(relationVO);
		}
		for (HcMemberRelation hcMemberRelation : twoLevelRelations) {
			HcMemberRelationVO relationVO = new HcMemberRelationVO();
			BeanUtil.copyProperties(hcMemberRelation, relationVO);
			relationVO.setChildName(hideName(hcMemberRelation.getChildMember().getName()));
			relationVO.setChildPic(hcMemberRelation.getChildMember().getPic());
			relationVO.setParentName(hideName(hcMemberRelation.getParentMember().getName()));
			relationVO.setParentPic(hcMemberRelation.getParentMember().getPic());
			twoLevelRelationsVO.add(relationVO);
		}
		for (HcMemberRelation hcMemberRelation : threeLevelRelations) {
			HcMemberRelationVO relationVO = new HcMemberRelationVO();
			BeanUtil.copyProperties(hcMemberRelation, relationVO);
			relationVO.setChildName(hideName(hcMemberRelation.getChildMember().getName()));
			relationVO.setChildPic(hcMemberRelation.getChildMember().getPic());
			relationVO.setParentName(hideName(hcMemberRelation.getParentMember().getName()));
			relationVO.setParentPic(hcMemberRelation.getParentMember().getPic());
			threeLevelRelationsVO.add(relationVO);
		}
		model.addAttribute("oneLevelRelations", oneLevelRelationsVO);
		model.addAttribute("twoLevelRelations", twoLevelRelationsVO);
		model.addAttribute("threeLevelRelations", threeLevelRelationsVO);
		MessageUtil.success("操作成功", model);
		return model;
	}
	
	private String hideName(String name){
		if(StringUtil.isNull(name)){
			return "";
		}
		if(name.length()==1){
			return name;
		}
		if(name.length()==2){
			return name.substring(0, 1)+"*";
		}
		if(name.length()>2){
			return name.substring(0, 1)+"*"+name.substring(name.length()-1, name.length());
		}
		return name;
				 
	}
}
