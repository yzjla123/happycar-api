package com.happycar.api.aspect;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.happycar.api.annotation.Authentication;
import com.happycar.api.contant.Constant;
import com.happycar.api.contant.ErrorCode;
import com.happycar.api.dao.MemberDao;
import com.happycar.api.exception.BussinessException;
import com.happycar.api.model.HcMember;
import com.happycar.api.utils.RedisUtil;
import com.happycar.api.utils.StringUtil;


@Aspect
@Component
public class AuthenticationAspect {
	
	@Resource
	private MemberDao memberDao;
	
	/**
     * 对Api进行安全和身份校验  
	 * @throws Throwable 
     */
    @Around(value="execution(* com.happycar.api.controller.member.*.*(..))")
    public Object validIdentityAndSecure(ProceedingJoinPoint pjp) throws Throwable {
        
        MethodSignature joinPointObject=(MethodSignature) pjp.getSignature();
        Method method = joinPointObject.getMethod();
        Authentication authentication=null;
        //类名上是否有authentication
        authentication=pjp.getTarget().getClass().getAnnotation(Authentication.class);
        //方法名上是否有authentication
        if(authentication==null)  authentication=method.getAnnotation(Authentication.class);
        if(authentication!=null){
        	HttpServletRequest request=getRequest(pjp);
        	if(request==null) throw new BussinessException("该方法未定义Request参数");
        	String token = request.getParameter(Constant.KEY_ACCESS_TOKEN);
        	if(StringUtil.isNull(token))  throw new BussinessException("token参数不能为空");
        	//当前登录用户信息
        	String pid = RedisUtil.getString(Constant.KEY_ACCESS_TOKEN+token);
        	if(StringUtil.isNull(pid))  throw new BussinessException(ErrorCode.TOKEN_INVALID);
        	HcMember member = memberDao.findOne(Integer.parseInt(pid));
        	request.setAttribute(Constant.KEY_LOGIN_PASSENGER, member);
        }
        
        return pjp.proceed();
    }
    
    /**
     * 获取当前拦截对象的request请求对象
     * @param pjp
     * @return
     */
    private HttpServletRequest getRequest(ProceedingJoinPoint pjp) {
    	 Object[] args = pjp.getArgs();
    	 for (Object object : args) {
			if(object instanceof HttpServletRequest){
				return (HttpServletRequest) object;
			}
		}
    	return null;
    }
}
