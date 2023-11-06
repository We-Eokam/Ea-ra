// import React from "react";
import { useEffect, useState } from "react";
import styled from "styled-components";
import HeadBar from "../../components/HeadBar/HeadBar";
import { ModalFrame } from "../../components/Modal/ModalFrame";
import "../../style/kakaomapOverlay.css"
// import CompanyList from "../../common/act.json"

interface CategoryProps {
  isSelected: boolean;
}

declare global {
  interface Window {
    kakao: any;
  }
}

export default function MapPage() {
  const categoryList = [
    "전체보기",
    "전자영수증",
    "텀블러",
    "일회용컵 반환",
    "리필스테이션",
    "다회용기",
    "고품질 재활용품",
    "친환경제품",
    "무공해차",
    "폐휴대폰",
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

  const places = [
    {
      category: "텀블러",
      company: "스타벅스",
      name: "스타벅스 학동역점",
      lat: 37.5146173,
      lng: 127.0307978,
      distance: "더미m",
      time: "더미분",
      type: 2,
    },
    {
      category: "텀블러",
      company: "스타벅스",
      name: "스타벅스 논현힐탑점",
      lat: 37.5114981,
      lng: 127.0321654,
      distance: "더미m",
      time: "도보 더미분",
      type: 2
    },
    {
      category: "전자영수증",
      company: "KB국민은행",
      name: "KB국민은행 학동역",
      lat: 37.5135422,
      lng: 127.0305215,
      distance: "더미m",
      time: "더미초",
      type: 2,
    }
  ];

  
  useEffect(() => {
    let container = document.getElementById("map"); //지도를 담을 영역의 DOM 레퍼런스
    let options = {
      //지도를 생성할 때 필요한 기본 옵션
      center: new window.kakao.maps.LatLng(37.5013068, 127.0396597), //지도의 중심좌표.
      level: 4, //지도의 레벨(확대, 축소 정도)
    };

    let map = new window.kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

    
    const displayMarkers = () => {
      // 지도 영역을 얻어옵니다.
      let bounds = map.getBounds();
      // @ts-ignore
      let swLatLng = bounds.getSouthWest(); // 남서쪽 좌표를 얻어옵니다.
      // @ts-ignore
      let neLatLng = bounds.getNorthEast(); // 북동쪽 좌표를 얻어옵니다.
      
      const selectedCategory = categoryList[selectedCategoryIndex];
      
      places.forEach((place) => {
        if (selectedCategory === "전체보기" || place.category === selectedCategory) {
          let position = new window.kakao.maps.LatLng(place.lat, place.lng);
          
          // 현재 지도 영역 내에 있는지 확인합니다.
          if (bounds.contain(position)) {
            // 마커를 생성하고 지도에 표시합니다.
            // @ts-ignore
            let marker = new window.kakao.maps.Marker({
              map: map,
              position: position,
            });

            var customContent = `<div class="custom-overlay">${place.name}</div>`
            
            // @ts-ignore
            var customOverlay = new window.kakao.maps.CustomOverlay({
              map: map,
              position: position,
              content: customContent,
              yAnchor: 2.6,
            })

            // let infowindow = new window.kakao.maps.InfoWindow({
            //   map: map,
            //   position: position,
            //   content: place.name,
            // })

            // infowindow.open(map, marker)

            
          }
        }
      });
    };

    // 지도 이동 시 이벤트 리스너를 등록합니다.
    window.kakao.maps.event.addListener(map, "idle", displayMarkers);

    if (navigator.geolocation) {
      // GeoLocation을 이용해서 접속 위치를 얻어옵니다
      navigator.geolocation.getCurrentPosition(function (position) {
        var lat = position.coords.latitude - 0.00025, // 위도
          lon = position.coords.longitude - 0.0003; // 경도



        console.log(lat, lon);

        var locPosition = new window.kakao.maps.LatLng(lat, lon);
        map.setCenter(locPosition);

        // 마커와 인포윈도우를 표시합니다
        var imageSrc = "/images/gps-my.png";
        var imageSize = new window.kakao.maps.Size(32, 32);
        var imageOption = {offset : new window.kakao.maps.Point(16,24)};

        var markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

        var marker = new window.kakao.maps.Marker({
          position: locPosition,
          image: markerImage
        })

        marker.setMap(map);
      });
    }

    window.kakao.maps.event.addListener(map, "dragend", function () {
      // 지도의 중심 좌표를 얻어옵니다
      var center = map.getCenter();
      console.log(
        `드래그가 끝난 후 중앙 좌표: 위도(${center.getLat()}), 경도(${center.getLng()})`
      );
    });
    window.kakao.maps.event.addListener(map, "idle", displayMarkers);

    // 처음에 마커를 표시합니다.
    displayMarkers();
  }, [selectedCategoryIndex]);

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
        <MapFrame>
          <Map id="map"></Map>
        </MapFrame>
        <MapModal>
          <CurrencyInfoFrame>
            <CurrencyInfo>탄소중립포인트&nbsp;&nbsp;&nbsp;그린</CurrencyInfo>
          </CurrencyInfoFrame>

          <StoreScroll>
          {places
            .filter((Store) => 
              selectedCategoryIndex === 0 ||
              Store.category === categoryList[selectedCategoryIndex]
            )
            .map((Store, index) => (
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
  border: ${(props) =>
    props.isSelected ? "1px solid transparent" : "1px solid var(--gray)"};
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
  height: 54%;
  background-color: var(--white);
`;

const Map = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
`;

const MapModal = styled(ModalFrame)`
  position: absolute;
  bottom: 0;
  height: 48.6%;
  overflow-y: hidden;
  z-index: 3;
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
