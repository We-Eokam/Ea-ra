// import React from 'react'
import { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

import data from "../../common/question.json";
import MainFrame from "../../components/MainFrame/MainFrame";
import { LongButton } from "../../style";

interface AnswerProps {
  type: string;
  content1: string;
  content2: string;
}

interface DataProps {
  id: number;
  question1: string;
  question2: string;
  answers: AnswerProps[];
}

export default function TestPage() {
  const [id, setId] = useState(0);
  const [question, setQuestion] = useState<DataProps>(data[0]);
  const [results, setResults] = useState<string[]>([]);

  const navigate = useNavigate();

  useEffect(() => {
    setQuestion(data[id]);

    if (id === 5) {
      setResults(JSON.parse(localStorage.getItem("results") || '{}'));
      navigate("/result");
    };
  }, [id]);

  const hadleAnswerClick = (type: string) => {
    if (type === "+") {
      let count = Number(localStorage.getItem("results")) + 1;
      localStorage.setItem("results", `${count}`);
    }
    setId(id + 1);
  };

  return (
    <>
      <MainFrame headbar="yes" navbar="yes" marginsize="large" bgcolor="">
        {/* 진행상황 바 */}
        <QuestionFrame>
          <Situation dangerouslySetInnerHTML={{__html: question.question1}} />
          <div>{question.question2}</div>
        </QuestionFrame>
        <MarginFrame/>
      </MainFrame>
      <BtnFrame>
        {question.answers.map((ans) => {
          return (
            <Button onClick={() => hadleAnswerClick(ans.type)}>
              {ans.content1}
              <Action>{ans.content2}</Action>
            </Button>
          )
        })}
      </BtnFrame>
    </>
  );
}

const QuestionFrame = styled.div`
  padding: 20px 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 40px;
  font-size: 22px;
  font-weight: 650;
`;

const Situation = styled.div`
  font-size: 17.5px;
  font-weight: 400;
  color: var(--dark-gray);
  text-align: center;
`;

const MarginFrame = styled.div`
  width: 100%;
  height: 32%;
  max-height: 164px;
`

const BtnFrame = styled.div`
  position: absolute;
  left: 6.67%;
  right: 6.67%;
  bottom: 0;
  padding-bottom: 8%;
  z-index: 2;
  background-color: var(--white);
`;

const Button = styled(LongButton)`
  flex-direction: column;
  width: calc(100% - 16px);
  font-weight: 300;
  margin: 20px 4px;
  padding: 16px 4px;
`;

const Action = styled.div`
  font-size: 18px;
  font-weight: 500;
  padding-top: 8px;
`;