package com.happycar.api.controller.member;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.annotation.Authentication;
import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.ExamApplyDao;
import com.happycar.api.model.HcExamApply;
import com.happycar.api.model.HcMember;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "考试申请管理")
@RestController
@RequestMapping("/api/member/examApply")	
@Authentication
public class ExamApplyController extends BaseController{
	
	private Logger logger = Logger.getLogger(ExamApplyController.class);
	@Resource
	private ExamApplyDao examApplyDao;
	
	@ApiOperation(value = "新增申请", httpMethod = "POST", notes = "新增申请")
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "subjectType", value = "科目类型 1:科目一,2:科目二,3:科目三,4:科目四", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	@Transactional
	public ResponseModel add(Integer subjectType,HttpServletRequest request) throws ParseException{
		ResponseModel model = new ResponseModel();
		HcMember member = getLoginMember(request);
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(0);
		statusList.add(1);
		statusList.add(4);
		statusList.add(5);
		HcExamApply examApply1 = examApplyDao.findByStatusInAndMemberIdAndAndSubjectTypeAndIsDeleted(statusList,member.getId(),subjectType,0);
		if(examApply1!=null){
			if(examApply1.getStatus()==0){
				MessageUtil.fail("已申请过了,请耐心等待!", model);
				return model;
			}
			if(examApply1.getStatus()==1){
				MessageUtil.fail("已申请过了,请耐心等待!", model);
				return model;
			}
			if(examApply1.getStatus()==4){
				MessageUtil.fail("已批准考试,请耐心等待!", model);
				return model;
			}
			if(examApply1.getStatus()==5){
				MessageUtil.fail("已考试通过,无需再次申请!", model);
				return model;
			}
		}
		HcExamApply examApply = new HcExamApply();
		examApply.setMemberId(member.getId());
		examApply.setStatus(0);
		examApply.setSubjectType(subjectType);
		examApply.setIsDeleted(0);
		examApply.setAddTime(new Date());
		examApplyDao.save(examApply);
		MessageUtil.success("申请成功", model);
		return model;
	}
	

}
