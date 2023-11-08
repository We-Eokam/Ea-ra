import styled from "styled-components";
import { useState, useEffect } from 'react';

interface FollowBtnProps {
  status?: string;
}

const FollowBtn = ({ status }: FollowBtnProps) => {
  const [followed, setFollowed] = useState(false);
  const [content, setContent] = useState("");
 
  useEffect(() => {
    if (status === "follow") {
      setContent("친구 끊기");
      setFollowed(true);
    } else if (status === "request") {
      setContent("요청됨");
      setFollowed(true);
    } else if (status === "accept") {
      setContent("수락하기");
    } else if (status === "nothing") {
      setContent("친구 맺기");
    }
  }, [status]);

  const handleFollowClick = async () => {
    // axios 요청 성공 후 버튼 바뀌도록
    // setFollowed(!followed)
  };

  return (
    <>
      <Button followed={followed} onClick={handleFollowClick}>
        {content}
      </Button>
    </>
  );
};

const Button = styled.div<{ followed?: boolean }>`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100px;
  height: 32px;
  background-color: ${(props) =>
    props.followed ? "var(--gray)" : "var(--primary)"};
  color: ${(props) =>
    props.followed ? "var(--black)" : "var(--white)"};
  font-size: 13px;
  font-weight: 500;
  border-radius: 6px;
  cursor: pointer;
`;

export default FollowBtn;
