// import React from 'react'
import NavBar from "../../components/NavBar/NavBar"
import HeadBar from "../../components/HeadBar/HeadBar"
import MainFrame from "../../components/MainFrame/MainFrame"


export default function FeedPage() {
  return (
    <>
      <HeadBar pagename="예시" bgcolor="white" backbutton="yes"/>
      <MainFrame headbar="yes" navbar="yes" bgcolor="background" marginsize="no">

      </MainFrame>
      <NavBar />
    </>
  )
}