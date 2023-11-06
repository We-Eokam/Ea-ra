// import React from 'react'
import { useState } from "react";
import styled from "styled-components";
import { ReactComponent as SearchIcon } from "../../assets/icons/search-icon.svg"
import { ReactComponent as CloseRing } from "../../assets/icons/close_ring.svg"

export default function SearchBar() {
  const [isFocused, setIsFocused] = useState(false);
  const [inputValue, setInputValue] = useState("");

  const handleFocus = () => {
    setIsFocused(true);
  }
  const handleBlur = () => {
    setIsFocused(false);
  }

  const handleCancel = () => {
    handleBlur();
    setInputValue("");
  }

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(e.target.value);
  }

  const handleErase = () => {
    setInputValue("");
  }

  const users = [
    {
      profileImg: "",
      nickname: "어쩌라고라고어쩌라고",
      gru: 25000,
      isFollow: true,
    },
    {
      profileImg: "",
      nickname: "어쩌라고",
      gru: 25000,
      isFollow: true,
    },
    {
      profileImg: "",
      nickname: "어쩌",
      gru: 25000,
      isFollow: true,
    },
    {
      profileImg: "",
      nickname: "지구 지킴",
      gru: 25000,
      isFollow: true,
    },
  ];

  return (
    <>
      <SearchBarFrame>
        <SearchWindow isFocused={isFocused}>
          <SearchIcon />
          <SearchInput
            placeholder="검색.."
            onFocus={handleFocus}
            value={inputValue}
            onChange={handleChange}
            />
          {inputValue && <CloseRing onClick={handleErase} />}
        </SearchWindow>
        <CancelButton isFocused={isFocused} onClick={handleCancel}>취소</CancelButton>
      </SearchBarFrame>
      <SearchResultFrame isFocused={isFocused}>
        {users.map((user) => (
            <UserInfoContainer>
              <ProfileImg src={user.profileImg} />
              <TextBox>
                {user.nickname}
                <SubText>{user.gru}그루</SubText>
              </TextBox>
            </UserInfoContainer>
          ))}
      </SearchResultFrame>
    </>
  );
}

const SearchBarFrame = styled.div`
  position: absolute;
  z-index: 1;
  padding: 0 20px;
  left: 0;
  right: 0;
  height: 52px;
  border-bottom: 0.5px solid var(--gray);
  display: flex;
  align-items: center;
  margin-top: env(safe-area-inset-top);
  background-color: var(--white);
`;

const SearchWindow = styled.div<{isFocused: Boolean}>`
  /* margin: 0px 20px; */
  padding: 0px 6px;
  width: ${props => (props.isFocused ? 'calc(100% - 48px)' : '100%')};
  transition: width 0.3s ease-in-out;
  height: 32px;
  border-radius: 6px;
  background-color: var(--gray);
  display: flex;
  align-items: center;
`;

const SearchInput = styled.input`
  width: 100%;
  margin-left: 8px;
  border: none;
  outline: none;
  background-color: transparent;
`;

const CancelButton = styled.div<{isFocused : Boolean}>`
  position: absolute;
  white-space: nowrap;
  font-size: 14px;
  font-weight: 400;
  right: -24px; // Adjust this value as needed
  right: ${props => (props.isFocused ? '18px' : '-24px')};
  opacity: ${props => (props.isFocused ? '1' : '0')};
  visibility: ${props => (props.isFocused ? 'visible' : 'hidden')};
  transition: opacity 0.3s ease-in-out, visibility 0.3s ease-in-out, right 0.3s ease-in-out;
`;

const SearchResultFrame = styled.div<{ isFocused: boolean }>`
  position: absolute;
  left: 0;
  right: 0;
  margin-top: calc(env(safe-area-inset-top) + 53px);
  height: calc(100% - 76px - 53px);
  z-index: 1;
  background-color: var(--white);
  visibility: ${props => (props.isFocused ? 'visible' : 'hidden')};
  opacity: ${props => (props.isFocused ? '1' : '0')};
  transition: visibility 0.3s, opacity 0.3s, transform 0.3s;
`;

const UserInfoContainer = styled.div`
  position: relative;
  left: 0;
  right: 0;
  padding: 0 4.44%;
  display: flex;
  align-items: center;
  margin: 20px 0;
`;

const ProfileImg = styled.img`
  width: 52px;
  height: 52px;
  border-radius: 50%;
  border: 0.5px solid var(--nav-gray);
  box-sizing: border-box;
`;

const TextBox = styled.div`
  flex-grow: 1;
  margin-left: 12px;
  font-size: 15px;
  font-weight: 400;
  word-wrap: break-word;
`;

const SubText = styled.div`
  margin-top: 5px;
  font-size: 12px;
  color: var(--dark-gray);
  margin-bottom: 3px;
`;