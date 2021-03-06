package com.cesske.mps.config;

import com.cesske.mps.constants.CommonConst;
import com.cesske.mps.constants.ResultCodeConst;
import com.cesske.mps.model.ServiceResponse;
import com.cesske.mps.utils.StringUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.MultipartProperties;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 全局异常通知
 */
@ControllerAdvice
@Slf4j
class GlobalException {


    /**
     * 数据校验失败
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ServiceResponse handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        Map<String, String> errors = Maps.newHashMap();
        String serverId = ServletRequestUtils.getStringParameter(request, CommonConst.TRACE_ID, "");
        log.error("Parameter check failed", ex);
        ex.getBindingResult().getFieldErrors().stream().forEach(fieldError ->
                errors.put(fieldError.getField(), fieldError.getDefaultMessage())
        );
        String msg = ex.getBindingResult().getFieldErrors() != null && !ex.getBindingResult().getFieldErrors().isEmpty() ? ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage() : "";

        return ServiceResponse.createFailResponse(serverId, ResultCodeConst.PARAMETER_ERROR, errors, msg);

    }

    /**
     * 请求方式异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ServiceResponse methodNotSupportedException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        Map<String, String> errors = Maps.newHashMap();
        String serverId = ServletRequestUtils.getStringParameter(request, CommonConst.TRACE_ID, "");
        log.error("Parameter check failed", ex);
        ex.getBindingResult().getFieldErrors().stream().forEach(fieldError ->
                errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
        String msg = ex.getBindingResult().getFieldErrors() != null && !ex.getBindingResult().getFieldErrors().isEmpty() ? ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage() : "";
        return ServiceResponse.createFailResponse(serverId, ResultCodeConst.PARAMETER_ERROR, errors, msg);

    }

    /**
     * 数据绑定失败
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ServiceResponse handleBindException(HttpServletRequest request, BindException ex) {
        Map<String, String> errors = Maps.newHashMap();
        String serverId = ServletRequestUtils.getStringParameter(request, CommonConst.TRACE_ID, "");
        log.error("Parameter binding failed", ex);
        ex.getFieldErrors().stream().forEach(fieldError ->
                errors.put(fieldError.getField(), fieldError.getDefaultMessage())
        );
        String msg = ex.getFieldErrors() != null && !ex.getFieldErrors().isEmpty() ? ex.getFieldErrors().get(0).getDefaultMessage() : "";
        return ServiceResponse.createFailResponse(serverId, ResultCodeConst.PARAMETER_ERROR, errors, msg);

    }

    /**
     * 数据绑定失败
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ServiceResponse ServletRequestParameterBindException(HttpServletRequest request, MissingServletRequestParameterException ex) {
        Map<String, String> errors = Maps.newHashMap();
        String serverId = ServletRequestUtils.getStringParameter(request, CommonConst.TRACE_ID, "");
        log.error("ServletRequest Parameter binding failed", ex);
        errors.put(ex.getParameterName(), ex.getLocalizedMessage());
        return ServiceResponse.createFailResponse(serverId, ResultCodeConst.PARAMETER_ERROR, errors, ex.getMessage());

    }


    /**
     * 500 - Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ServiceResponse handleException(HttpServletRequest request, Exception ex) {
        String serverId = ServletRequestUtils.getStringParameter(request, CommonConst.TRACE_ID, "");
        log.error("excption_global "+ ex.getMessage().replaceAll("\r\n","")+" exception_str "+StringUtils.ExpToString(ex));
        ServiceResponse serviceResponse = ServiceResponse.defaultFailResponse(serverId);
        return serviceResponse;
    }

    @Autowired
    private MultipartProperties multipartProperties;
    /**
     * 处理文件上传大小超限制
     * @param ex
     * @return
     */
    @ExceptionHandler(value = MultipartException.class)
    @ResponseBody
    public ServiceResponse fileUploadExceptionHandler(HttpServletRequest request, MultipartException ex)  {
        String serverId = ServletRequestUtils.getStringParameter(request, CommonConst.TRACE_ID, "");
        String msg;
        Map<String, String> errors = Maps.newHashMap();
//        Throwable cause = ex.getRootCause();
        Throwable cause = ex.getCause().getCause();
        if (cause instanceof FileUploadBase.FileSizeLimitExceededException) {
            msg="上传文件过大[单个文件大小不得超过" + multipartProperties.getMaxFileSize() + "]";
            //log.error(ex.getMessage());
        }else if(cause instanceof FileUploadBase.SizeLimitExceededException){
            msg="上传文件过大[总上传大小不得超过" + multipartProperties.getMaxRequestSize() + "]";
            //log.error(ex.getMessage());
        }else {
            msg="文件上传失败[服务器异常]";
            //log.error(ex.getMessage());
        }
        //log.error(msg, ex);
        return ServiceResponse.createFailResponse(serverId, ResultCodeConst.PROMPT_ERROR, errors, msg);
    }

}
