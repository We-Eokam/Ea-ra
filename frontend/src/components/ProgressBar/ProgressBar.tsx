// import React from 'react'
import styled from 'styled-components'

interface ProgressBarProps {
  progress: number;
  greeninit: number;
}

export default function ProgressBar({progress, greeninit} : ProgressBarProps) {
  var newMargin = progress
  if (progress < 4) {
    newMargin = 3.5
  }

  if (progress > 96) {
    newMargin = 96
  }

  if (progress >= 100) {
    newMargin = 95
  }

  return (
    <ProgressFrame>
      <ProgressRate style={{marginLeft: `calc(${newMargin}% - 11px)`}}>
        {progress}%
      </ProgressRate>
      <ProgressBarBack />
      <ProgressBarFront style={{width: `${progress}%`}} />
      <GreenFirst>
        {greeninit?.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",")}그린
      </GreenFirst>

    </ProgressFrame>
  )
}

const ProgressFrame = styled.div`
  position: relative ;
  width: 100%;
  height: 50px;
`

const ProgressRate = styled.div`
  position: absolute;
  font-size: 11px;
  color: var(--dark-gray);
  margin-top: 1px;
`
const ProgressBarBack = styled.div`
  position: absolute;
  margin-top: 20px;
  width: 100%;
  height: 8px;
  background-color: var(--third);
  border-radius: 16px;
  z-index: 1;
`

const ProgressBarFront = styled.div`
  position: absolute;
  margin-top: 20px;
  width: 60%;
  height: 8px;
  background-color: var(--primary);
  border-radius: 16px;
  z-index: 2;
`
const GreenFirst = styled.div`
  position: absolute;
  right: 0px;
  margin-top: 35px;
  font-size: 11px;
  color: var(--dark-gray);
`