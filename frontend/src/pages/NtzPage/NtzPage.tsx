import styled from "styled-components"
import NavBar from "../../components/NavBar/NavBar"
import MainFrame from "../../components/MainFrame/MainFrame"
import { ReactComponent as PigCoin } from "../../assets/icons/pig-coin.svg"
import { ShadowBox } from "../../components/ShadowBox/ShadowBox"

export default function NtzPage() {
  return (
    <>
      {/* <HeadBar pagename="예시" bgcolor="white" backbutton="yes"/> */}
      <MainFrame headbar="no" navbar="yes" bgcolor="background" marginsize="medium">
        <CPointContainer>
          <TextLine>
            <Bold>환경구해</Bold>님이
          </TextLine>
          <TextLine>
            이번달 모은 <Green>탄소중립포인트</Green>는
          </TextLine>
          <CPoint>
            <Bold>12,610</Bold>원
          </CPoint>
          <PigCoinFrame>
            <PigCoin/>
          </PigCoinFrame>
          <Notice>
            익월 <Bold>17-21일</Bold> 중 입금될 예정입니다
          </Notice>
        </CPointContainer>

        <InfoButtonsFrame>
          <InfoButton>
            <InfoButtonGray>
              내가 놓친
            </InfoButtonGray>
            <InfoButtonBlack>
              보조금 혜택
            </InfoButtonBlack>
            <InfoImage src="/images/subsidy.png" />
          </InfoButton>

          <InfoButton>
          <InfoButtonGray>
              내 주변
            </InfoButtonGray>
            <InfoButtonBlack>
              참여가능 매장
            </InfoButtonBlack>
            <InfoImage src="/images/store.png" />

          </InfoButton>

          <InfoButton>
          <InfoButtonGray>
              참여기업
            </InfoButtonGray>
            <InfoButtonBlack>
              60<SmallText>개</SmallText>
            </InfoButtonBlack>
            <InfoImage src="/images/company.png" />

          </InfoButton>

          <InfoButton style={{marginRight: '3.5px'}}>
          <InfoButtonGray>
              카카오톡으로
            </InfoButtonGray>
            <InfoButtonBlack>
              친구초대
            </InfoButtonBlack>
          <InfoImage src="/images/invite.png" />
          </InfoButton>
        </InfoButtonsFrame>

        <CPointContainer style={{marginTop:'8px', height:'288px'}}>

            <TextLine>
              <Bold>나의 활동 요약</Bold>
            </TextLine>
            <TextLine style={{fontSize:"14px", marginTop:'6px'}}>
              텀블러 이용하기로 가장 많은 포인트를 얻었어요!
            </TextLine>
            <div style={{marginTop:'80px', marginLeft:'40px'}}>
              여기에 차트
            </div>
        </CPointContainer>
      </MainFrame>
      <NavBar />
    </>

  )
}

const CPointContainer = styled(ShadowBox)`
  height: 308px;
  margin-top: 54px;
  padding-top: 20px;
  font-size: 18px;
  font-weight: 400;
`

const TextLine = styled.div`
  position: relative;
  width: 100%;
  margin-bottom: 4px;
  margin-left: 20px;
`

const CPoint = styled.div`
  font-size: 28px;
  margin-top: 16px;
  margin-bottom: 28px;
  margin-left: 20px;
`

const PigCoinFrame = styled.div`
  position: relative;
  width: 100%;
  height: 116px;
  display: flex;
  justify-content: center;
`

const Notice = styled.div`
  position: relative;
  margin-top: 38px;
  font-size: 12px;
  color: var(--dark-gray);
  font-weight: 400;
  margin-left: 20px;
`

const Bold = styled.span`
  font-weight: 600;
`

const Green = styled(Bold)`
  color: var(--primary);
`

const InfoButtonsFrame = styled.div`
  position: relative;
  width: calc(100%);
  height: 132px;
  margin-top: 8px;
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
  justify-content: center;
  overflow-x: auto;
`

const InfoButton = styled.div`
  width: 110px;
  height: 116px;
  background-color: var(--white);
  border-radius: 10px;
  box-shadow: 2px 2px 6px rgba(0,0,0, 0.04);
  margin-right: 16px;
`

const InfoButtonGray = styled.div`
  position: relative;
  margin-top: 14px;
  margin-left: 12px;
  color: var(--dark-gray);
  font-size: 12px;
  font-weight: 400;
`

const InfoButtonBlack = styled.div`
  position: relative;
  margin-top: 4px;
  margin-left: 12px;
  font-size: 14px;
  font-weight: 600;
`
const SmallText = styled.span`
  font-size: 11px;
  font-weight: 400;
`

const InfoImage = styled.img`
  position: absolute;
  width: 52px;
  height: auto;
  bottom: 13px;
  margin-left: 53.5px;
`