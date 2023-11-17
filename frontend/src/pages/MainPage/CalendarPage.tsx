// import React from "react";
import { useState, useEffect } from "react";
import HeadBar from "../../components/HeadBar/HeadBar";
import MainFrame from "../../components/MainFrame/MainFrame";
import NavBar from "../../components/NavBar/NavBar";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import "../../style/MonthCalendar.css";
import styled from "styled-components";
import moment from "moment";
import axiosInstance from "../../api/axiosInstance";

interface GrooSavingProps {
  date: string;
  proof_sum: number;
  proof_count: 1;
  accusation_sum: number;
  accusation_count: number;
}

interface MonthCalendarProps {
  proof_sum: number;
  proof_count: number;
  accusation_sum: number;
  accusation_count: number;
  groo_saving_list: GrooSavingProps[];
}

export default function CalendarPage() {
  const [value, onChange] = useState<any>(new Date());
  const monthOfActiveDate = moment(value).format("YYYY-MM");
  const [activeMonth, setActiveMonth] = useState(monthOfActiveDate);
  const [monthGroo, setMonthGroo] = useState<MonthCalendarProps | null>(null);
  const axios = axiosInstance();

  const apiYear = activeMonth.slice(0, 4);
  const apiMonth = Number(activeMonth.slice(5, 7));

  const getMonthAct = async () => {
    try {
      const response = await axios.get(
        `/groo?year=${apiYear}&month=${apiMonth}`
      );
      const data = await response.data;
      setMonthGroo(data);
    } catch (error) {
      console.log(error);
    }
  };

  const getActiveMonth = (activeStartDate: moment.MomentInput) => {
    const newActiveMonth = moment(activeStartDate).format("YYYY-MM");
    setActiveMonth(newActiveMonth);
  };

  useEffect(() => {
    getMonthAct();
  }, [activeMonth]);

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
            const data = monthGroo?.groo_saving_list.find(
              (x: any) => x.date === moment(date).format("YYYY-MM-DD")
            );
            if (data) {
              return (
                <GrewFrame>
                  {data.proof_sum != 0 ? (
                    <PlusGrew>
                      +{generateComma(Number(data.proof_sum))}
                    </PlusGrew>
                  ) : null}
                  {data.accusation_sum != 0 ? (
                    <MinusGrew>
                      -{generateComma(Number(data.accusation_sum))}
                    </MinusGrew>
                  ) : null}
                </GrewFrame>
              );
            }
          }}
        />
        <MonthSumText>
          {Number(moment(activeMonth).format("YYYY-MM").slice(5, 7))}월 종합내역
        </MonthSumText>

        {monthGroo?.proof_count === null &&
        monthGroo?.accusation_count === null ? (
          <MonthActText style={{ marginTop: "-8px", fontSize: "15px" }}>
            활동 탭에서 환경활동을 시작해보세요
          </MonthActText>
        ) : (
          <MonthActFrame>
            <MonthActText>전체 활동</MonthActText>
            <ActCountFrame>
              <ActCountText>
                {monthGroo?.proof_count ? monthGroo.proof_count : 0}회
              </ActCountText>
              <ActGrewCount>
                {generateComma(monthGroo?.proof_sum ? monthGroo.proof_sum : 0)}
                그루
              </ActGrewCount>
            </ActCountFrame>
          </MonthActFrame>
        )}

        {monthGroo?.proof_count === null &&
        monthGroo?.accusation_count === null ? (
          <></>
        ) : (
          <MonthActFrame>
            <MonthActText>전체 제보</MonthActText>
            <ActCountFrame>
              <ActCountText>
                {monthGroo?.accusation_count ? monthGroo.accusation_count : 0}회
              </ActCountText>
              <ReportGrewCount>
                -
                {generateComma(
                  monthGroo?.accusation_sum ? monthGroo.accusation_sum : 0
                )}
                그루
              </ReportGrewCount>
            </ActCountFrame>
          </MonthActFrame>
        )}
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
  font-size: 12px;
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
