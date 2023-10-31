// import React from 'react'
import { useState, useEffect, useRef } from 'react';
import NavBar from "../../components/NavBar/NavBar"
import HeadBar from "../../components/HeadBar/HeadBar";
import MainFrame from "../../components/MainFrame/MainFrame";
import styled from "styled-components";
import { ReactComponent as LeftArrow } from '../../assets/icons/left-arrow.svg'
import { ShadowBox } from '../../components/ShadowBox/ShadowBox';

export default function ActPage() {
  const imageBox = useRef<HTMLDivElement | null>(null);
  const [num, setNum] = useState<number>(1);
  const [carouselTransition, setCarouselTransition] = useState('');
  const images = [
    "src/assets/images/bn1.png",
    "src/assets/images/bn2.png",
    "src/assets/images/bn1.png",
  ];
  const cloneImages = [images[images.length - 1], ...images, images[0]];
  const lastImage = cloneImages.length - 1;

  useEffect(() => {
    if (num == lastImage) handleOriginSlide(1);
    else if (num === 0) handleOriginSlide(lastImage - 1);
  }, [cloneImages.length, lastImage, num]);
  
  useEffect(() => {
    const timer = setInterval(() => {
      setNum((num) => num + 1);
      setCarouselTransition('transform 500ms ease-in-out');
    }, 3500);

    return () => {
      clearInterval(timer);
    };
  }, []);

  function handleSlide(direction: string) {
    direction === 'prev' ? setNum((num) => num - 1) : setNum((num) => num + 1);
    setCarouselTransition('transform 500ms ease-in-out');
  }

  function handleOriginSlide(index: number): void {
    setTimeout(() => {
      setNum(index);
      setCarouselTransition('');
    }, 500);
  }

  // const customVH = window.innerHeight * 0.01;
  // document.documentElement.style.setProperty("--vh", customVH + "px");
  // window.addEventListener("resize", () => {
  //   document.documentElement.style.setProperty("--vh", customVH + "px");
  // });

  return (
    <>
      <HeadBar pagename="활동 인증" bgcolor="background" backbutton="no" />
      <MainFrame headbar="yes" navbar="yes" bgcolor="background" marginsize="no">
        <CarouselContainer>
          <Carousel
            style={{
              transition: `${carouselTransition}`,
              transform:`translateX(-${num * 100}%)`,
            }}
            ref={imageBox}
          >
            {cloneImages.map((image, idx) => {
              return <BannerImg key={idx} src={image} alt={`Image ${idx}`} />;
            })}
          </Carousel>
          <CarouselControls>
            <LeftArrow onClick={() => handleSlide('prev')} />
            <RightArrow onClick={() => handleSlide('next')} />
          </CarouselControls>
        </CarouselContainer>

        <ActsContainer>
          <ActRowFrame style={{ height: '25.7%', fontSize: "2.3vh" }}>
            <CustomShadowBox style={{ paddingTop: "4%" }}>
              전자영수증
              <SubText style={{ marginTop: "8px" }}>종이 영수증 대신<br/>전자영수증을<br/>발급받았어요</SubText>
              <ImgBox style={{ height: "60%" }} src='src/assets/images/e-receipt.png' />
            </CustomShadowBox>
            <CustomShadowBox style={{ paddingTop: "4%" }}>
              텀블러•다회용컵
              <SubText style={{ marginTop: "8px" }}>카페 갈 때<br/>텀블러를 지참했어요</SubText>
            </CustomShadowBox>
          </ActRowFrame>

          <ActRowFrame>
            <CustomShadowBox>
              일회용컵 반환
              <SubText>사용한 일회용품을 반납했어요</SubText>
              <ImgBox src='src/assets/images/tumbler.png' />
            </CustomShadowBox>
          </ActRowFrame>

          <ActRowFrame style={{ height: '14.6%' }}>
            <CustomShadowBox>
              리필스테이션
              <SubText>리필스테이션을<br/>이용했어요</SubText>
              <ImgBox src='src/assets/images/station.png' />
            </CustomShadowBox>
            <CustomShadowBox>
              다회용기
              <SubText>다회용기에<br/>음식을 포장했어요</SubText>
            </CustomShadowBox>
          </ActRowFrame>

          <ActRowFrame>
            <CustomShadowBox>
              고품질 재활용품
              <SubText>이건 왜 환경에 도움이 되는걸까? 왜일까??</SubText>
            </CustomShadowBox>
          </ActRowFrame>
          <ActRowFrame style={{ height: '11.8%', fontSize: "1.9vh" }}>
            <CustomShadowBox>친환경제품</CustomShadowBox>
            <CustomShadowBox>무공해차</CustomShadowBox>
            <CustomShadowBox>폐휴대폰</CustomShadowBox>
          </ActRowFrame>

          <ActRowFrame>
            <CustomShadowBox>
              기타
              <SubText>지구를 보호했어요</SubText>
            </CustomShadowBox>
            <CustomShadowBox>
              목격담 제보하기
              <SubText>환경파괴범을 목격했어요</SubText>
            </CustomShadowBox>
          </ActRowFrame>
        </ActsContainer>
      </MainFrame>
      <NavBar />
    </>
  );
}

const CarouselContainer = styled.div`
  width: 100%;
  overflow: hidden;
  position: relative;
`;

const Carousel = styled.div`
  display: flex;
`;

const CarouselControls = styled.div`
  position: absolute;
  display: flex;
  justify-content: space-between;
  left: 0;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
`;

const BannerImg = styled.img`
  width: 100%;
`;

const RightArrow = styled(LeftArrow)`
  transform: scaleX(-1);
`;

const CustomShadowBox = styled(ShadowBox)`
  padding: 3% 4%;
  font-weight: 550;
  cursor: pointer;
`;

const ActsContainer =  styled.div`
  position: relative;
  margin: 32px 5.56% 12px;
  display: flex;
  flex-direction: column;
  height: 86%;
  gap: 2%;
`;

const ActRowFrame = styled.div`
  position: relative;
  width: 100%;
  height: 12.5%;
  display: flex;
  justify-content: center;
  gap: 3.5%;
  font-size: 2.2vh;
`;

const SubText = styled.div`
  color: var(--dark-gray);
  font-size: 1.6vh;
  font-weight: 400;
  margin-top: 4px;
`;

const ImgBox = styled.img`
  position: absolute;
  right: 4%;
  bottom: 4%;
  height: 70%;
`;
