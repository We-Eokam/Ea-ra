package com.eokam.accusation.unit;

import static org.mockito.ArgumentMatchers.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.eokam.accusation.infrastructure.service.S3Service;

public class S3ServiceTest {

	@Spy
	@InjectMocks
	private S3Service s3Service;

	@Mock
	private AmazonS3 amazonS3Client;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("성공적으로 업로드 된 파일의 URL들을 반환한다.")
	public void getFileUrls() throws IOException {
		List<MultipartFile> multipartFile = new ArrayList<>();
		MockMultipartFile file1 = new MockMultipartFile("file", "filename.png", "image/png",
			"image1".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("file", "filename2.png", "image/png",
			"image222".getBytes());
		multipartFile.add(file1);
		multipartFile.add(file2);

		String fileName1 = s3Service.makeFileName(file1.getOriginalFilename());
		String fileName2 = s3Service.makeFileName(file2.getOriginalFilename());

		BDDMockito.given(s3Service.makeFileName(eq(file1.getOriginalFilename()))).willReturn(fileName1);
		BDDMockito.given(s3Service.makeFileName(eq(file2.getOriginalFilename()))).willReturn(fileName2);
		BDDMockito.given(amazonS3Client.putObject(any())).willReturn(null);
		BDDMockito.given(amazonS3Client.getUrl(any(), eq(fileName1))).willReturn(new URL("http://file1URL"));
		BDDMockito.given(amazonS3Client.getUrl(any(), eq(fileName2))).willReturn(new URL("http://file2URL"));

		// when
		List<String> fileUrls = s3Service.uploadFile(multipartFile);

		// then
		Assertions.assertThat(fileUrls).hasSize(2);
		Assertions.assertThat(fileUrls.get(0)).isEqualTo("http://file1URL");
		Assertions.assertThat(fileUrls.get(1)).isEqualTo("http://file2URL");
	}

	@Test
	@DisplayName("파일 이름을 생성한다.")
	public void makeFileName() {
		// given
		String fileName = "fileName.png";

		// when
		String madeFileName = s3Service.makeFileName(fileName);

		// then
		boolean matches = madeFileName.matches(
			"\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])-\\d{2}-\\d{7}_" + fileName);
		Assertions.assertThat(matches).isTrue();
	}
}
