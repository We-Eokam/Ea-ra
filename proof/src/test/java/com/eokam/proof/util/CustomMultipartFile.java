package com.eokam.proof.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public class CustomMultipartFile implements MultipartFile {
	private String name;
	private String originalFileName;
	private String contentType;
	private byte[] input;

	public CustomMultipartFile(String name, String originalFileName, String contentType, byte[] input) {
		this.name = name;
		this.originalFileName = originalFileName;
		this.contentType = contentType;
		this.input = input;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getOriginalFilename() {
		return originalFileName;
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public long getSize() {
		return 0;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return input;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return null;
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {

	}
	//We've defined the rest of the interface methods in the next snippet
}
