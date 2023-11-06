import styled from "styled-components";
import NavBar from "../../components/NavBar/NavBar";
import MainFrame from "../../components/MainFrame/MainFrame";
import { ModalFrame } from "../../components/Modal/ModalFrame";
import ProgressBar from "../../components/ProgressBar/ProgressBar";
import { ShortButton } from "../../style";
import { useNavigate } from "react-router-dom";

import { ReactComponent as Notification } from "../../assets/icons/notification-icon.svg";


export default function MainPage() {
  const navigate = useNavigate();

  const toNotification = () => {
    navigate("/notice")
  }

  const toMonthCal = () => {
    navigate("/calendar");
  };

  const toAct = () => {
    navigate("/act")
  }

  function getCurrentWeek() {
    const day = new Date();
    const sunday = day.getTime() - 86400000 * day.getDay();

    day.setTime(sunday);

    const result = [day.toISOString().slice(0, 10)];

    for (let i = 1; i < 7; i++) {
      day.setTime(day.getTime() + 86400000);
      result.push(day.toISOString().slice(0, 10));
    }

    return result;
  }

  const currentWeek = getCurrentWeek();

  const onlyDay = currentWeek.map((date) => {
    const lastTwoChars = date.slice(-2);
    if (lastTwoChars.startsWith("0")) {
      return lastTwoChars.substring(1);
    }
    return lastTwoChars;
  });

  var progress = 100;
  var greenInit = 2400000;

  const weekActCount = [4, 2, 0, 6, 8, 4, 0];

  const getCountColor = (count: number): string => {
    if (count === 0) {
      return "var(--white)";
    } else if (count <= 2) {
      return "var(--third)";
    } else if (count <= 4) {
      return "var(--secondary)";
    } else {
      return "var(--primary)";
    }
  };

  return (
    <>
      <MainFrame headbar="no" navbar="yes" bgcolor="third" marginsize="no">
        <NotificationIcon onClick={toNotification} />
        <HomeFrame>
          <ShowDate>10월 23일 기준</ShowDate>
          <NicknameLine>
            <Bold>환경구해</Bold>님의 남은 빚
          </NicknameLine>
          <GreenLeft>
            <Bold>1,242,600</Bold>그린
          </GreenLeft>

          <ProgressBar progress={progress} greeninit={greenInit} />

          <SummaryText>주간 활동 요약</SummaryText>

          <WeekdayFrame>
            <WeekNameFrame>
              <WeekName>일</WeekName>
              <WeekName>월</WeekName>
              <WeekName>화</WeekName>
              <WeekName>수</WeekName>
              <WeekName>목</WeekName>
              <WeekName>금</WeekName>
              <WeekName>토</WeekName>
            </WeekNameFrame>
            {onlyDay.map((day, index) => (
              <OneDay>
                <DayNumber>{day}</DayNumber>
                <DayProgress count={getCountColor(weekActCount[index])} />
              </OneDay>
            ))}
          </WeekdayFrame>

          <ButtonsFrame>
            <ShortButton background="var(--third)" color="var(--primary)" onClick={toAct} >
              남은 빚 갚기
            </ShortButton>
            <ShortButton onClick={toMonthCal}>월별 내역</ShortButton>
          </ButtonsFrame>
        </HomeFrame>
      </MainFrame>
      <NavBar />
    </>
  );
}

const NotificationIcon = styled(Notification)`
  position: absolute;
  right: 5.56%;
  top: calc(env(safe-area-inset-top) + 40px);
  filter: drop-shadow(2px 2px 6px rgba(0,0,0,0.12))

`

const HomeFrame = styled(ModalFrame)`
  padding: 0px 5.56%;
  max-height: 56.4%;
  font-weight: 400;
  overflow-y: scroll;
`;

const ShowDate = styled.div`
  margin-top: calc(4px + 5.56%);
  font-size: 12px;
  color: var(--dark-gray);
`;

const NicknameLine = styled.div`
  margin-top: 3px;
  font-size: 18px;
`;

const Bold = styled.span`
  font-weight: 600;
`;

const GreenLeft = styled.div`
  margin-top: 14px;
  font-size: 28px;
  margin-bottom: 8px;
`;

const SummaryText = styled.div`
  margin-top: 24px;
  font-size: 14.5px;
  font-weight: 550;
`;

const WeekdayFrame = styled.div`
  position: relative;
  width: 105.56%;
  margin-left: -2.78%;
  height: 84px;
  margin-top: 8px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
`;

const WeekNameFrame = styled.div`
  position: relative;
  width: 100%;
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  margin-top: 14px;
  margin-bottom: 12px;
`;

const WeekName = styled.div`
  position: relative;
  width: calc(100% / 7);
  text-align: center;
`;

const OneDay = styled.div`
  position: relative;
  width: 14.2%;
  height: 90%;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
`;

const DayNumber = styled.div`
  position: relative;
  width: 100%;
  text-align: center;
  font-size: 13.5px;
  font-weight: 500;
`;

const DayProgress = styled.div<{ count: string }>`
  width: 8px;
  height: 8px;
  border-radius: 2px;
  margin-top: 4px;
  background-color: ${(props) => props.count};
`;

const ButtonsFrame = styled.div`
  position: relative;
  width: 100%;
  margin-top: 56px;
  display: flex;
  justify-content: space-between;
  margin-bottom: 24px;
`;
