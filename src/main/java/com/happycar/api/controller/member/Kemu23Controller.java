package com.happycar.api.controller.member;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.Kemu23CommentDao;
import com.happycar.api.dao.Kemu23Dao;
import com.happycar.api.dao.MemberDao;
import com.happycar.api.model.HcKemu23;
import com.happycar.api.model.HcKemu23Comment;
import com.happycar.api.model.HcMember;
import com.happycar.api.utils.BeanUtil;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.vo.HcKemu23CommentVO;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;


@Api(value = "科目二三管理")
@RestController
@RequestMapping("/api/member/kemu23")	
public class Kemu23Controller extends BaseController{
	
	private Logger logger = Logger.getLogger(Kemu23Controller.class);
	@Resource
	private Kemu23Dao kemu23Dao;
	@Resource
	private Kemu23CommentDao comment1Dao;
	@Resource
	private MemberDao memberDao;
	
	@ApiOperation(value = "科目二三列表", httpMethod = "GET", notes = "科目二三列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "subjectId", value = "类型2:科目二,3:科目三", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "page", value = "第几页(0页开始)", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "size", value = "每页条数", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel list(Integer subjectId,HttpServletRequest request,@ApiIgnore Pageable pageable) throws ParseException{
		ResponseModel model = new ResponseModel();
		Page<HcKemu23> page = kemu23Dao.findBySubjectIdAndIsDeleted(subjectId,0,pageable);
		model.addAttribute("page", page);
		MessageUtil.success("获取成功", model);
		return model;
	}

	@ApiOperation(value = "详情", httpMethod = "GET", notes = "详情信息")
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "id", value = "id", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel getById(Integer id,HttpServletRequest request) throws ParseException{
		ResponseModel model = new ResponseModel();
		HcKemu23 kemu23 = kemu23Dao.findOne(id);
		Pageable pageable = new PageRequest(0,10,Direction.DESC,"addTime");
		Page<HcKemu23Comment> page = comment1Dao.findByKemu23Id(id,pageable);
		List<HcKemu23Comment> list = page.getContent();
		List<HcKemu23CommentVO> list1 = new ArrayList<>();
		for (HcKemu23Comment comment : list) {
			HcKemu23CommentVO commentVO = new HcKemu23CommentVO();
			BeanUtil.copyProperties(comment, commentVO);
			commentVO.setMemberName(comment.getMember()!=null?comment.getMember().getName():"游客");
			commentVO.setMemberPic(comment.getMember()!=null?comment.getMember().getPic():"");
			list1.add(commentVO);
		}
		Page<HcKemu23CommentVO> page1 = new PageImpl<>(list1, pageable, page.getTotalElements());
		
		model.addAttribute("detail", kemu23);
		model.addAttribute("comments", page1.getContent());
		MessageUtil.success("获取成功", model);
		return model;
	}

	@ApiOperation(value = "评价详情列表", httpMethod = "GET", notes = "评价信息")
	@RequestMapping(value = "/comment/list", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "id", value = "id", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel commentList(Integer id,HttpServletRequest request,@ApiIgnore @PageableDefault(sort="addTime",direction=Direction.DESC)Pageable pageable) throws ParseException{
		ResponseModel model = new ResponseModel();
		Page<HcKemu23Comment> page = comment1Dao.findByKemu23Id(id,pageable);
		
		List<HcKemu23Comment> list = page.getContent();
		List<HcKemu23CommentVO> list1 = new ArrayList<>();
		for (HcKemu23Comment comment : list) {
			HcKemu23CommentVO commentVO = new HcKemu23CommentVO();
			BeanUtil.copyProperties(comment, commentVO);
			commentVO.setMemberName(comment.getMember()!=null?comment.getMember().getName():"游客");
			commentVO.setMemberPic(comment.getMember()!=null?comment.getMember().getPic():"");
			
			list1.add(commentVO);
		}
		Page<HcKemu23CommentVO> page1 = new PageImpl<>(list1, pageable, page.getTotalElements());
		model.addAttribute("page", page1);
		MessageUtil.success("获取成功", model);
		return model;
	}

	@ApiOperation(value = "提交评价", httpMethod = "POST", notes = "提交评价")
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "id", value = "视频主键", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "memberId", value = "学员id", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "content", value = "content", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "token", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel addComment(Integer id,Integer memberId,String content,HttpServletRequest request) throws ParseException{
		ResponseModel model = new ResponseModel();
//		HcMember member = getLoginMember(request);
		HcKemu23Comment comment = new HcKemu23Comment();
		comment.setKemu23Id(id);
		comment.setContent(content);
		comment.setMemberId(memberId);
		comment.setZanNum(0);
		comment.setAddTime(new Date());
		comment.setUpdateTime(new Date());
		comment1Dao.save(comment);
		HcKemu23CommentVO commentVO = new HcKemu23CommentVO();
		BeanUtil.copyProperties(comment, commentVO);
		if(memberId!=null&&memberId>0){
			HcMember member = memberDao.findOne(memberId);
			comment.setMember(member);
		}
		commentVO.setMemberName(comment.getMember()!=null?comment.getMember().getName():"游客");
		commentVO.setMemberPic(comment.getMember()!=null?comment.getMember().getPic():"");
		
		model.addAttribute("comment", commentVO);
		MessageUtil.success("评论成功", model);
		return model;
	}
}
