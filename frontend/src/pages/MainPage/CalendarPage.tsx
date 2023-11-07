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
  const monthOfActiveDate = moment(value).format('YYYY-MM');
  const [activeMonth, setActiveMonth] = useState(monthOfActiveDate);

  const getActiveMonth = (activeStartDate: moment.MomentInput) => {
    const newActiveMonth = moment(activeStartDate).format('YYYY-MM');
    setActiveMonth(newActiveMonth);
  };
  
  const testList = {
    "proof_sum": 2500,
    "proof_count": 10,
    "accusation_sum": 3000,
    "accusation_count": 10,
    "groo_saving_list": [
        {
            "date": "2023-11-17",
            "proof_sum": 250,
            "proof_count": 1,
            "accusation_sum": 0,
            "accusation_count": 0
        },
        {
            "date": "2023-11-18",
            "proof_sum": 250,
            "proof_count": 1,
            "accusation_sum": 0,
            "accusation_count": 0
        },
        {
            "date": "2023-11-19",
            "proof_sum": 250,
            "proof_count": 1,
            "accusation_sum": 0,
            "accusation_count": 0
        },
        {
            "date": "2023-11-20",
            "proof_sum": 250,
            "proof_count": 1,
            "accusation_sum": 0,
            "accusation_count": 0
        },
        {
            "date": "2023-11-21",
            "proof_sum": 250,
            "proof_count": 1,
            "accusation_sum": 300,
            "accusation_count": 1
        },
        {
            "date": "2023-11-22",
            "proof_sum": 250,
            "proof_count": 1,
            "accusation_sum": 300,
            "accusation_count": 1
        },
        {
            "date": "2023-11-23",
            "proof_sum": 250,
            "proof_count": 1,
            "accusation_sum": 300,
            "accusation_count": 1
        },
        {
            "date": "2023-11-24",
            "proof_sum": 250,
            "proof_count": 1,
            "accusation_sum": 300,
            "accusation_count": 1
        },
        {
            "date": "2023-11-25",
            "proof_sum": 250,
            "proof_count": 1,
            "accusation_sum": 300,
            "accusation_count": 1
        },
        {
            "date": "2023-11-26",
            "proof_sum": 250,
            "proof_count": 1,
            "accusation_sum": 300,
            "accusation_count": 1
        },
        {
            "date": "2023-11-27",
            "proof_sum": 0,
            "proof_count": 0,
            "accusation_sum": 300,
            "accusation_count": 1
        },
        {
            "date": "2023-11-28",
            "proof_sum": 0,
            "proof_count": 0,
            "accusation_sum": 300,
            "accusation_count": 1
        },
        {
            "date": "2023-11-29",
            "proof_sum": 0,
            "proof_count": 0,
            "accusation_sum": 300,
            "accusation_count": 1
        },
        {
            "date": "2023-11-30",
            "proof_sum": 0,
            "proof_count": 0,
            "accusation_sum": 300,
            "accusation_count": 1
        }
    ]
}

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
          onActiveStartDateChange={({ activeStartDate }) =>
              getActiveMonth(activeStartDate)
            }
          // @ts-ignore
          formatDay={(locale, date) =>
            new Date(date).toLocaleDateString("en-us", { day: "2-digit" })
          }
          // @ts-ignore
          tileContent={({ date, view }) => {
            const data = testList.groo_saving_list.find(
              (x) => x.date === moment(date).format("YYYY-MM-DD")
              );
              if (data) {
                console.log(data)
                return (
                <GrewFrame>
                  {data.proof_sum != 0 ? (
                    <PlusGrew>+{generateComma(Number(data.proof_sum))}</PlusGrew>
                  ) : null}
                  {data.accusation_sum != 0 ? (
                    <MinusGrew>-{generateComma(Number(data.accusation_sum))}</MinusGrew>
                  ) : null}
                </GrewFrame>
              );
            }
          }}
        />
        <MonthSumText>
          
          {Number(moment(activeMonth).format("YYYY-MM").slice(5,7))}월 종합내역
        </MonthSumText>
        <MonthActFrame>
          <MonthActText>전체 활동</MonthActText>
          <ActCountFrame>
            <ActCountText>{testList.proof_count}회</ActCountText>
            <ActGrewCount>{generateComma(testList.proof_sum)}그루</ActGrewCount>
          </ActCountFrame>
        </MonthActFrame>

        <MonthActFrame>
          <MonthActText>전체 제보</MonthActText>
          <ActCountFrame>
            <ActCountText>{testList.accusation_count}회</ActCountText>
            <ReportGrewCount>
              -{generateComma(testList.accusation_sum)}그루
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
