![OrderLabs](./frontend/src/assets/img/readme/Banner.png)


<br>


## 한 줄 소개

생육 데이터와 기상 정보를 활용해 농산물의 주문 가능 여부를 자동 판단하는 스마트 주문 관리 플랫폼

---

## 📋 프로젝트 개요

| 항목 | 내용 |
|------|------|
| **기간** | 2025.06 ~ 2025.09 |
| **인원** | 5명 |
| **내 역할** | DB 설계(ERD/요구사항), 주문 UI · UI 품질 개선, 주문 API 구현 · Swagger 문서화, GitHub 문서 |
| **핵심 기술** | Java, Spring Boot, Vue 3, MySQL, MariaDB, SpringDoc(Swagger) |

---

## 🕵️ 팀원 소개

<div align="center">

| <img src="https://avatars.githubusercontent.com/u/149382180?v=4" width="100" height="100"/> | <img src="https://avatars.githubusercontent.com/u/96688099?v=4" width="100" height="100"/> | <img src="https://avatars.githubusercontent.com/u/195714592?v=4" width="100" height="100"/> | <img src="https://avatars.githubusercontent.com/u/92301360?v=4" width="100" height="100"/> | <img src="https://avatars.githubusercontent.com/u/201225844?v=4" width="100" height="100"/> |
| :-----------------------------------------------------------------------------------------: | :----------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------: | :----------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------: |
|                 🦊 **양승우**<br/>[@atimaby28](https://github.com/atimaby28)                 |                🐻 **이시욱**<br/>[@David9733](https://github.com/David9733)                |                 🦎 **구창모**<br/>[@kucha240](https://github.com/kucha240)                  |                🐰 **유현경**<br/>[@gaangstar](https://github.com/gaangstar)                |                  🐱 **윤소민**<br/>[@somminn](https://github.com/somminn)                   |

</div>

---

## 🎯 프로젝트 목적

"자라는 만큼만 주문받는다."

농산물의 생육 데이터(농촌진흥청 공공데이터)와 기상 데이터(기상청 공공 API)를 기반으로 
<br>주문 가능 여부를 자동 판단·관리하는 B2B 스마트 주문 플랫폼입니다.

 **기존 방식**: 재고 기반 판매<br>
 **본 서비스**: 생육·기상 데이터를 분석해 작물별 예상 수확량을 산출하고 주문 가능 여부를 자동 판단<br>
 **효과**: 생산자는 재고 과잉·무리한 주문 방지, 소비자는 신선한 상품과 정확한 일정 제공

---

## 🙋 내 기여

### 1단계 · DB 설계

- 역할별 요구사항 명세서 작성 및 ERD 설계
- DB 서버 6대 아키텍처 설계

### 2단계 · Frontend

- 템플릿 선정 및 커스터마이징으로 UI 초기 구조 구축
- Figma 디자인 작업
- 기능별 시나리오 테스트 및 오류 검토
- 주문 관련 화면(주문 생성·조회·수정·완료) 구현
- 저장된 로그인 정보를 활용해 농부(판매자)·구매자 역할별 화면 설계

### 3단계 · Backend

- 주문 관련 CRUD API 구현
- SpringDoc(Swagger) 기반 API 문서화 적용

### 4단계 · 문서 관리

- README 작성 및 구조 정리
- 기능 시연 영상 촬영 및 정리

---

## ✨ 주요 기능

- 생육·기상 데이터 기반 주문 가능 여부 판단
- 농장·작물 등록 및 재고 관리
- 장바구니·주문·결제 (Portone 연동)
- 생산량 예측 (기상 데이터 유사도 매칭)
- 카카오 OAuth2 로그인
- 푸시 알림 
- 역할별 대시보드(판매자, 구매자)

---

## 🔄 사용자 흐름

| 역할 | 예상 흐름 |
|------|-----------|
| 🌾 **농부 (판매자)** | 농장 등록 -> 생물 등록 -> 재고 관리에서 확인 |
| 🛒 **구매자** | 상품 검색 -> 상품 목록 확인 -> 주문 -> 장바구니 -> 결제 |

---

## 🌱 주문 가능 여부 판단

### 전체 흐름

```
기상 데이터 수집 (공공 API)
        │
        ▼
역대 기상 DB와 유사도 비교
        │
        ▼
유사도 점수 계산 (일사량·기온·습도 가중합)
        │
        ▼
기준 수확량 × (1 - 평균오차) → 예상 수확량
        │
        ▼
연속 예측 보정 (오늘 × 0.7 + 전날 × 0.3)
        │
        ▼
단위수확량 × 재배면적 → 재고 산정
        │
        ▼
재고 > 0 → AVAILABLE 
재고 ≤ 0 → UNAVAILABLE
```

### 유사도 점수 계산

각 과거 기상 데이터와 현재 기상 데이터의 오차율을 구하고, 가중합으로 유사도 점수를 산출합니다.

```
오차율_일사량 = |현재 일사량 - 기준 일사량| / 기준 일사량
오차율_기온   = |현재 기온   - 기준 기온  | / |기준 기온|
오차율_습도   = |현재 습도   - 기준 습도  | / 기준 습도

유사도 점수 = (오차율_일사량 × 0.4) + (오차율_기온 × 0.4) + (오차율_습도× 0.2)
```

| 항목 | 가중치 | 선택 이유 |
|------|--------|-----------|
| 일사량 | 40 % | 광합성량에 직결, 수확량 영향 가장 큼 |
| 기온 | 40 % | 생육 속도·병충해 발생과 밀접 |
| 습도 | 20 % | 보조 지표, 오차 편차가 상대적으로 작음 |

점수가 낮을수록(오차 작을수록) 현재 기상과 유사한 과거 데이터입니다.<br>
현재 기상과 유사한 과거 레코드를 유사도 점수로 정렬 후 상위 N 건 평균 수확량을 기준 yield로 사용합니다.

 기준 DB : 농촌진흥청·기상청 공공데이터 / 수록 항목 : 일사량, 기온, 습도, 실제 수확량(yield)

### 예상 수확량 계산

```
예상 수확량 = 기준 yield × (1 - 평균오차율)
```

평균오차율 : 선택된 유사 기상 데이터들의 유사도 점수 평균
오차가 클수록 예상 수확량은 보수적으로 감소

### 연속 예측 보정 (전날 반영)

단기 예측 값이 하루마다 크게 흔들리는 것을 방지하기 위해 전날 예측값을 30 % 반영합니다.

```
보정된 예상 수확량 = 오늘 예측값 × 0.7 + 전날 예측값 × 0.3
```

### 최종 판매 가능 여부 결정

```
재고 = 단위수확량(kg/㎡) × 재배면적(㎡)

재고 > 0  →  SaleStatus = AVAILABLE   (주문 가능)
재고 ≤ 0  →  SaleStatus = UNAVAILABLE (주문 불가)
```

---

## 🛠️ 기술 스택

**Backend**
Spring Boot 3.5.4, Java 17, Spring Security, JWT 0.11.5, OAuth2(Kakao), QueryDSL 5.0.0, WebSocket, AWS S3, SpringDoc 2.8.4, PortOne SDK 0.19.2, Web Push 5.1.1

**Frontend**
Vue 3.4.19, Pinia 3.0.3, Vue Router 4.3.0, Axios 1.10.0, Chart.js 4.4.1, Bootstrap 5.3.3

**DB**
MariaDB 10.6.22, MySQL 8.0.42

**Infra**
Docker, Kubernetes, Jenkins, Kaniko, Ansible, Ingress, Nginx

**모니터링**
JMeter, Prometheus, Grafana

**협업**
Git, GitHub, Figma, Discord


<div>
  <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
  <img src="https://img.shields.io/badge/Java%2017-007396?style=for-the-badge&logo=openjdk&logoColor=white">
  <img src="https://img.shields.io/badge/vue.js-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white">
  <img src="https://img.shields.io/badge/nginx-009639?style=for-the-badge&logo=nginx&logoColor=white">
  <img src="https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white">
  <img src="https://img.shields.io/badge/PINIA-FFE801?style=for-the-badge">
  <img src="https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white">
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
  <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
  <img src="https://img.shields.io/badge/kubernetes-326CE5?style=for-the-badge&logo=kubernetes&logoColor=white">
  <img src="https://img.shields.io/badge/jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white">
  <img src="https://img.shields.io/badge/AWS-242F4B?style=for-the-badge">
  <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
  <img src="https://img.shields.io/badge/discord-5865F2?style=for-the-badge&logo=discord&logoColor=white">
</div>

---

## 🗄️ DB 아키텍처

![DB 아키텍처](./frontend/src/assets/img/readme/04_Architecture.png)

---

## 🖥️ Front/Backend 아키텍처
<br>

![시스템 아키텍처](https://github.com/user-attachments/assets/1494b5ae-def8-4651-9860-0d43d639791e)

<br><br>

---

## 🏗️ CI/CD 아키텍처

![CI/CD 아키텍처](https://github.com/user-attachments/assets/64e17f01-a635-470d-aa99-d06da349d345)

> Kubernetes 클러스터 위에 Jenkins를 구축하여 GitHub Push/Merge 시 WebHook으로 파이프라인이 자동 트리거됩니다.

| 구분 | 흐름 | 배포 전략 |
|------|------|-----------|
| **Frontend** | GitHub Push → Jenkins (Git Clone → npm build → Kaniko 이미지 빌드 → Docker Hub Push → k8s 배포) | **Canary** : 20% 트래픽으로 먼저 검증 후 전체 배포. 문제 시 즉시 롤백 |
| **Backend** | GitHub Push → Jenkins (Git Clone → Gradle build → Kaniko 이미지 빌드 → Docker Hub Push → k8s 배포) | **Blue-Green** : 구버전(Blue)·신버전(Green) 동시 운영 후 전환. 문제 시 즉시 롤백 |

---

## 🤔 기술 선택 이유

<details>
<summary>Database</summary>

| 구분 | 선택 | 이유 |
|------|------|------|
| **Replication** | Master-Slave | 운영 서버의 단일 DB 장애 시 전체 서비스 중단을 막기 위해 데이터 복제(Data Replication)를 적용했습니다. Master 장애 시 Slave로 자동 전환(Failover)이 가능하도록 구성하여 서비스 가용성과 안정성을 최우선으로 하였습니다. |
| **Clustering** | Galera Cluster | 작물 상태·온도·습도·일사량 등 실시간 기상 데이터가 끊기면 자동화 시스템이 오작동할 수 있어, 클러스터링으로 장애를 대비했습니다. |
| **연산 전용 DB** | Separate DB | 운영 DB에 부하를 주지 않고 분석·집계 작업을 수행하기 위해 별도 DB를 분리했습니다. 시계열 데이터의 반복 집계 쿼리가 운영 서비스 성능에 영향을 주지 않도록 하였습니다. |

</details>

<details>
<summary>Frontend</summary>

| 구분 | 선택 | 이유 |
|------|------|------|
| **프레임워크** | Vue 3 | Composition API로 컴포넌트 재사용성 향상, 팀원 학습 경험 고려 |
| **상태 관리** | Pinia | Vue 3 공식 권장 상태 관리 라이브러리, Vuex 대비 타입 추론과 코드량 간결 |
| **배포 전략** | Canary | B2B이지만 판매자·구매자 모두 직관적인 UI가 필요. 새 버전을 일부 사용자(20%)에게 먼저 제공해 오류·반응 확인 후 전면 배포 — 점진적 검증으로 안정적인 업데이트 경험 제공 |

</details>

<details>
<summary>Backend</summary>

| 구분 | 선택 | 이유 |
|------|------|------|
| **프레임워크** | Spring Boot 3.5 | Spring Security·JPA·WebSocket 등 필요한 기능을 일관된 방식으로 통합할 수 있어 선택 |
| **인증** | JWT | Stateless 구조로 서버 세션 부담 없이 Kubernetes 다중 인스턴스 환경에서 인증 처리 가능 |
| **소셜 로그인** | Kakao OAuth2 | 별도 회원가입 없이 간편 로그인 제공, Spring Security OAuth2 Client로 연동 |
| **DB** | MariaDB | MySQL 호환 오픈소스, 팀 학습 경험 보유 |
| **ORM / 쿼리** | QueryDSL | 농장·작물 조건 검색 등 동적 쿼리가 필요한 부분에 타입 안전 쿼리 작성 |
| **결제** | PortOne | 국내 PG 통합 SDK, 서버 측 결제 금액 검증 API 제공 |
| **배포 전략** | Blue-Green | 주문·결제·재고 등 핵심 비즈니스 로직 담당 — 장애 시 서비스 신뢰도에 직접 영향. 구버전(Blue)·신버전(Green) 동시 운영 후 트래픽을 한 번에 전환해 무중단 배포. 문제 발생 시 즉시 이전 버전으로 롤백 가능 |

</details>

<details>
<summary>CI/CD</summary>

| 구분 | 선택 | 이유 |
|------|------|------|
| **빌드 자동화** | Jenkins | GitHub Actions 대비 자체 서버 기반으로 빌드 자원을 직접 제어 가능. 프론트·백엔드를 독립 에이전트로 분리 운영하는 프로젝트 특성에 적합. 다양한 브랜치 전략·품질 관리 도구 통합이 용이하고 오픈소스 생태계가 풍부해 선택 |
| **이미지 빌드** | Kaniko | Docker in Docker 방식은 보안 취약(privileged 권한)·캐시 공유 불가·성능 저하 문제가 있어 배제. Kaniko는 Docker 데몬 없이 Kubernetes 내부에서 안전하게 이미지 빌드 가능 |
| **서버 프로비저닝** | Ansible | 수동 설정 시 서버마다 환경이 달라져 재현성 문제 발생. YAML Playbook으로 다수 노드(마스터·워커)에 동일 설정을 일괄 적용 — 일관성·재사용성·자동화 확보 |
| **트래픽 라우팅** | Ingress Controller | MetalLB + LoadBalancer만으로는 서비스마다 외부 IP를 별도 할당해야 해 관리 복잡. 경로 기반 라우팅과 Blue-Green·Canary 배포 적용을 위해 필수적으로 도입. 향후 HTTPS 인증 확장도 고려 |

</details>

<details>
<summary>Infrastructure</summary>

| 구분 | 선택 | 이유 |
|------|------|------|
| **웹 서버** | Nginx (EC2) | AWS EC2에서 운영. 다중 WAS 구성 시 요청 라우팅과 부하 분산 담당 |
| **WAS** | Spring Boot × 2 (모놀리식) | 백엔드 서버 2대 구성으로 한 대 장애 시에도 나머지가 즉시 트래픽 처리 주문·결제 안정성 확보 |
| **파일 저장** | AWS S3 | 프로필·농장 이미지 등 대용량 파일 저장. DB에는 경로·메타데이터만 보관해 DB I/O 절감. Presigned URL로 WAS 부하 최소화 |
| **DB Replication** | MariaDB Master–Slave | 부하 및 장애 대비. Master는 쓰기(주문 생성·상태 변경), Slave는 읽기(상품·재고 조회) 트래픽 분산 |
| **DB 인스턴스** | AWS RDS for MariaDB | 관리형 DB로 운영 부담 절감. 향후 도메인별 MSA 전환 시 인스턴스 분리·독립 확장 가능성 확보 |

</details>

---

## 🔑 핵심 메서드 (주문 파트)

| 함수/메서드 | 위치 | 설명 |
|-------------|------|------|
| `createOrder` | OrderService | 장바구니 ID 목록으로 주문서 생성, 사용자 검증 및 총 가격 계산 |
| `orderConfirm` | OrderService | 주문 확정, 주문번호 생성 후 DB 저장 |
| `addCart` | CartService | 장바구니 담기, 기존 상품이면 수량·가격 갱신 |
| `allCarts` | CartService | 사용자별 장바구니 목록 조회 |
| `validation` | PaymentService | PortOne 결제 검증, 금액 대조 후 결제 내역 저장 |

### 🛒 주문자

#### 🙋‍♂️ 주문
<img src="https://github.com/user-attachments/assets/5e41a1f7-e3be-43c5-8a25-c20715f7eec5">
<br><br>

#### 🛒 장바구니 담기
<img src="https://github.com/user-attachments/assets/c4aa2df4-cc6a-42f6-9ed6-9f7c11082eda">
<br><br>

#### 💳 주문 관리
<img src="https://github.com/user-attachments/assets/49521164-27d7-4654-9793-4ca9ed519347">
<br><br>

---

## 🔧 트러블슈팅 / 개선 경험

### Database

- **SQL 성능 개선**: JMeter로 DB에 직접 부하를 가하고 Prometheus·Grafana로 양상을 관찰했습니다. 쿼리 구조 변경(JOIN → Subquery, 0.063s → 0.047s)과 Index 적용(2.57ms → 0.255ms)으로 응답 속도를 개선했습니다.

### Frontend

- **역할별 화면 분리 누락**
  초기 개발 시 농부(판매자)와 구매자를 구분하지 않고 단일 흐름으로 화면을 구성했습니다.
  테스트 중 두 역할이 동일한 메뉴·기능에 접근하는 문제를 발견했고, Navigation Guards와 Pinia에 저장된 사용자 역할 정보를 기반으로 농부 대시보드·구매자 대시보드를 분리 설계하여 해결했습니다.

### Backend

- **PortOne 결제 금액 검증 불일치**
  프론트에서 전달된 결제 금액과 PortOne 서버에서 조회한 실제 결제 금액이 불일치하는 경우 결제가 완료된 것처럼 처리될 수 있는 문제를 발견했습니다.
  `PaymentService.validation`에서 PortOne API로 금액을 재조회한 뒤 주문 금액과 대조하는 서버 측 검증 로직을 추가하여 해결했습니다.

- **Spring Security 필터 순서 문제**
  JWT 인증 필터(`JwtAuthFilter`)와 Kakao OAuth2 필터가 충돌하여 일부 엔드포인트에서 인증이 정상적으로 동작하지 않는 문제가 발생했습니다.
  Security 필터 체인의 순서를 명시적으로 지정하고 `/api/login` 경로는 별도 `LoginFilter`로 분리하여 해결했습니다.

### 협업

- **Merge 충돌**: 팀원들이 중간중간 merge를 하지 않아 나중에 한꺼번에 합칠 때 충돌이 자주 발생했습니다. GitHub 브랜치 정리 및 merge 확인을 담당하여 주기적인 merge를 유도하는 프로세스를 수립했습니다.

---

## 🚀 실행 및 테스트

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

---

## 📎 참고자료

<a href="https://docs.google.com/spreadsheets/d/1XSZN87etTnIHmDfupnch9-_gnz6C2W93tIQPNkhwSUY/edit?gid=1400486362#gid=1400486362" target="_blank">요구사항 명세서 바로가기</a>

<details>
<summary>ERD 보기</summary>

![ERD](./frontend/src/assets/img/readme/03_ERD.png)

</details>

<a href="https://www.figma.com/design/b62xD9jx6Nby2e4xa7ylJZ/%EC%A0%9C%EB%AA%A9-%EC%97%86%EC%9D%8C?node-id=0-1&t=51nZR2QNFvuL1JE2-1" target="_blank">Figma 화면 설계 바로가기</a>
