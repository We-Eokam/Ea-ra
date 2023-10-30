import { BrowserRouter, Route, Routes } from "react-router-dom";
import LoginPage from "../pages/LoginPage/LoginPage";
import MainPage from "../pages/MainPage/MainPage";
import NotiPage from "../pages/NotiPage/NotiPage";
import FeedPage from "../pages/FeedPage/FeedPage";
import ActPage from "../pages/ActPage/ActPage";
import NtzPage from "../pages/NtzPage/NtzPage";
import MyPage from "../pages/MyPage/MyPage";


import Subsidy from "../pages/NtzPage/Subsidy";
import MapPage from "../pages/NtzPage/MapPage";
import CompanyPage from "../pages/NtzPage/CompanyPage";


export default function Router() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />}></Route>
        <Route path="/" element={<MainPage />}></Route>
        <Route path="/notice" element={<NotiPage />}></Route>
        <Route path="/feed" element={<FeedPage />}></Route>
        <Route path="/act" element={<ActPage />}></Route>
        <Route path="/netzero" element={<NtzPage />}></Route>
        <Route path="/netzero/subsidy" element={<Subsidy />}></Route>
        <Route path="/netzero/map" element={<MapPage />}></Route>
        <Route path="/netzero/company" element={<CompanyPage />}></Route>
        <Route path="/mypage" element={<MyPage />}></Route>
      </Routes>
    </BrowserRouter>
  );
}
