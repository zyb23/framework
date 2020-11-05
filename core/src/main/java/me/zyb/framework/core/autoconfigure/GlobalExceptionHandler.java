package me.zyb.framework.core.autoconfigure;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.ReturnCode;
import me.zyb.framework.core.base.BaseException;
import me.zyb.framework.core.constant.ConstNumber;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局异常处理，使用时，实例化为bean到spring容器即可
 * @author zhangyingbin
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler implements HandlerExceptionResolver {
	@Override
	public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
		ModelAndView mav = new ModelAndView();
		FastJsonJsonView view = new FastJsonJsonView();
		Map<String, Object> attributes = new HashMap<String, Object>(ConstNumber.INITIAL_CAPACITY);

		log.error("执行异常：", e);
		attributes.put("code", ReturnCode.SYSTEM_BUSY.getValue());
		attributes.put("message", StringUtils.isBlank(e.getMessage()) ? e.toString() : e.getMessage());
		view.setAttributesMap(attributes);
		mav.setView(view);

		return mav;
	}

	/**
	 * 业务异常
	 * @return  Object
	 */
	@ExceptionHandler(BaseException.class)
	public ModelAndView handleBaseException(BaseException e) {
		ModelAndView mav = new ModelAndView();
		FastJsonJsonView view = new FastJsonJsonView();
		Map<String, Object> attributes = new HashMap<String, Object>(ConstNumber.INITIAL_CAPACITY);

		if(StringUtils.isNotBlank(e.getCode())){
			attributes.put("code", e.getCode());
		} else {
			attributes.put("code", ReturnCode.FAILURE.getValue());
		}
		attributes.put("message", StringUtils.isBlank(e.getMessage()) ? e.toString() : e.getMessage());
		view.setAttributesMap(attributes);
		mav.setView(view);
		return mav;
	}

	/**
	 * 参数校验异常
	 * @return  Object
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ModelAndView handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		ModelAndView mav = new ModelAndView();
		FastJsonJsonView view = new FastJsonJsonView();
		Map<String, Object> attributes = new HashMap<String, Object>(ConstNumber.INITIAL_CAPACITY);
		BindingResult bindingResult = e.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		StringBuilder stringBuilder = new StringBuilder(fieldErrors.size() * 16);
		for(int i = 0; i < fieldErrors.size(); i++){
			if(i > 0){
				stringBuilder.append(",");
			}
			FieldError fieldError = fieldErrors.get(i);
			stringBuilder.append(fieldError.getField());
			stringBuilder.append(":");
			stringBuilder.append(fieldError.getDefaultMessage());
		}

		attributes.put("code", ReturnCode.PARAMETER_ERROR.getValue());
		attributes.put("message", stringBuilder.toString());
		view.setAttributesMap(attributes);
		mav.setView(view);
		return mav;
	}
}
