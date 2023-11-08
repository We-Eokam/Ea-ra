// import React from 'react'
import { useEffect, useState } from "react";
import styled from "styled-components";
import { ReactComponent as SearchIcon } from "../../assets/icons/search-icon.svg";
import { ReactComponent as CloseRing } from "../../assets/icons/close_ring.svg";

interface SearchBarProps {
  setUserId: React.Dispatch<React.SetStateAction<number | null>>;
  type: string;
}

// 일단 임시로 해놓음 api 완성되면 수정 필
interface FriendDataProps {
  userId: number;
  profileImg: string;
  nickname: string;
  gru: number;
  isFollow: boolean;
}

// 임시 데이터 나중에 삭제
const users = [
  {
    profileImg: "",
    nickname: "어쩌라고라고어쩌라고",
    gru: 25000,
    isFollow: true,
    userId: 1,
  },
  {
    profileImg: "",
    nickname: "어쩌라고",
    gru: 25000,
    isFollow: true,
    userId: 2,
  },
  {
    profileImg: "",
    nickname: "어쩌",
    gru: 25000,
    isFollow: true,
    userId: 3,
  },
  {
    profileImg: "",
    nickname: "지구지킴",
    gru: 25000,
    isFollow: true,
    userId: 4,
  },
  {
    profileImg: "",
    nickname: "지",
    gru: 25000,
    isFollow: true,
    userId: 5,
  },
  {
    profileImg: "",
    nickname: "지구지",
    gru: 25000,
    isFollow: true,
    userId: 6,
  },
  {
    profileImg: "",
    nickname: "지현",
    gru: 25000,
    isFollow: true,
    userId: 7,
  },
  {
    profileImg: "",
    nickname: "지현이",
    gru: 25000,
    isFollow: true,
    userId: 8,
  },
];

export default function SearchBar({ setUserId, type }: SearchBarProps) {
  const [isFocused, setIsFocused] = useState(false);
  const [inputValue, setInputValue] = useState("");
  const [entire, setEntire] = useState<FriendDataProps[]>([]);
  const [results, setReseults] = useState<FriendDataProps[]>([]);
  const [minuspx, setMinuspx] = useState(48);

  useEffect(() => {
    // type에 따라 사용자 목록 불러와서 entire에 넣어주기
    if (type == "all") {
      // 전체 사용자 불러오는 api
    } else if (type == "follow") {
      setMinuspx(68);
      // 맞팔인 사용자만 불러오는 api
    }
    // 일단 임시 데이터 넣어놓음
    setEntire(users);
  });

  const handleFocus = () => {
    setIsFocused(true);
  };
  const handleBlur = () => {
    setIsFocused(false);
  };

  const handleCancel = () => {
    handleBlur();
    setInputValue("");
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const tmpValue = e.target.value.trim();
    setInputValue(tmpValue);
    if (tmpValue) {
      setReseults(
        entire.filter((friend: FriendDataProps) =>
          friend.nickname.includes(tmpValue)
        )
      );
    } else if (results.length) {
      setReseults([]);
    }
  };

  const handleErase = () => {
    setInputValue("");
    if (results.length) {
      setReseults([]);
    }
  };

  return (
    <>
      <SearchBarFrame type={type}>
        <SearchWindow isFocused={isFocused} px={minuspx}>
          <SearchIcon />
          <SearchInput
            placeholder="검색.."
            onFocus={handleFocus}
            value={inputValue}
            onChange={handleChange}
          />
          {inputValue && <CloseRing onClick={handleErase} />}
        </SearchWindow>
        <CancelButton isFocused={isFocused} onClick={handleCancel}>
          취소
        </CancelButton>
      </SearchBarFrame>
      <SearchResultFrame isFocused={isFocused}>
        {results.map((user) => (
          <UserInfoContainer onClick={() => setUserId(user.userId)}>
            <ProfileImg src={user.profileImg} />
            <TextBox>
              {user.nickname}
              <SubText>{user.gru}그루</SubText>
            </TextBox>
          </UserInfoContainer>
        ))}
        <BottomBox>
          {results.length ? "마지막 검색 결과입니다." : "검색 결과가 없습니다."}
        </BottomBox>
      </SearchResultFrame>
    </>
  );
}

const SearchBarFrame = styled.div<{ type: string }>`
  position: absolute;
  z-index: 1;
  padding: ${(props) => (props.type === "all" ? "0 20px" : "0")};
  left: 0;
  right: 0;
  height: 52px;
  border-bottom: 0.5px solid var(--gray);
  display: flex;
  align-items: center;
  margin-top: env(safe-area-inset-top);
  background-color: var(--white);
`;

const SearchWindow = styled.div<{ isFocused: boolean, px: number }>`
  /* margin: 0px 20px; */
  padding: 0px 6px;
  width: ${(props) => (props.isFocused ? `calc(100% - ${props?.px}px)` : "100%")};
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

const CancelButton = styled.div<{ isFocused: Boolean }>`
  position: absolute;
  white-space: nowrap;
  font-size: 14px;
  font-weight: 400;
  right: -24px; // Adjust this value as needed
  right: ${(props) => (props.isFocused ? "18px" : "-24px")};
  opacity: ${(props) => (props.isFocused ? "1" : "0")};
  visibility: ${(props) => (props.isFocused ? "visible" : "hidden")};
  transition:
    opacity 0.3s ease-in-out,
    visibility 0.3s ease-in-out,
    right 0.3s ease-in-out;
`;

const SearchResultFrame = styled.div<{ isFocused: boolean }>`
  position: absolute;
  left: 0;
  right: 0;
  margin-top: calc(env(safe-area-inset-top) + 53px);
  height: calc(100% - 76px);
  z-index: 1;
  background-color: var(--white);
  overflow-y: scroll;
  visibility: ${(props) => (props.isFocused ? "visible" : "hidden")};
  opacity: ${(props) => (props.isFocused ? "1" : "0")};
  transition:
    visibility 0.3s,
    opacity 0.3s,
    transform 0.3s;
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
  width: 48px;
  height: 48px;
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

const BottomBox = styled.div`
  text-align: center;
  padding: 20px;
  color: var(--dark-gray);
`;
