import { useRef, useState } from 'react';
import { Cropper, ReactCropperElement } from 'react-cropper';
import 'cropperjs/dist/cropper.css';
import styled from 'styled-components';

interface CropProps {
  onCrop: (image: string) => void;
  children: React.ReactNode;
}

const ImageCropper = ({ onCrop, children }: CropProps) => {
  const inputRef = useRef<HTMLInputElement>(null);
  const cropperRef = useRef<ReactCropperElement>(null);
  const [image, setImage] = useState<null | string>(null);
        
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

  const getCropData = () => {
    if (typeof cropperRef.current?.cropper !== "undefined") {
      onCrop(cropperRef.current?.cropper.getCroppedCanvas().toDataURL());
      setImage(null);
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
        <ModalContainer>
          <div className="backdrop" />
          <div className="modal">
            <h3>이미지 편집하기</h3>
            <div className="content-wrapper">
              <div className="content">
                <Cropper
                  ref={cropperRef}
                  aspectRatio={1}
                  src={image}
                  viewMode={1}
                  width={800}
                  height={500}
                  background={false}
                  responsive
                  autoCropArea={1}
                  checkOrientation={false}
                  guides
                />
              </div>
            </div>
            <div className="footer">
              <button onClick={() => setImage(null)}>취소</button>
              <button className="crop" onClick={getCropData}>
                적용하기
              </button>
            </div>
          </div>
        </ModalContainer>
      )}
    </Container>
  );
};

export default ImageCropper;

const Container = styled.div`
`;

const ModalContainer = styled.div`
  position: relative;
  display: inline-block;
  cursor: pointer;

  .backdrop {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: 1;
  }

  .modal {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: white;
    padding: 20px;
    z-index: 2;
  }

  h3 {
    text-align: center;
  }

  .content-wrapper {
    display: flex;
    justify-content: center;
  }

  .content {
    width: 100%;
    max-width: 800px;
    height: 500px;
  }

  .footer {
    display: flex;
    justify-content: center;
    margin-top: 10px;

    button {
      margin: 5px;
      padding: 10px;
    }
  }
`;

const ProfileInput = styled.input`
  position: absolute;
  width: 100%;
  height: 100%;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  border: 0;
  z-index: 2;
`;

const InputLabel = styled.label`
  position: absolute;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  &:hover {
    cursor: pointer;
  }
  overflow: hidden;
  z-index: 3;
`;