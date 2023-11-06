// import React from 'react'
import styled from "styled-components";
import HeadBar from "../../components/HeadBar/HeadBar";
import MainFrame from "../../components/MainFrame/MainFrame";
import NavBar from "../../components/NavBar/NavBar";

interface User {
  profileImg: string;
  nickname: string;
  gru: number;
}
export default function FriendsList() {
  const handleReportBtn = (user: User) => {
    console.log(user.nickname+"에게 경고하자");
  }

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
            <ReportBtn onClick={() => handleReportBtn(user)}> 경고하기 </ReportBtn>
          </UserInfoContainer>
        ))}
      </MainFrame>
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
  width: 60px;
  height: 60px;
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

const ReportBtn = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100px;
  height: 32px;
  /* background-color: var(--gray);
  color: var(--black); */
  background-color: var(--primary);
  color: var(--white);
  font-size: 13px;
  font-weight: 500;
  border-radius: 6px;
  cursor: pointer;
`;