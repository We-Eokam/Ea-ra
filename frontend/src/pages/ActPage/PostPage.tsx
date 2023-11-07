// import React from 'react'
import { useState, useEffect } from "react";
import styled from "styled-components";
import HeadBar from "../../components/HeadBar/HeadBar";
import MainFrame from "../../components/MainFrame/MainFrame";
import ImageCropper from "../../components/ImageCropper/ImageCropper";
import DetailInput from "../../components/Input/DetailInput";
import { ReactComponent as DropdownSvg } from "../../assets/icons/dropdown.svg";
import { ShortButton, LongButton, ButtonFrame } from "../../style";
import data from "../../common/act.json";

interface CompanyPorops {
  name: string;
  logo: string;
  detail: string;
}

interface DataProps {
  id: number;
  name: string;
  englishName: string;
  companies : CompanyPorops[]
}

export default function PostPage() {
  const [type, setType] = useState(1);
  const [actType, setActType] = useState<DataProps>(data[0]);
  const [showOptions, setShowOptions] = useState(false);
  const [activityDetail, setActivityDetail] = useState("");
  const [croppedImage, setCroppedImage] = useState<string | null>(null);
  const [isRegist, setIsRegist] = useState(false);
  const [selectIdx, setSelectIdx] = useState<number | null>(null);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const typeFromURL = Number(params.get("type"));

    if (0 < typeFromURL && typeFromURL < 11) {
      setType(typeFromURL);
    }
  }, [location]);

  useEffect(() => {
    setActType(data[type - 1]);
  }, [type]);

  const handleSelectOption = (index: number) => {
    setType(index + 1);
    setIsRegist(false);
    setShowOptions(false);
  };

  useEffect(() => {
    console.log('바뀌나?', showOptions);
  }, [showOptions])

  const handleImageCrop = (image: string) => {
    setCroppedImage(image);
  };

  const handleRegist = (select: boolean) => {
    setIsRegist(select);
    if (select === false) {
      setSelectIdx(null);
    }
  };

  return (
    <>
      <HeadBar pagename="활동 인증" bgcolor="white" backbutton="yes" center={true}/>
      <MainFrame headbar="yes" navbar="no" bgcolor="white" marginsize="large">
        <div style={{ marginTop: "20px" }}>
          <ImageCropper onCrop={handleImageCrop}>
            {croppedImage ? (
              <CropImg src={croppedImage} alt="Cropped" />
            ) : (
              <ImgIcon src="/public/images/upload-image.png" />
            )}
          </ImageCropper>
        </div>
        <InfoFrame>
          <InfoName>인증 활동</InfoName>
          <SelectBox onClick={() => setShowOptions((prev) => !prev)}>
            <Label>{actType.name}</Label>
            <Dropdown isShow={showOptions}/>
          </SelectBox>
          <SelectOptions show={showOptions}>
            {data.map((option, index) => (
              <Option key={index} onClick={() => handleSelectOption(index)}>
                {option.name}
              </Option>
            ))}
          </SelectOptions>
        </InfoFrame>
        {type === 10  ? (
          <InfoFrame>
            <DetailInput value={activityDetail} setValue={setActivityDetail} type="post"/>
          </InfoFrame>
        ) : (
          <InfoFrame>
            <InfoName>참여 기업 등록</InfoName>
            <ButtonsFrame>
              <Button
                onClick={() => handleRegist(false)}
                isSelected={isRegist === false}
              >
                아니요
              </Button>
              <Button
                onClick={() => handleRegist(true)}
                isSelected={isRegist === true}
              >
                예
              </Button>
            </ButtonsFrame>
          </InfoFrame>
        )}
        {isRegist && (
          <InfoFrame>
            <InfoName>기업선택</InfoName>
            <ButtonsFrame>
              {actType.companies.map((company, idx) => (
                <Button
                  width="30%"
                  onClick={() => setSelectIdx(idx)}
                  isSelected={selectIdx === idx}
                >
                  {company.name}
                </Button>
              ))}
            </ButtonsFrame>
          </InfoFrame>
        )}
        <Margin />
        <ButtonFrame>
          <LongButton>인증하기</LongButton>
        </ButtonFrame>
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
  margin-bottom: 12px;
`;

const ButtonsFrame = styled.div`
  position: relative;
  width: 100%;
  display: flex;
  gap: 5%;
  flex-flow: wrap;
`;

const SelectBox = styled.div`
  position: relative;
  width: 96%;
  padding: 1% 2%;
  cursor: pointer;
`;

const Dropdown = styled(DropdownSvg)<{ isShow: boolean }>`
  transform: ${({ isShow }) => (isShow ? "rotate(270deg)" : "rotate(90deg)")};
  transition: transform 0.25s ease;
  position: absolute;
  right: 2px;
  cursor: pointer;
`;

const Label = styled.label`
  text-align: center;
`;

const SelectOptions = styled.ul<{ show: boolean }>`
  position: absolute;
  left: 0;
  width: 86.2%;
  overflow: hidden;
  padding: 0;
  margin: 8px 6.72%;
  height: 180px;
  max-height: ${(props) => (props.show ? 'none' : '0')};
  border-radius: 0 0 12px 12px;
  border: 1px solid var(--nav-gray);
  border-bottom: ${(props) => (props.show ? '' : 'none')};
  background-color: var(--white);
  color: var(--nav-gray);
  overflow-y: auto;
  z-index: 1;
`;

const Option = styled.li`
  padding: 6px 10px;
  transition: background-color 0.2s ease-in;
  &:hover {
    font-size: 17px;
    background-color: var(--third);
    color: var(--black);
  }
`;

const Button = styled(ShortButton)<{ isSelected: boolean }>`
  box-shadow: ${(props) =>
    props.isSelected ? "" : "0 0 0 1px var(--nav-gray) inset"};
  color: ${(props) => (props.isSelected ? "var(--white)" : "var(--nav-gray)")};
  background-color: ${(props) =>
    props.isSelected ? "var(--primary)" : "var(--white)"};
  height: 34px;
  margin-top: 8px;
  &:hover {
    font-size: 14px;
    color: var(--black);
  }
`;

const Margin = styled.div`
  margin: 108px
`;