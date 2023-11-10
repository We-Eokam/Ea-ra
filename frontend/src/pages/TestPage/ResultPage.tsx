// import React from 'react'
import { useState, useEffect } from "react";
import styled from "styled-components";
import Lottie from "lottie-react";
import AngryEarth from "../../assets/lottie/angry-earth.json";
import ChuEarth from "../../assets/lottie/chu-earth.json";
import CryEarth from "../../assets/lottie/cry-earth.json";
import MeltingEarth from "../../assets/lottie/melting-earth.json";
import WowEarth from "../../assets/lottie/wow-earth.json";
import MainFrame from "../../components/MainFrame/MainFrame";

export default function ResultPage() {
  const [debt, setDebt] = useState(0);
  const [earth, setEarth] = useState(AngryEarth);
  const [context, setContext] = useState("앵그리");

  useEffect(() => {
    const testValue = JSON.parse(localStorage.getItem("results") || "{}");
    // const testValue = 95000;
    setDebt(testValue);

    if (testValue < 56000) {
      setEarth(WowEarth);
      setContext("와우");
    } else if (testValue < 66000) {
      setEarth(ChuEarth);
      setContext("츄");
    } else if (testValue < 81000) {
      setEarth(MeltingEarth);
      setContext("멜팅");
    } else if (testValue < 91000) {
      setEarth(CryEarth);
      setContext("크라이");
    }
  });

  return (
    <MainFrame headbar="yes" navbar="yes" marginsize="large" bgcolor="">
      <Title>당신의 <span>벌금</span>은?</Title>
      <Debt>{debt} 그루</Debt>
      <EarthFrame>
        <Lottie animationData={earth} />
      </EarthFrame>
      {context}
      어라로 초대할게 !
      이동하는 버튼
      내 결과 공유하기
    </MainFrame>
  );
}

const Title = styled.div`
  width: 100%;
  text-align: center;
  font-size: 32px;
  font-weight: 700;

  span {
    color: var(--red);
  }
`;

const Debt = styled.div`
  margin: 32px;
  text-align: center;
  font-size: 40px;
  font-weight: 650;
`;

const EarthFrame = styled.div`
  width: 80%;
  padding: 40px 10%;
`;