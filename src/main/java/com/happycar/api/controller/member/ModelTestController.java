package com.happycar.api.controller.member;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.annotation.Authentication;
import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.ModelTestDao;
import com.happycar.api.dao.ModelTestQuestionDao;
import com.happycar.api.model.HcMember;
import com.happycar.api.model.HcModelTest;
import com.happycar.api.model.HcModelTestQuestion;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "模拟考试管理")
@RestController
@RequestMapping("/api/member/modelTest")	
@Authentication
public class ModelTestController extends BaseController{
	
	private Logger logger = Logger.getLogger(ModelTestController.class);
	@Resource
	private ModelTestDao modelTestDao;
	@Resource
	private ModelTestQuestionDao modelTestQuestionDao;
	
	@ApiOperation(value = "最高分数", httpMethod = "GET", notes = "最高分数")
	@RequestMapping(value = "/bestScore", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel bestScore(HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		final HcMember member = getLoginMember(request);
		Integer bestScore = modelTestDao.findMaxScoreByMemberId(member.getId());
		model.addAttribute("bestScore", bestScore);
		MessageUtil.success("获取成功", model);
		return model;
	}
	
	@ApiOperation(value = "获取已练习考题", httpMethod = "GET", notes = "获取已练习考题")
	@RequestMapping(value = "/doneTheoryIds", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel doneTheoryIds(HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		final HcMember member = getLoginMember(request);
		List<HcModelTest> modelTests = modelTestDao.findByMemberIdAndIsDeleted(member.getId(),0);
		List<Integer> modelTestIds = new ArrayList<>();
		for (HcModelTest test : modelTests) {
			modelTestIds.add(test.getId());
		}
		List<Object> list = modelTestQuestionDao.findDoneTheoryIds(modelTestIds);
		model.addAttribute("doneTheoryIds", list); 
		MessageUtil.success("获取成功", model);
		return model;
	}
	
	@ApiOperation(value = "提交考试成绩", httpMethod = "POST", notes = "提交考试成绩")
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "subjectType", value = "科目类型 1:科目一,4:科目四", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "wrongNum", value = "错误数", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "rightNum", value = "正确数", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "answerCards", value = "答案,用英文逗号隔开.如:1#A,B,C#0;2#A#1;", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "startTime", value = "开始时间,时间戳", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "endTime", value = "结束时间,时间戳", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	@Transactional
	public ResponseModel submit(
			Integer subjectType,
			Integer wrongNum,
			Integer rightNum,
			String answerCards,
			Integer elapsedMin,
			Long startTime,
			Long endTime,
			HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		HcMember member = getLoginMember(request);
//		if(StringUtils.isEmpty(answerCards)){
//			MessageUtil.fail("答题卡不能为空", model);
//			return model;
//		}
		HcModelTest modelTest = new HcModelTest();
		modelTest.setSubjectType(subjectType);
		modelTest.setMemberId(member.getId());
		modelTest.setElapsedMin(elapsedMin);
		modelTest.setWrongNum(wrongNum);
		modelTest.setRightNum(rightNum);
		modelTest.setScore(rightNum);
		modelTest.setStartTime(new Date(startTime));
		modelTest.setEndTime(new Date(endTime));
		modelTest.setIsDeleted(0);
		modelTest.setAddTime(new Date());
		modelTestDao.save(modelTest);
		if(!StringUtils.isEmpty(answerCards)){
			String[] answerArr = answerCards.split(";");
			List<HcModelTestQuestion> questions = new ArrayList();
			for(String answerCard : answerArr){
				String[] array = answerCard.split("#");
				HcModelTestQuestion question = new HcModelTestQuestion();
				question.setModelTestId(modelTest.getId());
				question.setTheoryId(Integer.parseInt(array[0]));
				question.setAnswer(array[1]);
				question.setIsWrong(Integer.parseInt(array[2]));
				question.setAddTime(new Date());
				questions.add(question);
			}
			modelTestQuestionDao.save(questions);
		}
		MessageUtil.success("提交成功", model);
		return model;
	}
}
