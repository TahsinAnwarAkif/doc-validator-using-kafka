package com.sdase.docvalidator.util;

import java.util.Arrays;

/**
 * @author akif
 * @since 3/11/22
 */
public enum FileType {

	PDF("pdf"),
	DOC("doc"),
	DOCX("docx"),
	TXT("txt");

	private String naturalName;

	FileType(String naturalName) {
		this.naturalName = naturalName;
	}

	public String getNaturalName() {
		return naturalName;
	}

	public static FileType getFileType(String naturalName) {
		return Arrays.stream(FileType.values())
			.filter(fileType -> fileType.getNaturalName().equals(naturalName))
			.findFirst()
			.orElse(null);

	}
}
