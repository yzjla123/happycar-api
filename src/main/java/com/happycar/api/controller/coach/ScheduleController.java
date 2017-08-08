package com.happycar.api.controller.coach;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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
import com.happycar.api.contant.Constant;
import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.BookDao;
import com.happycar.api.dao.CoachDao;
import com.happycar.api.dao.ScheduleDao;
import com.happycar.api.dao.SysParamDao;
import com.happycar.api.dao.BookDao;
import com.happycar.api.model.HcBook;
import com.happycar.api.model.HcCoach;
import com.happycar.api.model.HcBook;
import com.happycar.api.model.HcMember;
import com.happycar.api.model.HcSchedule;
import com.happycar.api.model.HcSysParam;
import com.happycar.api.utils.BeanUtil;
import com.happycar.api.utils.DateUtil;
import com.happycar.api.utils.LocationUtils;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.utils.StringUtil;
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
@RestController("CoachScheduleController")
@RequestMapping("/api/coach/schedule")	
@Authentication
public class ScheduleController extends BaseController{
	
	private Logger logger = Logger.getLogger(ScheduleController.class);
	@Resource
	private ScheduleDao scheduleDao;
	@Resource
	private SysParamDao paramDao;
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
			String drivingLicenseType,
			HttpServletRequest request) throws ParseException{
		ResponseModel model = new ResponseModel();
		HcCoach coach = getLoginCoach(request);
		List<HcSchedule> list = scheduleDao.findByCoachIdAndDateAndSubjectTypeAndDrivingLicenseTypeOrderByTime1Asc(coach.getId(),DateUtil.parseTime(date, DateUtil.YYYYMMDD),subjectType,drivingLicenseType);
		List<HcScheduleVO> schedules = new ArrayList<HcScheduleVO>();
		for (HcSchedule schedule : list) {
			HcScheduleVO scheduleVO = new HcScheduleVO();
			BeanUtil.copyProperties(schedule, scheduleVO);
			schedules.add(scheduleVO);
		}
		List<HcBook> books = bookDao.findByCoachIdAndDate(coach.getId(),DateUtil.parseTime(date, DateUtil.YYYYMMDD));
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
	
	@ApiOperation(value = "最后排班日期", httpMethod = "GET", notes = "最后排班日期")
	@RequestMapping(value = "/lastScheduleDate", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "subjectType", value = "科目类型 2:科目二,3:科目三", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel lastScheduleDate(
			Integer subjectType,
			String drivingLicenseType,
			HttpServletRequest request) throws ParseException{
		ResponseModel model = new ResponseModel();
		HcCoach coach = getLoginCoach(request);
		Date lastScheduleDate = scheduleDao.findLastScheduleDateByCoachIdAndDateAndSubjectTypeAndDrivingLicenseType(coach.getId(),subjectType,drivingLicenseType);
		if(lastScheduleDate!=null){
			model.addAttribute("lastScheduleDate", new SimpleDateFormat("yyyy-MM-dd").format(lastScheduleDate));
		}else{
			model.addAttribute("lastScheduleDate", null);
		}
		MessageUtil.success("获取成功", model);
		return model;
	}

	@ApiOperation(value = "保存快速排班", httpMethod = "POST", notes = "保存快速排班")
	@RequestMapping(value = "/quickSchedule", method = RequestMethod.POST)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "startDate", value = "开始日期(yyyy-MM-dd)", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "endDate", value = "结束日期(yyyy-MM-dd)", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "carNum", value = "车辆数", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "memberNum", value = "可学学员数", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "subjectType", value = "科目类型 2:科目二,3:科目三", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	@Transactional
	public ResponseModel quickSchedule(
			String startDate,
			String endDate,
			Integer carNum,
			Integer memberNum,
			Integer subjectType,
			String drivingLicenseType,
			HttpServletRequest request) throws ParseException{
		ResponseModel model = new ResponseModel();
		HcCoach coach = getLoginCoach(request);
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		startCal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
		endCal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
		if(startCal.after(endCal)){
			MessageUtil.fail("开始时间不能大于结束时间", model);
			return model;
		}
		if(startCal.after(new Date())){
			MessageUtil.fail("开始时间不能小于当前时间", model);
			return model;
		}
		List<Date> bookDates = scheduleDao.findBookDate(startCal.getTime(),endCal.getTime(),coach.getId(),subjectType,drivingLicenseType);
		if(bookDates.size()>0){
			MessageUtil.fail("日期："+new SimpleDateFormat("yyyy-MM-dd").format(bookDates.get(0))+"已有学员预约,不能重新排班", model);
			return model;
		}
		//删除已有的排班
		scheduleDao.deleteBySubjectTypeAndDateBetweenBetweenAndDrivingLicenseType(subjectType,startCal.getTime(),endCal.getTime(),drivingLicenseType);
		//加载时间段
		HcSysParam parentParam = paramDao.findByCode(Constant.PARAM_CODE_SCHEDULE_DURATION);
		List<HcSysParam> timeParams = paramDao.findByPidOrderBySeqAsc(parentParam.getId());
		
		List<HcSchedule> schedules = new ArrayList<>();
		while(!startCal.after(endCal)){
			for(HcSysParam param : timeParams){
				String[] timeArr = param.getValue().split("-");
				HcSchedule schedule = new HcSchedule();
				schedule.setCoachId(coach.getId());
				schedule.setBookNum(0);
				schedule.setMemberNum(memberNum);
				schedule.setCarNum(carNum);
				schedule.setStatus(1);
				schedule.setDate(startCal.getTime());
				schedule.setSubjectType(subjectType);
				schedule.setAddTime(new Date());
				schedule.setUpdateTime(new Date());
				schedule.setTime1(timeArr[0]);
				schedule.setTime2(timeArr[1]);
				schedule.setDrivingLicenseType(drivingLicenseType);
				schedules.add(schedule);
			}
			startCal.add(Calendar.DATE, 1);
		}
		scheduleDao.save(schedules);
		MessageUtil.success("操作成功", model);
		return model;
	}
	

	@ApiOperation(value = "更新排班", httpMethod = "POST", notes = "更新排班")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "ids", value = "id,多个用逗号隔开", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "memberNums", value = "可预约人数,多个用逗号隔开", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态,多个用逗号隔开", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	@Transactional
	public ResponseModel update(
			String ids,
			String memberNums,
			String status,
			HttpServletRequest request) throws ParseException{
		ResponseModel model = new ResponseModel();
		HcCoach coach = getLoginCoach(request);
		if(StringUtil.isNull(ids)){
			MessageUtil.fail("排班信息不能为空", model);
			return model;
		}
		String[] idsArray = ids.split(",");
		List<Integer> list = new ArrayList<>();
		for (String id : idsArray) {
			list.add(Integer.parseInt(id));
		}
		List<HcSchedule> schedules = scheduleDao.findByIdIn(list);
		String[] memberNumArr = memberNums.split(",");
		String[] statusArr = status.split(",");
		for (int i=0; i<schedules.size(); i++) {
			HcSchedule hcSchedule = schedules.get(i);
			if(hcSchedule.getCoachId().intValue()!=coach.getId()){
				MessageUtil.fail("权限不足", model);
				return model;
			}
			hcSchedule.setMemberNum(Integer.parseInt(memberNumArr[i]));
			hcSchedule.setStatus(Integer.parseInt(statusArr[i]));
		}
		scheduleDao.save(schedules);
		MessageUtil.success("操作成功", model);
		return model;
	}

}
