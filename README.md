<img width="3312" height="1107" alt="Image" src="https://github.com/user-attachments/assets/319894c4-7d18-4be0-afa1-598fb2517989" />
<br>

## 🍀 프로젝트 소개
<br>
<div>
  <h3 align="center">
    "자라는 만큼만 주문받는다."
  </h3>
</div>

 <br>

이 서비스는 농·수산물의 생육 상태나 생물의 건강 데이터를 기반으로 주문 가능 여부를
자동 판단하고 관리하는 스마트 주문 관리 플랫폼입니다.
기존의 단순 재고 기반 판매 방식과 달리, 생물의 생육데이터 여기서는 공공 데이터(예:
기상정보, 생육 센서 데이터 등)와 실시간 수집된 생물 정보(예: 수산물 성장률, 건강도)를
분석하여 언제 주문을 받을 수 있고, 언제 배송이 가능한지를 예측 및 자동 통제합니다.
이를 통해 생산자는 재고 과잉이나 무리한 주문을 방지하고, 소비자는 더 신선하고 정확한
일정으로 상품을 수령할 수 있습니다.
생물은 자라야 팔 수 있습니다. <br><br>

우리는 그 **자라는 과정**까지 주문 시스템에 담습니다.

<br><br>


## 🕵️ 팀원 소개
<br>
<div align="center">

| <img src="https://avatars.githubusercontent.com/u/149382180?v=4" width="100" height="100"/> | <img src="https://avatars.githubusercontent.com/u/96688099?v=4" width="100" height="100"/> | <img src="https://avatars.githubusercontent.com/u/195714592?v=4" width="100" height="100"/> | <img src="https://avatars.githubusercontent.com/u/92301360?v=4" width="100" height="100"/> | <img src="https://avatars.githubusercontent.com/u/201225844?v=4" width="100" height="100"/> |
| :-----------------------------------------------------------------------------------------: | :----------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------: | :----------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------: |
|                 🦊 **양승우**<br/>[@atimaby28](https://github.com/miyad927)                 |                🐻 **이시욱**<br/>[@David9733](https://github.com/David9733)                |                 🦎 **구창모**<br/>[@kucha240](https://github.com/kucha240)                  |                🐰 **유현경**<br/>[@gaangstar](https://github.com/gaangstar)                |                  🐱 **윤소민**<br/>[@somminn](https://github.com/somminn)                   |

</div>

<br><br>


## 🛠️ 기술 스택
<br>
<div>
  <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
  <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
  <img src="https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white">
  <img src="https://img.shields.io/badge/discord-5865F2?style=for-the-badge&logo=discord&logoColor=white">
</div>
<div>
  <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
  <img src="https://img.shields.io/badge/kubernetes-326CE5?style=for-the-badge&logo=kubernetes&logoColor=white">
  <img src="https://img.shields.io/badge/jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white">
</div>
<br><br>


## ⚙️ 시스템 아키텍처

<br>

![시스템 아키텍처](https://github.com/user-attachments/assets/64e17f01-a635-470d-aa99-d06da349d345)

<br><br>

## 🎬 CI/CD 파이프라인 흐름 및 시나리오

이번  CI/CD 프로젝트에서는 쿠버네티스 클러스터 위에 Jenkins를 구축함.(고칠거임)

프론트: 소스코드 git에 Push/Merge→ Jenkins Pipe line ->  Node 빌드 (npm install && npm run build)→ 정적 산출물 생성 (dist/)→ Kaniko 빌드 (Nginx 베이스 Dockerfile, Kaniko)→ Docker Hub에 이미지 푸시→ 쿠버네티스 배포 (Deployment & Ingress)→ Canary 배포 검증 (20% → 100%)→ UI 확인을 통한 사용자 경험 검증을 통한 서비스 제공

백엔드: 소스코드  git에 Push/Merge → Jenkins Pipe line -> Gradle 빌드 (./gradlew clean bootJar) → JAR 산출물 생성 (build/libs/*.jar) → Docker 이미지 빌드 (JRE 베이스 Dockerfile, Kaniko) →  Docker Hub에 이미지 푸시 → 쿠버네티스 배포 (Blue-Green 전략 적용) → Readiness/Liveness Probe 검증 → 사용자에게 일관적 서비스 제공

<br>

<br><br>


## ✔️ CI/CD 배포 방식 선택 배경

<br>

### 프론트에 Canary 배포 쓴 이유

우리 프로젝트는 B2B 시스템이다 보니 B2C처럼 UI에 민감하지는 않습니다.   
그러나 실제로 사용하는 판매자와 구매자 입장에서는 편리하고 직관적인 화면이 반드시 필요했습니다.  
이를 위해 우리는 UI 개선을 단순히 한 번에 배포하는 대신 **Canary 배포** 방식을 적용했습니다.  
NEW 버전을 **전체 사용자에게 일괄 적용하지 않고 일부 사용자에게만** 우선 제공하였습니다.   
그 결과, 사용 반응과 오류 여부를 확인할 수 있습니다.   
이렇게 제한된 범위에서 검증 → 피드백 반영 → 안정성 확보 → 전면 배포라는 단계를 거침으로써, 서비스 품질을 점진적으로 높이고 사용자에게 안정적인 업데이트 경험을 제공할 수 있습니다.


<br>

### 백엔드에 Blue/Green 쓴 이유

프로젝트의 백엔드는 농장, 사용자, 주문, 재고, 장바구니, 결제 및 검증 같은 핵심 비즈니스 로직을 담당합니다.   
이런 영역은 단순한 UI 문제가 아니라 시스템 전체 안정성과 직결되기 때문에, 장애나 오류가 발생하면 곧바로 서비스 신뢰도에 영향을 줄 수 있습니다.   
그래서 무중단 배포 전략으로 **Blue/Green 방식**을 적용하였습니다.   
기존 버전과 신규 버전을 동시에 준비한 뒤, **트래픽을 한 번에 전환하는 구조**이므로 사용자는 배포 중에도 끊김이나 불안정성을 체감하지 않습니다.   
또한 전체 서버 버전을 단숨에 업데이트할 수 있으며, 문제가 발생할 경우 즉시 이전 버전으로 롤백이 가능하여 리스크를 최소화할 수 있습니다.  


<br>

### Github Action 아닌 Jenkins를 선택한 이유

GitHub Actions도 처음에 고려하였지만, 대규모 프로젝트로 확장될 경우 빌드 속도가 느리고 성능 확장성에도 한계가 발생하였습니다.    
반면 **Jenkins**는 **자체 서버 기반**으로 동작하기 때문에 빌드와 배포를 실행하는 서버 자원을 직접 제어할 수 있으며, 특히 프론트엔드와 백엔드를 독립적으로 관리하고 빌드와 배포 과정을 서로 다른 에이전트로 분리해 운영해야 하는 우리 프로젝트의 특성에 적합하여 적용하였습니다.  
Jenkins를 활용하면 **다양한 브랜치 전략을 유연하게 적용**할 수 있어 기능 단위 배포와 긴급 핫픽스 대응이 가능해집니다.   
또한 Docker 기반의 배포 자동화를 통해 **개발 환경과 운영 환경 간의 일관성을 보장**하고, 잦은 업데이트 상황에서도 안정성을 유지할 수 있습니다.   
더불어 **품질 관리 도구와의 통합이 용이**하여 배포 전에 코드 품질 검사 및 자동 검증을 사전에 수행할 수 있으며, 오픈소스 기반이라 커뮤니티와 문서가 풍부해 문제 해결과 커스터마이징에도 유리하다는 점에서 Jenkins를 선택하게 되었습니다.  



<br>

### Kaniko를 쓴 이유

컨테이너 기반 CI/CD 파이프라인에서 Docker in Docker 방식은 특정 권한 모드를 필요로 해서 보안적으로 위험하고, 빌드 캐시를 공유할 수 없는 문제나 중첩된 컨테이너 실행으로 인한 성능 저하까지 유발하기 때문에 사용하면 안됩니다.   
그래서 우리는 처음부터 **Kaniko**를 도입해 **Docker 데몬 없이도 이미지를 안전하게 빌드**할 수 있도록 했고, 실제 적용 과정에서 안정적이고 효율적인 빌드 환경을 구축할 수 있었습니다.


<br>

### Ansible 쓴 이유 

CI/CD 및 인프라 운영 과정에서 여러 대의 서버(마스터·워커 노드 등)에 동일한 설정을 반복적으로 적용해야 했습니다.   
처음에는 수동으로 진행했는데, 서버마다 환경이 달라져서 문제가 생기고 재현성도 떨어져 **Ansible**을 도입하였습니다.   
에이전트 설치 없이 SSH만으로 서버를 제어할 수 있으며, YAML 기반의 Playbook을 통해 서버 설정, 패키지 설치, 배포 과정을 코드로 관리할 수 있습니다.  
이를 통해 일관성 있는 **환경 구성과 재사용성, 자동화**를 보장할 수 있었습니다.   
따라서 Ansible을 활용함으로써 쿠버네티스 노드 초기 설정과 Jenkins 배포 과정에서 **인프라 전반의 안정성과 운영 효율성**을 확보할 수 있었습니다.


<br>

### Ingress Controller 쓴 이유 

우리 프로젝트에서는 쿠버네티스 환경에서 MetalLB와 LoadBalancer Service 조합만으로도 외부 접근을 열 수 있었습니다.   
그런데 서비스가 많아지다 보니, 서비스마다 외부 IP를 따로 할당해야 해서 관리가 힘들고, 도메인 기반이나 경로 기반 라우팅을 적용하기도 어려웠습니다.   
특히 우리는 백엔드에 Blue-Green 배포, 프론트엔드에 Canary 배포를 적용했는데, 이런 **무중단 배포를 활용하려면 Ingress Controller가 꼭 필요**했습니다.   
현재는 HTTPS 인증서를 따로 적용하지 않았지만, 추후 서비스를 정식으로 오픈할 때는 **보안 강화**를 위해 **HTTPS 인증을 도입할 계획**입니다.   
단순히 외부에 노출만 하는 게 아니라, 서비스 확장성과 운영까지 고려해서 Ingress Controller를 활용할 수 있도록 미리 확장 가능성을 열어 두었습니다.  


<br>


<br><br>



 ## 📺 CI/CD 배포 및 테스트

<br>

---











