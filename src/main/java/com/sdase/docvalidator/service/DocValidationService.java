package com.sdase.docvalidator.service;

import com.sdase.docvalidator.dto.CheckEvent;
import com.sdase.docvalidator.exception.RestTemplateExceptionResolver;
import com.sdase.docvalidator.util.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import static com.sdase.docvalidator.util.FileType.values;
import static com.sdase.docvalidator.util.FileUtils.getFileSendingDirectory;

/**
 * @author akif
 * @since 3/10/22
 */
@Service
public class DocValidationService {

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	public JSONObject getValidatedIbanDetailsJson(final String iban) {
		final String url = getIbanValidatorApiUrlWithIban(iban);
		final HttpHeaders headers = getPreparedHeaders();
		final JSONObject validatedResultJson = getRestTemplate().exchange(url, HttpMethod.GET,
			new HttpEntity<>("body", headers), JSONObject.class).getBody();

		return Objects.requireNonNull(validatedResultJson);
	}

	public CheckEvent getPreparedCheckEvent(final CheckEvent event, final boolean isLocalFile) throws IOException {
		final File fileSendingDirectory = getFileSendingDirectory();
		final File fileToBeSent = new File(fileSendingDirectory.getAbsolutePath() + File.separator
			+ "file_" + Utils.getUniqueId() + "." + event.getFileType());

		if (isLocalFile) {
			FileUtils.copyFile(new File(event.getUrl()), fileToBeSent);
		} else {
			final URL url = new URL(event.getUrl());
			URLConnection uc = url.openConnection();

			uc.connect();
			uc = url.openConnection();
			uc.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			uc.getInputStream();
			FileUtils.copyInputStreamToFile(uc.getInputStream(), fileToBeSent);
		}

		event.setFile(fileToBeSent);

		return event;
	}

	public boolean isEventInvalid(final CheckEvent file) {
		return !Arrays.asList(values()).contains(FileType.getFileType(file.getFileType()));
	}

	public HttpHeaders getPreparedHeaders() {
		final HttpHeaders headers = new HttpHeaders();
		headers.add("user-agent", "Application");
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		return headers;
	}

	private RestTemplate getRestTemplate() {
		return restTemplateBuilder
			.errorHandler(new RestTemplateExceptionResolver())
			.build();
	}

	private String getIbanValidatorApiUrlWithIban(final String iban) {
		return Constants.IBAN_VALIDATOR_URL.replace("{iban}", StringUtils.nullSafeString(iban));
	}
}
