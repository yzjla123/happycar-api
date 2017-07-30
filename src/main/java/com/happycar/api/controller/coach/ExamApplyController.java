package com.happycar.api.controller.coach;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.annotation.Authentication;
import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.ExamApplyDao;
import com.happycar.api.dao.MessageCenterDao;
import com.happycar.api.model.HcCoach;
import com.happycar.api.model.HcExamApply;
import com.happycar.api.model.HcMessageCenter;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.utils.SMSUtil;
import com.happycar.api.vo.HcExamApplyVO;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "考试申请管理")
@RestController("coachExamApply")
@RequestMapping("/api/coach/examApply")	
@Authentication
public class ExamApplyController extends BaseController{
	
	private Logger logger = Logger.getLogger(ExamApplyController.class);
	@Resource
	private ExamApplyDao examApplyDao;
	@Resource
	private MessageCenterDao centerDao;
	
	@ApiOperation(value = "新增申请列表", httpMethod = "GET", notes = "新增申请列表")
	@RequestMapping(value = "/newApply", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "subjectType", value = "科目类型 1:科目一,2:科目二,3:科目三,4:科目四", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel newApply(Integer subjectType,HttpServletRequest request) throws ParseException{
		ResponseModel model = new ResponseModel();
		HcCoach coach = getLoginCoach(request);
		List<Object[]> list = examApplyDao.findNewApplyByCoachIdAndSubjectType(coach.getId(),subjectType);
		List<HcExamApplyVO> applys = new ArrayList<HcExamApplyVO>();
		for (Object[] objects : list) {
			HcExamApplyVO apply = new HcExamApplyVO();
			apply.setId(Integer.parseInt(objects[0].toString()));
			apply.setMemberName(objects[1].toString());
			apply.setAddTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(objects[2].toString()));
			apply.setStatus(Integer.parseInt(objects[3].toString()));
			applys.add(apply);
		}
		model.addAttribute("list", applys);
		MessageUtil.success("申请成功", model);
		return model;
	}
	
	
	@ApiOperation(value = "通过申请", httpMethod = "POST", notes = "通过申请")
	@RequestMapping(value = "/pass", method = RequestMethod.POST)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "examApplyId", value = "申请id", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	@Transactional
	public ResponseModel pass(Integer examApplyId,HttpServletRequest request) throws ParseException{
		ResponseModel model = new ResponseModel();
		HcCoach coach = getLoginCoach(request);
		HcExamApply examApply = examApplyDao.findOne(examApplyId);
		if(examApply.getStatus()!=0){
			MessageUtil.fail("状态不是为新申请", model);
			return model;
		}
		examApply.setStatus(1);
		examApplyDao.save(examApply);
		MessageUtil.success("操作成功", model);
		return model;
	}

	@ApiOperation(value = "驳回申请", httpMethod = "POST", notes = "驳回申请")
	@RequestMapping(value = "/reject", method = RequestMethod.POST)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "examApplyId", value = "申请id", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "rejectReson", value = "驳回原因", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	@Transactional
	public ResponseModel reject(Integer examApplyId,String rejectReson,HttpServletRequest request) throws ParseException{
		ResponseModel model = new ResponseModel();
		HcCoach coach = getLoginCoach(request);
		HcExamApply examApply = examApplyDao.findOne(examApplyId);
		if(examApply.getStatus()!=0){
			MessageUtil.fail("状态不是为新申请", model);
			return model;
		}
		if(StringUtils.isEmpty(rejectReson)){
			MessageUtil.fail("驳回原因不能为空", model);
			return model;
		}
		examApply.setStatus(2);
		examApply.setRejectReson(rejectReson);
		examApplyDao.save(examApply);
		MessageUtil.success("操作成功", model);
		//发送短信通知
		Map<String,String> paramMap = new HashMap();
		paramMap.put("subjectType", "科目"+subjectType2Chinese(examApply.getSubjectType()));
		paramMap.put("rejectReson", examApply.getRejectReson());
		SMSUtil.send(examApply.getMember().getPhone(),SMSUtil.TPL_EXAM_REJECT,paramMap);
		HcMessageCenter center = new HcMessageCenter();
		center.setContent("您的科目"+subjectType2Chinese(examApply.getSubjectType())+"考试申请由于"+examApply.getRejectReson()+"已被驳回。如有疑问，请联系客服咨询。");
		center.setIsDeleted(0);
		center.setIsRead(0);
		center.setMemberId(examApply.getMemberId());
		center.setNo("");
		center.setAddTime(new Date());
		centerDao.save(center);
		return model;
	}
	
	public static String subjectType2Chinese(Integer subjectType){
		if(subjectType==1){
			return "一";
		}else if(subjectType==2){
			return "二";
		}else if(subjectType==3){
			return "三";
		}else if(subjectType==4){
			return "四";
		}
		return "";
	}
}
