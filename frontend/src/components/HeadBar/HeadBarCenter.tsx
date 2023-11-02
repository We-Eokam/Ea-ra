// import React from 'react'
import { useNavigate } from "react-router-dom"
import styled from "styled-components"
import { ReactComponent as LeftArrow } from '../../assets/icons/left-arrow.svg'

interface HeadBarCenterProps {
  pagename: string;
  bgcolor: string;
  backbutton: string;
}

export default function HeadBarCenter({ pagename, bgcolor, backbutton }: HeadBarCenterProps) {
  const navigate = useNavigate();

  const goBack = () => {
    navigate(-1);
  }

  return (
    <HeadBarFrame style={{ backgroundColor: `var(--${bgcolor})` }}>
      <HeadBarContext>
        {backbutton === "yes" ? <LeftArrow onClick={goBack} /> : null}
        &nbsp;
        <CenterFrame>
          {pagename}
        </CenterFrame>
      </HeadBarContext>
    </HeadBarFrame>
  )
}


const HeadBarFrame = styled.div`
  position: absolute;
  width: 100%;
  height: 96px;
  top: 0;
  left: 0;
  border-bottom: 1px solid var(--gray);;
  /* border: 1px black solid; */

  z-index: 2;
`

const HeadBarContext = styled.div`
  position: relative;
  margin-top: 60px;
  padding-left: 12px;
  display: flex;
  align-items: center;
`

const CenterFrame = styled.div`
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  font-size: 18px;
  font-weight: 600;
`;