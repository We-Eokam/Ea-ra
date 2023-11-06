// import React from 'react'
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import MainFrame from "../../components/MainFrame/MainFrame";
import ProgressBar from "../../components/ProgressBar/ProgressBar";
import NavBar from "../../components/NavBar/NavBar";
import { ReactComponent as Add } from "../../assets/icons/add-icon.svg";
import { ReactComponent as Setting } from "../../assets/icons/setting-icon.svg";

export default function MyPage() {
  const navigate = useNavigate();

  const tabs = ["인증", "제보"];
  const [activeTab, setActiveTab] = useState("인증");
  const [offsetX, setOffsetX] = useState(0);

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

  const user = {
    profileImg: "",
    nickname: "어쩌라고라고어쩌라고",
    gru: 25000,
    progress: 100,
    greenInit: 2400000,
    isFollow: true,
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
            <Setting />
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
      <NavBar />
    </>
  );
}

const HeadFrame = styled.div`
  position: absolute;
  width: 100%;
  height: 96px;
  top: 0;
  left: 0;
  border-bottom: 1px solid var(--gray);
  top: env(safe-area-inset-top);
  /* border: 1px black solid; */

  z-index: 2;
`;

const HeadContext = styled.div`
  position: relative;
  margin-top: 60px;
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
