// import React from 'react'
import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import styled from "styled-components";
import HeadBar from "../../components/HeadBar/HeadBar";
import MainFrame from "../../components/MainFrame/MainFrame";
import FollowBtn from "../../components/Buttons/FollowButton";
import ProgressBar from "../../components/ProgressBar/ProgressBar";
import NavBar from "../../components/NavBar/NavBar";
import { ReactComponent as NoAct } from "../../assets/icons/no-act-icon.svg";
import { ReactComponent as NoReport } from "../../assets/icons/no-repot-icon.svg";
import { ReactComponent as ReportSend } from "../../assets/icons/report-send-icon.svg";
import axiosInstance from "../../api/axiosInstance";

interface UserInfoProps {
  id: number;
  nickname: string;
  groo: number;
  repayGroo: number;
  leftGroo: number;
  profileImg: string;
  progress: number;
}

const PostExample: { postId: number,coverImg: string }[] = [];

const ReportExample = [
  {
    reportId: 5,
    coverImg: "/images/template6.png",
  },
  {
    reportId: 6,
    coverImg: "/images/template6.png",
  },
  {
    reportId: 7,
    coverImg: "/images/template5.png",
  },
  {
    reportId: 8,
    coverImg: "/images/template2.png",
  },
];

const calcPercent = (tmp: number, total: number) => {
  if (total === 0) {
    return 100
  }
  return Math.round((tmp / total) * 100)
};

export default function ProfilePage() {
  const { id } = useParams<{ id: string }>();
  const [ userInfo, setUserInfo ] = useState<UserInfoProps | null>(null);
  const [ status, setStatus ] = useState("NOTHING");
  const tabs = ["인증", "제보"];
  const [activeTab, setActiveTab] = useState("인증");
  const [offsetX, setOffsetX] = useState(0);
  const navigate = useNavigate();
  const axios = axiosInstance();

  useEffect(() => {
    getMemberInfo();
    getStatus();
  }, []);

  useEffect(() => {
    console.log(status)
    console.log(userInfo)
  }, [status, userInfo])

  const getMemberInfo = async () => {
    try {
      const response = await axios.get(`/member?memberId=${id}`);
      const data = response.data.member_list[0];
      const progress = calcPercent(data.repay_groo, data.groo)
      const memberInfo = {
        id: data.member_id,
        nickname: data.nickname,
        groo: data.groo,
        repayGroo: data.repay_groo,
        leftGroo: data.groo - data.repay_groo,
        profileImg: data.profile_image_url,
        progress: progress,
      };
      setUserInfo(memberInfo);
    } catch (error) {
      console.log(error);
    }
  }

  const getStatus = async () => {
    try {
      const response = await axios.get(`/member/follow?memberId=${id}`);
      const data = response.data.follow_status;
      setStatus(data);
    } catch (error) {
      console.log(error);
    }
  }

  useEffect(() => {
    if (status === "FRIEND") {
      // 다른 상태에서 친구로 변경될 때만 정보 가져와서 바꿈
    } else {
      // 그 외 다른 상태로 변경되면 아무일도 일어나지 않음
    }
  }, [status]);


  useEffect(() => {
    const tabIndex = tabs.indexOf(activeTab);
    const newPosition = (tabIndex * 200) / tabs.length;
    setOffsetX(newPosition);
  }, [activeTab]);

  const handleSlider = (tabName: string) => {
    setActiveTab(tabName);
  };

  const handleReportBtn = () => {
    navigate(`/act/report?target=${userInfo?.id}`)
  }

  const handleNavigate = (where: string, id: number) => {
    navigate(`/${where}/${id}`);
  }

  if (userInfo === null) {
    return
  }

  return (
    <>
      <HeadBar pagename={userInfo?.nickname} bgcolor="white" backbutton="yes" center={true} />
      <MainFrame headbar="yes" navbar="yes" bgcolor="white" marginsize="no">
        <UserFrame>
          <UserInfoContainer>
            <ProfileImg src={userInfo?.profileImg} />
            <TextBox>
              {userInfo?.nickname}
              <SubText>
                {userInfo?.leftGroo === 0 ? (
                  "그루를 다 갚았어요 !"
                ) : (
                  `빚 청산까지 ${userInfo?.leftGroo}그루`
                )}
              </SubText>
            </TextBox>
            <FollowBtn status={status} setStatus={setStatus} />
          </UserInfoContainer>
          <ProgressBar progress={userInfo?.progress} greeninit={userInfo?.groo} />
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

        {status === "FRIEND" && activeTab !== "인증" && (
          <SendFrame onClick={handleReportBtn}>
            <ReportSend />
            <SendTexts>
              <SendBold>경고장보내기</SendBold>
              <SendText>환경 파괴 현장을 목격하면 제보해주세요!</SendText>
            </SendTexts>
          </SendFrame>
        )}
        
        {status === "FRIEND" ? (
          <PostsFrame>
            {activeTab === "인증" ? (
              PostExample.length === 0 ? (
                <NoPost>
                  <NoAct />활동 인증 없음
                </NoPost>
              ) : (
                PostExample.map((post) => (
                  <Post onClick={() => {handleNavigate("post", post.postId)}}>
                    <CoverImg src={post.coverImg} />
                  </Post>
                ))
              )
            ) : (
              ReportExample.length === 0 ? (
                <NoPost hasSendFrame={true}>
                  <NoReport />보낸 경고장 없음
                </NoPost>
              ) : (
                ReportExample.map((post) => (
                  <Post onClick={() => {handleNavigate("report", post.reportId)}}>
                    <CoverImg src={post.coverImg} />
                  </Post>
                ))
              )
            )}
          </PostsFrame>
        ) : (
          <Text>
            {status === "REQUEST" && (
              `${userInfo?.nickname}님이 회원님의 친구요청을 수락하면`
            )}
            {status === "ACCEPT" && (
              "친구요청을 수락하면"
            )}
            {status === "NOTHING" && (
              "친구 신청을 보내보세요!"
            )}
            <br />{userInfo?.nickname}님의 활동을 볼 수 있어요
          </Text>
        )}
      </MainFrame>
      <NavBar />
    </>
  );
}

const UserFrame = styled.div`
  padding: 12px 5.56%;
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
  font-size: 17px;
  font-weight: 500;
  word-wrap: break-word;
`;

const SubText = styled.div`
  margin-top: 8px;
  font-size: 13px;
  color: var(--dark-gray);
`;

const SliderFrame = styled.div`
  position: relative;
  margin: 28px 4.44% 20px;
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

const SendFrame = styled.div`
  display: flex;
  margin: 20px 4.44%;
  padding: 16px 4.44%;
  justify-content: center;
  align-items: center;
  gap: 16px;
  border-radius: 10px;
  background: var(--background);
  cursor: pointer;
`;

const SendTexts = styled.div`
  width: 100%;
`;

const SendBold = styled.div`
  font-size: 14px;
  font-weight: 600;
  margin-bottom: 4px;
`;

const SendText = styled.div`
  font-size: 13px;
  color: var(--dark-gray);
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

const NoPost = styled.div<{ hasSendFrame?: boolean }>`
  height: ${props => props.hasSendFrame ? 'calc(100% - 318px)' : 'calc(100% - 230px)'}; 
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 16px;
  margin-top: 96px;
  font-size: 16px;
  font-weight: 500;
  color: var(--nav-gray);
`;

const Text = styled.div`
  color: var(--dark-gray);
  text-align: center;
  padding: 32px 0;
  line-height: 24px;
`;