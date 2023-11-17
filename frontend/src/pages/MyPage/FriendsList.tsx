// import React from 'react'
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import HeadBar from "../../components/HeadBar/HeadBar";
import MainFrame from "../../components/MainFrame/MainFrame";
import NavBar from "../../components/NavBar/NavBar";
import { ReactComponent as ReportSend } from "../../assets/icons/report-send-icon.svg";
import { ReactComponent as PersonCancel } from "../../assets/icons/person_cancel.svg";
import OptionModal from "../../components/Modal/OptionModal";
import axiosInstance from "../../api/axiosInstance";

interface User {
  id: number;
  profileImg?: string;
  nickname?: string;
  groo?: number;
}

export default function FriendsList() {
  const navigate = useNavigate();
  const axios = axiosInstance();

  const [modalOpen, setModalOpen] = useState(false);
  const [modalContent, setModalContent] = useState("");

  const [friends, setFriends] = useState<User[]>([]);
  const [target, setTarget] = useState<User>({
    id: 0
  });

  useEffect(() => {
    getFriends();
  }, []);

  const getFriends = async () => {
    try {
      const response = await axios.get(`/member/follow/list`);
      const data = response.data;

      const transUsers = data.member_list.map((member: any) => ({
        id: member.member_id,
        profileImg: member.profile_image_url,
        nickname: member.nickname,
        groo: member.groo
      }));

      setFriends(transUsers);
    } catch (error) {
      console.log(error);
    }
  }

  const deleteFriend = async () => {
    if (!target.id) return;

    try {
      await axios.delete(`/member/follow?targetId=${target.id}`);
      getFriends();
      closeModal();
    } catch (error) {
      console.error('친구 삭제 실패:', error);
    }
  }

  const handleReportBtn = (e: any, userId: number) => {
    e.stopPropagation();
    goToReport(userId);
  }

  const goToReport = (userId: number) => {
    navigate(`/act/report?target=${userId}`);
  }

  const handleDeleteBtn = (e: any, newTarget: User) => {
    e.stopPropagation();
    showModal(newTarget);
  }

  const showModal = (newTarget: User) => {
    setTarget(newTarget);
    setModalContent("정말로 " + newTarget.nickname + "님과 친구를 끊으시겠습니까?")
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
    setTarget({ id: 0 });
  };

  return (
    <>
      <HeadBar pagename="친구 목록" bgcolor="white" backbutton="yes" center={true} />
      <MainFrame headbar="yes" navbar="yes" bgcolor="white" marginsize="medium">
        {friends.length === 0 ? (
          <NoFriends> 친구가 없습니다. </NoFriends>
        ) : (
          friends.map((friend) => (
            <UserInfoContainer onClick={() => navigate(`/profile/${friend.id}`)}>
              <ProfileImg src={friend.profileImg} />
              <TextBox>
                {friend.nickname}
                <SubText>{friend.groo}그루</SubText>
              </TextBox>
              <IconContainer>
                <ReportSend onClick={(e) => handleReportBtn(e, friend.id)} />
                <PersonCancel onClick={(e) => handleDeleteBtn(e, friend)} />
              </IconContainer>
            </UserInfoContainer>
          ))
        )}
      </MainFrame>

      <OptionModal title="친구 끊기" content={modalContent} btnText="삭제" isOpen={modalOpen} closeModal={closeModal} onConfirm={() => deleteFriend} />
      <NavBar />
    </>
  )
}

const NoFriends = styled.div`
  height: calc(100% - 96px);
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 16px;

  font-size: 16px;
  font-weight: 500;
  color: var(--nav-gray);
`;

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
  gap: 20px;
`;