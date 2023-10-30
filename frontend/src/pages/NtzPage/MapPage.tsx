// import React from "react";
import { useState } from "react";
import styled from "styled-components";
import HeadBar from "../../components/HeadBar/HeadBar";
import { ModalFrame } from "../../components/Modal/ModalFrame";

interface CategoryProps {
  isSelected: boolean;
}

export default function MapPage() {
  const categoryList = [
    "전체보기",
    "전자영수증",
    "텀블러",
    "컵반환",
    "리필스테이션",
    "다회용기",
    "재활용품",
    "친환경제품",
    "무공해차",
    "폐휴대폰",
  ];

  const TumblerExample = [
    {
      company: "스타벅스",
      name: "스타벅스 GS타워점",
      type: "2",
      address: "주소",
      distance: "12m",
      time: "도보 1분",
    },
    {
      company: "스타벅스",
      name: "스타벅스 역삼대로점",
      type: "1",
      address: "주소",
      distance: "120m",
      time: "도보 3분",
    },
    {
      company: "스타벅스",
      name: "스타벅스 큰길타워점",
      type: "2",
      address: "주소",
      distance: "240m",
      time: "도보 6분",
    },
    {
      company: "스타벅스",
      name: "스타벅스 역삼대로점",
      type: "1",
      address: "주소",
      distance: "120m",
      time: "도보 3분",
    },
  ];

  const [selectedCategoryIndex, setSelectedCategoryIndex] = useState<number>(0);

  // 클릭된 카테고리를 출력
  const handleCategoryClick = (index: number) => {
    if (selectedCategoryIndex === 0 && index === 0) {
    } else if (selectedCategoryIndex != index) {
      setSelectedCategoryIndex(index);
    } else if (selectedCategoryIndex != 0 && selectedCategoryIndex === index) {
      setSelectedCategoryIndex(0);
    }
  };

  return (
    <>
      <HeadBar pagename="지도" bgcolor="white" backbutton="yes" />
      <Categories>
        <Margin />
        {categoryList.map((category, index) => (
          <Category
            key={index}
            isSelected={index === selectedCategoryIndex}
            onClick={() => handleCategoryClick(index)}
          >
            {category}
          </Category>
        ))}
      </Categories>
      <MapAndModal>
        <MapFrame></MapFrame>
        <MapModal>
          <CurrencyInfoFrame>
            <CurrencyInfo>탄소중립포인트&nbsp;&nbsp;&nbsp;그린</CurrencyInfo>
          </CurrencyInfoFrame>

          <StoreScroll>
            {TumblerExample.map((Store, index) => (
              <StoreFrame key={index}>
                <LogoFrame>로고</LogoFrame>
                <StoreInfoFrame>
                  <StoreName>{Store?.name}</StoreName>
                  <StoreInfo>
                    {Store?.type} &nbsp; {Store?.distance}&nbsp;&nbsp;
                    <Middot>&middot;</Middot>&nbsp;&nbsp;{Store?.time}
                  </StoreInfo>
                </StoreInfoFrame>
              </StoreFrame>
            ))}
            <HideLastBorder />
          </StoreScroll>
        </MapModal>
      </MapAndModal>
    </>
  );
}

const Categories = styled.div`
  position: absolute;
  z-index: 3;
  margin-top: 96px;
  width: 100%;
  height: 52px;
  background-color: var(--white);
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
  justify-content: center;
  overflow-x: auto;
  border-bottom: 1px solid var(--gray);
`;

const Margin = styled.div`
  position: relative;
  width: 14px;
  height: 100%;
`;

const Category = styled.div<CategoryProps>`
  position: relative;
  height: 32px;
  width: auto;
  border-radius: 20px;
  border: ${(props) => (props.isSelected ? "1px solid transparent" : "1px solid var(--gray)")};
  background-color: ${(props) =>
    props.isSelected ? "var(--primary)" : "var(--white)"};
  color: ${(props) => (props.isSelected ? "var(--white)" : "var(--dark-gray)")};
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 13px;
  font-weight: 400;
  margin-right: 12px;
  margin-bottom: 2.5px;
  padding: 0px 14px;
`;

const MapAndModal = styled.div`
  position: absolute;
  width: 100%;
  height: calc(100% - 146px);
  margin-top: 146px;
`;

const MapFrame = styled.div`
  position: relative;
  width: 100%;
  height: 53%;
  background-color: var(--white);
`;

const MapModal = styled(ModalFrame)`
  position: absolute;
  bottom: 0;
  height: 52.6%;
  overflow-y: hidden;
`;

const CurrencyInfoFrame = styled.div`
  position: relative;
  width: 100%;
  height: 16px;
  margin-top: 20px;
`;

const CurrencyInfo = styled.span`
  position: absolute;
  font-size: 9px;
  right: 0;
  color: var(--dark-gray);
`;

const StoreScroll = styled.div`
  position: relative;
  width: 100%;
  height: calc(100% - 34px);
  overflow-y: scroll;
`;

const StoreFrame = styled.div`
  position: relative;
  width: 100%;
  height: 96px;
  display: flex;
  align-items: center;
  border-bottom: 1px solid var(--gray);
`;

const LogoFrame = styled.div`
  position: relative;
  width: 64px;
  height: 64px;
  background-color: var(--background);
  border-radius: 8px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const StoreInfoFrame = styled.div`
  position: relative;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin-left: 14px;
`;

const StoreName = styled.div`
  position: relative;
  font-size: 14px;
  font-weight: 550;
`;

const StoreInfo = styled.div`
  position: relative;
  font-size: 12px;
  font-weight: 400;
  margin-top: 5px;
  color: var(--dark-gray);
`;

const Middot = styled.span`
  font-weight: 700;
`;

const HideLastBorder = styled.div`
  position: relative;
  width: 100%;
  height: 5px;
  background-color: var(--white);
  margin-top: -2.5px;
`;
