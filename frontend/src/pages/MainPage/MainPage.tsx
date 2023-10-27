import styled from "styled-components"
import NavBar from "../../components/NavBar/NavBar"
import MainFrame from "../../components/MainFrame/MainFrame"

export default function MainPage() {
  return (
    <>
      <MainFrame headbar="no" navbar="yes" bgcolor="third" marginsize="no">
        <HomeFrame>

        </HomeFrame>
      </MainFrame>
      <NavBar />
    </>

  )
}

const HomeFrame = styled.div`
  position: absolute;
  width: 100%;
  height: 56%;
  bottom: 0;
  background-color: var(--white);
  border-radius: 25px 25px 0px 0px;
  box-shadow: 0px -2px 16px rgba(0, 0, 0, 0.12);
`