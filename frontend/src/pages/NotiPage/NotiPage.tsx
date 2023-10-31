// import React from 'react'
import NavBar from "../../components/NavBar/NavBar";
import HeadBar from "../../components/HeadBar/HeadBar";
import MainFrame from "../../components/MainFrame/MainFrame";
import styled from "styled-components";
import FollowBtn from "../../components/Buttons/FollowButton";
import { ShadowBox } from "../../components/ShadowBox/ShadowBox";

export default function NotiPage() {
  const NoticeExample = [
    {
      type: 2,
      userNickname: "짱구는못말려",
      profileImg: "src/assets/images/jjanggu.png",
      witnessImg: "src/assets/images/witness.png",
      time: "방금",
    },
    {
      type: 1,
      userNickname: "짱구는못말려",
      profileImg: "src/assets/images/jjanggu.png",
      isFollow: false,
      accept: false,
      time: "2분 전",
    },
    {
      type: 1,
      userNickname: "나더워",
      profileImg: "src/assets/images/earth.png",
      isFollow: true,
      accept: false,
      time: "1일 전",
    },
    {
      type: 1,
      userNickname: "지뀨하기",
      profileImg: "src/assets/images/ziggu.png",
      isFollow: true,
      accept: true,
      time: "1일 전",
    },
  ];

  return (
    <>
      <HeadBar pagename="알림" bgcolor="white" backbutton="yes" />
      <MainFrame headbar="yes" navbar="yes" bgcolor="white" marginsize="small">
        <MarginFrame />
        {NoticeExample.map((notice, index) => (
          <Container key={index}>
            <LeftContainer type={notice.type}>
              <ProfileImg src={notice.profileImg} />
              <TextContainer>
                <NickName>{notice.userNickname}</NickName>
                {notice.type == 1 ? (
                  notice.accept == true ? (
                    <span>님이 회원님의 친구 요청을 수락했어요</span>
                  ) : (
                    <span>님이 회원님과 친구가 되고 싶어해요.</span>
                  )
                ) : (
                  <span>님이 회원님의 환경 오염 활동을 목격했어요.</span>
                )}
                <Time>{notice.time}</Time>
              </TextContainer>
            </LeftContainer>
            {notice.type == 1 ? (
              <FollowBtn isFollow={notice.isFollow} />
            ) : (
              <WitnessImg src={notice.witnessImg} />
            )}
          </Container>
        ))}
      </MainFrame>
      <NavBar />
    </>
  );
}

const MarginFrame = styled.div`
  margin: 8px;
`;

const Container = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 0;
`;

const LeftContainer = styled.div<{ type: number }>`
  display: flex;
  align-items: center;
  width: ${(props) =>
    props.type == 1 ? "calc(100% - 92px)" : "calc(100% - 60px)"};
`;

const ProfileImg = styled.img`
  height: 50px;
  border-radius: 50%;
  border: 0.5px solid var(--nav-gray);
  margin-right: 4%;
`;

const WitnessImg = styled.img`
  height: 60px;
`;

const TextContainer = styled.div`
  width: calc(92% - 50px);
  color: var(--black);
  font-size: 13px;
  font-weight: 400;
  word-wrap: break-word;
`;

const NickName = styled.span`
  font-weight: 500;
`;

const Time = styled.span`
  padding-left: 2%;
  color: var(--dark-gray);
`;
