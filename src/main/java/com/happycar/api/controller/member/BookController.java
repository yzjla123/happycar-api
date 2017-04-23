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
import com.happycar.api.dao.BookDao;
import com.happycar.api.model.HcBook;
import com.happycar.api.model.HcBook;
import com.happycar.api.model.HcMember;
import com.happycar.api.utils.BeanUtils;
import com.happycar.api.utils.DateUtil;
import com.happycar.api.utils.LocationUtils;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.vo.HcBookVO;
import com.happycar.api.vo.HcBookVO;
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
	
	@ApiOperation(value = "预约列表", httpMethod = "GET", notes = "预约列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "date", value = "预约日期(yyyy-MM-dd)", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel list(final String date,HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		HcMember member = getLoginMember(request);
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
				//过滤已删除的数据
//		        list.add(cb.equal(root.get("isDeleted").as(Integer.class), 0));  
		        Predicate[] p = new Predicate[list.size()];  
		        return cb.and(list.toArray(p)); 
			}
		});
		List<HcBookVO> books = new ArrayList<HcBookVO>();
		for (HcBook book : list) {
			HcBookVO bookVO = new HcBookVO();
			BeanUtils.copyNotNullProperties(bookVO, book);
			books.add(bookVO);
		}
		model.addAttribute("books", books);
		MessageUtil.success("获取成功", model);
		return model;
	}
	
	
	@ApiOperation(value = "新增预约", httpMethod = "POST", notes = "新增预约")
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "scheduleIds", value = "排班id,多个用逗号隔开", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "date", value = "预约日期(yyyy-MM-dd)", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	@Transactional
	public ResponseModel add(final String scheduleIds,String date,HttpServletRequest request) throws ParseException{
		ResponseModel model = new ResponseModel();
		HcMember member = getLoginMember(request);
		//删除这一天的全部预约
		bookDao.deleteByDate(DateUtil.parseTime(date, DateUtil.YYYYMMDD));
		String[] scheduleIdArray = scheduleIds.split(",");
		for (String scheduleId : scheduleIdArray) {
			HcBook book = new HcBook();
			book.setMemberId(member.getId());
			book.setScheduleId(Integer.parseInt(scheduleId));
			book.setStatus(0);
			book.setDate(DateUtil.parseTime(date, DateUtil.YYYYMMDD));
			book.setAddTime(new Date());
			bookDao.save(book);
		}
		MessageUtil.success("预约成功", model);
		return model;
	}

}
