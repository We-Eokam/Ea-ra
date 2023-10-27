// import React from 'react'
import styled from "styled-components";

interface MainFrameProps {
  headbar: string;
  navbar: string;
  bgcolor: string;
  marginsize: string;
  children: React.ReactNode;
}

export default function MainFrame({
  headbar,
  navbar,
  marginsize,
  bgcolor,
  children,
}: MainFrameProps) {
  var marginTop = 0;
  var marginBottom = 0;
  var frame = 0;
  var marginsides = 0;

  if (headbar === "yes") {
    marginTop += 96;
    frame += 96;
  }

  if (navbar === "yes") {
    marginBottom += 76;
    frame += 76;
  }

  if (marginsize === "small") {
    marginsides = 16;
  } else if (marginsize === "medium") {
    marginsides = 20;
  } else if (marginsize === "large") {
    marginsides = 24;
  } else if (marginsize === "no") {
    marginsides = 0;
  }

  return (
    <Main
      style={{
        backgroundColor: `var(--${bgcolor})`,
        height: `calc(100% - ${frame}px)`,
        marginTop: `${marginTop}px`,
        marginBottom: `${marginBottom}px`,
        paddingLeft: `${marginsides}px`,
        paddingRight: `${marginsides}px`,
        padding: `0px ${marginsides}px`,
      }}
    >
      {children}
    </Main>
  );
}

const Main = styled.div`
  position: relative;
  top: 0;
  left: 0;
  right: 0;
  height: 100%;
  background-color: var(--white);
  color: var(--black);
  overflow-x: hidden;
  overflow-y: scroll;
`;
