// import React from 'react'
import { useState, useEffect } from "react";
import styled from "styled-components";

import HeadBar from "../../components/HeadBar/HeadBar";
import MainFrame from "../../components/MainFrame/MainFrame";
import { ModalBackground } from "../../components/Modal/ModalBackground";
import DetailInput from "../../components/Input/DetailInput";
import SearchBar from "../../components/SearchBar/SearchBar";
import AnimationModal from "../../components/Modal/AnimationModal";
import ImageCropper from "../../components/ImageCropper/ImageCropper";
import { LongButton, ButtonFrame } from "../../style";
import { ReactComponent as DropdownSvg } from "../../assets/icons/dropdown.svg";
import reportTypes from "../../common/report.json"

interface ReportTypeProps {
  type: string;
  content: string;
  example: string;
  imgUrl: string;
}

export default function ReportPage() {
  const [activityType, setActivityType] = useState<ReportTypeProps>(
    reportTypes[0]
  );
  const [activityDetail, setActivityDetail] = useState("");
  const [imgSelectorOpen, setImgSelectorOpen] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  // @ts-ignore
  const [friendId, setFriendId] = useState<number | null>(null);
  const [friendModalOpen, setFriendModalOpen] = useState(false);
  const [croppedImage, setCroppedImage] = useState<string | null>(null);
  
  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const targetFromURL = Number(params.get("target"));
    console.log("url 안넘어오면 뭐라찍히나", targetFromURL)
    if (targetFromURL) {
      // url에 친구가 넘어오면 api 요청 보내서 제보할 친구 데이터 바꾸기
    }
  }, [location]);

  useEffect(() => {
    setFriendModalOpen(false);
  }, [friendId])

  const handleImgSelector = () => {
    setImgSelectorOpen((prev) => !prev);
  };

  const handleTempleteClick = (reportType: ReportTypeProps) => {
    setActivityType(reportType);
    setImgSelectorOpen(false);
  };

  const handleImageCrop = (image: string) => {
    setCroppedImage(image);
  };

  const handleFriendModalClose = () => {
    setFriendModalOpen(false);
  };

  return (
    <>
      <HeadBar pagename="경고장 작성" bgcolor="white" backbutton="yes" center={true} />
      <MainFrame headbar="yes" navbar="no" bgcolor="white" marginsize="large">
        <InfoFrame>
          <InfoName>경고 사유</InfoName>
          <SelectTempleteBox>
            <Templete
              src={activityType.imgUrl}
              onClick={() => setIsModalOpen(true)}
            />
            <Text>
              {activityType.content}
              <Text className="gray">{activityType.example}</Text>
            </Text>
          </SelectTempleteBox>
        </InfoFrame>
        {activityType.type === "OTHER" && (
          <InfoFrame>
            <DetailInput value={activityDetail} setValue={setActivityDetail} type="warn"/>
          </InfoFrame>
        )}
        <InfoFrame>
          <InfoName onClick={handleImgSelector}>
            경고장 선택
            <Dropdown isShow={imgSelectorOpen} />
          </InfoName>
          <TempletesFrame isShow={imgSelectorOpen}>
            {reportTypes.map((reportType) => (
              <Templete
                key={reportType.type}
                src={reportType.imgUrl}
                onClick={() => handleTempleteClick(reportType)}
              />
            ))}
          </TempletesFrame>
        </InfoFrame>
        <InfoFrame>
          <InfoName onClick={() => setFriendModalOpen(true)}>
            제보할 친구
          </InfoName>
          누르면 모달로 제보할 친구 목록 보여줌
        </InfoFrame>
        <InfoFrame>
          <InfoName>증거 사진 제출</InfoName>
          <ImageCropper onCrop={handleImageCrop}>
            {croppedImage ? (
              <CropImg src={croppedImage} alt="Cropped" />
            ) : (
              <ImgIcon src="/images/upload-image.png" />
            )}
          </ImageCropper>
        </InfoFrame>

        <Margin />
      </MainFrame>

      <ButtonFrame>
        <LongButton background="var(--red)">경고장 보내기</LongButton>
      </ButtonFrame>

      <Background isShow={isModalOpen} onClick={() => setIsModalOpen(false)}>
        <BigImg isShow={isModalOpen} src={activityType.imgUrl} />
      </Background>

      <AnimationModal
        isOpen={friendModalOpen}
        closeModal={handleFriendModalClose}
      >
        <FriendListFrame>
          <SearchBar setUserId={setFriendId} type="follow"/>
        </FriendListFrame>
      </AnimationModal>
    </>
  );
}

const InfoFrame = styled.div<{ padding?: string }>`
  width: 100%;
  padding: ${({ padding }) => padding || "32px 0 0"};
`;

const InfoName = styled.div`
  position: relative;
  width: 100%;
  font-size: 14px;
  color: var(--dark-gray);
  margin-bottom: 12px;
  display: flex;
  justify-content: space-between;
`;

const SelectTempleteBox = styled.div`
  width: calc(100% - 36px);
  padding: 0 16px 0 20px;
  display: flex;
  align-items: center;
  height: 92px;
  background-color: white;
  border-radius: 10px;
  box-shadow: 2px 2px 6px rgba(0, 0, 0, 0.08);
`;

const Templete = styled.img`
  height: 80%;
  margin-right: 16px;
  cursor: pointer;
`;

const Background = styled(ModalBackground)<{ isShow?: boolean }>`
  transform: ${(props) => (props.isShow ? "scale(1)" : "scale(0)")};
`;

const BigImg = styled.img<{ isShow?: boolean }>`
  width: 100%;
  position: absolute;
  top: 28%;
  transition: transform 0.3s;
  transform: ${(props) => (props.isShow ? "scale(1)" : "scale(0)")};
`;

const Text = styled.div`
  font-size: 13px;

  &.gray {
    color: var(--dark-gray);
    font-size: 11.5px;
    font-weight: 400;
    margin: 6px 4px 0 0;
  }
`;

const Dropdown = styled(DropdownSvg)<{ isShow: boolean }>`
  transform: ${({ isShow }) => (isShow ? "rotate(270deg)" : "rotate(90deg)")};
  transition: transform 0.25s ease;
  position: absolute;
  right: 2px;
  cursor: pointer;
`;

const TempletesFrame = styled.div<{ isShow: boolean }>`
  width: calc(100%);
  height: ${({ isShow }) => (isShow ? "116px" : "0px")};
  border-bottom: 1px solid var(--nav-gray);
  transition: height 0.3s ease;
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
  justify-content: center;
  overflow-x: auto;
  z-index: 2;
  margin-bottom: 4px;
`;

const CropImg = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;

const ImgIcon = styled.img`
  width: 36%;
  padding: 32%;
`;

const Margin = styled.div`
  margin: 88px;
`;

const FriendListFrame = styled.div`
  height: 580px;
`;
