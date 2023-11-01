import { useRef, useState } from "react";
import { Cropper, ReactCropperElement } from "react-cropper";
import "cropperjs/dist/cropper.css";
import "../../style/ImageCropper.css";
import styled from "styled-components";
import { ShortButton } from "../Buttons/ShortButton";
import { ReactComponent as RotateSvg } from "../../assets/icons/rotate-icon.svg";
import { ReactComponent as ResetSvg } from "../../assets/icons/reset-icon.svg";

interface CropProps {
  onCrop: (image: string) => void;
  children: React.ReactNode;
}

const ImageCropper = ({ onCrop, children }: CropProps) => {
  const inputRef = useRef<HTMLInputElement>(null);
  const cropperRef = useRef<ReactCropperElement>(null);
  const [image, setImage] = useState<null | string>(null);
  const [rotation, setRotation] = useState(0);

  const handleChildrenClick = () => {
    if (inputRef.current) inputRef.current.click();
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    e.preventDefault();

    const files = e.target.files;

    if (!files) return;

    const reader = new FileReader();
    reader.onload = () => {
      setImage(reader.result as string);
    };
    reader.readAsDataURL(files[0]);
  };

  const handleCancleClick = () => {
    setImage(null);
    setRotation(0);
  };

  const getCropData = () => {
    if (typeof cropperRef.current?.cropper !== "undefined") {
      onCrop(cropperRef.current?.cropper.getCroppedCanvas().toDataURL());
      setImage(null);
      setRotation(0);
    }
  };

  const rotateImage = (degrees: number) => {
    if (typeof cropperRef.current?.cropper !== "undefined") {
      if (degrees >= 0) {
        cropperRef.current?.cropper.rotate(degrees - rotation);
        setRotation(degrees);
      } else {
        cropperRef.current?.cropper.rotate(degrees);
      }
    }
  };

  const handleResetClick = () => {
    if (cropperRef.current) {
      cropperRef.current.cropper.reset();
      setRotation(0);
    }
  };

  return (
    <Container>
      <input
        type="file"
        ref={inputRef}
        style={{ display: "none" }}
        onChange={handleFileChange}
      />
      <span onClick={handleChildrenClick}>{children}</span>
      {image && (
        <ModalFrame>
          <div className="backdrop" />
          <div className="modal">
            <ModalTitle>이미지 편집하기</ModalTitle>
            <IconFrame>
              <RotateSvg onClick={() => rotateImage(-90)} />
              <ResetSvg onClick={handleResetClick}/>
            </IconFrame>
            <div className="content-wrapper">
              <Cropper
                ref={cropperRef}
                aspectRatio={1}
                src={image}
                viewMode={1}
                background={false}
                responsive
                autoCropArea={1}
                checkOrientation={false}
                guides
              />
            </div>
            Rotation: {rotation}°
            <Slider
              type="range"
              min="0"
              max="360"
              value={rotation}
              onChange={(e) => rotateImage(Number(e.target.value))}
            />
            <div className="footer">
              <LeftButton onClick={handleCancleClick}>
                취소
              </LeftButton>
              <ShortButton onClick={getCropData}>
                적용하기
              </ShortButton>
            </div>
          </div>
        </ModalFrame>
      )}
    </Container>
  );
};

export default ImageCropper;

const Container = styled.div`
  background-color: var(--background);
  border: 1px solid var(--gray);
  position: relative;
  width: 100%;
`;

const ModalFrame = styled.div`
  position: relative;
  display: inline-block;
`;

const ModalTitle = styled.div`
  font-size: 18px;
  font-weight: 550;
  text-align: center;
`;

const IconFrame = styled.div`
  position: absolute;
  right: 10%;
  display: flex;
  gap: 8px;
`;

const Slider = styled.input`
  width: calc(100% - 4px);
  background-color: transparent;
  accent-color: var(--primary);
  margin: 10px 0;
`;

const LeftButton = styled(ShortButton)`
  background-color: var(--third);
  color: var(--primary);
`;
