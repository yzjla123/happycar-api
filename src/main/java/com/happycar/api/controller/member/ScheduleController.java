package com.happycar.api.controller.member;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.annotation.Authentication;
import com.happycar.api.contant.Constant;
import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.BookDao;
import com.happycar.api.dao.ExamApplyDao;
import com.happycar.api.dao.ScheduleDao;
import com.happycar.api.dao.SysParamDao;
import com.happycar.api.model.HcBook;
import com.happycar.api.model.HcExamApply;
import com.happycar.api.model.HcMember;
import com.happycar.api.model.HcSchedule;
import com.happycar.api.model.HcSysParam;
import com.happycar.api.utils.BeanUtil;
import com.happycar.api.utils.DateUtil;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.vo.HcBookVO;
import com.happycar.api.vo.HcScheduleVO;
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
	@Resource
	private ExamApplyDao examApplyDao;
	@Resource
	private SysParamDao paramDao;
	
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
		/**
		 * 科目三考试时间为7月10号 该学员的预约则从7月5日开放到7月8日总共四天，
		 * 最多可预约到四个课时，考试后合格，后台关闭预约，不合格后台根据预约考试申请继续开放预约
		 */
		//查找科目三的考试时间
		HcExamApply examApply = null;
		if(subjectType==3){
			examApply = examApplyDao.findByMemberIdAndSubjectTypeAndStatusAndIsDeleted(member.getId(),3,4,0);
		}
		
		
		List<HcScheduleVO> schedules = new ArrayList<HcScheduleVO>();
		for (HcSchedule schedule : list) {
			HcScheduleVO scheduleVO = new HcScheduleVO();
			BeanUtil.copyProperties(schedule, scheduleVO);
			if(examApply!=null){
				//如果是可用状态
				if(scheduleVO.getStatus()==1){
					boolean available = isAvailable(examApply.getExamTime(), scheduleVO.getDate());
					//还没有到预约时间,则不可预约
					if(!available){
						scheduleVO.setStatus(0);
					}
				}
			}
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
	
	
	private static boolean isAvailable(Date examDate,Date scheduleDate) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String s = sdf.format(examDate);
		Date date =  sdf.parse(s);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE,-2);
		long maxDate = cal.getTimeInMillis();
		cal.add(Calendar.DATE,-3);
		long minDate = cal.getTimeInMillis();
		if(scheduleDate.getTime()>=minDate&&scheduleDate.getTime()<=maxDate){
			return true;
		}else{
			return false;
		}
	}
	
	public static void main(String[] args) throws ParseException {
		System.out.println(isAvailable(new Date(),new Date("Sat Jul 18 00:00:00 CST 2017")));
	}

}
