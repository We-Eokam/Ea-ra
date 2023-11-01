// import React from 'react'
import { useState, useEffect } from "react";
import HeadBar from "../../components/HeadBar/HeadBar";
import MainFrame from "../../components/MainFrame/MainFrame";
import styled from "styled-components";
import ImageCropper from "../../components/ImageCropper/ImageCropper";
import { ShortButton } from "../../components/Buttons/ShortButton";
import { LongButton } from "../../components/Buttons/LongButton";

export default function PostPage() {
  const [type, setType] = useState(0);
  const [croppedImage, setCroppedImage] = useState<string | null>(null);
  const [isRegist, setIsRegist] = useState(false);
  const companys = [
    "더벤티",
    "메가커피",
    "스타벅스",
    "풀 바셋",
    "해피해빗",
    "그린업",
    "(주)다와",
  ];
  const [companyIdx, setCompanyIdx] = useState<number | null>(null);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const typeFromURL = Number(params.get("type"));

    if (0 <= typeFromURL && typeFromURL < 10) {
      setType(typeFromURL);
    }
  }, [location]);

  const handleImageCrop = (image: string) => {
    setCroppedImage(image);
  };

  const handleRegist = (select: boolean) => {
    setIsRegist(select);
    if (select === false) {
      setCompanyIdx(null);
    }
  };

  return (
    <>
      <HeadBar pagename="" bgcolor="white" backbutton="yes" />
      <MainFrame headbar="yes" navbar="no" bgcolor="white" marginsize="large">
        <div style={{ marginTop: "20px" }}>
          <ImageCropper onCrop={handleImageCrop}>
            {croppedImage ? (
              <CropImg src={croppedImage} alt="Cropped" />
            ) : (
              <ImgIcon src="../src/assets/images/upload-image-icon.png" />
            )}
          </ImageCropper>
        </div>
        <InfoFrame>
          <InfoName>인증 활동</InfoName>
          드롭다운 들어갈 자리
        </InfoFrame>
        <InfoFrame>
          <InfoName>참여 기업 등록</InfoName>
          <ButtonsFrame>
            <Buton
              onClick={() => handleRegist(false)}
              isSelected={isRegist === false}
            >
              아니요
            </Buton>
            <Buton
              onClick={() => handleRegist(true)}
              isSelected={isRegist === true}
            >
              예
            </Buton>
          </ButtonsFrame>
        </InfoFrame>
        {isRegist && (
          <InfoFrame>
            <InfoName>기업선택</InfoName>
            <ButtonsFrame>
              {companys.map((company, idx) => (
                <Buton
                  style={{ width: "30%" }}
                  onClick={() => setCompanyIdx(idx)}
                  isSelected={companyIdx === idx}
                >
                  {company}
                </Buton>
              ))}
            </ButtonsFrame>
          </InfoFrame>
        )}
        <LongButton>인증하기</LongButton>
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

const ButtonsFrame = styled.div`
  position: relative;
  width: 100%;
  display: flex;
  justify-content: space-between;
  flex-flow: wrap;
`;

const Buton = styled(ShortButton)<{ isSelected: boolean }>`
  border: ${(props) =>
    props.isSelected ? "1px solid transparent" : "1px solid var(--nav-gray)"};
  color: ${(props) => (props.isSelected ? "var(--white)" : "var(--nav-gray)")};
  background-color: ${(props) =>
    props.isSelected ? "var(--primary)" : "var(--white)"};
  height: 34px;
  margin-top: 8px;
`;
