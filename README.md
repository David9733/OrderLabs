![OrderLabs](./frontend/src/assets/img/readme/Banner.png)
<h1 align="center" style="color: #50C878;"> OrderLabs 🌿</h1>


<br>


## 한 줄 소개

생육 데이터와 기상 정보를 활용해 농·수산물의 주문 가능 여부를 자동 판단하는 스마트 주문 관리 플랫폼

---

## 프로젝트 개요

| 항목 | 내용 |
|------|------|
| **기간** | 2025.06 ~ 2025.09 |
| **인원** | 5명 |
| **내 역할** | DB 설계(ERD/요구사항), 프론트엔드 품질·주문 UI, 백엔드 주문 일부·Swagger, GitHub 정리 |
| **핵심 기술** | Spring Boot, Vue 3, MariaDB, Docker, Kubernetes, Jenkins |
| **배포** | 없음 |

---

## 프로젝트 목적

"자라는 만큼만 주문받는다."

농·수산물의 생육 상태와 생물 건강 데이터를 기반으로 주문 가능 여부를 자동 판단·관리하는 B2B 스마트 주문 플랫폼입니다.

- **기존 방식**: 재고 기반 판매
- **본 서비스**: 공공 데이터(기상정보, 생육 센서 등)와 실시간 생물 정보(성장률, 건강도)를 분석해 주문·배송 시점 예측 및 자동 제어
- **효과**: 생산자는 재고 과잉·무리한 주문 방지, 소비자는 신선한 상품과 정확한 일정 제공

---

## 🕵️ 팀원 소개
<br>
<div align="center">

| <img src="https://avatars.githubusercontent.com/u/149382180?v=4" width="100" height="100"/> | <img src="https://avatars.githubusercontent.com/u/96688099?v=4" width="100" height="100"/> | <img src="https://avatars.githubusercontent.com/u/195714592?v=4" width="100" height="100"/> | <img src="https://avatars.githubusercontent.com/u/92301360?v=4" width="100" height="100"/> | <img src="https://avatars.githubusercontent.com/u/201225844?v=4" width="100" height="100"/> |
| :-----------------------------------------------------------------------------------------: | :----------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------: | :----------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------: |
|                 🦊 **양승우**<br/>[@atimaby28](https://github.com/miyad927)                 |                🐻 **이시욱**<br/>[@David9733](https://github.com/David9733)                |                 🦎 **구창모**<br/>[@kucha240](https://github.com/kucha240)                  |                🐰 **유현경**<br/>[@gaangstar](https://github.com/gaangstar)                |                  🐱 **윤소민**<br/>[@somminn](https://github.com/somminn)                   |

</div>

<br><br>
---

## 내 기여

### 1차 DB 설계

- ERP 시스템 목표로 역할 배정 및 요구사항 명세서 작성
- ERD 설계

### 2차 Frontend

- 사이트 뼈대 디자인 찾기 및 선정 (커스텀 가능한 템플릿으로 시간 절약)
- Figma 디자인
- 정상 작동 확인 및 오류 검토 (품질 담당)
- 주문 부분 프론트 화면 구현

### 3차 Backend

- 주문 부분 일부 구현 (복잡한 로직은 다른 팀원 담당, 기본 CRUD 등 상대적으로 단순한 부분 담당)
- Swagger(SpringDoc) API 문서화 담당

### 4차 CI/CD

- CI/CD는 양승우 팀원 담당 (본인 미참여)
- 대신 GitHub 정리, merge 확인 담당

---

## 주요 기능

- 생육·기상 데이터 기반 주문 가능 여부 판단
- 농장·작물 등록 및 재고 관리
- 장바구니·주문·결제 (Portone 연동)
- 생산량 예측 (기상 데이터 유사도 매칭)
- 카카오 OAuth2 로그인
- WebSocket 채팅, 푸시 알림
- 판매량 조회, 대시보드

---

## 기술 스택

**Backend** | Spring Boot 3.5, Java 17, Spring Security, JWT, OAuth2(Kakao), MariaDB, QueryDSL, WebSocket, AWS S3, SpringDoc(API 문서), Portone(결제), Web Push  
**Frontend** | Vue 3, Pinia, Vue Router, Axios, Chart.js, Bootstrap  
**Infra** | Docker, Kubernetes, Jenkins, Kaniko, Ansible, Ingress  
**협업** | Git, GitHub, Figma, Discord  

- 의존성: [backend/build.gradle](backend/build.gradle) | [frontend/package.json](frontend/package.json)

<div>
  <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
  <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
  <img src="https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white">
  <img src="https://img.shields.io/badge/discord-5865F2?style=for-the-badge&logo=discord&logoColor=white">
  <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
  <img src="https://img.shields.io/badge/kubernetes-326CE5?style=for-the-badge&logo=kubernetes&logoColor=white">
  <img src="https://img.shields.io/badge/jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white">
</div>

---

## 시스템 아키텍처

![시스템 아키텍처](https://github.com/user-attachments/assets/64e17f01-a635-470d-aa99-d06da349d345)

---

## 기술 선택 이유

| 구분 | 선택 | 이유 |
|------|------|------|
| **프론트** | Canary | UI 변경을 일부 사용자(20%)에게 먼저 적용해 검증 후 전면 배포 |
| **백엔드** | Blue-Green | 핵심 비즈니스 로직 담당. 트래픽 한 번에 전환해 무중단 배포, 문제 시 즉시 롤백 |
| **빌드** | Jenkins | GitHub Actions 대비 서버 자원 직접 제어, 프론트/백엔드 분리 빌드 |
| **이미지 빌드** | Kaniko | Docker 데몬 없이 이미지 빌드, 보안·성능 이점 |
| **설정 관리** | Ansible | 여러 서버에 동일 설정 적용, YAML Playbook으로 재현성·자동화 |
| **라우팅** | Ingress Controller | 도메인/경로 기반 라우팅, Canary·Blue-Green 배포 지원 |

---

## CI/CD

프로젝트에 CI/CD 적용됨 (Jenkins, Docker, Kubernetes)

---

## 핵심 메서드 (주문 파트)

| 함수/메서드 | 위치 | 설명 |
|-------------|------|------|
| `createOrder` | OrderService | 장바구니 ID 목록으로 주문서 생성, 사용자 검증 및 총 가격 계산 |
| `orderConfirm` | OrderService | 주문 확정, 주문번호 생성 후 DB 저장 |
| `addCart` | CartService | 장바구니 담기, 기존 상품이면 수량·가격 갱신 |
| `allCarts` | CartService | 사용자별 장바구니 목록 조회 |
| `validation` | PaymentService | PortOne 결제 검증, 금액 대조 후 결제 내역 저장 |

---

## 트러블슈팅 / 개선 경험

- **Merge 충돌**: 팀원들이 중간중간 merge를 하지 않아, 나중에 한꺼번에 합칠 때 충돌이 자주 발생함. 이를 해결하기 위해 GitHub 정리 및 merge 확인을 담당하여 주기적인 merge를 유도함.

---

## 실행 및 테스트

### 로컬 실행

**Backend**
```bash
cd backend
./gradlew bootRun
```
필요 환경변수: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `AWS_ACCESS_KEY`, `KAKAO_CLIENT_ID` 등 (application.yml 참고)

**Frontend**
```bash
cd frontend
npm install
npm run serve
```

### CI/CD 배포 테스트

1. Jenkins에 `pipelineFrontend.yaml`, `pipelineBackend.yaml` 파이프라인 등록
2. GitHub WebHook 설정 (Push/Merge 시 트리거)
3. main 브랜치 Push 후 파이프라인 자동 실행 확인




