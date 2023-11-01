// import React from 'react'
import { useState, useEffect, useRef } from "react";
import HeadBar from "../../components/HeadBar/HeadBar";
import MainFrame from "../../components/MainFrame/MainFrame";
import styled from "styled-components";
import ImageCropper from "../../components/ImageCropper/ImageCropper";

export default function PostPage() {
  const [type, setType] = useState(0);
  const [croppedImage, setCroppedImage] = useState<string | null>(null);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const typeFromURL = Number(params.get("type"));

    if (0 <= typeFromURL && typeFromURL < 10) {
      setType(typeFromURL);
    }
  }, [location]);

  const handleImageCrop = (image: string) => {
    setCroppedImage(image);
  };

  return (
    <>
      <HeadBar pagename="" bgcolor="white" backbutton="yes" />
      <MainFrame headbar="yes" navbar="no" bgcolor="white" marginsize="large">
        <div style={{ margin: "1px" }} />
        <ImageCropper onCrop={handleImageCrop}>
          {croppedImage ? (
            <CropImg src={croppedImage} alt="Cropped" />
          ) : (
            <ImgIcon src="../src/assets/images/upload-image-icon.png" />
          )}
        </ImageCropper>
      </MainFrame>
    </>
  );
}

const CropImg = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;

const ImgIcon = styled.img`
  width: 36%;
  padding: 32%;
`;
