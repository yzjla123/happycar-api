package com.happycar.api.controller.coach;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.ArrayUtils;
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
import com.happycar.api.vo.HcBookVO;
import com.happycar.api.vo.HcCoachVO;
import com.happycar.api.vo.HcCommentVO;
import com.happycar.api.vo.HcMemberVO;
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
@RestController("coachBookController")
@RequestMapping("/api/coach/book")	
@Authentication
public class BookController extends BaseController{
	
	private final Logger logger = Logger.getLogger(BookController.class);
	@Resource
	private BookDao bookDao;
	@Resource
	private ScheduleDao scheduleDao;
	@Resource
	private CommentDao commentDao;
	@Resource
	private CoachDao coachDao;
	
	@ApiOperation(value = "预约会员列表", httpMethod = "GET", notes = "预约会员列表")
	@RequestMapping(value = "/member/list", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "scheduleId", value = "排班id", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "date", value = "日期(yyyy-MM-dd)", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel list(
			final Integer scheduleId,
			final String date,
			HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		final HcCoach coach = getLoginCoach(request);
		List<HcBook> list = bookDao.findAll(new Specification<HcBook>() {
			
			@Override
			public Predicate toPredicate(Root<HcBook> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();  
		        if(scheduleId!=null){  
		            list.add(cb.equal(root.get("scheduleId").as(String.class), scheduleId));  
		        }
		        if(date!=null){  
		            try {
						list.add(cb.equal(root.get("date").as(Date.class), DateUtil.parseTime(date, DateUtil.YYYYMMDD)));
					} catch (ParseException e) {
						logger.error("Date parse error", e);
					}  
		        }
		        list.add(root.get("status").as(Integer.class).in(0,1,3));  
		        list.add(cb.equal(root.get("coachId").as(Integer.class),coach.getId()));  
				//过滤已删除的数据
//		        list.add(cb.equal(root.get("isDeleted").as(Integer.class), 0));  
		        Predicate[] p = new Predicate[list.size()];  
		        return cb.and(list.toArray(p)); 
			}
		},new Sort(Direction.ASC,"date","schedule.time1"));
		List<HcMemberVO> members = new ArrayList<HcMemberVO>();
		for (HcBook book : list) {
			HcMemberVO memberVO = new HcMemberVO();
			BeanUtil.copyProperties(book.getMember(), memberVO);
			members.add(memberVO);
		}
		model.addAttribute("members", members);
		MessageUtil.success("获取成功", model);
		return model;
	}

}
