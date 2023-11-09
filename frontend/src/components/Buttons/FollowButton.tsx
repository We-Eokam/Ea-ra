import styled from "styled-components";
import { useState, useEffect, SetStateAction } from "react";


interface FollowBtnProps {
  status?: string;
  setStatus: React.Dispatch<SetStateAction<string>>;
}

const FollowBtn = ({ status, setStatus }: FollowBtnProps) => {
  const [content, setContent] = useState("");
  const [color, setColor] = useState("black");
  const [bgColor, setBgColor] = useState("gray");

  useEffect(() => {
    if (status === "follow") {
      setContent("친구 끊기");
      setColor("black");
      setBgColor("gray");
    } else if (status === "request") {
      setContent("요청됨");
      setColor("primary");
      setBgColor("third");
    } else if (status === "accept") {
      setColor("white");
      setContent("수락하기");
      setBgColor("blue");
    } else if (status === "nothing") {
      setContent("친구 맺기");
      setColor("white");
      setBgColor("primary");
    }
  }, [status]);

  const handleBtnClick = () => {
    if (status === "follow") {
      // 친구 진짜 끊으시겠습니까? -> 확인 후 친구 삭제 요청 보내고 성공하면 친구 끊기
      setStatus("nothing");
    } else if (status === "accept") {
      // 수락하기 누르면 승인 acios 보낸 후 성공하면 바로 친구 된 것
      setStatus("follow");
    } else if (status === "nothing") {
      // 친구신청 요청보낸 후 성공하면 요청됨으로 변경
      setStatus("request");
    }
  };

  return (
    <>
      <Button
        style={{
          color: `var(--${color})`,
          backgroundColor: `var(--${bgColor})`,
        }}
        onClick={handleBtnClick}
      >
        {content}
      </Button>
    </>
  );
};

const Button = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100px;
  height: 32px;
  font-size: 13px;
  font-weight: 500;
  border-radius: 6px;
  cursor: pointer;
`;

export default FollowBtn;
