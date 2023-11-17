import React, { useState, useEffect, SetStateAction } from "react";
import styled from "styled-components";
import axiosInstance from "../../api/axiosInstance";


interface FollowBtnProps {
  status: string;
  setStatus: React.Dispatch<SetStateAction<string>> | (() => void);
  target: number;
}

const FollowBtn = ({ status, setStatus, target }: FollowBtnProps) => {
  const [content, setContent] = useState("");
  const [color, setColor] = useState("black");
  const [bgColor, setBgColor] = useState("gray");
  const axios = axiosInstance();

  useEffect(() => {
    if (status === "FRIEND") {
      setContent("친구 끊기");
      setColor("black");
      setBgColor("gray");
    } else if (status === "REQUEST") {
      setContent("요청됨");
      setColor("primary");
      setBgColor("third");
    } else if (status === "ACCEPT") {
      setColor("white");
      setContent("수락하기");
      setBgColor("blue");
    } else if (status === "NOTHING") {
      setContent("친구 맺기");
      setColor("white");
      setBgColor("primary");
    }
  }, [status]);

  const handleBtnClick = async () => {
    if (status === "REQUEST") { return; }
    
    if (status === "FRIEND") {
      try {
        const response = await axios.delete(`/member/follow?targetId=${target}`);
        setStatus(response.data.follow_status);
      } catch (error) {
        console.log(error);
      }
    } else {
      const requestData = { target_id: Number(target) };
      let endPoint = ""
      if (status === "ACCEPT") {
        endPoint = "/accept"
      }
      try {
        const response = await axios.post(`/member/follow${endPoint}`, requestData);
        setStatus(response.data.follow_status);
      } catch (error) {
        console.log(error);
      }
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
