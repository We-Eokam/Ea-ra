// import React from 'react'
import styled from "styled-components";
import MainFrame from "../../components/MainFrame/MainFrame";
import { ReactComponent as LoginBackgroundSVG } from "../../assets/icons/login-background.svg";


export default function LoginPage() {
  return (
    <>
      <MainFrame headbar="no" navbar="no" bgcolor="white" marginsize="no">
        <BackgroundFrame>
          <LoginBackground />
        </BackgroundFrame>
        <EARA>
          <EARABold>어 - 라?</EARABold><br />어느 날 갑자기<br />지구가 당신을<br />고소한다면?
        </EARA>
        <LogoImage src="/images/logo-nobackground.png"/>
        <ButtonsFrame>
          <KakaoButton>
            카카오로 시작하기
            <KakaoLogo src="images/kakao-logo.png" />
          </KakaoButton>
          <HelpButtonsFrame>
            <HelpButton>
              고객센터 문의하기
            </HelpButton>
            <HelpButton>
              탄소중립포인트제도
            </HelpButton>
          </HelpButtonsFrame>
            <Terms>
              회원가입 시 어라의 개인정보 처리방침 및 이용약관에
              <br />
              동의하는 것으로 간주합니다
            </Terms>
        </ButtonsFrame>
      </MainFrame>
    </>
  );
}

const BackgroundFrame = styled.div`
  left: 0;
  right: 0;
  height: 45%;
  display: flex;
  justify-content: center;
`

const LoginBackground = styled(LoginBackgroundSVG)`
  position: relative;
  width: auto;
  height: 100%;
  transform: scale(2.36) translate(11.9%, -15%);
`

const EARA = styled.div`
  margin-top: 20px;
  font-size: 1.625em;
  font-weight: 400;
  line-height: 1.4em;
  margin-left: 10.56%;
`

const EARABold = styled.span`
  font-size: 1em;
  font-weight: 600;
  line-height: 2em;

`

const LogoImage = styled.img`
  position: absolute;
  top: calc(45% + 112px);
  width: 34%;
  right: 6%;
`

const ButtonsFrame = styled.div`
  position: absolute;
  height: 220px;
  background-color: var(--white);
  left: 0;
  right: 0;
  bottom: 0;
`;

const KakaoButton = styled.div`
  position: relative;
  left: 0;
  right: 0;
  margin: 0px 6.67%;
  height: 46px;
  background-color: var(--kakao-yellow);
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: 550;
  font-size: 14px;
  color: var(--kakao-black);
  margin-top: 24px;
`;

const KakaoLogo = styled.img`
  position: absolute;
  left: 18px;
  width: 28px;
`;

const HelpButtonsFrame = styled.div`
  position: relative;
  left: 0;
  right: 0;
  height: 40px;
  margin-top: 24px;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
`;

const HelpButton = styled.div`
  font-size: 13px;
  color: var(--dark-gray);
  font-weight: 500;
`;

const Terms = styled.div`
  left: 0;
  right: 0;
  margin-top: 14px;
  text-align: center;
  font-size: 11.5px;
  font-weight: 300;
`