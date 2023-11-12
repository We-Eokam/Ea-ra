// import React from "react";
import { useEffect, useState } from "react";
import styled from "styled-components";
import HeadBar from "../../components/HeadBar/HeadBar";
import { ModalFrame } from "../../style/ModalFrame";
import "../../style/kakaomapOverlay.css";
import actList from "../../common/act.json";

interface CategoryProps {
  isSelected: boolean;
}

declare global {
  interface Window {
    kakao: any;
  }
}

interface Store {
  company_id: number;
  company_name: string;
  branch_name: string;
  latitude: number;
  longitude: number;
  distance: number;
}

interface Activity {
  activity_type: string;
  stores: Store[];
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

  const categoryInEnglish = [
    "",
    "ELECTRONIC_RECEIPT",
    "TUMBLER",
    "DISPOSABLE_CUP",
    "REFILL_STATION",
    "MULTI_USE_CONTAINER",
    "HIGH_QUALITY_RECYCLED_PRODUCTS",
    "ECO_FRIENDLY_PRODUCTS",
    "EMISSION_FREE_CAR",
    "DISCARDED_PHONE",
  ];

  const [selectedCategoryIndex, setSelectedCategoryIndex] = useState<number>(0);
  var filteredList: Store[];

  // 클릭된 카테고리를 출력
  const handleCategoryClick = (index: number) => {
    if (selectedCategoryIndex === 0 && index === 0) {
    } else if (selectedCategoryIndex != index) {
      setSelectedCategoryIndex(index);
    } else if (selectedCategoryIndex != 0 && selectedCategoryIndex === index) {
      setSelectedCategoryIndex(0);
    }
  };

  const store_list: Activity[] = [
    {
      activity_type: "EMISSION_FREE_CAR",
      stores: [
        {
          company_id: 3,
          company_name: "쏘카",
          branch_name: "땡땡지점",
          latitude: 127.0282206319216,
          longitude: 37.51267716377659,
          distance: 4800,
        },
        {
          company_id: 3,
          company_name: "CU",
          branch_name: "역삼캠퍼스",
          latitude: 127.0307206319216,
          longitude: 37.51267716377659,
          distance: 1200,
        },
      ],
    },
    {
      activity_type: "TUMBLER",
      stores: [
        {
          company_id: 1,
          company_name: "스타벅스",
          branch_name: "강남점",
          latitude: 127.0264206319216,
          longitude: 37.51267716377659,
          distance: 100.0,
        },
      ],
    },
    {
      activity_type: "ELECTRONIC_RECEIPT",
      stores: [
        {
          company_id: 2,
          company_name: "메가MGC커피",
          branch_name: "언주로점",
          latitude: 127.000001,
          longitude: 35.0,
          distance: 110.0,
        },
      ],
    },
  ];

  if (selectedCategoryIndex === 0) {
    filteredList = store_list.flatMap((item) => item.stores);
  } else {
    const selectedType = categoryInEnglish[selectedCategoryIndex];
    const foundItem = store_list.find(
      (item) => item.activity_type === selectedType
    );
    filteredList = foundItem ? foundItem.stores : [];
  }
  filteredList.sort((a, b) => a.distance - b.distance);

  const calculateTime = (distance: number) => {
    if (distance < 80) {
      return "1분";
    } else if (distance < 4800) {
      return `${Math.round(distance / 80)}분`;
    } else {
      return `${Math.floor(distance / 4800)}시간`;
    }
  };

  function getLogoPath(companyName: string) {
    for (let act of actList) {
      for (let company of act.companies) {
        if (company.name === companyName) {
          return company.logo;
        }
      }
    }
  }

  useEffect(() => {
    // console.log(filteredList);
    let container = document.getElementById("map"); //지도를 담을 영역의 DOM 레퍼런스
    let options = {
      //지도를 생성할 때 필요한 기본 옵션
      center: new window.kakao.maps.LatLng(37.5013068, 127.0396597), //지도의 중심좌표.
      level: 4, //지도의 레벨(확대, 축소 정도)
    };

    let map = new window.kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

    map.setMinLevel(1);
    map.setMaxLevel(8);

    const displayMarkers = () => {
      // 지도 영역을 얻어옵니다.
      let bounds = map.getBounds();
      // @ts-ignore
      let swLatLng = bounds.getSouthWest(); // 남서쪽 좌표를 얻어옵니다.
      // @ts-ignore
      let neLatLng = bounds.getNorthEast(); // 북동쪽 좌표를 얻어옵니다.

      filteredList.forEach((place) => {
        let position = new window.kakao.maps.LatLng(
          place.longitude,
          place.latitude
        );
        // 현재 지도 영역 내에 있는지 확인합니다.
        if (bounds.contain(position)) {
          // 마커를 생성하고 지도에 표시합니다.
          // @ts-ignore
          let marker = new window.kakao.maps.Marker({
            map: map,
            position: position,
          });

          var customContent = `<div class="custom-overlay">${place?.company_name} ${place?.branch_name}</div>`;

          // @ts-ignore
          var customOverlay = new window.kakao.maps.CustomOverlay({
            map: map,
            position: position,
            content: customContent,
            yAnchor: 2.6,
          });

          // let infowindow = new window.kakao.maps.InfoWindow({
          //   map: map,
          //   position: position,
          //   content: place.name,
          // })

          // infowindow.open(map, marker)
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

        var locPosition = new window.kakao.maps.LatLng(lat, lon);
        map.setCenter(locPosition);

        // 마커와 인포윈도우를 표시합니다
        var imageSrc = "/images/netzero/gps-my.png";
        var imageSize = new window.kakao.maps.Size(32, 32);
        var imageOption = { offset: new window.kakao.maps.Point(16, 24) };

        var markerImage = new window.kakao.maps.MarkerImage(
          imageSrc,
          imageSize,
          imageOption
        );

        var marker = new window.kakao.maps.Marker({
          position: locPosition,
          image: markerImage,
        });

        marker.setMap(map);
      });
    }

    window.kakao.maps.event.addListener(map, "dragend", function () {
      // 지도의 중심 좌표를 얻어옵니다

      var center = map.getCenter();
      var mapLevel = map.getLevel();
      var radius = 505;
      if (mapLevel == 1) {
        radius = 58;
      } else if (mapLevel == 2) {
        radius = 112;
      } else if (mapLevel == 3) {
        radius = 260;
      } else if (mapLevel == 4) {
        radius = 505;
      } else if (mapLevel > 4) {
        radius = 995;
      }
      console.log(
        `드래그가 끝난 후 중앙 좌표: 레벨(${mapLevel}), 반경(${radius}) 위도(${center.getLat()}), 경도(${center.getLng()})`
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
          <CurrencyInfoFrame />

          <StoreScroll>
            {filteredList?.length > 0 ? (
              filteredList.map((Store, index: number) => (
                <StoreFrame key={index}>
                  <LogoFrame>
                    <Logo src={getLogoPath(Store.company_name)} />
                  </LogoFrame>
                  <StoreInfoFrame>
                    <StoreName>
                      {Store?.company_name} {Store?.branch_name}
                    </StoreName>
                    <StoreInfo>
                      {Store?.distance < 1000
                        ? `${Store?.distance}m`
                        : `${(Store?.distance / 1000).toFixed(1)}km`}
                      &nbsp;&nbsp;
                      <Middot>&middot;</Middot>&nbsp;&nbsp;
                      {calculateTime(Store?.distance)}
                    </StoreInfo>
                  </StoreInfoFrame>
                </StoreFrame>
              ))
            ) : (
              <div>dd</div>
            )}
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
  margin-top: max(47px, calc(47px + env(safe-area-inset-top)));
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
  height: calc(100% - 52px - 48px - env(safe-area-inset-top));
  margin-top: calc(48px + env(safe-area-inset-top) + 51px);
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
  height: 12px;
`;

const StoreScroll = styled.div`
  position: relative;
  width: 100%;
  height: calc(100% - 16px);
  overflow-y: scroll;
`;

const StoreFrame = styled.div`
  position: relative;
  width: 100%;
  height: 100px;
  display: flex;
  align-items: center;
  border-bottom: 1px solid var(--gray);
`;

const LogoFrame = styled.div`
  position: relative;
  width: 68px;
  height: 68px;
  border: 1px solid var(--gray);
  border-radius: 8px;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
`;

const Logo = styled.img`
  position: relative;
  width: 100%;
  height: auto;
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
  font-size: 15x;
  font-weight: 550;
`;

const StoreInfo = styled.div`
  position: relative;
  font-size: 12.5px;
  font-weight: 400;
  margin-top: 6px;
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
