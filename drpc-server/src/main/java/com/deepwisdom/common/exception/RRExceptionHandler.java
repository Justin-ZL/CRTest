

package com.deepwisdom.common.exception;

import com.deepwisdom.common.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
* @ClassName: RRExceptionHandler.java
* @Author: justin(zhanglei@fuzhi.ai)
* @Date: 2021/8/2 16:26
* @Description: 异常处理器
* @Version: 1.0
*/
@RestControllerAdvice
public class RRExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(RRException.class)
	public R handleRRException(RRException e){
		R r = new R();
		r.put("code", e.getCode());
		r.put("msg", e.getMessage());

		return r;
	}

//	@ExceptionHandler(NoHandlerFoundException.class)
//	public R handlerNoFoundException(Exception e) {
//		logger.error(e.getMessage(), e);
//		return R.error(404, "路径不存在，请检查路径是否正确");
//	}
//
//	@ExceptionHandler(DuplicateKeyException.class)
//	public R handleDuplicateKeyException(DuplicateKeyException e){
//		logger.error(e.getMessage(), e);
//		return R.error("数据库中已存在该记录");
//	}

/*
	@ExceptionHandler(Exception.class)
	public R handleException(Exception e){
		logger.error(e.getMessage(), e);
		return R.error();
	}*/
}
