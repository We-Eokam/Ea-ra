// import React from 'react'
import { useState, useEffect, useRef } from "react";
import HeadBar from "../../components/HeadBar/HeadBar";
import MainFrame from "../../components/MainFrame/MainFrame";
import styled from "styled-components";
import ImageCropper from "../../components/ImageCropper/ImageCropper";

export default function PostPage() {
  const [type, setType] = useState(0);
  const [croppedImage, setCroppedImage] = useState<string | null>(null);
  const [frameWidth, setFrameWidth] = useState<number | null>(null);
  const pictureFrameRef = useRef(null);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const typeFromURL = Number(params.get("type"));

    if (0 <= typeFromURL && typeFromURL < 10) {
      setType(typeFromURL);
    }
  
    function handleResize() {
      const frame = pictureFrameRef.current; // ref를 사용하여 요소에 접근
      if (frame) {
        setFrameWidth(frame.offsetWidth);
      }
    }
    window.addEventListener("resize", handleResize);
    handleResize(); // 초기 frameWidth 설정

    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, [location]);

  const handleImageCrop = (image: string) => {
    setCroppedImage(image);
  };

  return (
    <>
      <HeadBar pagename="" bgcolor="white" backbutton="yes" />
      <MainFrame headbar="yes" navbar="no" bgcolor="white" marginsize="medium">
        <PictureFrame>
          <ImageCropper onCrop={handleImageCrop}>
            {croppedImage ? (
              <CropImg
                style={{ height: `${frameWidth}px` }}
                src={croppedImage} alt="Cropped" />
              ) : (
              <ImgIcon src="../src/assets/images/upload-image-icon.png" />
            )}
          </ImageCropper>
        </PictureFrame>
      </MainFrame>
    </>
  );
}

const PictureFrame = styled.div`
  position: relative;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: var(--background);
  border-radius: 4px;
`;

const CropImg = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;

const ImgIcon = styled.img`
  width: 36%;
  padding: 32%;
`;

// const CropImg = styled.img`
//   width: 100%;
//   object-fit: cover;
// `;