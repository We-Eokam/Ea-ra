import styled from "styled-components"
import NavBar from "../../components/NavBar/NavBar"
import MainFrame from "../../components/MainFrame/MainFrame"
import { ModalFrame } from "../../components/Modal/ModalFrame"
import ProgressBar from "../../components/ProgressBar/ProgressBar"
import { ShortButton } from "../../components/Buttons/ShortButton"

export default function MainPage() {
  var progress = 100
  var greenInit = 2400000

  return (
    <>
      <MainFrame headbar="no" navbar="yes" bgcolor="third" marginsize="no">
        <HomeFrame>
          <ShowDate>
            10월 23일 기준
          </ShowDate>
          <NicknameLine>
            <Bold>환경구해</Bold>님의 남은 빚
          </NicknameLine>
          <GreenLeft>
            <Bold>1,242,600</Bold>그린
          </GreenLeft>
          
          <ProgressBar progress={progress} greeninit={greenInit}/>
          
          <SummaryText>
            주간 활동 요약
          </SummaryText>

          <WeekdayFrame>
            <OneDay>
              <DayName>일</DayName>
              <DayNumber>22</DayNumber>
              <DayProgress/>
            </OneDay>
            <OneDay>
              <DayName>월</DayName>
              <DayNumber>23</DayNumber>
              <DayProgress/>
            </OneDay>
            <OneDay>
              <DayName>화</DayName>
              <DayNumber>24</DayNumber>
              <DayProgress/>
            </OneDay>
            <OneDay>
              <DayName>수</DayName>
              <DayNumber>25</DayNumber>
              <DayProgress/>
            </OneDay>
            <OneDay>
              <DayName>목</DayName>
              <DayNumber>26</DayNumber>
              <DayProgress/>
            </OneDay>
            <OneDay>
              <DayName>금</DayName>
              <DayNumber>27</DayNumber>
              <DayProgress/>
            </OneDay>
            <OneDay>
              <DayName>토</DayName>
              <DayNumber>28</DayNumber>
              <DayProgress/>
            </OneDay>
          </WeekdayFrame>

          <ButtonsFrame>
            <LeftButton>남은 빚 갚기</LeftButton>
            <RightButton>월별 내역</RightButton>
          </ButtonsFrame>

        </HomeFrame>
      </MainFrame>
      <NavBar />
    </>

  )
}

const HomeFrame = styled(ModalFrame)`
  padding-left: 24px;
  padding-right: 24px;
  height: 56%;
  font-weight: 400;
  overflow-y: scroll;
`

const ShowDate = styled.div`
  margin-top: 28px;
  font-size: 12px;
  color: var(--dark-gray);
`

const NicknameLine = styled.div`
  margin-top: 3px;
  font-size: 18px;
`

const Bold = styled.span`
  font-weight: 600;
`

const GreenLeft = styled.div`
  margin-top: 14px;
  font-size: 28px;
  margin-bottom: 8px;
`

const SummaryText = styled.div`
  margin-top: 24px;
  font-size: 14.5px;
  font-weight: 550;
`

const WeekdayFrame = styled.div`
  position: relative;
  width: calc(100% + 24px);
  margin-left: -12px;
  height: 96px;
  margin-top: 8px;
  display: flex;
  justify-content: space-between;
  align-items: center;
`

const OneDay = styled.div`
  position: relative;
  width: 14.2%;
  height: 90%;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
`

const DayName = styled.div`
  position: relative;
  width: 100%;
  text-align: center;
  font-size: 12px;
`

const DayNumber = styled.div`
  position: relative;
  width: 100%;
  text-align: center;
  font-size: 13.5px;
  font-weight: 500;
`

const DayProgress = styled.div`
  width: 8px;
  height: 8px;
  border-radius: 2px;
  margin-top: 4px;
  background-color: var(--primary);
`

const ButtonsFrame = styled.div`
  position: relative;
  width: 100%;
  margin-top: 32px;
  display: flex;
  justify-content: space-between;
`

const LeftButton = styled(ShortButton)`
  position: relative;
  width: 47.5%;
  background-color: var(--third);
  color: var(--primary);
`

const RightButton = styled(ShortButton)`
  position: relative;
  width: 47.5%;

`

