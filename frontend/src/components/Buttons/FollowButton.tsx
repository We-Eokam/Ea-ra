import styled from "styled-components";
import { useState, useEffect } from 'react';

interface FollowBtnProps {
  isFollow?: boolean;
}

const FollowBtn = ({ isFollow }: FollowBtnProps) => {
  const [followed, setFollowed] = useState(isFollow);
 
  useEffect(() => {
    setFollowed(isFollow);
  }, [isFollow]);

  const handleFollowClick = async () => {
    // axios 요청 성공 후 버튼 바뀌도록
    setFollowed(!followed)
  };

  return (
    <>
      <Button followed={followed} onClick={handleFollowClick}>
        {followed ? "친구 끊기" : "친구 맺기"}
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
