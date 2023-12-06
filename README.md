<div align="center">
<h2>
	생활 속 탄소 중립 실천 동행 서비스
</h2>
<img src="https://github.com/We-Eokam/Ea-ra/assets/80499642/f18375d7-6ef8-4a8f-9a77-ba5650770fd7"/>
<p>
<br>어느 날 지구가 갑자기 당신을 고소한다면?<br>
<b>EA</b>rth-t<b>RA</b>ial : 지구 재판이 시작됩니다!<br>
<br>
탄소 중립 실천을 통해 측정된 벌금을 갚아보세요! <br>
<b>어라</b>와 함께 하면 탄소 중립 포인트도 더 이상 복잡하지 않아요🤗
<br><br>
</p>
<p>
<a href=""><img src="https://img.shields.io/badge/EARA%20체험해보기-56B984?style=flat-square&logo=vercel&logoColor=white"/></a>
<a href="https://lemonade-log.notion.site/38aa0b3d39b948e8aac676cb81f86f50?v=b0a1bfa1a8c048c1b01986e233ea6772&pvs=4"><img src="https://img.shields.io/badge/팀%20 어캄%20Wiki-000000?style=flat-square&logo=notion&logoColor=white"/></a>
<br><br>
</p>

[프로젝트 소개](#🚀-프로젝트-소개) • [주요 기능](#✨-주요-기능) • [기술 스택](#🔧-기술-스택)• [인프라 구조](#⚙️-인프라-구조)• [팀원 소개](#👩‍💻-팀원-소개)

</div>

<br /><br /><br />

## 🎉 프로젝트 소개

 <table>
    <tr>
      <td align="center"><img src="=" /></td>
      <td align="center"><img src="" /></td>
      <td align="center"><img src="" /></td>
    </tr>
</table>


📝 <b>서비스 설명</b>

> 가입 후 테스트를 통해 가상의 벌금이 측정됩니다. \
> 카카오톡 공유를 통해 친구에게 테스트 결과를 공유할 수 있습니다.\
> 생활 속 탄소 중립 실천을 통해 벌금을 갚을 수 있습니다. \
> 실천을 인증하면 이를 바탕으로 예상 탄소 중립 포인트를 추정합니다. \
> 제보를 통해 친구에게 경고장을 전송할 수도 있습니다. \
> 복잡한 탄소 중립 포인트 제도 정보를 보기 쉽게 제공합니다. \
> 현재 위치를 기반으로 포인트 적립 가능한 주변 매장을 안내합니다. \

<br /><br /><br />

## ✨ 주요 기능
> 자세한 내용은 [시연 시나리오](./exec/시연시나리오.md) 를 확인해주세요😃
<br>

- [x] 카카오 **소셜 로그인** 지원
- [x] FCM(Firebase Cloud Messaging)을 활용한 푸시 **알림**
- [x] **테스트 및 공유 기능**
  - 환경 인식 관련 테스트를 통한 벌금 특정
  - SNS(카카오) 및 링크 복사를 통한 결과 공유
- [x] **메인 페이지**
  - 주/월별 활동 요약 조회
  - 사용자 활동에 따른 지구(캐릭터)의 감정 변화 표시
- [x] **인증 및 경고**
  - 탄소 중립 실천 활동 인증
  - 제보를 통한 경고장 전송
  - 사진 업로드 시 크롭 기능
- [x] **탄소 중립 포인트 관리**
  - 인증 내역을 기반으로 예상 탄소 중립 포인트 추적 및 조회
  - 탄소 중립 포인트에 관한 활동 요약 조회
  - 주변 매장 및 연계 기업 목록 조회
- [x] **프로필 페이지 (마이페이지)**
  - 친구(본인)의 인증 내역 조회
  - 본인이 보낸(받은) 경고장 조회
  - 잔여 빚 조회 및 프로그레스 바를 통한 상환 현황 표시
- [x] **피드 및 친구 관리**
  - 친구의 실시간 인증 내역 조회
  - 전체 사용자 검색
  - 친구 요청, 수락, 삭제

<br /><br /><br />

## 🔧 기술 스택

<div align="center">
<img src="https://img.shields.io/badge/React-61DAFB?style=flat-square&logo=react&logoColor=black" />
<img src="https://img.shields.io/badge/TypeScript-3178C6?style=flat-square&logo=typescript&logoColor=black" />
<img src="https://img.shields.io/badge/Vite-646CFF?style=flat-square&logo=vite&logoColor=white" />
<img src="https://img.shields.io/badge/Node.js-339933?style=flat-square&logo=nodedotjs&logoColor=white" />
<img src="https://img.shields.io/badge/npm-CB3837?style=flat-square&logo=npm&logoColor=white" />
<br />
<img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat-square&logo=spring&logoColor=white" />
<img src="https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=flat-square&logoColor=white"/>
<img src="https://img.shields.io/badge/Querydsl-008DD1?style=flat-square&logoColor=white"/>
<img src="https://img.shields.io/badge/Java%2011-3766AB?style=flat-square&logoColor=white"/>
<img src="https://img.shields.io/badge/JUnit5-25A162?style=flat-square&logo=junit5&logoColor=white"/>
<img src="https://img.shields.io/badge/REST%20Assured-139D31?style=flat-square&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring%20Cloud%20Eureka-6DB33F?style=flat-square&logoColor=white"/>
<br />
<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white" />
<img src="https://img.shields.io/badge/MariaDB-003545?style=flat-square&logo=mariadb&logoColor=white" />
<img src="https://img.shields.io/badge/MongoDB-47A248?style=flat-square&logo=mongodb&logoColor=white" />
<img src="https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=redis&logoColor=white" />
<br />
<img src="https://img.shields.io/badge/Amazon%20EC2-FF9900?style=flat-square&logo=amazonec2&logoColor=white" />
<img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white" />
<img src="https://img.shields.io/badge/Jenkins-D24939?style=flat-square&logo=jenkins&logoColor=white" />
<img src="https://img.shields.io/badge/NGINX-009639?style=flat-square&logo=nginx&logoColor=white" />
<br />
<img src="https://img.shields.io/badge/Git-F05032?style=flat-square&logo=git&logoColor=white" />
<img src="https://img.shields.io/badge/GitLab-FC6D26?style=flat-square&logo=gitlab&logoColor=white" />
<img src="https://img.shields.io/badge/Notion-000000?style=flat-square&logo=notion&logoColor=white" />
<img src="https://img.shields.io/badge/Figma-F24E1E?style=flat-square&logo=figma&logoColor=white" />
<img src="https://img.shields.io/badge/Mattermost-0058CC?style=flat-square&logo=mattermost&logoColor=white" />
<img src="https://img.shields.io/badge/Postman-FF6C37?style=flat-square&logo=postman&logoColor=white" />
</div>


<br /><br /><br />

## ⚙️ 인프라 구조

<img src="https://github.com/We-Eokam/Ea-ra/assets/80499642/acd6bd31-9a32-4ec4-b8e9-587296d65869" width="800" />

<br /><br /><br />

## 👩‍💻 팀원 소개

  <table align="center">
    <tr>
      <td align="center" colspan="3"><b>Back-End</b></td>
      <td align="center" colspan="3"><b>Front-End</b></td>
    </tr>
    <tr>
      <td align="center">김지은</td>
      <td align="center">양정훈</td>
      <td align="center">이상재</td>
      <td align="center">유연정</td>
      <td align="center">윤지현</td>
      <td align="center">임준수</td>
    </tr>
    <tr>
      <td align="center"><img src="https://avatars.githubusercontent.com/u/102013524?v=4" width="160"></td>
      <td align="center"><img src="https://avatars.githubusercontent.com/u/77479853?v=4" width="160"></td>
      <td align="center"><img src="https://avatars.githubusercontent.com/u/26706925?v=4" width="160"></td>
      <td align="center"><img src="https://avatars.githubusercontent.com/u/80499642?v=4" width="160"></td>
      <td align="center"><img src="https://avatars.githubusercontent.com/u/122516175?v=4" width="160"></td>
      <td align="center"><img src="https://avatars.githubusercontent.com/u/123082095?v=4" width="160"></td>
    </tr>
    <tr>
      <td align="center"><a href="https://github.com/jxixeun" target="_blank">@jxixeun</a></td>
      <td align="center"><a href="https://github.com/Jeong-Hoon-Yang" target="_blank">@Jeong-Hoon-Yang</a></td>
      <td align="center"><a href="https://github.com/sj7699" target="_blank">@sj7699</a></td>
      <td align="center"><a href="https://github.com/yeonuy" target="_blank">@yeonuy</a></td>
      <td align="center"><a href="https://github.com/gus991121" target="_blank">@gus991121</a></td>
      <td align="center"><a href="https://github.com/YJS96" target="_blank">@YJS96</a></td>
    </tr>
    <tr>
      <td align="center" colspan="3">API 개발, CI/CD 구축</td>
      <td align="center">디자인</td>
      <td align="center" colspan="2">UI/UX</td>
    </tr>
    <tr>
      <td align="center" width="160">고발 서버, 서비스 내 화폐 거래 서버</td>
      <td align="center" width="160">환경 활동 인증 서버, 알림 서버</td>
      <td align="center" width="160">멤버 서버, 탄소 중립 포인트 서버</td>
      <td align="center" width="160">피드, 프로필</td>
      <td align="center" width="160">테스트, 활동</td>
      <td align="center" width="160">메인, 탄소 중립 포인트</td>
    </tr>
  </table>
