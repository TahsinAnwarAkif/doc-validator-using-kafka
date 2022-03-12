package com.sdase.docvalidator.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class FileUtils {

	public static File getFileSendingDirectory() {
		String path = System.getProperty("user.home");
		path += File.separator + "validated-files";
		File directory = new File(path);

		if (!directory.exists()) {
			directory.mkdirs();
		}

		return directory;
	}

	public static String getText(String fileName, FileType fileType) {
		final String text;

		switch (fileType) {
			case DOC:
			case DOCX:
			case TXT:
				text = getTextFromDocFile(fileName);

				break;

			case PDF:
				text = getTextFromPdfFile(fileName);

				break;

			default:
				throw new IllegalArgumentException("Invalid FileType!");
		}

		return text;
	}

	private static String getTextFromDocFile(String fileName) {
		final File file = new File(fileName);
		final StringBuilder text = new StringBuilder();

		try (final Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				final String line = scanner.nextLine();
				text.append(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return text.toString();
	}

	private static String getTextFromPdfFile(String fileName) {
		String text = null;

		try (PDDocument document = PDDocument.load(new File(fileName))) {
			if (!document.isEncrypted()) {
				PDFTextStripper stripper = new PDFTextStripper();
				text = stripper.getText(document);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return text;
	}
}
