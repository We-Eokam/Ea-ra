// import React from 'react'
import { useState, useEffect, useRef } from 'react';
import NavBar from "../../components/NavBar/NavBar"
import HeadBar from "../../components/HeadBar/HeadBar";
import MainFrame from "../../components/MainFrame/MainFrame";
import styled from "styled-components";
import { ReactComponent as LeftArrow } from '../../assets/icons/left-arrow.svg'

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
    }, 2500);

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

  return (
    <>
      <HeadBar pagename="활동 인증" bgcolor="white" backbutton="no" />
      <MainFrame headbar="yes" navbar="yes" bgcolor="white" marginsize="no">
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