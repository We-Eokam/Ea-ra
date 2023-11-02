// import React from "react";
import { useState } from "react";
import HeadBar from "../../components/HeadBar/HeadBar";
import MainFrame from "../../components/MainFrame/MainFrame";
import NavBar from "../../components/NavBar/NavBar";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import "../../style/MonthCalendar.css";
import styled from "styled-components";
import moment from "moment";

export default function CalendarPage() {
  const [value, onChange] = useState<any>(new Date());
  const testData = [
    ["2023-11-02", 1200, 600],
    ["2023-11-04", 300, 0],
    ["2023-11-07", 0, 800],
    ["2023-11-10", 2400, 100],
    ["2023-11-13", 1200, 0],
    ["2023-11-15", 2000, 200],
    ["2023-11-17", 0, 800],
    ["2023-11-19", 600, 400],
    ["2023-11-22", 1100, 600],
    ["2023-11-25", 400, 1300],
    ["2023-11-27", 2000, 3000],
    ["2023-11-30", 200, 0],
  ];

  const onlyAct = testData.filter((data) => data[1] !== 0);
  const actSum = testData.reduce((total, data) => total + Number(data[1]), 0);

  const onlyReport = testData.filter((data) => data[2] !== 0);
  const reportSum = testData.reduce(
    (total, data) => total + Number(data[2]),
    0
  );

  const generateComma = (price: number) => {
    return price.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
  };

  return (
    <>
      <HeadBar pagename="월별 활동 내역" bgcolor="white" backbutton="yes" />
      <MainFrame headbar="yes" navbar="yes" bgcolor="white" marginsize="large">
        <MonthCalendar
          className="MonthCalendar"
          onChange={onChange}
          value={value}
          calendarType="gregory"
          locale="ko-KR"
          minDetail="month"
          showNeighboringMonth={false}
          showNavigation={true}
          // @ts-ignore
          formatDay={(locale, date) =>
            new Date(date).toLocaleDateString("en-us", { day: "2-digit" })
          }
          // @ts-ignore
          tileContent={({ date, view }) => {
            const data = testData.find(
              (x) => x[0] === moment(date).format("YYYY-MM-DD")
            );
            if (data) {
              return (
                <GrewFrame>
                  {data[1] != 0 ? (
                    <PlusGrew>+{generateComma(Number(data[1]))}</PlusGrew>
                  ) : null}
                  {data[2] != 0 ? (
                    <MinusGrew>-{generateComma(Number(data[2]))}</MinusGrew>
                  ) : null}
                </GrewFrame>
              );
            }
          }}
        />
        <MonthSumText>
          {moment(value).format("YYYY-MM-DD").slice(5, 7)}월 종합내역
        </MonthSumText>
        <MonthActFrame>
          <MonthActText>전체 활동</MonthActText>
          <ActCountFrame>
            <ActCountText>{onlyAct.length}회</ActCountText>
            <ActGrewCount>{generateComma(Number(actSum))}그루</ActGrewCount>
          </ActCountFrame>
        </MonthActFrame>

        <MonthActFrame>
          <MonthActText>전체 제보</MonthActText>
          <ActCountFrame>
            <ActCountText>{onlyReport.length}회</ActCountText>
            <ReportGrewCount>
              -{generateComma(Number(reportSum))}그루
            </ReportGrewCount>
          </ActCountFrame>
        </MonthActFrame>
        <Margin />
      </MainFrame>
      <NavBar />
    </>
  );
}

const MonthCalendar = styled(Calendar)``;

const GrewFrame = styled.div`
  position: absolute;
  width: calc(100% / 7);
  margin-top: -40px;
  margin-left: -2.8%;
  display: flex;
  flex-direction: column;
  font-weight: 400;
  justify-content: flex-start;
  align-items: flex-start;
`;

const PlusGrew = styled.div`
  font-size: 12px;
  color: var(--primary);
  position: relative;
  width: 100%;
  text-align: center;
  height: 12px;
  margin-bottom: 5px;
`;

const MinusGrew = styled.div`
  font-size: 11px;
  color: var(--red);
  position: relative;
  width: 100%;
  text-align: center;
  height: 12px;
`;

const MonthSumText = styled.div`
  position: relative;
  margin-top: 36px;
  margin-bottom: 24px;
  font-size: 18px;
  font-weight: 550;
`;

const MonthActFrame = styled.div`
  position: relative;
  width: calc(100% - 4px);
  margin-left: 2px;
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  font-size: 15px;
  font-weight: 400;
  margin-bottom: 24px;
`;

const MonthActText = styled.div``;

const ActCountFrame = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
`;

const ActCountText = styled.div`
  margin-bottom: 3px;
`;

const ActGrewCount = styled.div`
  color: var(--primary);
  font-size: 12px;
`;

const ReportGrewCount = styled.div`
  color: var(--red);
  font-size: 12px;
`;

const Margin = styled.div`
  width: 100%;
  height: 24px;
`;
