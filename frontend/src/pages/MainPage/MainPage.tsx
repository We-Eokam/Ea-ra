import styled from "styled-components"
import NavBar from "../../components/NavBar/NavBar"
import MainFrame from "../../components/MainFrame/MainFrame"
import { ModalFrame } from "../../components/Modal/ModalFrame"
import ProgressBar from "../../components/ProgressBar/ProgressBar"

export default function MainPage() {
  var progress = 40
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