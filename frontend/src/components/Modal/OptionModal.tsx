import styled from "styled-components";
import AnimationModal from "./AnimationModal";
import { ReactComponent as Edit } from "../../assets/icons/edit-icon.svg";
import { ReactComponent as Trash } from "../../assets/icons/Trash-icon.svg";

interface OptionModalProps {
  isOpen: boolean;
  closeModal: () => void;
}

export default function OptionModal({
  isOpen,
  closeModal
}: OptionModalProps) {
  
  return (
    <AnimationModal isOpen={isOpen} closeModal={closeModal}>
      <OptionFrame>
        <Opt>
          <Edit />
          <OptText>수정</OptText>
        </Opt>
        <Opt>
          <Trash />
          <OptText isRed={true}>삭제</OptText>
        </Opt>
      </OptionFrame>
    </AnimationModal>
  );
};

const OptionFrame = styled.div`
  width: 100%;
  border-radius: 12px;
  border: 1px solid var(--gray);
  background-color: var(--background);
`;

const Opt = styled.div`
  padding: 14px 16px;
  display: flex;
  align-items: center;

  &:not(:last-child) {
    border-bottom: 1px solid var(--gray);
  }
`;

const OptText = styled.div<{ isRed?: boolean }>`
  font-weight: 500;
  margin-left: 12px;
  flex-grow: 1;
  margin-top: 1px;
  
  ${(props) => props.isRed && `color: var(--red);`}
`;