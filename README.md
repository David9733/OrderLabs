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
| **내 역할** | DB 설계(ERD/요구사항), 주문 UI · UI 품질 개선, 주문 API 구현, Swagger·Github 문서화 |
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

농산물의 생육 데이터(농촌진흥청 공공데이터)와 기상 데이터(기상청 공공 API)를 기반으로 주문 가능 여부를 자동 판단·관리<br>하는 
B2B 스마트 주문 플랫폼입니다.

| 구분 | 내용 |
|------|------|
| **기존 방식** | 재고 기반 판매 |
| **본 서비스** |생육·기상 데이터를 분석해 작물별 예상 수확량을 산출하고 주문 가능 여부를 자동 판단 |
| **효과** | 생산자는 재고 과잉·무리한 주문 방지, 소비자는 신선한 상품과 정확한 일정 제공 |

---

## 🙋 내 기여

### 1단계 · Database

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

평균오차율 : 선택된 유사 기상 데이터들의 유사도 점수 평균<br>
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

**Backend** : 
Spring Boot 3.5.4, Java 17, Spring Security, JWT 0.11.5, OAuth2(Kakao), Spring Data JPA, QueryDSL 5.0.0, AWS S3, SpringDoc 2.8.4, PortOne SDK 0.19.2, Web Push 5.1.1

**Frontend** : 
Vue 3.4.19, Pinia 3.0.3, Vue Router 4.3.0, Axios 1.10.0, Chart.js 4.4.1, Bootstrap 5.3.3

**DB** :
MariaDB 10.6.22, MySQL 8.0.42

**Infra** :
Docker, Kubernetes, Jenkins, Kaniko, Ansible, Ingress, Nginx, AWS(EC2, RDS, S3)

**Monitoring** : 
JMeter, Prometheus, Grafana

**Collaboration** : 
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

Kubernetes 클러스터 위에 Jenkins를 구축하여 GitHub Push/Merge 시 WebHook으로 파이프라인이 자동 트리거됩니다.

| 구분 | 흐름 | 배포 전략 |
|------|------|-----------|
| **Frontend** | GitHub Push → Jenkins (Git Clone → npm build → Kaniko 이미지 빌드 → Docker Hub Push → k8s 배포) | **Canary** : 20% 트래픽으로 먼저 검증 후<br> 전체 배포. 문제 시 즉시 롤백 |
| **Backend** | GitHub Push → Jenkins (Git Clone → Gradle build → Kaniko 이미지 빌드 → Docker Hub Push → k8s 배포) | **Blue-Green** : 구버전(Blue)·신버전(Green) 동시 운영 후 전환. 문제 시 즉시 롤백 |

---

## 🤔 기술 선택 이유

<details>
<summary>Database</summary>

| 선택 | 이유 |
|------|------|
| Replication<br>Master/Slave | 운영 서버의 단일 DB 장애 시 전체 서비스 중단을 막기 위해 데이터 복제(Data Replication)를 적용<br>Master 장애 시 Slave로 자동 전환(Failover)이 가능하도록 구성하여 가용성, 안정성을 최우선으로 함. |
| Clustering | 작물 상태·온도·습도·일사량 등 실시간 기상 데이터가 끊기면 자동화 시스템이 오작동할 수 있어,<br>클러스터링으로 장애를 대비했습니다. |
| Separate DB | 운영 DB에 부하를 주지 않고 분석·집계 작업을 수행하기 위해 별도 DB를 분리했습니다.<br>시계열 데이터의 반복 집계 쿼리가 운영 서비스 성능에 영향을 주지 않도록 하였습니다. |

</details>

<details>
<summary>Frontend</summary>

| 선택 | 이유 |
|------|------|
| Vue 3 | Composition API 기반으로 컴포넌트 로직 분리·재사용이 용이 |
| Pinia | Vue 3 공식 권장 상태 관리 라이브러리로 Composition API와의 연동이 자연스러움 |
| Canary | B2B이지만 판매자·구매자 모두 직관적인 UI가 필요, 새 버전을 일부 사용자(20%)에게 먼저 제공해<br>오류·반응 확인 후 전면 배포, 점진적 검증으로 안정적인 업데이트 경험 제공 |

</details>

<details>
<summary>Backend</summary>

| 선택 | 이유 |
|------|------|
| Spring Boot 3.5 | Spring Security, Spring Data JPA 등 스프링 생태계 기능을 스타터/자동 설정 기반으로 일관되게 <br>연동할 수 있어 선택 |
| JWT | 요청마다 토큰을 검증하는 Stateless 인증 방식으로, 서버 세션 공유 없이도 Kubernetes 다중 인스턴스 <br>환경에서 일관된 인증 처리가 가능해 선택 |
| Kakao OAuth2 | 카카오 소셜 로그인을 도입해 회원가입/로그인 절차를 간소화하고, Spring Security OAuth2 Client를 <br>활용해 인가 코드 기반 OAuth2 로그인 흐름을 구현 |
| MariaDB | MySQL과의 높은 호환성을 바탕으로 학습/개발 진입장벽이 낮고, 오픈소스 기반으로 비용 부담 없이 <br>관계형 데이터 관리를 구현하기 위해 선택 |
| QueryDSL | 농장·작물 조건 검색 등 동적 쿼리가 필요한 부분에 타입 안전 쿼리 작성 |
| PortOne | 국내 PG 연동을 공통 SDK로 구현해 결제 기능 개발 복잡도를 줄이고, 서버 측 결제 금액 검증 API를 통해 결제 신뢰성을 높이기 위해 선택 |
| Blue-Green | 주문·결제·재고 등 핵심 비즈니스 로직 담당, 장애 시 서비스 신뢰도에 직접 영향. 구버전(Blue)·신버전(Green) 동시 운영 후 트래픽을 한 번에 전환해 무중단 배포. 문제 발생 시 즉시 이전 버전으로 롤백 가능 |

</details>

<details>
<summary>CI/CD</summary>

| 선택 | 이유 |
|------|------|
| Kubernetes | 서비스 인스턴스를 여러 개로 운영하면서 배포 자동화, 확장, 장애 복구를 체계적으로 관리해 운영 <br>안정성을 높이기 위해 선택 |
| Jenkins | GitHub Actions 대비 자체 서버 기반으로 빌드 자원을 직접 제어 가능. 프론트·백엔드를 <br>독립 에이전트로 분리 운영하는 프로젝트 특성에 적합. 다양한 브랜치 전략·품질 관리 도구 통합이<br> 용이하고 오픈소스 생태계가 풍부해 선택 |
| Kaniko | Docker in Docker 방식은 보안 취약(privileged 권한)·캐시 공유 불가·성능 저하 문제가 있어 배제. Kaniko는 Docker 데몬 없이 Kubernetes 내부에서 안전하게 이미지 빌드 가능 |
| Ansible | 수동 설정 시 서버마다 환경이 달라져 재현성 문제 발생. YAML Playbook으로 다수 노드<br>(마스터·워커)에 동일 설정을 일괄 적용, 일관성·재사용성·자동화 확보 |
| Ingress Controller | MetalLB + LoadBalancer만으로는 서비스마다 외부 IP를 별도 할당해야 해 관리 복잡. <br>경로 기반 라우팅과 Blue-Green·Canary 배포 적용을 위해 필수적으로 도입. HTTPS 인증 확장도 고려 |

</details>

<details>
<summary>Infra</summary>

| 선택 | 이유 |
|------|------|
| Nginx (EC2) | AWS EC2에서 운영. 다중 WAS 구성 시 요청 라우팅과 부하 분산 담당 |
| Spring Boot × 2<br> (모놀리식,WAS) | 백엔드 서버 2대 구성으로 장애 시 나머지가 즉시 트래픽 처리 주문·결제 안정성 확보 |
| AWS S3 | 이미지 등 대용량 파일 저장. DB에는 경로·메타데이터만 보관해 DB I/O 절감.<br> Presigned URL로 WAS 부하 최소화 |
| MariaDB <br>Master/Slave(Replication) | 부하 및 장애 대비.<br> Master는 쓰기(주문 생성·상태 변경), Slave는 읽기(상품·재고 조회) 트래픽 분산 |
| AWS RDS for MariaDB | 관리형 DB로 운영 부담 절감.<br> 향후 도메인별 MSA 전환 시 인스턴스 분리·독립 확장 가능성 확보 |

</details>

<details>
<summary>Monitoring</summary>

| 선택 | 이유 |
|------|------|
| Prometheus / Grafana | Prometheus로 메트릭을 수집하고 Grafana 대시보드로 시각화해, 서비스 상태 점검 및 장애 징후 모니터링을 위해 사용 |

</details>

---

## 🔑 핵심 메서드 (주문 파트)

| 함수/메서드 | 위치 | 설명 |
|-------------|------|------|
| createOrder | OrderService | 장바구니 ID 목록으로 주문서 생성, 사용자 검증 및 총 가격 계산 |
| orderConfirm | OrderService | 주문 확정, 주문번호 생성 후 DB 저장 |
| addCart | CartService | 장바구니 담기, 기존 상품이면 수량·가격 갱신 |
| allCarts | CartService | 사용자별 장바구니 목록 조회 |
| validation | PaymentService | PortOne 결제 검증, 금액 대조 후 결제 내역 저장 |

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

- **SQL 성능 개선** : JMeter로 DB에 직접 부하를 가하고 Prometheus·Grafana로 양상을 관찰했습니다. 쿼리 구조 변경(JOIN → Subquery, 0.063s → 0.047s)과 Index 적용(2.57ms → 0.255ms)으로 응답 속도를 개선했습니다.

### Frontend

- **역할별 화면 분리 누락** : 초기 개발 시 농부(판매자)와 구매자를 구분하지 않고 단일 흐름으로 화면을 구성했습니다.
  <br>테스트 중 두 역할이 동일한 메뉴·기능에 접근하는 문제를 발견했고, 저장된 사용자 역할 정보를 기반으로 농부 대시보드·<br>구매자 대시보드를 분리 설계하여 해결했습니다.
  이 경험으로 다중 역할 서비스에서는 기능 구현보다 역할별 접근 권한과 UI 진입점을 먼저 정의해야 한다는 것을 체감했습니다.

### Backend

- **백엔드 모듈 통합 충돌** : 주문 CRUD를 구현한 뒤 결제 모듈과 병합하는 과정에서 팀원과 설계 방향이 달라 코드 구조가 <br>크게 달랐습니다. 일정 내 완성을 우선해 내 구현을 결제 흐름에 맞춰 재편하는 방향으로 조율했고, 이 과정에서 개발 <br>초기에 인터페이스와 데이터 흐름을 팀 단위로 합의하는 것의 중요성을 체감했습니다.

### Documentation

- **문서화 부재** : 팀원들이 개발에 집중하면서 GitHub 문서화는 후순위로 밀렸고, 프로젝트 후반에 README가 거의 없는 <br>상태임을 확인했습니다. 문서 전체 구조를 설계하고 필요 항목별로 정리했습니다. 주요 시나리오(주문 생성 → 장바구니 → 결제)를 흐름 순서대로 재편집하고 역할별(농부·구매자) 시나리오로 분리해 시연 영상의 전달력도 개선했습니다. <br>이 경험을 통해 코드 못지않게 문서도 프로젝트 완성도를 결정한다는 점을 체감했습니다.

---

## 🚀 실행 및 테스트

### 로컬 실행

**Backend**
```bash
cd backend
./gradlew bootRun
```
필요 환경변수: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `AWS_ACCESS_KEY`, `AWS_SECRET_KEY`, `AWS_BUCKET_NAME`, `AWS_REGION_NAME`, `KAKAO_CLIENT_ID`, `KAKAO_CLIENT_SECRET`, `KAKAO_REDIRECT_URI`, `MAIL_USERNAME`, `MAIL_PASSWORD`, `WEATHER_API_KEY`, `PORT_ONE_API_KEY`, `PORT_ONE_SECRET_KEY`, `PUBLIC_KEY`, `PRIVATE_KEY`, `WEBPUSH_SUBJECT` (application.yml 참고)

Frontend 필요 환경변수: `VUE_APP_ENCRYPTION_KEY` (.env.local 파일에 설정)

**Frontend**
```bash
cd frontend
npm install
npm run serve
```

### CI/CD 배포 테스트

1. Jenkins에 pipelineFrontend.yaml, pipelineBackend.yaml 파이프라인 등록
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
