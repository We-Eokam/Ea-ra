// import React from 'react'
import { useState } from "react";
import styled from "styled-components";
import HeadBar from "../../components/HeadBar/HeadBar";
import MainFrame from "../../components/MainFrame/MainFrame";
import NavBar from "../../components/NavBar/NavBar";
import { ReactComponent as PointCircle } from "../../assets/icons/point-circle.svg"
import { ReactComponent as GruCircle } from "../../assets/icons/gru-circle.svg"
import { ReactComponent as LeafEmpty } from "../../assets/icons/leaf-empty.svg"
import { ReactComponent as LeafFill } from "../../assets/icons/leaf-fill.svg"
import { ReactComponent as BallMenu } from "../../assets/icons/ball-menu-icon.svg"
import OptionModal from "../../components/Modal/OptionModal";

export default function PostDetail() {
  const post = {
    writerProfileImg: "",
    writerNickname: "지구구해",
    time: "2023년 10월 24일",
    act: "다회용기 이용",
    company: "",
    point: "1,000",
    gru: "200",
    img: "",
    likedUser: "일회용품뿌셔",
    liked: 24
  }

  const [isLiked, setIsLiked] = useState(false);
  const [modalOpen, setModalOpen] = useState(false);

  const toggleLeaf = () => {
    setIsLiked(!isLiked);
  }

  const showModal = () => {
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
  };

  return (
    <>
      <HeadBar pagename="활동 인증" bgcolor="white" backbutton="yes" center={true} />
      <MainFrame headbar="yes" navbar="yes" bgcolor="white" marginsize="small">
        <PostFrame>
          <WriterContainer>
            <ProfileImg src={post.writerProfileImg} />
            <TextBox>
              <Bold>{post.writerNickname}</Bold>
              <SubText>{post.time}</SubText>
            </TextBox>
            <BallMenu onClick={showModal} />
          </WriterContainer>
          <ActImg src={post.img} />
          <ReactionContainer>
            {isLiked ? <LeafFill onClick={toggleLeaf} /> : <LeafEmpty onClick={toggleLeaf} />}
            <ReactionText><Bold>{post.likedUser}</Bold>님 외 {post.liked}명이 좋아해요</ReactionText>
          </ReactionContainer>
        </PostFrame>
        <MiddleMargin></MiddleMargin>
        <PostInfoFrame>
          <RewardContainer>
            <PointCircle />
            <RewardText>{post.point} 포인트 적립</RewardText>
            <GruCircle />
            <RewardText>{post.gru} 그루 갚음</RewardText>
          </RewardContainer>
          <ActContainer>
            <ActText>활동</ActText>
            <ActText>{post.act}</ActText>
          </ActContainer>
          {post.company &&
            <ActContainer>
              <ActText>기업</ActText>
              <ActText>{post.company}</ActText>
            </ActContainer>}
        </PostInfoFrame>
      </MainFrame>

      <OptionModal title="게시물 삭제" content="정말로 인증 내역을 삭제하시겠습니까?" btnText="삭제" isOpen={modalOpen} closeModal={closeModal} />
      <NavBar />
    </>
  )
}

const PostFrame = styled.div`
  position: relative;
  padding: 12px 0px;
`;

const WriterContainer = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 12px;
`;

const ProfileImg = styled.img`
  width: 50px;
  height: 50px;
  aspect-ratio: 1/1;
  border-radius: 50%;
  border: 0.5px solid var(--nav-gray);
  box-sizing: border-box;
`;

const TextBox = styled.div`
  margin-left: 4%;
  width: 100%;
  font-size: 14px;
  word-wrap: break-word;
`;

const Bold = styled.span`
  font-weight: 600;
`;

const SubText = styled.div`
  margin-top: 8px;
  font-size: 12px;
  color: var(--dark-gray);
`;

const ActImg = styled.img`
  width: 100vw;
  max-width: calc(100% + 2 * 5.56%);
  position: relative;
  left: 50%;
  transform: translateX(-50%);
`;

const ReactionContainer = styled.div`
  margin-top: 8px;
  display: flex;
  align-items: center;
`;

const ReactionText = styled.div`
  margin-left: 4px;
  font-size: 12px;
`;

const MiddleMargin = styled.div`
  width: calc(100% + 32px);
  height: 8px;
  margin: 0 -16px;
  background-color: var(--background);
`;

const PostInfoFrame = styled.div`
  position: relative;
  padding: 16px 0px;
`;

const RewardContainer = styled.div`
  display: flex;
  align-items: center;
`;

const RewardText = styled.div`
  margin-left: 4px;
  margin-right: 2.5%;
  font-size: 12px;
  color: var(--dark-gray);
`;

const ActContainer = styled.div`
  display: flex;
  margin-top: 14px;
`;

const ActText = styled.div`
  font-size: 14px;
  word-wrap: break-word;
  &:not(:last-child) {
    color: var(--dark-gray);
    margin-right: 16px;
  }
`;