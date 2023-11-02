// import React from 'react'
import { useState } from "react";
import HeadBarCenter from "../../components/HeadBar/HeadBarCenter";
import MainFrame from "../../components/MainFrame/MainFrame";
import styled from "styled-components";
import AnimationModal from "../../components/Modal/AnimationModal";
import ImageCropper from "../../components/ImageCropper/ImageCropper";
import { LongButton, ButtonFrame } from "../../style";

export default function ReportPage() {
  const [croppedImage, setCroppedImage] = useState<string | null>(null);
  const [reportingImg, setReportingImg] = useState("");
  const [imgSelectorOpen, setImgSelectorOpen] = useState(false);
  const [friend, setFriend] = useState("");
  const [friendModalOpen, setFriendModalOpen] = useState(false);
  const reportTypes = [
    {
      type: "PLASTIC",
      content: "일회용품 사용",
      example: "카페 일회용컵 사용, 비닐봉지 사용, 배달 용기 사용",
    },
    {
      type: "PAPER",
      content: "종이 낭비",
      example: "???",
    },
    {
      type: "ELECTRICITY",
      content: "전기 낭비",
      example: "플러그 안뽑기, 빈 방에 불 켜놓기",
    },
    {
      type: "WATER",
      content: "물 낭비",
      example: "양치 컵 미사용, 물 틀어놓고 설거지",
    },
    {
      type: "FOOD",
      content: "??",
      example: "",
    },
    {
      type: "OTHER",
      content: "",
      example: "기타 환경에 오염되는 활동을 목격했다면 ~~",
    },
  ];

  const handleImageCrop = (image: string) => {
    setCroppedImage(image);
  };

  const handleFriendModalClose = () => {
    setFriendModalOpen(false);
  };

  const handleImgSelector = () => {
    setImgSelectorOpen((prev) => !prev);
  }

  return (
    <>
      <HeadBarCenter pagename="경고장 작성" bgcolor="white" backbutton="yes" />
      <MainFrame headbar="yes" navbar="no" bgcolor="white" marginsize="large">
        {/* <InfoFrame>
          경고장 고르기 (누르면 모달로 경고장 고르게 하기)
        </InfoFrame> */}
        <InfoFrame>
          <InfoName onClick={handleImgSelector}>경고장 선택</InfoName>
          <InfoButtonsFrame isShow={imgSelectorOpen}>
            <InfoButton>
              <Templete
                src="../src/assets/images/templete1.png"
              />
            </InfoButton>
            <InfoButton>
              <Templete
                src="../src/assets/images/templete2.png"
              />
            </InfoButton>
            <InfoButton>
              <Templete
                src="../src/assets/images/templete3.png"
              />
            </InfoButton>
            <InfoButton>
              <Templete
                src="../src/assets/images/templete1.png"
              />
            </InfoButton>
            <InfoButton>
              <Templete
                src="../src/assets/images/templete2.png"
              />
            </InfoButton>
            <InfoButton>
              <Templete
                src="../src/assets/images/templete3.png"
              />
            </InfoButton>
          </InfoButtonsFrame>
        </InfoFrame>
        <InfoFrame>
          <InfoName onClick={() => setFriendModalOpen(true)}>제보할 친구</InfoName>
          누르면 모달로 제보할 친구 목록 보여줌
        </InfoFrame>
        <InfoFrame>
          <InfoName>증거 사진 제출</InfoName>
          <ImageCropper onCrop={handleImageCrop}>
            {croppedImage ? (
              <Templete src={croppedImage} alt="Cropped" />
            ) : (
              <Templete src="../src/assets/images/upload-image-icon.png" />
            )}
          </ImageCropper>
        </InfoFrame>

        <Margin />
        <ButtonFrame>
          <LongButton background="var(--red)">경고장 보내기</LongButton>
        </ButtonFrame>

        {/* 친구 목록 뜨는 모달 */}
        <AnimationModal
          isOpen={friendModalOpen}
          closeModal={handleFriendModalClose}
          closeBtn={true}
        >
          친구 목록이 뜰 거 에 오
          <br />아직 안만들었어요
        </AnimationModal>
      </MainFrame>
    </>
  );
}

const Templete = styled.img`
  width: 100%;
`;

const InfoFrame = styled.div`
  width: 100%;
  margin: 32px 0;
`;

const InfoButtonsFrame = styled.div<{isShow: boolean}>`
  position: relative;
  width: calc(100%);
  height: ${({ isShow }) => (isShow ? "132px" : "0px")};
  transition: height 0.3s ease;
  margin-top: 8px;
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
  justify-content: center;
  overflow-x: auto;
`

const InfoButton = styled.div`
  width: 110px;
  height: 116px;
  background-color: var(--white);
  border-radius: 10px;
  box-shadow: 2px 2px 6px rgba(0,0,0, 0.04);
  margin-right: 16px;
`

const InfoName = styled.div`
  position: relative;
  width: 100%;
  font-size: 14px;
  color: var(--dark-gray);
  margin-bottom: 12px;
`;

const Margin = styled.div`
  margin: 88px;
`;
