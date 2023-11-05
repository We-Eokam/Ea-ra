// import React from 'react'
import { useState } from "react";
import styled from "styled-components";
import HeadBar from "../../components/HeadBar/HeadBar";
import MainFrame from "../../components/MainFrame/MainFrame";
import { ModalBackground } from "../../components/Modal/ModalBackground";
import AnimationModal from "../../components/Modal/AnimationModal";
import ImageCropper from "../../components/ImageCropper/ImageCropper";
import { LongButton, ButtonFrame } from "../../style";
import { ReactComponent as DropdownSvg } from "../../assets/icons/dropdown.svg";

interface ReportTypeProps {
  type: string;
  content: string;
  example: string;
  imgUrl: string;
}

const reportTypes = [
  {
    type: "PLASTIC",
    content: "일회용품 사용",
    example: "카페 일회용컵, 비닐봉지, 배달 용기 등 불필요한 일회용품 사용",
    imgUrl: "../src/assets/images/templete1.png",
  },
  {
    type: "PAPER",
    content: "종이 낭비",
    example: "종이 영수증을 받거나 휴지를 과도하게 사용하는 등 종이를 낭비",
    imgUrl: "../src/assets/images/templete2.png",
  },
  {
    type: "ELECTRICITY",
    content: "전기 낭비",
    example:
      "플러그 안뽑기, 빈 방에 불 켜놓기, 반팔입고 히터 사용 등 과도한 전기 사용",
    imgUrl: "../src/assets/images/templete3.png",
  },
  {
    type: "WATER",
    content: "물 낭비",
    example:
      "양치 컵 미사용, 물 틀어놓고 설거지, 세탁 나눠서 하기 등 물 절약 비실천",
    imgUrl: "../src/assets/images/templete4.png",
  },
  {
    type: "FOOD",
    content: "식재료 낭비",
    example:
      "먹을만큼 음식을 구매하지 않는 행위 등으로 많은 음식물 쓰레기를 배출",
    imgUrl: "../src/assets/images/templete5.png",
  },
  {
    type: "OTHER",
    content: "기타",
    example: "이외 분리수거를 안하거나, 물티슈 사용 등 환경 오염 활동",
    imgUrl: "../src/assets/images/templete6.png",
  },
];

export default function ReportPage() {
  const [activityType, setActivityType] = useState<ReportTypeProps>(
    reportTypes[0]
  );
  const [activityDetail, setActivityDetail] = useState("");
  const [imgSelectorOpen, setImgSelectorOpen] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [friend, setFriend] = useState("");
  const [friendModalOpen, setFriendModalOpen] = useState(false);
  const [croppedImage, setCroppedImage] = useState<string | null>(null);

  const handleImgSelector = () => {
    setImgSelectorOpen((prev) => !prev);
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const nowInput = e.target.value;
    if (nowInput.length < 21) {
      setActivityDetail(nowInput);
      // setDetailLen(false);
    } else {
      // setDetailLen(true);
    }
  }

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
            <InfoName onClick={handleImgSelector}>
              기타 사유 입력
              <Text className="gray">{activityDetail.length}/20</Text>
            </InfoName>
            <DetailInput
              value={activityDetail}
              maxLength={20}
              onChange={handleInputChange}
              placeholder="ex) 쓰레기 무단 투기, 차량 요일제 무시 등"
            />
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
              <CropImg src="../src/assets/images/upload-image-icon.png" />
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
        closeBtn={true}
      >
        친구 목록
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

const DetailInput = styled.input`
  width: calc(100% - 8px);
  font-size: 13px;
  outline: none;
  border: none;
  border-bottom: 1px solid var(--nav-gray);
  padding: 4px;

  &::placeholder {
    font-weight: 300;
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

const Margin = styled.div`
  margin: 88px;
`;
