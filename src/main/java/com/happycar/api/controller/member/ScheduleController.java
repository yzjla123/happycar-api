package com.happycar.api.controller.member;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.annotation.Authentication;
import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.BookDao;
import com.happycar.api.dao.ScheduleDao;
import com.happycar.api.dao.BookDao;
import com.happycar.api.model.HcBook;
import com.happycar.api.model.HcBook;
import com.happycar.api.model.HcMember;
import com.happycar.api.model.HcSchedule;
import com.happycar.api.utils.BeanUtil;
import com.happycar.api.utils.DateUtil;
import com.happycar.api.utils.LocationUtils;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.vo.HcBookVO;
import com.happycar.api.vo.HcScheduleVO;
import com.happycar.api.vo.HcBookVO;
import com.happycar.api.vo.HcSchoolVO;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "排班管理")
@RestController
@RequestMapping("/api/member/schedule")	
@Authentication
public class ScheduleController extends BaseController{
	
	private Logger logger = Logger.getLogger(ScheduleController.class);
	@Resource
	private ScheduleDao scheduleDao;
	@Resource
	private BookDao bookDao;
	
	@ApiOperation(value = "排班列表", httpMethod = "GET", notes = "排班列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "date", value = "预约日期(yyyy-MM-dd)", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "subjectType", value = "科目类型 2:科目二,3:科目三", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel list(
			String date,
			Integer subjectType,
			HttpServletRequest request) throws ParseException{
		ResponseModel model = new ResponseModel();
		HcMember member = getLoginMember(request);
		List<HcSchedule> list = scheduleDao.findByCoachIdAndDateAndSubjectTypeOrderByTime1Asc(member.getCoachId(),DateUtil.parseTime(date, DateUtil.YYYYMMDD),subjectType);
		List<HcScheduleVO> schedules = new ArrayList<HcScheduleVO>();
		for (HcSchedule schedule : list) {
			HcScheduleVO scheduleVO = new HcScheduleVO();
			BeanUtil.copyProperties(schedule, scheduleVO);
			schedules.add(scheduleVO);
		}
		List<HcBook> books = bookDao.findByMemberIdAndDate(member.getId(),DateUtil.parseTime(date, DateUtil.YYYYMMDD));
		List<HcBookVO> bookVOs = new ArrayList<HcBookVO>();
		for (HcBook book : books) {
			HcBookVO bookVO = new HcBookVO();
			BeanUtil.copyProperties(book, bookVO);
			bookVOs.add(bookVO);
		}
		model.addAttribute("schedules", schedules);
		model.addAttribute("books", books);
		MessageUtil.success("获取成功", model);
		return model;
	}
	
	

}
