// import React from 'react'
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import MainFrame from "../../components/MainFrame/MainFrame";
import ProgressBar from "../../components/ProgressBar/ProgressBar";
import AnimationModal from "../../components/Modal/AnimationModal";
import NavBar from "../../components/NavBar/NavBar";
import { ReactComponent as Add } from "../../assets/icons/add-icon.svg";
import { ReactComponent as Setting } from "../../assets/icons/setting-icon.svg";
import { ReactComponent as Edit } from "../../assets/icons/edit-icon.svg";
import { ReactComponent as Article } from "../../assets/icons/article-icon.svg";
import { ReactComponent as Logout } from "../../assets/icons/logout-icon.svg";
import { ReactComponent as Trash } from "../../assets/icons/Trash-icon.svg";

export default function MyPage() {
  const navigate = useNavigate();

  const tabs = ["인증", "제보"];
  const [activeTab, setActiveTab] = useState("인증");
  const [offsetX, setOffsetX] = useState(0);
  const [modalOpen, setModalOpen] = useState(false);


  useEffect(() => {
    const tabIndex = tabs.indexOf(activeTab);
    const newPosition = (tabIndex * 200) / tabs.length;
    setOffsetX(newPosition);
  }, [activeTab]);

  const handleSlider = (tabName: string) => {
    setActiveTab(tabName);
  };

  const navigateFriends = () => {
    navigate("/mypage/friends");
  };

  const showModal = () => {
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
  };

  const user = {
    profileImg: "",
    nickname: "어쩌라고라고어쩌라고",
    gru: 25000,
    progress: 100,
    greenInit: 2400000,
    reportCnt: 2,
  };

  const PostExample = [
    {
      coverImg: "",
    },
  ];

  const ReportExample = [
    {
      coverImg: "",
    },
  ];

  return (
    <>
      <HeadFrame>
        <HeadContext>
          <IconContainer>
            <Add />
            <Setting onClick={showModal} />
          </IconContainer>
        </HeadContext>
      </HeadFrame>
      <MainFrame headbar="yes" navbar="yes" bgcolor="white" marginsize="no">
        <UserFrame>
          <UserInfoContainer>
            <ProfileImg src={user.profileImg} />
            <TextBox>
              {user.nickname}
              <SubText>{user.gru}그루</SubText>
            </TextBox>
            <ShowBtn onClick={navigateFriends}> 친구 보기 </ShowBtn>
          </UserInfoContainer>
          <ProgressBar progress={user.progress} greeninit={user.greenInit} />
        </UserFrame>
        <SliderFrame>
          {tabs.map((tab) => (
            <SliderTab
              key={tab}
              active={activeTab === tab}
              onClick={() => handleSlider(tab)}
            >
              {tab}
            </SliderTab>
          ))}
          <ActiveTab offsetX={offsetX} />
        </SliderFrame>
        <PostsFrame>
          {activeTab === "인증"
            ? PostExample.map((post) => (
              <Post>
                <CoverImg src={post.coverImg} />
              </Post>
            ))
            : ReportExample.map((post) => (
              <Post>
                <CoverImg src={post.coverImg} />
              </Post>
            ))}
        </PostsFrame>
      </MainFrame>

      <AnimationModal isOpen={modalOpen} closeModal={closeModal}>
        <OptionFrame>
          <Opt>
            <Edit />
            <OptText>프로필 수정</OptText>
          </Opt>
          <Opt>
            <Article />
            <OptText>경고장 수</OptText>
            <OptSubText>{user.reportCnt} 개</OptSubText>
          </Opt>
          <Opt>
            <Logout />
            <OptText isRed={true}>로그아웃</OptText>
          </Opt>
          <Opt>
            <Trash />
            <OptText isRed={true}>회원탈퇴</OptText>
          </Opt>
        </OptionFrame>
      </AnimationModal>
      <NavBar />
    </>
  );
}

const HeadFrame = styled.div`
  position: absolute;
  width: 100%;
  height: calc(48px + env(safe-area-inset-top));
  top: env(safe-area-inset-top);
  left: 0;
  border-bottom: 1px solid var(--gray);
  /* border: 1px black solid; */
  z-index: 2;

`;

const HeadContext = styled.div`
  position: relative;
  height: 100%;
  padding-left: 12px;
  font-size: 21px;
  font-weight: 650;
  display: flex;
  align-items: center;
  justify-content: flex-end;
`;

const IconContainer = styled.div`
  display: flex;
  gap: 12px;
  margin-right: 5.56%;
`;

const UserFrame = styled.div`
  padding: 0px 5.56%;
`;

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

const ShowBtn = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100px;
  height: 32px;
  background-color: var(--gray);
  color: var(--black);
  font-size: 13px;
  font-weight: 500;
  border-radius: 6px;
  cursor: pointer;
`;

const SliderFrame = styled.div`
  position: relative;
  margin: 20px 4.44%;
  height: 40px;
  padding: 4px;
  border-radius: 100px;
  border: 1px solid var(--gray);
  box-sizing: border-box;
  background-color: var(--background);
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const SliderTab = styled.div<{ active?: boolean }>`
  z-index: 1;
  width: 50%;
  text-align: center;
  font-size: 13px;
  font-weight: 600;
  cursor: pointer;

  ${(props) => !props.active && `color: var(--dark-gray);`}
`;

const ActiveTab = styled.div<{ offsetX: number }>`
  position: absolute;
  left: 4px;
  width: calc(50% - 4px);
  height: calc(100% - 8px);
  border-radius: 100px;
  background-color: var(--white);
  box-shadow: 1px 1px 4px 0px rgba(0, 0, 0, 0.08);
  transition: transform 0.3s ease;
  transform: translateX(${(props) => props.offsetX}%);
`;

const PostsFrame = styled.div`
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
`;

const Post = styled.div`
  flex-basis: 33%;
  position: relative;
  margin-bottom: calc(100% * 0.005);
  &:not(:nth-child(3n)) {
    margin-right: 0.5%;
  }
  aspect-ratio: 1/1;
`;

const CoverImg = styled.img`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  object-fit: cover;
`;

const OptionFrame = styled.div`
  width: 100%;
  border-radius: 12px;
  border: 1px solid var(--gray);
  background-color: var(--background);
`;

const Opt = styled.div`
  padding: 14px 16px;
  display: flex;
  align-items: center;

  &:not(:last-child) {
    border-bottom: 1px solid var(--gray);
  }
`;

const OptText = styled.div<{ isRed?: boolean }>`
  font-weight: 500;
  margin-left: 12px;
  flex-grow: 1;
  margin-top: 1px;
  
  ${(props) => props.isRed && `color: var(--red);`}
`;

const OptSubText = styled.div`
  color: var(--dark-gray);
`;