// import React from 'react'
import styled from "styled-components";
import HeadBar from "../../components/HeadBar/HeadBar";
import MainFrame from "../../components/MainFrame/MainFrame";
import NavBar from "../../components/NavBar/NavBar";
import { ReactComponent as ReportSend } from "../../assets/icons/report-send-icon.svg";
import { ReactComponent as PersonCancel } from "../../assets/icons/person_cancel.svg";
import OptionModal from "../../components/Modal/OptionModal";
import { useState } from "react";
interface User {
  profileImg: string;
  nickname: string;
  gru: number;
}
export default function FriendsList() {
  const users:User[] = [
    {
      profileImg: "",
      nickname: "어쩌라고라고어쩌라고",
      gru: 25000,
    },
    {
      profileImg: "",
      nickname: "어쩌라고",
      gru: 25000,
    },
    {
      profileImg: "",
      nickname: "어쩌",
      gru: 25000,
    },
    {
      profileImg: "",
      nickname: "지구 지킴",
      gru: 25000,
    },
  ];

  const [modalOpen, setModalOpen] = useState(false);
  const [modalContent, setModalContent] = useState("");

  const handleReportBtn = (user: User) => {
    console.log(user.nickname+"에게 경고하자");
  }

  const showModal = (user: User) => {
    setModalContent("정말로 " + user.nickname + "님과 친구를 끊으시겠습니까?")
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
  };

  return (
    <>
      <HeadBar pagename="친구 목록" bgcolor="white" backbutton="yes" center={true} />
      <MainFrame headbar="yes" navbar="yes" bgcolor="white" marginsize="medium">
        {users.map((user) => (
          <UserInfoContainer>
            <ProfileImg src={user.profileImg} />
            <TextBox>
              {user.nickname}
              <SubText>{user.gru}그루</SubText>
            </TextBox>
            <IconContainer>
              <ReportSend onClick={() => handleReportBtn(user)} />
              <PersonCancel onClick={() => showModal(user)}/>
            </IconContainer>
          </UserInfoContainer>
        ))}
      </MainFrame>

      <OptionModal title="친구 끊기" content={modalContent}  btnText="삭제" isOpen={modalOpen} closeModal={closeModal} />
      <NavBar />
    </>
  )
}

const UserInfoContainer = styled.div`
  display: flex;
  align-items: center;
  margin: 20px 0;
`;

const ProfileImg = styled.img`
  width: 48px;
  height: 48px;
  border-radius: 50%;
  border: 0.5px solid var(--nav-gray);
  box-sizing: border-box;
`;

const TextBox = styled.div`
  flex-grow: 1;
  margin-left: 12px;
  font-size: 18px;
  font-weight: 500;
  word-wrap: break-word;
`;

const SubText = styled.div`
  margin-top: 8px;
  font-size: 12px;
  color: var(--dark-gray);
`;

const IconContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 16px;
`;