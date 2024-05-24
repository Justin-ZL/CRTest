/**
 * deepwisdom
 */

package com.deepwisdom.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.deepwisdom.common.annotation.SysLog;
import com.deepwisdom.common.utils.*;
import com.deepwisdom.modules.common.entity.SysLogEntity;
import com.deepwisdom.modules.common.entity.SysUserEntity;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Objects;


/**
 * 系统日志，切面处理类
 *
 * @author justin zhanglei@fuzhi.ai
 */
@Aspect
@Component
@Slf4j
public class SysLogAspect {

	@Value("${out.url.admin}")
	private String adminUrl;

	@Autowired
	private RedisUtils redisUtils;

	@Pointcut("@annotation(com.deepwisdom.common.annotation.SysLog)")
	public void logPointCut() { 
		
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;

		//保存日志
		saveSysLog(point, time);

		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		SysLogEntity sysLog = new SysLogEntity();
		SysLog syslog = method.getAnnotation(SysLog.class);
		if(syslog != null){
			//注解上的描述
			sysLog.setOperation(syslog.value());
		}

		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");

		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = new Gson().toJson(args);
			sysLog.setParams(params);
		}catch (Exception e){

		}

		//获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		//设置IP地址
		sysLog.setIp(IPUtils.getIpAddr(request));

		//用户名
		SysUserEntity user = redisUtils.get(RedisKeys.getUserSessionKey(request.getHeader("token")), SysUserEntity.class);
		if(Objects.isNull(user)){
			sysLog.setUsername("第三方用户");
		}else {
			sysLog.setUsername(user.getUsername());
		}
		sysLog.setTime(time);
		sysLog.setCreateDate(new Date());
		//保存系统日志
		RestTemplate restTemplate = new RestTemplate();
		try{
			restTemplate.postForObject(adminUrl,sysLog, R.class);
		}catch (Exception e){
			log.error("提交操作日志" + JSONObject.toJSONString(sysLog) + "失败");
		}
	}
}
