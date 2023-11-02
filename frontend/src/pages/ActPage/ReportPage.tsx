// import React from 'react'
import { useState } from "react";
import HeadBar from "../../components/HeadBar/HeadBar";
import MainFrame from "../../components/MainFrame/MainFrame";
import styled from "styled-components";
import ImageCropper from "../../components/ImageCropper/ImageCropper";
import { LongButton } from "../../style";

export default function ReportPage() {
  const [croppedImage, setCroppedImage] = useState<string | null>(null);
  const [reportingImg, setReportingImg] = useState("");
  const [imgModalOpen, setImgModalOpen] = useState(false);
  const [friend, setFriend] = useState("");
  const [friendModalOpen, setFriendModalOpen] = useState(false);

  const handleImageCrop = (image: string) => {
    setCroppedImage(image);
  };

  return (
    <>
      <HeadBar pagename="" bgcolor="white" backbutton="yes" />
      <MainFrame headbar="yes" navbar="no" bgcolor="white" marginsize="large">
        <InfoFrame>
          경고장 고르기 (누르면 모달로 경고장 고르게 하기)
        </InfoFrame>
        <InfoFrame>
          <InfoName>경고 종류</InfoName>
          물인지 전기인지 경고장 고르면 자동으로 표시되게
        </InfoFrame>
        <InfoFrame>
          <InfoName>제보할 친구</InfoName>
          누르면 모달로 제보할 친구 목록 보여줌
        </InfoFrame>
        <InfoFrame>
          <InfoName>증거 사진 제출</InfoName>
          <ImageCropper onCrop={handleImageCrop}>
            {croppedImage ? (
              <CropImg src={croppedImage} alt="Cropped" />
            ) : (
              <ImgIcon src="../src/assets/images/upload-image-icon.png" />
            )}
          </ImageCropper>
        </InfoFrame>
        <LongButton  background="var(--red)">경고하기</LongButton>

        {imgModalOpen && (
          <></>
        )}

        {friendModalOpen && (
          <></>
        )}
      </MainFrame>
    </>
  );
}

const CropImg = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;

const ImgIcon = styled.img`
  width: 36%;
  padding: 32%;
`;

const InfoFrame = styled.div`
  width: 100%;
  margin: 32px 0;
`;

const InfoName = styled.div`
  position: relative;
  width: 100%;
  font-size: 14px;
  color: var(--dark-gray);
`;
