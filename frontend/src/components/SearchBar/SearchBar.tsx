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

  return (
    <SearchBarFrame>
      <SideMargin />
      <SearchWindow>
        <SearchIcon />
        <SearchInput
          placeholder="검색.."
          onFocus={handleFocus}
          value={inputValue}
          onChange={handleChange}
        />
        {inputValue && <CloseRing onClick={handleErase} />}
      </SearchWindow>
      {isFocused && <CancelButton onClick={handleCancel}>취소</CancelButton>}
      <SideMargin />
    </SearchBarFrame>
  );
}

const SideMargin = styled.div`
  width: 5.56%;
`;

const SearchBarFrame = styled.div`
  position: relative;
  width: 100%;
  height: 52px;
  border-bottom: 0.5px solid var(--gray);
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const SearchWindow = styled.div`
  padding: 0px 6px;
  width: 100%;
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

const CancelButton = styled.div`
  width: 25px;
  white-space: nowrap;
  font-size: 14px;
  font-weight: 400;
  margin-left: 12px;
`;
