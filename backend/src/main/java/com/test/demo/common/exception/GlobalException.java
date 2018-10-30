package com.test.demo.common.exception;

import com.test.demo.common.httpHelper.StatusHelper;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalException {
//    private Logger logger = Logger.getLogger(GlobalException.class);
//    @Autowired
//    private StatusHelper statusHelper;
//    @ResponseBody
//    @ExceptionHandler(value = ApiHttpCodeException.class)
//    public JSONObject apiHttpCodeErrorHandler(ApiHttpCodeException ex) {
//        JSONObject exception = new JSONObject();
//        exception.put(ex.getCode(),ex.getMsg());
//        logger.info("ApiHttpCodeException:"+exception);
//        return exception;
//    }
//    @ResponseBody
//    @ExceptionHandler(value = Exception.class)
//    public JSONObject serviceErrorHandler() {
//        JSONObject exception = new JSONObject();
//        exception.put(statusHelper.getSTATUS(), statusHelper.getSERVICEERROR());
//        logger.info("exception:"+exception);
//        return exception;
//    }
//    @ResponseBody
//    @ExceptionHandler(value = java.net.ConnectException.class)
//    public JSONObject connectErrorHandler() {
//        return apiHttpCodeErrorHandler(new ApiHttpCodeException(statusHelper.getSTATUS(),statusHelper.getHOSTORURL()));
//
//    }
}
