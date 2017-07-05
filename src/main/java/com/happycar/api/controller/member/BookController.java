package com.happycar.api.controller.member;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.annotation.Authentication;
import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.BookDao;
import com.happycar.api.dao.CoachDao;
import com.happycar.api.dao.CommentDao;
import com.happycar.api.dao.ScheduleDao;
import com.happycar.api.model.HcBook;
import com.happycar.api.model.HcCoach;
import com.happycar.api.model.HcComment;
import com.happycar.api.model.HcMember;
import com.happycar.api.model.HcSchedule;
import com.happycar.api.utils.BeanUtil;
import com.happycar.api.utils.DateUtil;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.utils.StringUtil;
import com.happycar.api.vo.HcBookVO;
import com.happycar.api.vo.HcCoachVO;
import com.happycar.api.vo.HcCommentVO;
import com.happycar.api.vo.HcScheduleVO;
import com.happycar.api.vo.HcSchoolVO;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "预约管理")
@RestController
@RequestMapping("/api/member/book")	
@Authentication
public class BookController extends BaseController{
	
	private Logger logger = Logger.getLogger(BookController.class);
	@Resource
	private BookDao bookDao;
	@Resource
	private ScheduleDao scheduleDao;
	@Resource
	private CommentDao commentDao;
	@Resource
	private CoachDao coachDao;
	
	@ApiOperation(value = "预约列表", httpMethod = "GET", notes = "预约列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "date", value = "预约日期(yyyy-MM-dd)", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel list(final String date,HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		final HcMember member = getLoginMember(request);
		List<HcBook> list = bookDao.findAll(new Specification<HcBook>() {
			
			@Override
			public Predicate toPredicate(Root<HcBook> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();  
		        if(member!=null){  
		            list.add(cb.equal(root.get("memberId").as(String.class), member.getId()));  
		        }
		        if(date!=null){  
		            try {
						list.add(cb.equal(root.join("schedule").get("date").as(Date.class), DateUtil.parseTime(date, DateUtil.YYYYMMDD)));
					} catch (ParseException e) {
						logger.error("Date parse error", e);
					}  
		        }
		        list.add(root.get("status").as(Integer.class).in(0,1,3));  
				//过滤已删除的数据
//		        list.add(cb.equal(root.get("isDeleted").as(Integer.class), 0));  
		        Predicate[] p = new Predicate[list.size()];  
		        return cb.and(list.toArray(p)); 
			}
		},new Sort(Direction.ASC,"date","schedule.time1"));
		List<HcBookVO> books = new ArrayList<HcBookVO>();
		for (HcBook book : list) {
			HcBookVO bookVO = new HcBookVO();
			BeanUtil.copyProperties(book, bookVO);
			HcCoachVO coachVO = new HcCoachVO();
			BeanUtil.copyProperties(book.getSchedule().getCoach(), coachVO);
			HcScheduleVO scheduleVO = new HcScheduleVO();
			BeanUtil.copyProperties(book.getSchedule(), scheduleVO);
			bookVO.setCoach(coachVO);
			bookVO.setSchedule(scheduleVO);
			books.add(bookVO);
		}
		model.addAttribute("books", books);
		MessageUtil.success("获取成功", model);
		return model;
	}

	@ApiOperation(value = "预约详情", httpMethod = "GET", notes = "预约详情")
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "id", value = "id", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel detail(Integer id,HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		HcMember member = getLoginMember(request);
		HcBook book = bookDao.findOne(id);
		if(book.getMemberId().intValue()!=member.getId().intValue()){
			MessageUtil.fail("权限不足", model);
			return model;
		}
		HcBookVO bookVO = new HcBookVO();
		BeanUtil.copyProperties(book, bookVO);
		HcCoachVO coachVO = new HcCoachVO();
		BeanUtil.copyProperties(book.getSchedule().getCoach(), coachVO);
		HcScheduleVO scheduleVO = new HcScheduleVO();
		BeanUtil.copyProperties(book.getSchedule(), scheduleVO);
		HcSchoolVO schoolVO = new HcSchoolVO();
		BeanUtil.copyProperties(book.getSchedule().getCoach().getSchool(), schoolVO);
		
		bookVO.setCoach(coachVO);
		bookVO.setSchedule(scheduleVO);
		if(book.getStatus().intValue()==2){
			HcComment comment = commentDao.findByBookIdAndIsDeleted(book.getId(), 0);
			HcCommentVO commentVO = new HcCommentVO();
			BeanUtil.copyProperties(comment, commentVO);
			model.addAttribute("comment", commentVO);
		}
		model.addAttribute("book", bookVO);
		model.addAttribute("schedule", scheduleVO);
		model.addAttribute("coach", coachVO);
		model.addAttribute("school", schoolVO);
		MessageUtil.success("获取成功", model);
		return model;
	}
	
	
	@ApiOperation(value = "新增预约", httpMethod = "POST", notes = "新增预约")
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "scheduleIds", value = "排班id,多个用逗号隔开", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	@Transactional
	public ResponseModel add(final String scheduleIds,Integer subjectType,HttpServletRequest request) throws ParseException{
		ResponseModel model = new ResponseModel();
		HcMember member = getLoginMember(request);
		if(subjectType==2&&member.getProgress()!=6){
			MessageUtil.fail("您的学习进度不是科目二", model);
			return model;
		}
		if(subjectType==3&&member.getProgress()!=8){
			MessageUtil.fail("您的学习进度不是科目三", model);
			return model;
		}
		if(StringUtil.isNull(scheduleIds)){
			MessageUtil.fail("请选择课程!", model);
			return model;
		}
		String[] scheduleIdArray = scheduleIds.split(",");
		List<Integer> list = new ArrayList<>();
		for (String id : scheduleIdArray) {
			list.add(Integer.parseInt(id));
		}
		//是否有预约满
		List<HcSchedule> fullSchedules = scheduleDao.findFullBookByIdIn(list);
		if(fullSchedules.size()>0){
			String subjectTypeStr = subjectType==2?"科目二":"科目三";
			String date = new SimpleDateFormat("M/d").format(fullSchedules.get(0).getDate());
			String timeDuration = fullSchedules.get(0).getTime1()+"-"+fullSchedules.get(0).getTime2();
			MessageUtil.fail(subjectTypeStr+date+timeDuration+"已约满,不能预约!", model);
			return model;
		}
		//加载预约的排班表信息
		List<HcSchedule> bookSchedules = scheduleDao.findByIdIn(list);
		List<Date> bookDates = new ArrayList<>();
		for (HcSchedule schedule : bookSchedules) {
			bookDates.add(schedule.getDate());
		}
		//加载这些日期已预约的信息
		List<HcBook> books = bookDao.findByMemberIdAndDateIn(member.getId(), bookDates);
		//定义一个map,key 为预约日期,value 为预约的次数
		Map<Long,Integer> dateTimes = new HashMap<>();
		for (HcBook hcBook : books) {
			Integer times = dateTimes.get(hcBook.getDate().getTime());
			if(times==null){ times = 0; }
			times += 1;
			dateTimes.put(hcBook.getDate().getTime(), times);
		}
		for (HcSchedule schedule : bookSchedules) {
			Integer times = dateTimes.get(schedule.getDate().getTime());
			if(times==null){ times = 0; }
			times += 1;
			dateTimes.put(schedule.getDate().getTime(), times);
		}
		//遍历查看是否有超过次数的
		for (Long dateKey : dateTimes.keySet()) {
			Date date = new Date(dateKey);
			//星期六或星期天只能预约一天
			if(date.getDay()==0||date.getDay()==6){
				if(dateTimes.get(dateKey)>1){
					MessageUtil.fail("周末每天最多预约1个课时", model);
					return model;
				}
			}else{
				//平时最多预约两个课时
				if(dateTimes.get(dateKey)>2){
					MessageUtil.fail("平时每日最多预约2个课时", model);
					return model;
				}
			}
		}
		for (String scheduleId : scheduleIdArray) {
			HcSchedule schedule = scheduleDao.findOne(Integer.parseInt(scheduleId));
			HcBook book = new HcBook();
			book.setMemberId(member.getId());
			book.setScheduleId(Integer.parseInt(scheduleId));
			book.setStatus(0);
			book.setDate(schedule.getDate());
			book.setAddTime(new Date());
			book.setCoachId(schedule.getCoachId());
			bookDao.save(book);
			//预约人数加1
			schedule.setBookNum(schedule.getBookNum()+1);
			schedule.setUpdateTime(new Date());
			scheduleDao.save(schedule);
		}
		MessageUtil.success("预约成功", model);
		return model;
	}
	

	@ApiOperation(value = "取消预约", httpMethod = "POST", notes = "取消预约")
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "id", value = "id", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	@Transactional
	public ResponseModel cancel(Integer id,HttpServletRequest request) throws ParseException{
		ResponseModel model = new ResponseModel();
		HcMember member = getLoginMember(request);
		HcBook book = bookDao.findOne(id);
		if(book==null){
			MessageUtil.fail("未找到该预约,请刷新重试", model);
			return model;
		}
		if(book.getMemberId().intValue()!=member.getId().intValue()){
			MessageUtil.fail("只能取消自已的预约", model);
			return model;
		}
		if(book.getStatus()!=0){
			MessageUtil.fail("状态不是预约中,无法取消", model);
			return model;
		}
		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(new SimpleDateFormat("yyyy-MM-dd").format(book.getDate())+" "+book.getSchedule().getTime1());
//		if(date.getTime() - new Date().getTime() < 1000*60*60){
//			//一小时之内不能取消
//			MessageUtil.fail("1小时之内的预约无法取消", model);
//			return model;
//		}
		HcSchedule schedule = book.getSchedule();
		schedule.setBookNum(schedule.getBookNum()-1);
		scheduleDao.save(schedule);
		bookDao.delete(id);
		MessageUtil.success("取消预约成功", model);
		return model;
	}
	
	@ApiOperation(value = "评论", httpMethod = "POST", notes = "评论")
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "bookId", value = "预约id", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "star", value = "分数(1-5)", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "content", value = "内容", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	@Transactional
	public ResponseModel comment(Integer bookId,HcComment comment,HttpServletRequest request) throws ParseException{
		ResponseModel model = new ResponseModel();
		HcMember member = getLoginMember(request);
		HcBook book = bookDao.findOne(bookId);
		if(book==null){
			MessageUtil.fail("未找到该预约,请刷新重试", model);
			return model;
		}
		if(book.getMemberId().intValue()!=member.getId().intValue()){
			MessageUtil.fail("只能评价自已的", model);
			return model;
		}
		if(book.getStatus()!=1){
			MessageUtil.fail("状态不是待评价", model);
			return model;
		}
		if(comment.getStar()<1||comment.getStar()>5){
			MessageUtil.fail("星级只能在1-5", model);
			return model;
		}
		if(comment.getContent()==null){
			MessageUtil.fail("内容不能为空", model);
			return model;
		}
		if(comment.getContent().length()>255){
			MessageUtil.fail("内容只能在255个字以内", model);
			return model;
		}
		HcCoach coach = book.getSchedule().getCoach();
		comment.setBookId(book.getId());
		comment.setCoachId(coach.getId());
		comment.setMemberId(member.getId());
		comment.setAddTime(new Date());
		comment.setUpdateTime(new Date());
		commentDao.save(comment);
		book.setStatus(2);
		book.setUpdateTime(new Date());
		bookDao.save(book);
		Float star = commentDao.findAvgStarByCoachId(coach.getId());
		if(star==null) star=5f;
		coach.setStar(Math.round(star));
		coach.setUpdateTime(new Date());
		coachDao.save(coach);
		MessageUtil.success("评价成功", model);
		return model;
	}

}
