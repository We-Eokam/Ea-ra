// import React from "react";
import { useState } from "react";
import { ModalFrame } from "./ModalFrame";
import styled, { keyframes } from "styled-components";
import { ModalBackground } from "./ModalBackground";
import { ReactComponent as CloseIcon } from "../../assets/icons/close-icon.svg";
import { LongButton } from "../Buttons/LongButton";

interface CompanyInfoProps {
  id: number;
  name: string;
  logo: string;
  points: string;
  connected: boolean;
}

interface CompanyDetailProps {
  closeModal: () => void;
  companyInfo: CompanyInfoProps;
}

interface BackgroundProps {
  isclosing: boolean;
}

interface ModalProps {
  isclosing: boolean;
}

export default function CompanyDetail({
  closeModal,
  companyInfo,
}: CompanyDetailProps) {
  const [isclosing, setIsClosing] = useState(false);

  const closeAndAnimate = () => {
    setIsClosing(true);
    setTimeout(() => {
      closeModal();
    }, 260);
  };

  return (
    <>
      <Background isclosing={isclosing} onClick={closeAndAnimate} />
      <CompanyDetailModal isclosing={isclosing}>
        <CloseFrame>
          <CloseModalButton onClick={closeAndAnimate} />
        </CloseFrame>
        <InnerContainer>
          {/* 이미지로 바꾸기 */}
          <Logo>{companyInfo?.logo}</Logo>
          <CompanyName>{companyInfo?.name}</CompanyName>
          <CompanyPoints>{companyInfo?.points}</CompanyPoints>
          {companyInfo.connected === true ? (
            <ConnectedButton>연동했어요</ConnectedButton>
          ) : (
            <ConnectButton>연동하기</ConnectButton>
          )}
        </InnerContainer>
      </CompanyDetailModal>
    </>
  );
}

const fadeIn = keyframes`
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
`;

const fadeOut = keyframes`
  from {
    opacity: 1;
  }
  to {
    opacity: 0;
  }
`;

const slideIn = keyframes`
  from {
    transform: translateY(100%);
  }
  to {
    transform: translateY(0);
  }
`;

const slideOut = keyframes`
  from {
    transform: translateY(0);
  }
  to {
    transform: translateY(100%);
  }
`;

const Background = styled(ModalBackground)<BackgroundProps>`
  z-index: 3;
  animation: ${({ isclosing }) => (isclosing ? fadeOut : fadeIn)} 0.3s
    ease-in-out;
`;

const CompanyDetailModal = styled(ModalFrame)<ModalProps>`
  height: 372px;
  z-index: 4;
  animation: ${({ isclosing }) => (isclosing ? slideOut : slideIn)} 0.35s
    ease-in-out;
`;

const CloseFrame = styled.div`
  position: relative;
  width: 100%;
  height: 24px;
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
`;

const CloseModalButton = styled(CloseIcon)`
  position: relative;
`;

const InnerContainer = styled.div`
  position: relative;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow-y: scroll;
`;

// img로 바꾸기
const Logo = styled.div`
  margin-top: 30px;
  height: 92px;
  width: 92px;
  background-color: var(--gray);
`;

const CompanyName = styled.span`
  margin-top: 20px;
  font-size: 19px;
  font-size: 400;
`;

const CompanyPoints = styled.span`
  margin-top: 8px;
  font-size: 13px;
  color: var(--dark-gray);
  margin-bottom: 36px;
`;

const ConnectButton = styled(LongButton)`
  width: 86.25%;
  height: 40px;
  font-size: 14px;
`;

const ConnectedButton = styled(ConnectButton)`
  background-color: var(--gray);
  color: var(--black);
`;
