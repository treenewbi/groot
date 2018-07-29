package com.huangwu.exception;

import com.huangwu.common.ErrorCode;
import com.huangwu.common.Result;
import com.huangwu.common.ErrorCode;
import com.huangwu.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSendException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

import static com.huangwu.common.ErrorCode.INVALID_MAIL_ADDRESS;
import static com.huangwu.common.ErrorCode.MAIL_SEND_ERROR;

/**
 * 全局异常处理handler
 *
 * @Package: com.huangwu.exception
 * @Author: huangwu
 * @Date: 2018/5/19 16:24
 * @Description:
 * @LastModify:
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {
        try {
            if (e instanceof EtcdException) {
                EtcdException exception = (EtcdException) e;
                logger.error("EtcdException", exception);
                Integer errorcode = 0;
                if (exception.errorResponse != null && exception.errorResponse.getErrorCode() != 0) {
                    errorcode = exception.errorResponse.getErrorCode();
                }
                exception.errorResponse.getErrorCode();
                return Result.error(errorcode.toString(), exception.getErrorResponse().getMessage());
            } else if (e instanceof BindException) {
                BindException exception = (BindException) e;
                logger.error("BindException", exception, exception.getMessage());
                return Result.error(ErrorCode.SERVER_ERROR.getCode(), exception.getMessage());
            } else if (e instanceof UnknownHostException) {
                UnknownHostException exception = (UnknownHostException) e;
                logger.error("UnknownHostException", exception);
                return Result.error(ErrorCode.ERCD_ADDRESS_NOT_EXIST);
            } else if (e instanceof GlobalException) {
                GlobalException exception = (GlobalException) e;
                logger.error("GlobalException[{errorCode},[{message}]]", exception.getErrorCode(), exception.getMessage());
                logger.error("GlobalException", exception);
                return Result.error(exception.getErrorCode(), exception.getMessage());
            } else if (e instanceof MethodArgumentNotValidException) {
                MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
                String message;
                try {
                    message = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
                } catch (Exception e1) {
                    message = ErrorCode.ETCD_PARAMETER_ERROR.getMessage();
                }
                logger.error("MethodArgumentNotValidException", exception);
                return Result.error(ErrorCode.ETCD_PARAMETER_ERROR.getCode(), message);
            } else if (e instanceof MessagingException) {
                MessagingException exception = (MessagingException) e;
                logger.error("MessagingException ", exception);
                return Result.error(ErrorCode.MAIL_SEND_ERROR);
            } else if (e instanceof MailSendException) {
                MailSendException exception = (MailSendException) e;
                logger.error("SendFailedException", exception);
                return Result.error(ErrorCode.INVALID_MAIL_ADDRESS);
            }
            else {
                logger.error("Exception", e);
                return Result.error(ErrorCode.SERVER_ERROR);
            }
        } finally {
            String url = request.getRequestURL().toString();
            String ip = request.getRemoteAddr();
            logger.error("Processing failed requests from ip[{}],url[{}]", ip, url);
        }

    }
}
