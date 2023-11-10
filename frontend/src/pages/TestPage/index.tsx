// import React from 'react'
import { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Lottie from "lottie-react";
import JudgeEokam from "../../assets/lottie/eokam-judge.json"
import data from "../../common/question.json";
import MainFrame from "../../components/MainFrame/MainFrame";
import { LongButton } from "../../style";

interface AnswerProps {
  type: number;
  thoughts: string;
  action: string;
}

interface DataProps {
  id: number;
  situation: string;
  question: string;
  answers: AnswerProps[];
}

export default function TestPage() {
  const [id, setId] = useState(0);
  const [question, setQuestion] = useState<DataProps>(data[0]);

  const navigate = useNavigate();

  useEffect(() => {
    setQuestion(data[id]);

    if (id === 10) {
      navigate("/result");
    };

    if (id === 0) {
      localStorage.setItem("results", "50000");
    };
  }, [id]);

  const hadleAnswerClick = (type: number) => {
    if (type) {
      let debt = Number(localStorage.getItem("results")) + type;
      localStorage.setItem("results", `${debt}`);
    }
    setId(id + 1);
  };

  return (
    <>
      <ProgressGreen progress={id * 10} />
      <MainFrame headbar="no" navbar="yes" marginsize="large" bgcolor="">
        <QuestionFrame>
          <Situation dangerouslySetInnerHTML={{ __html: question.situation }} />
          <div>{question.question}</div>
        </QuestionFrame>
      </MainFrame>
      <CharacterFrame>
        <Lottie animationData={JudgeEokam} />
      </CharacterFrame>
      <BtnFrame>
        {question.answers.map((ans) => {
          return (
            <Button onClick={() => hadleAnswerClick(ans.type)}>
              {ans.thoughts}
              <Action>{ans.action}</Action>
            </Button>
          )
        })}
      </BtnFrame>
    </>
  );
}

const ProgressGreen = styled.div<{ progress: number }>`
  position: absolute;
  margin-top: env(safe-area-inset-top);
  height: 12px;
  background-color: var(--primary);
  width: ${({ progress }) => `${progress}%`};
  transition: width 0.26s ease-in-out;
  z-index: 1;
`

const QuestionFrame = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: end;
  gap: 32px;
  height: 30%;
  font-size: 22px;
  font-weight: 650;
`;

const Situation = styled.div`
  font-size: 17.5px;
  font-weight: 400;
  color: var(--dark-gray);
  text-align: center;
`;

const CharacterFrame = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 64%;
  max-width: 300px;
`;

const BtnFrame = styled.div`
  position: absolute;
  left: 6%;
  right: 6%;
  bottom: 0;
  height: 30%;
  max-height: 260px;
  z-index: 2;
  background-color: var(--white);
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Button = styled(LongButton)`
  flex-direction: column;
  width: calc(100% - 16px);
  max-width: 400px;
  font-weight: 300;
  margin: 10px 4px;
  padding: 16px 4px;
`;

const Action = styled.div`
  font-size: 17.4px;
  font-weight: 500;
  padding-top: 8px;
`;