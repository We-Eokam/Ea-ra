// import React from 'react'
import { useEffect, useState } from "react";
import styled from "styled-components";
import NavBar from "../../components/NavBar/NavBar";
import MainFrame from "../../components/MainFrame/MainFrame";
import SearchBar from "../../components/SearchBar/SearchBar";
import { ReactComponent as PointCircle } from "../../assets/icons/point-circle.svg";
import { ReactComponent as GruCircle } from "../../assets/icons/gru-circle.svg";
import { ReactComponent as BallMenu } from "../../assets/icons/ball-menu-icon.svg";
import { ReactComponent as LeafEmpty } from "../../assets/icons/leaf-empty.svg";
import { ReactComponent as LeafFill } from "../../assets/icons/leaf-fill.svg";
import OptionModal from "../../components/Modal/OptionModal";

export default function FeedPage() {
  const PostExample = [
    {
      writerProfileImg: "",
      writerNickname: "지구구해",
      time: "오늘",
      act: "다회용기 이용",
      point: "1,000",
      gru: "200",
      img: "",
      likedUser: "일회용품뿌셔",
      liked: 24,
    },
    {
      writerProfileImg: "",
      writerNickname: "지뀨해",
      time: "오늘",
      act: "텀블러•다회용컵 이용",
      point: "300",
      gru: "100",
      img: "",
      likedUser: "지구구해",
      liked: 22,
    },
  ];

  const [isLiked, setIsLiked] = useState(false);
  const [modalOpen, setModalOpen] = useState(false);
  const [userId, setUserId] = useState<number | null>(null);

  // userId 바뀌면 해당 유저의 프로필 페이지로
  useEffect(() => {
    if (userId) {
      // navigate 써서 페이지 보내기
    }
  }, [userId]);

  const toggleLeaf = () => {
    setIsLiked(!isLiked);
  };

  const showModal = () => {
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
  };

  return (
    <>
      {/* <HeadBar pagename="예시" bgcolor="white" backbutton="yes"/> */}
      <SearchBar setUserId={setUserId} type="all"/>
      <MainFrame headbar="no" navbar="yes" bgcolor="white" marginsize="small">
        <Margin />
        {PostExample.map((post, index) => (
          <PostFrame key={index}>
            <WriterContainer>
              <ProfileImg src={post.writerProfileImg} />
              <TextBox>
                <Bold>{post.writerNickname}</Bold>님이 {post.time}&nbsp;
                <Bold>{post.act}</Bold>에 참여했어요!
                <RewardContainer>
                  <PointCircle />
                  <RewardText>{post.point} 포인트 적립</RewardText>
                  <GruCircle />
                  <RewardText>{post.gru} 그루 갚음</RewardText>
                </RewardContainer>
              </TextBox>
              <BallMenu onClick={showModal} />
            </WriterContainer>
            <ContentContainer>
              <ActImg src={post.img} />
              <ReactionContainer>
                {isLiked ? (
                  <LeafFill onClick={toggleLeaf} />
                ) : (
                  <LeafEmpty onClick={toggleLeaf} />
                )}
                <ReactionText>
                  <Bold>{post.likedUser}</Bold>님 외 {post.liked}명이 좋아해요
                </ReactionText>
              </ReactionContainer>
            </ContentContainer>
          </PostFrame>
        ))}
        <BottomMargin />
      </MainFrame>

      <OptionModal isOpen={modalOpen} closeModal={closeModal} />
      <NavBar />
    </>
  );
}

const Margin = styled.div`
  position: relative;
  height: calc(env(safe-area-inset-top) + 52px);
  width: 100%;
`

const PostFrame = styled.div`
  position: relative;
  padding: 16px 0px;
  border-bottom: 0.5px solid var(--gray);
`;

const WriterContainer = styled.div`
  display: flex;
  align-items: flex-start;
  margin-bottom: 12px;
`;

const ProfileImg = styled.img`
  width: 50px;
  height: 50px;
  border-radius: 50%;
  border: 0.5px solid var(--nav-gray);
  box-sizing: border-box;
`;

const TextBox = styled.div`
  margin-top: 4px;
  margin-left: 4%;
  width: calc(100% - 50px);
  font-size: 14px;
  word-wrap: break-word;
`;

const Bold = styled.span`
  font-weight: 600;
`;

const RewardContainer = styled.div`
  display: flex;
  align-items: center;
  margin-top: 8px;
`;

const RewardText = styled.div`
  margin-left: 4px;
  margin-right: 2.5%;
  font-size: 12px;
  color: var(--dark-gray);
`;

const ContentContainer = styled.div`
  margin-left: calc(4% + 50px);
`;

const ActImg = styled.img`
  width: 100%;
`;

const ReactionContainer = styled.div`
  margin-top: 4px;
  display: flex;
  align-items: center;
`;

const ReactionText = styled.div`
  margin-left: 4px;
  font-size: 12px;
`;

const BottomMargin = styled.div`
  height: 52px;
`;
