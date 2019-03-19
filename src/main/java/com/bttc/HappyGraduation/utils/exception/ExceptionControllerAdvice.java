package com.bttc.HappyGraduation.utils.exception;

import com.bttc.HappyGraduation.utils.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
* <p>Title: ExceptionControllerAdvice</p>
* <p>Description: Controller增强器：捕捉异常进行处理</p> 
* @author liuxf6
* @date 2018年5月21日
*/
@RestControllerAdvice
public class ExceptionControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    /**
    * <p>Title: exceptionHandler</p>
    * <p>Description: 全局异常捕捉处理</p>
    */
    @ExceptionHandler(value = Exception.class)
    public ResultBean exceptionHandler(Exception e) {
    	if (logger.isErrorEnabled()) {
    		logger.error(e.getMessage(), e);
        }
        return ResultBean.internalServerError("系统异常：请联系管理员！");
    }
    
    /**
    * <p>Title: businessExceptionHandler</p>
    * <p>Description: 拦截捕捉业务异常（自定义异常）</p>
    */
    @ExceptionHandler(value = BusinessException.class)
    public ResultBean businessExceptionHandler(BusinessException e) {
    	if (logger.isErrorEnabled()) {
    		logger.error("业务异常编码为：" + e.getCode(), e);
    	}
        return ResultBean.internalServerError(e.getCode(), e.getMessage());
    }

}
