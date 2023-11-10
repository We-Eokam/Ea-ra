// import React from 'react'
import { useNavigate } from "react-router-dom";
import styled, { keyframes } from "styled-components";
import MainFrame from "../../components/MainFrame/MainFrame";

export default function StartPage() {
  const nickname = "짱구는못말려"
  const navigate = useNavigate();

  const handleBtnClick = () => {
    navigate("/test");
  }
  
  return (
    <>
      <MainFrame headbar="yes" navbar="no" marginsize="large" bgcolor="">
        <Title><Red>고소장</Red>이 도착했어요 !!</Title>
        <ImgBox src="../src/assets/images/plaint.png"/>
        <Text>
          <Highlight>환경오염의 주범</Highlight>으로 지구에게 고소당했습니다<br/>
          <Bold>{nickname}</Bold>님에게 <Bold><Red>벌금형</Red></Bold>이 가해지며,<br/>
          진술서를 바탕으로 벌금이 정해집니다<br/><br/>
          평소 생각이나 사실관계를 <Highlight>솔직하게 진술</Highlight>해주세요<br/>
        </Text>
        <MarginFrame />
      </MainFrame>
      <NavBarFrame>
        <StartBtn onClick={handleBtnClick}>진술서 작성하기</StartBtn>
      </NavBarFrame>
    </>
  );
}

const bounce = keyframes`
	/* 0% {margin-top: 0px;}
	20% {margin-top: 12px;}
	40% {margin-top: 0px;}
	60% {margin-top: 12px;}
	80% {margin-top: 0px;}
	100% {margin-top: 0px;} */
  from {
    opacity: 0;
    transform: scale(0);
  }
`;

const scale = keyframes`
  0% {transform: scale(1);}
  50% {transform: scale(0.9);}
  100% {transform: scale(1);}
`;

const ImgBox = styled.img`
  width: 100%;
  margin-top: 20%;
`;

const Title = styled.div`
  position: absolute;
  margin-left: -6.67%;
  width: 100%;
  text-align: center;
  top: 12px;
  font-size: 22.5px;
  font-weight: 600;
  /* animation: ${bounce} 1s linear 0s 2; */
  animation: ${bounce} 0.5s cubic-bezier(0.5, 0, 0.5, 1) forwards;
`;

const Red = styled.span`
  color: var(--red);
`;

const Text = styled.div`
  padding: 32px 12px 0;
  font-size: 16px;
  font-weight: 350;
  color: var(--dark-gray);
  text-align: center;
  line-height: 24px;
`;

const Highlight = styled.span`
  /* text-shadow: 0 0 10px var(--yellow); */
  background: linear-gradient(0deg, rgb(254, 226, 39, 0.6), transparent 75%);
`;

const Bold = styled.span`
  color: var(--black);
  font-weight: 400;
`;

const StartBtn = styled.div`
  position: relative;
  border-radius: 20px;
  background-color: var(--third);
  color: var(--primary);
  font-size: 20px;
  font-weight: 600;
  cursor: pointer;
  text-align: center;
  margin: 16px 24px;
  padding: 16px 0;
  animation: ${scale} 2s 10;
`;

const MarginFrame = styled.div`
  width: 100%;
  height: 18.5%;
  background-color: var(--white);
`

const NavBarFrame = styled.div`
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 16%;
  z-index: 2;
  background-color: var(--white);
`