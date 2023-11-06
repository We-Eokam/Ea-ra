// import React from 'react'
import styled from "styled-components"
import MainFrame from "../../components/MainFrame/MainFrame"

export default function LoginPage() {
  return (
    <>
      <MainFrame headbar="no" navbar="no" bgcolor="white" marginsize="large">
        <LogoFrame>
          <Logo src="icons/icon-512x512.png" /> 
        </LogoFrame>
        <KakaoButton>
          카카오로 시작하기
          <KakaoLogo src="images/kakao-logo.png" />
        </KakaoButton>
        <HelpCenter>
          문의하기
        </HelpCenter>
      </MainFrame>
    </>
  )
}

const LogoFrame = styled.div`
  position: absolute;
  left: 0;
  right: 0;
  top: 25%;
  display: flex;
  justify-content: center;
`

const Logo = styled.img`
  position: relative;
  width: 124px;
  border: 1px black solid;
`

const KakaoButton = styled.div`
  position: absolute;
  left: 0;
  right: 0;
  margin: 0px 6.67%;
  height: 50px;
  background-color: var(--kakao-yellow);
  border-radius: 10px;
  top: 75.62%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: 550;
  font-size: 14px;
  color: var(--kakao-black);
`

const KakaoLogo = styled.img`
  position: absolute;
  left: 18px;
  width: 28px;
`

const HelpCenter = styled.div`
  position: absolute;
  left: 0;
  right: 0;
  text-align: center;
  top: 83.5%;
  font-size: 12px;
  color: var(--dark-gray);
  font-weight: 450;
`