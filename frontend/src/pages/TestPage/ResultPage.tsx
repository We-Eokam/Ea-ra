// import React from 'react'
import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import styled, { keyframes } from "styled-components";
import Lottie from "lottie-react";
import AngryEarth from "../../assets/lottie/angry-earth.json";
import ChuEarth from "../../assets/lottie/chu-earth.json";
import CryEarth from "../../assets/lottie/cry-earth.json";
import MeltingEarth from "../../assets/lottie/melting-earth.json";
import WowEarth from "../../assets/lottie/wow-earth.json";
import data from "../../common/result.json"
import { ScoreBar } from "../../components/ProgressBar/ScoreBar";

interface ResultDataProps {
  name: string;
  content: string;
  detail : string;
}

const available = [38, 43, 48, 53, 58, 63, 68, 73, 78, 83, 88];

export default function ResultPage() {
  const { code } = useParams<{ code: string }>();
  const [analysisValue, setAnalysisValue] = useState([0, 0]);
  const [debt, setDebt] = useState(10000);
  const [earth, setEarth] = useState(AngryEarth);
  const [earthType, setEarthType] = useState<ResultDataProps>(data["앵그리"]);
  const navigate = useNavigate();

  useEffect(() => {
    if (code) {
      const implement = parseInt(code[0], 10);
      const interest = parseInt(code.slice(-1), 10);
      if (implement > 5 || interest > 5) {
        alert("잘못된 경로입니다. 테스트를 다시 실행해주세요");
        navigate("/test");
        return;
      }
      setAnalysisValue([implement, interest]);

      const testValue = parseInt(code.slice(1, 3), 10);
      if (available.includes(testValue)) {
        setDebt((testValue + 12) * 1000);

        if (testValue < 44) {
          setEarth(WowEarth);
          setEarthType(data["와우"]);
        } else if (testValue < 56) {
          setEarth(ChuEarth);
          setEarthType(data["츄"]);
        } else if (testValue < 69) {
          setEarth(MeltingEarth);
          setEarthType(data["멜팅"]);
        } else if (testValue < 79) {
          setEarth(CryEarth);
          setEarthType(data["크라이"]);
        }
      } else {
        alert("잘못된 경로입니다. 테스트를 다시 실행해주세요");
        navigate("/test");
      }
    }
  }, []);

  useEffect(() => {
    const isComplete = JSON.parse(localStorage.getItem("answer") || "{}");
    if (isComplete !== 10) {
      alert("모든 질문에 응답해주세요.");
      navigate('/test');
    }
  }, []);

  const shareKakao = () => {
    window.Kakao.Share.sendDefault({
      objectType: "feed",
      content: {
        title: "테스트 결과",
        description: `xx님의 테스트 결과를 확인해보세요`,
        imageUrl:
          "https://github.com/YJS96/eara_test_repo/blob/main/public/icons/icon-512x512.png?raw=true",
        link: {
          // [내 애플리케이션] > [플랫폼] 에서 등록한 사이트 도메인과 일치해야 함
          mobileWebUrl: `https://eara-test-repo.vercel.app/result`,
          webUrl: `http://localhost:5173/result`,
        },
      },
    });
  };

  return (
    <MainFrame>
      <Panel className="top">
        <Title>xx님의</Title>
      </Panel>
      <ResultFrame>
        <ResultInner>
          <TypeName>당신은 .. <br/><span>{earthType.name}</span></TypeName>
          <EarthFrame>
            <Lottie animationData={earth} />
          </EarthFrame>
          <Gray dangerouslySetInnerHTML={{ __html: earthType.detail }}/>
          <AnalysisBox>
            <SubTitle>재판 결과 분석</SubTitle>
            <ScoreBar title="실행력" score={analysisValue[0]}/>
            <ScoreBar title="관심도" score={analysisValue[1]}/>
          </AnalysisBox>
          <TypeName>
            당신의 <span style={{ color: "var(--red)" }}>벌금</span>은?
            <br/><span style={{ fontSize: "30px" }}>{debt} 그루</span>
          </TypeName>
          <Gray>진술한 내용을 바탕으로 벌금을 산정했어요.</Gray>

          <button onClick={() => navigate('/signup')}>회원가입하기</button>
          내 결과 공유하기
          <button onClick={shareKakao}>카카오로 공유하기</button>
        </ResultInner>
      </ResultFrame>
      <Panel className="bottom">
        <Title>지구재판 결과는?</Title>
      </Panel>
    </MainFrame>
  );
}

const slideUp = keyframes`
  33.3% {transform: translateY(0);}
  100% {transform: translateY(-100%);}
`;

const slideDwon = keyframes`
  33.3% {transform: translateY(0);}
  100% {transform: translateY(100%);}
`;

const MainFrame = styled.div`
  margin-top: env(safe-area-inset-top);
  width: 100%;
  height: 100%;
`;

const Panel = styled.div`
  background-color: var(--white);
  width: 100%;
  height: 50%;
  position: relative;
  z-index: 2;
  display: flex;
  flex-direction: column;
  justify-content: end;

  &.top {
    animation: ${slideUp} 3s ease-out forwards;
  }
  &.bottom {
    justify-content: start;
    animation: ${slideDwon} 3s ease-out forwards;
  }
`;

const Title = styled.div`
  width: 100%;
  height: 40px;
  text-align: center;
  font-size: 30px;
  font-weight: 700;
`;

const ResultFrame = styled.div`
  position: absolute;
  width: 100%;
  height: 84%;
  top: calc(12% + env(safe-area-inset-top));
  overflow-y: scroll;
`;

const ResultInner = styled.div`
  padding: 2% 10% 12%;
`;

const TypeName = styled.div`
  text-align: center;
  font-size: 32px;
  font-weight: 650;
  margin-bottom: 24px;
  span {
    color: var(--primary);
    font-size: 36px;
  }
  `;

const EarthFrame = styled.div`
  width: 70%;
  padding: 0 15%;
`;

const Gray = styled.div`
  font-size: 18px;
  margin: 28px 0;
  color: var(--dark-gray);
  text-align: center;
  span {
    font-weight: 450;
    color: var(--red);
  }
`;

const AnalysisBox = styled.div`
  width: calc(100% - 44px);
  padding: 4px 20px;
  margin-bottom: 52px;
  border: 2px solid var(--primary);
  border-radius: 12px;
`;

const SubTitle = styled.div`
  text-align: center;
  font-size: 20px;
  font-weight: 500;
  color: var(--black);
  padding: 12px 0 4px;
`;
