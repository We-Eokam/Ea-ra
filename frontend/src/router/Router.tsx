import { BrowserRouter, Route, Routes } from "react-router-dom";
import LoginPage from "../pages/LoginPage/LoginPage";
import MainPage from "../pages/MainPage/MainPage";
import FeedPage from "../pages/FeedPage/FeedPage";
import ActPage from "../pages/ActPage/ActPage";
import NtzPage from "../pages/NtzPage/NtzPage";
import MyPage from "../pages/MyPage/MyPage";


export default function Router() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />}></Route>
        <Route path="/" element={<MainPage />}></Route>
        <Route path="/feed" element={<FeedPage />}></Route>
        <Route path="/act" element={<ActPage />}></Route>
        <Route path="/netzero" element={<NtzPage />}></Route>
        <Route path="/mypage" element={<MyPage />}></Route>
      </Routes>
    </BrowserRouter>
  );
}
