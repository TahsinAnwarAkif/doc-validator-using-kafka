package com.sdase.docvalidator.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

/**
 * @author akif
 * @since 3/10/22
 */
@RestControllerAdvice
@EnableWebMvc
public class RestTemplateExceptionResolver implements ResponseErrorHandler {

	private static final Logger log = LoggerFactory.getLogger(RestTemplateExceptionResolver.class);

	@Override
	public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
		return httpResponse.getStatusCode().series() == CLIENT_ERROR
			|| httpResponse.getStatusCode().series() == SERVER_ERROR;
	}

	@Override
	public void handleError(ClientHttpResponse httpResponse) throws IOException {
		if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
			log.error("Server Error!");
		} else if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
			log.error("Client Error!");

			if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
				log.error("Not Found!");
			}
		}
	}
}
