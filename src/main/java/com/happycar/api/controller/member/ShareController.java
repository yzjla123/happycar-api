package com.happycar.api.controller.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.annotation.Authentication;
import com.happycar.api.contant.Constant;
import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.SysParamDao;
import com.happycar.api.model.HcMember;
import com.happycar.api.model.HcSysParam;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.utils.RedisUtil;
import com.happycar.api.utils.SpringContextUtil;
import com.happycar.api.utils.StringUtil;
import com.happycar.api.vo.HcShareVO;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "分享管理")
@RestController
@RequestMapping("/api/member/share")
public class ShareController extends BaseController {
	private Logger logger = Logger.getLogger(ShareController.class);
	
	@ApiOperation(value = "分享信息", httpMethod = "GET", notes = "获取分享信息")
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
	})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "") })
	@Authentication
	public ResponseModel share(HttpServletRequest request, HttpServletResponse response) {
		ResponseModel model = new ResponseModel();
		HcMember member = getLoginMember(request);
		SysParamDao paramDao = (SysParamDao) SpringContextUtil.getApplicationContext().getBean(SysParamDao.class);
		HcSysParam param = paramDao.findByCode(Constant.PARAM_CODE_SHARE);
		HcShareVO shareVO = new HcShareVO();
		shareVO.setTitle(param.getExt1());
		shareVO.setContent(param.getExt2());
		shareVO.setHref(param.getExt3()+"?phone="+member.getPhone());
		shareVO.setPic(param.getExt4());
		model.addAttribute("share", shareVO);
		MessageUtil.success("操作成功!", model);
		return model;
	}
}
