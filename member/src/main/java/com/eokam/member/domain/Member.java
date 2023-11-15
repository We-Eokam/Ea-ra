package com.eokam.member.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.eokam.member.infra.external.S3FileDetail;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nickname;

	private String profileImageUrl;

	private String profileImageFileName;

	private Long socialId;

	private Integer groo = 0;

	private Integer repayGroo = 0;

	private Integer billCount = 0;

	private Integer bill = 0;

	private Boolean isTestDone = false;

	public void repayGroo(SavingType savingType,Integer amount){
		if(savingType.equals(SavingType.PROOF)){
			this.repayGroo += amount;
		}
		if(savingType.equals(SavingType.ACCUSATION)){
			this.groo += amount;
		}
	}

	public Boolean addBillCount(){
		if(this.billCount<2){
			this.billCount++;
			return true;
		}
		else{
			this.billCount = 0;
			this.bill++;
			return false;
		}
	}

	public Boolean useBill(){
		 if(this.bill == 0){
			 return false;
		 }
		 else{
			 this.bill--;
			 return true;
		 }
	}

	public void changeNickname(String nickname){
		this.nickname = nickname;
	}

	public void changeProfileImage(String fileName, String url){
		this.profileImageUrl  = url;
		this.profileImageFileName = fileName;
	}

	public Boolean finishTest(Integer groo){
		if(this.isTestDone){
			return false;
		}
		this.groo += groo;
		this.isTestDone = true;
		return true;
	}

	@Builder
	public Member(Long id, String nickname, String profileImageUrl, String profileImageFileName,Long socialId) {
		this.id = id;
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
		this.profileImageFileName = profileImageFileName;
		this.socialId = socialId;
		this.groo = 0;
		this.repayGroo = 0;
		this.billCount = 0;
		this.bill = 0;
		this.isTestDone = false;
	}
}
