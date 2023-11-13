package com.eokam.member.application.service;

import static org.mockito.BDDMockito.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.*;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.eokam.member.infra.dto.JwtMemberDto;
import com.eokam.member.infra.external.service.S3Service;

public class S3ServiceTest extends BaseServiceTest{

	@InjectMocks
	private S3Service s3Service;

	@Mock
	private AmazonS3 amazonS3;

	@Test
	public void 프로필이미지_S3에_저장() throws IOException {
		//given
		ClassPathResource resource = new ClassPathResource("static/eora.png");


		String 변경할_파일이름 = "eora";
		String 변경할_파일이름_확장자 = "eora.png";


		MockMultipartFile file =
			new MockMultipartFile(
				변경할_파일이름,
				변경할_파일이름_확장자,
				MediaType.IMAGE_PNG_VALUE,
				resource.getContentAsByteArray()
			);

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(file.getSize());
		metadata.setContentType(file.getContentType());

		given(amazonS3.getUrl(any(),any()))
			.willReturn(new URL("http://test.jpg"));
		//when
		var 저장_결과 = s3Service.save(file);

		//then
		assertThat(저장_결과.getFileName())
			.isNotNull();

		assertThat(저장_결과.getUrl())
			.isEqualTo("http://test.jpg");

	}
}
