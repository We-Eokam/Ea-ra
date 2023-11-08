// import React from 'react'
import { useState, useEffect } from "react";
import styled from "styled-components";

import HeadBar from "../../components/HeadBar/HeadBar";
import MainFrame from "../../components/MainFrame/MainFrame";
import { ModalBackground } from "../../components/Modal/ModalBackground";
import DetailInput from "../../components/Input/DetailInput";

import { ReactComponent as AddSvg } from "../../assets/icons/add-icon.svg";
import { ReactComponent as RemoveSvg } from "../../assets/icons/close-icon.svg";
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

// 일단 임시로 해놓음 api 완성되면 수정 필
interface FriendDataProps {
  userId: number;
  profileImg: string;
  nickname: string;
}

const friendExaple = {
  "userId": 1,
  "profileImg": "/icons/icon-48x48.png",
  "nickname": "임시 닉네임",
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
  const [friendInfo, setFriendInfo] = useState<FriendDataProps | null>(null);
  const [friendModalOpen, setFriendModalOpen] = useState(false);
  const [croppedImage, setCroppedImage] = useState<string | null>(null);
  
  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const targetFromURL = Number(params.get("target"));

    if (targetFromURL) {
      setFriendId(targetFromURL);
    }
  }, [location]);

  useEffect(() => {
    setFriendModalOpen(false);
    console.log("넘어온 친구 아이디", friendId);
    if (friendId) {
      // api 보내서 friendInfo 바꿈 지금은 임시
      setFriendInfo(friendExaple);
    } else {
      setFriendInfo(null);
    }
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
          <InfoName>
            제보할 친구
          </InfoName>
          {friendInfo ? (
            <InfoName style={{ marginBottom: "0"}}>
              <ProfileImg><img src={friendInfo.profileImg}/></ProfileImg>
              {friendInfo.nickname}
              <RemoveSvg onClick={() => setFriendId(null)}/>
            </InfoName>
            ) : (
            <ProfileImg onClick={() => setFriendModalOpen(true)}>
              <AddBtn />
            </ProfileImg>
          )}
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
  font-size: 14px;
  color: var(--dark-gray);
  margin-bottom: 12px;
  display: flex;
  align-items: center;
  gap: 10px;
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
    font-size: 12px;
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

const ProfileImg = styled.div`
  position: relative;
  width: 42px;
  height: 42px;
  border-radius: 50%;
  border: 1px solid var(--nav-gray);
  /* background-color: var(--gray); */

  img {
    width: 100%;
    border-radius: 50%;
  }
`;

const AddBtn = styled(AddSvg)`
  position: absolute;
  left: 24px;
  top: 24px;

  path {
    fill: var(--white);
    stroke: var(--blue);
    stroke-width: 1.5;
  }
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
  height: 560px;
`;
