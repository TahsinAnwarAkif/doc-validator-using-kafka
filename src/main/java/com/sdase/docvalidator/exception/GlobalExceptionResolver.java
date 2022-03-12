package com.sdase.docvalidator.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.sdase.docvalidator.util.Constants.ERROR_MSG;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author akif
 * @since 3/10/22
 */
@ControllerAdvice
public class GlobalExceptionResolver {

	@ExceptionHandler(MissingServletRequestParameterException.class)
	protected ResponseEntity<Object> handleMissingServletReqParamException(MissingServletRequestParameterException e) {
		Map<String, Object> json = new LinkedHashMap<>();
		json.put("status", ERROR_MSG);
		json.put("error", e.getMessage());

		return new ResponseEntity<>(json, BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Object> handleException(HttpMessageNotReadableException e) {
		Map<String, Object> json = new LinkedHashMap<>();
		json.put("status", ERROR_MSG);
		json.put("error", e.getMessage());

		return new ResponseEntity<>(json, BAD_REQUEST);
	}
}
