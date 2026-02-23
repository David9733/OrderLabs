# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**GrowOrders (OrderLabs)** is a B2B smart order management platform for agricultural/aquatic products. Orders are auto-approved or denied based on crop growth data and weather information — not just inventory levels. Two user roles exist: `FARMER` and `BUYER`, each with separate dashboards and flows.

## Commands

### Backend (Spring Boot)

```bash
cd backend
./gradlew bootRun          # Run locally
./gradlew build            # Build (produces backend/build/libs/GrowOrders.jar)
./gradlew test             # Run all tests
./gradlew test --tests "org.example.groworders.SomeTest"  # Run single test
```

Required environment variables (see `backend/src/main/resources/application.yml`):
`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `AWS_ACCESS_KEY`, `AWS_SECRET_KEY`, `AWS_BUCKET_NAME`, `AWS_REGION_NAME`, `KAKAO_CLIENT_ID`, `KAKAO_CLIENT_SECRET`, `KAKAO_REDIRECT_URI`, `MAIL_USERNAME`, `MAIL_PASSWORD`, `WEATHER_API_KEY`, `PORT_ONE_API_KEY`, `PORT_ONE_SECRET_KEY`, `PUBLIC_KEY`, `PRIVATE_KEY`, `WEBPUSH_SUBJECT`

### Frontend (Vue 3)

```bash
cd frontend
npm install
npm run serve              # Dev server (port 8081)
npm run build              # Production build
npm run lint               # ESLint
npm run prettify           # Prettier format
```

## Architecture

### Backend (`backend/src/main/java/org/example/groworders/`)

Layered architecture: `Controller → Service → Repository`. Domain packages under `domain/`:

- **users** — JWT auth + Kakao OAuth2. `LoginFilter` handles JSON login at `/api/login`; `JwtAuthFilter` validates tokens per request. User data is encrypted in browser storage.
- **farms** — Farm registration by farmers. Uses QueryDSL for complex queries (`FarmQueryRepository`).
- **crops** — Crop/organism registration tied to farms. `CropStatus` and `SaleStatus` enums control ordering availability.
- **inventories** — Stock management derived from crop data.
- **orders / cart / payment** — Core ordering flow: `CartService.addCart` → `OrderService.createOrder` / `orderConfirm` → `PaymentService.validation` (PortOne payment verification).
- **predict** — Production yield prediction via weather data similarity matching.
- **weather** — Fetches external weather API data.
- **dashboard** — Aggregated stats for farmer dashboard.
- **config/push** — Web Push notifications using VAPID keys; event-driven via `PushEvent` / `PushEventListener`.
- **config/chat** — WebSocket (STOMP) chat at `/ws/**`.

All API responses use `BaseResponse<T>` from `common/model/`. All entities extend `BaseEntity` (auditing).

Swagger UI: `/api/swagger-ui/swagger-ui/index.html`

### Frontend (`frontend/src/`)

Vue 3 with Vue CLI (not Vite), based on the **Argon Dashboard 2** template by Creative Tim.

- **`router/index.js`** — Role-based redirect: logged-in users go to `/farmer/dashboard` or `/buyer/dashboard`. Auth guard checks `useUserStore.checkLogin()`.
- **`store/users/useUserStore.js`** — Primary Pinia store. User session stored in encrypted localStorage/sessionStorage via `encrypt-storage`. `rememberMe` flag determines which storage is used.
- **`store/index.js`** — Legacy Vuex store (still mounted alongside Pinia).
- **`plugins/axiosinterceptor.js`** — Axios instance with `baseURL: '/api'` and `withCredentials: true`. All API calls use this instance.
- **`api/`** — Domain-grouped API modules (`order/`, `cart/`, `crops/`, etc.).
- **`views/`** — Page components. `views/order/` contains the full order lifecycle views. `views/test/` contains development/prototype views still wired in the router.
- **`components/`** — Argon UI primitives (`ArgonButton`, `ArgonInput`, etc.) and app-specific components (`CropCard`, `SelectPosition`).
- **`examples/`** — Argon Dashboard layout components (Sidenav, Navbar, Cards, Charts).

### Infrastructure (`infra/`)

- **Kubernetes**: Backend uses **Blue-Green** deployment (`k8s/backend/`); Frontend uses **Canary** deployment at 20% weight (`k8s/frontend/`). Namespace: `orderlabs`.
- **Jenkins**: Separate pipelines for frontend and backend (`jenkins/pipelineFrontend.yaml`, `pipelineBackend.yaml`). Triggered by GitHub Webhook on push to `main`.
- **Image build**: Kaniko (no Docker daemon required).
- **Config**: Ansible playbooks for server provisioning (`infra/ansible/`). App config via Kubernetes ConfigMap (`infra/app-config.yaml`).

## Key Patterns

- **API responses**: Always return `BaseResponse<T>` from backend. Frontend checks `response.data.success`.
- **Authentication flow**: JSON POST to `/api/login` → JWT in response → stored implicitly (cookie/header). Frontend axios uses `withCredentials: true`.
- **QueryDSL**: Used for complex queries alongside Spring Data JPA repositories. Look for `*QueryRepository` classes alongside standard `*Repository` interfaces.
- **Dual state management**: Both Vuex (`store/index.js`) and Pinia coexist. New features should use Pinia. `store/test/` contains old prototype stores.
