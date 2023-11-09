import { useEffect, useState } from "react";
import styled from "styled-components";
import NavBar from "../../components/NavBar/NavBar";
import MainFrame from "../../components/MainFrame/MainFrame";
import { ReactComponent as PigCoin } from "../../assets/icons/pig-coin.svg";
import { ShadowBox } from "../../components/ShadowBox/ShadowBox";
import { useNavigate } from "react-router-dom";

import { ResponsivePie } from "@nivo/pie";

export default function NtzPage() {
  const [charSort, setChartSort] = useState(false);

  useEffect(() => {
    const timer = setTimeout(() => {
      setChartSort(true);
    }, 280)
    return () => clearTimeout(timer);
  }, [])

  const navigate = useNavigate();
  const chartData = [
    {
      id: "전자영수증",
      label: "전자영수증",
      value: 18,
      color: "hsl(233, 70%, 50%)",
    },
    {
      id: "텀블러",
      label: "텀블러",
      value: 32,
      color: "hsl(314, 70%, 50%)",
    },
    {
      id: "일회용컵",
      label: "일회용컵",
      value: 478,
      color: "hsl(80, 70%, 50%)",
    },
    {
      id: "다회용기",
      label: "다회용기",
      value: 226,
      color: "hsl(210, 70%, 50%)",
    },
    {
      id: "폐휴대폰",
      label: "폐휴대폰",
      value: 329,
      color: "hsl(1, 70%, 50%)",
    },
  ];

  const navigateMap = () => {
    navigate("/netzero/map");
  };

  const navigateSubsidy = () => {
    navigate("/netzero/subsidy");
  };

  const navigateComapny = () => {
    navigate("/netzero/company");
  };

  const shareKakao = () => {
    window.Kakao.Share.sendDefault({
      objectType: "feed",
      content: {
        title: "~~~~~~ 어라",
        description: `~~~님이 어라로 초대했어요`,
        imageUrl:
          "https://github.com/YJS96/eara_test_repo/blob/main/public/icons/icon-512x512.png?raw=true",
        link: {
          // [내 애플리케이션] > [플랫폼] 에서 등록한 사이트 도메인과 일치해야 함
          mobileWebUrl: `https://eara-test-repo.vercel.app/`,
          webUrl: `https://eara-test-repo.vercel.app/`,
        },
      },
    });
  };

  return (
    <>
      {/* <HeadBar pagename="예시" bgcolor="white" backbutton="yes"/> */}
      <MainFrame
        headbar="no"
        navbar="yes"
        bgcolor="background"
        marginsize="medium"
      >
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
            <PigCoin />
          </PigCoinFrame>
          <Notice>
            익월 <Bold>17-21일</Bold> 중 입금될 예정입니다
          </Notice>
        </CPointContainer>

        <InfoButtonsFrame>
          <InfoButton onClick={navigateSubsidy}>
            <InfoButtonGray>내가 놓친</InfoButtonGray>
            <InfoButtonBlack>보조금 혜택</InfoButtonBlack>
            <InfoImage src="/images/netzero/subsidy.png" />
          </InfoButton>

          <InfoButton onClick={navigateMap}>
            <InfoButtonGray>내 주변</InfoButtonGray>
            <InfoButtonBlack>참여가능 매장</InfoButtonBlack>
            <InfoImage src="/images/netzero/store.png" />
          </InfoButton>

          <InfoButton onClick={navigateComapny}>
            <InfoButtonGray>참여기업</InfoButtonGray>
            <InfoButtonBlack>
              60<SmallText>개</SmallText>
            </InfoButtonBlack>
            <InfoImage src="/images/netzero/company.png" />
          </InfoButton>

          <InfoButton onClick={shareKakao} style={{ marginRight: "3.5px" }}>
            <InfoButtonGray>카카오톡으로</InfoButtonGray>
            <InfoButtonBlack>친구초대</InfoButtonBlack>
            <InfoImage src="/images/netzero/invite.png" />
          </InfoButton>
        </InfoButtonsFrame>

        <ChartContainer>
          <TextLine>
            <Bold>나의 활동 요약</Bold>
          </TextLine>
          <TextLine style={{ fontSize: "14px", marginTop: "6px" }}>
            텀블러 이용하기로 가장 많은 포인트를 얻었어요!
          </TextLine>
          <ChartFrame>

            <ChartInner>
              <ResponsivePie
                data={chartData}
                margin={{ top: 0, right: 100, bottom: 9, left: 60 }}
                innerRadius={0.5}
                padAngle={1.3}
                cornerRadius={1}
                activeOuterRadiusOffset={8}
                borderWidth={1}
                borderColor={{
                  from: "color",
                  modifiers: [["darker", 0.2]],
                }}
                arcLinkLabelsSkipAngle={10}
                arcLinkLabelsTextColor="#727272"
                arcLinkLabelsThickness={1}
                arcLinkLabelsDiagonalLength={16}
                arcLinkLabelsStraightLength={10}
                arcLinkLabelsOffset={-11}
                arcLinkLabelsColor={{ from: "color" }}
                arcLabelsSkipAngle={10}
                arcLabelsTextColor="#1C1C1C"
                sortByValue={charSort}
                legends={[
                  {
                    anchor: "right",
                    direction: "column",
                    justify: false,
                    translateX: 112,
                    translateY: 90,
                    itemsSpacing: 1,
                    itemWidth: 100,
                    itemHeight: 15,
                    itemTextColor: "#727272",
                    itemDirection: "left-to-right",
                    itemOpacity: 1,
                    symbolSize: 10,
                    symbolShape: "circle",
                    effects: [
                    ],
                  },
                ]}
                />
            </ChartInner>
          </ChartFrame>
        </ChartContainer>
      </MainFrame>
      <NavBar />
    </>
  );
}

const CPointContainer = styled(ShadowBox)`
  height: 308px;
  margin-top: 54px;
  padding-top: 20px;
  font-size: 18px;
  font-weight: 400;
`;

const TextLine = styled.div`
  position: relative;
  width: 100%;
  margin-bottom: 4px;
  margin-left: 20px;
`;

const CPoint = styled.div`
  font-size: 28px;
  margin-top: 16px;
  margin-bottom: 28px;
  margin-left: 20px;
`;

const PigCoinFrame = styled.div`
  position: relative;
  width: 100%;
  height: 116px;
  display: flex;
  justify-content: center;
`;

const Notice = styled.div`
  position: relative;
  margin-top: 38px;
  font-size: 12px;
  color: var(--dark-gray);
  font-weight: 400;
  margin-left: 20px;
`;

const Bold = styled.span`
  font-weight: 600;
`;

const Green = styled(Bold)`
  color: var(--primary);
`;

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
`;

const InfoButton = styled.div`
  width: 110px;
  height: 116px;
  background-color: var(--white);
  border-radius: 10px;
  box-shadow: 2px 2px 6px rgba(0, 0, 0, 0.04);
  margin-right: 16px;
`;

const InfoButtonGray = styled.div`
  position: relative;
  margin-top: 14px;
  margin-left: 12px;
  color: var(--dark-gray);
  font-size: 12px;
  font-weight: 400;
`;

const InfoButtonBlack = styled.div`
  position: relative;
  margin-top: 4px;
  margin-left: 12px;
  font-size: 14px;
  font-weight: 600;
`;

const SmallText = styled.span`
  font-size: 11px;
  font-weight: 400;
`;

const InfoImage = styled.img`
  position: absolute;
  width: 52px;
  height: auto;
  bottom: 13px;
  margin-left: 53.5px;
`;

const ChartContainer = styled(CPointContainer)`
  height: 328px;
  margin-top: 8px;
  margin-bottom: 16px;
`

const ChartFrame = styled.div`
  position: relative;
  margin-top: 8px;
  width: 100%;
  height: 260px;
  display: flex;
  justify-content: center;
`

const ChartInner = styled.div`
  position: relative;
  width: 100%;
  max-width: 372px;
  height: 100%;
  /* max-width: 380px; */
`