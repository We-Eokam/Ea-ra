// import React from 'react'
import { useState } from "react";
import styled from "styled-components";
import HeadBar from "../../components/HeadBar/HeadBar";
import MainFrame from "../../components/MainFrame/MainFrame";
import NavBar from "../../components/NavBar/NavBar";
import { ReactComponent as GruCircle } from "../../assets/icons/gru-circle.svg";
import { ReactComponent as DropRight } from "../../assets/icons/drop-right-icon.svg";

export default function FeedDetail() {
  const report = {
    writerProfileImg: "../src/assets/images/templete1.png",
    writerNickname: "지구구해",
    targetNickname: "환경구해",
    time: "2023년 10월 24일",
    act: "다회용기 이용",
    img_list: [ "../src/assets/images/templete1.png", "../src/assets/images/templete2.png" ],
    likedUser: "일회용품뿌셔",
    fine: 300,
  };

  const [currentImgIndex, setCurrentImgIndex] = useState(0);

  const handleImageChangeByDot = (index: number) => {
    setCurrentImgIndex(index);
  };

  return (
    <>
      <HeadBar
        pagename="경고장"
        bgcolor="white"
        backbutton="yes"
        center={true}
      />
      <MainFrame headbar="yes" navbar="yes" bgcolor="white" marginsize="small">
        <PostFrame>
          <WriterContainer>
            <ProfileImg src={report.writerProfileImg} />
            <TextBox>
              <MainText>
                <Bold>{report.writerNickname}</Bold>
                <DropRight style={{ margin: "0 8px" }} />
                <Bold>{report.targetNickname}</Bold>
              </MainText>
              <SubText>{report.time}</SubText>
            </TextBox>
          </WriterContainer>
          <SlideContainer>
            <Slides currentImgIndex={currentImgIndex}>
              {report.img_list.map((img, index) => (
                <img src={img} key={index} style={{ width: '100%', flexShrink: 0 }} />
              ))}
            </Slides>
          </SlideContainer>
          <Dots>
            {report.img_list.map((_, index) => (
              <Dot key={index} active={index === currentImgIndex} onClick={() => handleImageChangeByDot(index)} />
            ))}
          </Dots>
          <ReactionContainer>
            <BigGruCircle />
            <ReactionText>
              <Bold>벌금 {report.fine}그루</Bold>
            </ReactionText>
          </ReactionContainer>
          <ActText>
            <Bold>{report.writerNickname}</Bold>님이 {report.targetNickname}님의{" "}
            {report.act} 현장을 목격했습니다. <br />
            <br />
          </ActText>
        </PostFrame>
      </MainFrame>
      <NavBar />
    </>
  );
}

const PostFrame = styled.div`
  position: relative;
  padding: 12px 0px;
`;

const WriterContainer = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 12px;
`;

const ProfileImg = styled.img`
  width: 50px;
  height: 50px;
  aspect-ratio: 1/1;
  border-radius: 50%;
  border: 0.5px solid var(--nav-gray);
  box-sizing: border-box;
`;

const TextBox = styled.div`
  margin-left: 4%;
  width: 100%;
  font-size: 14px;
  word-wrap: break-word;
`;

const MainText = styled.div`
  display: flex;
  align-items: center;
`;

const Bold = styled.span`
  font-weight: 600;
`;

const SubText = styled.div`
  margin-top: 8px;
  font-size: 12px;
  color: var(--dark-gray);
`;

const SlideContainer = styled.div`
  overflow: hidden;
  width: 100vw;
  max-width: calc(100% + 2 * 5.56%);
  position: relative;
  left: 50%;
  transform: translateX(-50%);
  margin-bottom: -16px;
`;

const Slides = styled.div<{ currentImgIndex: number }>`
  display: flex;
  transition: all 0.3s ease; 
  transform: translateX(-${props => props.currentImgIndex * 100}%);
`;

const Dots = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 10px;
  z-index: 1;
  position: relative;
  bottom: 16px;
  cursor: pointer;
`;

const Dot = styled.div<{ active: boolean }>`
  width: 8px;
  height: 8px;
  border-radius: 50%;
  margin: 0 4px;
  background-color: ${(props) => (props.active ? "var(--gray)" : "var(--nav-gray)")};
`;

const ReactionContainer = styled.div`
  margin-top: 8px;
  display: flex;
  align-items: center;
`;

const BigGruCircle = styled(GruCircle)`
  width: 16px;
  height: 16px;
  margin: 4px 4px 4px 0;
`;

const ReactionText = styled.div`
  margin-left: 4px;
  font-size: 12px;
`;

const ActText = styled.div`
  margin-top: 8px;
  font-size: 12px;
  line-height: 1.5;
  word-wrap: break-word;
`;
