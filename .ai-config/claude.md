# CLAUDE.md — 프로젝트 요구사항 & 설계 문서

> 이 문서는 AI 에이전트(Claude Code 등)가 프로젝트를 이해하고
> 코드를 작성할 때 참고하는 **단일 진실의 원천(Single Source of Truth)** 입니다.
> 기획/설계가 변경되면 이 문서를 먼저 업데이트하세요.

---

## 1. 프로젝트 개요

### 프로젝트명
<!-- 프로젝트 이름 -->
예) ShopEase — 이커머스 플랫폼

### 한 줄 설명
<!-- 이 프로젝트가 뭔지 한 문장으로 -->
예) 소규모 셀러를 위한 상품 등록~결제~배송 관리까지 지원하는 이커머스 백엔드 API

### 프로젝트 목적
<!-- 왜 만드는가? 사이드 프로젝트의 목표 -->
- 하네스 엔지니어링 역량 시연용 포트폴리오
- Spring Boot + AI 자동화 파이프라인 구축 경험
- 실무 수준의 이커머스 도메인 설계 경험

### 개발 기간
<!-- 마일스톤 -->
| 단계 | 기간 | 목표 |
|---|---|---|
| Phase 1 | 0주차 ~ 2주차 | 회원/인증, 상품 CRUD |
| Phase 2 | 3주차 ~ 4주차 | 주문/결제, 장바구니 |
| Phase 3 | 5주차 ~ 6주차 | 배송, 관리자 기능 |
| Phase 4 | 7주차 ~ 8주차 | AI 테스트 자동화 파이프라인 |

---

## 2. 기술 스택

### 백엔드
| 구분 | 기술 | 버전 | 비고 |
|---|---|---|---|
| Language | Java | 17 | LTS |
| Framework | Spring Boot | 3.x | |
| Security | Spring Security + JWT | | Access/Refresh 토큰 |
| ORM | JPA / Hibernate | | QueryDSL 병행 |
| Build | Gradle | 8.x | Kotlin DSL |
| DB | PostgreSQL | 16 | |
| Cache | Redis | 7.x | 세션, 장바구니 캐싱 |
| API Doc | Swagger / SpringDoc | | |

### 인프라 / DevOps
| 구분 | 기술 | 비고 |
|---|---|---|
| 컨테이너 | Docker + Docker Compose | 로컬 개발 |
| CI/CD | GitHub Actions | 빌드/테스트 자동화 |
| AI 파이프라인 | n8n (self-hosted) | 테스트 자동 생성 |
| AI 모델 | Claude API (Sonnet) | 테스트 코드 생성 |

### 프론트엔드 (해당 시)
| 구분 | 기술 | 비고 |
|---|---|---|
| Framework | React / Next.js | |
| 상태관리 | | |
| UI 라이브러리 | | |

---

## 3. 도메인 설계

### 핵심 도메인 & 바운디드 컨텍스트
<!-- 프로젝트의 주요 도메인 영역을 정의 -->

```
[회원]          [상품]          [주문]          [결제]          [배송]
 Member          Product         Order           Payment         Delivery
 Address         Category        OrderItem       PaymentHistory  DeliveryTracking
 MemberGrade     ProductImage    Cart/CartItem
```

### 도메인 관계
<!-- 도메인 간의 핵심 관계를 서술 -->
- 회원(1) → 주문(N): 한 회원이 여러 주문 가능
- 주문(1) → 주문상품(N): 한 주문에 여러 상품
- 주문(1) → 결제(1): 주문당 하나의 결제
- 주문(1) → 배송(1): 주문당 하나의 배송

---

## 4. 기능 요구사항

### 4.1 회원 (Member)
<!-- 각 기능을 유저 스토리 형태로 작성 -->

| ID | 기능 | 설명 | 우선순위 |
|---|---|---|---|
| M-01 | 회원가입 | 이메일/비밀번호로 회원가입 | P0 |
| M-02 | 로그인 | JWT 기반 인증, Access/Refresh 토큰 발급 | P0 |
| M-03 | 토큰 갱신 | Refresh 토큰으로 Access 토큰 재발급 | P0 |
| M-04 | 내 정보 조회 | 로그인한 회원의 프로필 조회 | P1 |
| M-05 | 내 정보 수정 | 이름, 전화번호, 주소 수정 | P1 |
| M-06 | 비밀번호 변경 | 기존 비밀번호 확인 후 변경 | P2 |
| M-07 | 회원 탈퇴 | Soft Delete (탈퇴일 기록, 30일 후 완전 삭제) | P2 |

**비즈니스 규칙:**
- 이메일 중복 불가
- 비밀번호: 최소 8자, 영문+숫자+특수문자 포함
- 탈퇴 후 같은 이메일로 30일간 재가입 불가

### 4.2 상품 (Product)

| ID | 기능 | 설명 | 우선순위 |
|---|---|---|---|
| P-01 | 상품 등록 | 이름, 가격, 설명, 카테고리, 재고 입력 | P0 |
| P-02 | 상품 목록 조회 | 페이징, 정렬(최신순/가격순/판매순) | P0 |
| P-03 | 상품 상세 조회 | 상품 정보 + 이미지 + 리뷰 | P0 |
| P-04 | 상품 수정 | 가격, 설명, 재고 수정 | P1 |
| P-05 | 상품 삭제 | Soft Delete | P1 |
| P-06 | 상품 검색 | 이름/카테고리 기반 검색 | P1 |
| P-07 | 카테고리 관리 | 카테고리 CRUD, 계층 구조 | P2 |

**비즈니스 규칙:**
- 가격은 0 이상의 정수
- 재고는 0 미만 불가
- 삭제된 상품은 목록에 미노출, 기존 주문 이력은 유지

### 4.3 주문 (Order)

| ID | 기능 | 설명 | 우선순위 |
|---|---|---|---|
| O-01 | 장바구니 추가 | 상품 + 수량 장바구니에 담기 | P0 |
| O-02 | 장바구니 조회 | 담긴 상품 목록 + 총 금액 | P0 |
| O-03 | 장바구니 수정/삭제 | 수량 변경, 상품 제거 | P0 |
| O-04 | 주문 생성 | 장바구니 → 주문 전환, 재고 차감 | P0 |
| O-05 | 주문 목록 조회 | 내 주문 이력 (페이징) | P1 |
| O-06 | 주문 상세 조회 | 주문 상품, 결제 정보, 배송 상태 | P1 |
| O-07 | 주문 취소 | 배송 전 취소 가능, 재고 복원 | P1 |

**비즈니스 규칙:**
- 주문 시 재고 확인 → 부족하면 예외
- 주문 상태: PENDING → PAID → PREPARING → SHIPPING → DELIVERED → CANCELLED
- 취소는 PREPARING 이전 단계에서만 가능
- 최소 주문금액: 10,000원

### 4.4 결제 (Payment)

| ID | 기능 | 설명 | 우선순위 |
|---|---|---|---|
| PA-01 | 결제 요청 | 주문 → PG사 결제 요청 | P0 |
| PA-02 | 결제 확인 | PG사 콜백 처리, 주문 상태 변경 | P0 |
| PA-03 | 환불 처리 | 주문 취소 시 결제 취소 | P1 |

**비즈니스 규칙:**
- 결제 수단: 카드, 계좌이체 (PG 연동은 테스트 모드)
- 결제 실패 시 주문 상태를 PENDING으로 유지
- 부분 환불 미지원 (전액 환불만)

### 4.5 배송 (Delivery)

| ID | 기능 | 설명 | 우선순위 |
|---|---|---|---|
| D-01 | 배송 시작 | 관리자가 송장번호 입력 → 배송 시작 | P1 |
| D-02 | 배송 상태 조회 | 현재 배송 상태 조회 | P1 |
| D-03 | 배송 완료 | 배송 완료 처리 → 주문 상태 변경 | P2 |

---

## 5. API 설계 가이드

### URL 규칙
```
POST   /api/v1/members              회원가입
POST   /api/v1/auth/login            로그인
POST   /api/v1/auth/refresh          토큰 갱신

GET    /api/v1/products              상품 목록
GET    /api/v1/products/{id}         상품 상세
POST   /api/v1/products              상품 등록 (관리자)
PUT    /api/v1/products/{id}         상품 수정 (관리자)
DELETE /api/v1/products/{id}         상품 삭제 (관리자)

POST   /api/v1/cart/items            장바구니 추가
GET    /api/v1/cart                   장바구니 조회
PATCH  /api/v1/cart/items/{id}       수량 변경
DELETE /api/v1/cart/items/{id}       장바구니에서 제거

POST   /api/v1/orders                주문 생성
GET    /api/v1/orders                내 주문 목록
GET    /api/v1/orders/{id}           주문 상세
POST   /api/v1/orders/{id}/cancel    주문 취소
```

### 공통 응답 형식
```json
{
  "status": 200,
  "message": "success",
  "data": { ... }
}
```

### 에러 응답 형식
```json
{
  "status": 400,
  "message": "재고가 부족합니다.",
  "code": "INSUFFICIENT_STOCK",
  "timestamp": "2026-04-21T10:00:00"
}
```

### 페이징 응답 형식
```json
{
  "status": 200,
  "message": "success",
  "data": {
    "content": [ ... ],
    "page": 0,
    "size": 20,
    "totalElements": 150,
    "totalPages": 8
  }
}
```

---

## 6. DB 설계 가이드

### 공통 컬럼
모든 테이블에 포함:
```
id              BIGINT       PK, AUTO_INCREMENT
created_at      TIMESTAMP    생성일 (자동)
updated_at      TIMESTAMP    수정일 (자동)
```

Soft Delete 대상 테이블에 추가:
```
deleted_at      TIMESTAMP    삭제일 (NULL이면 미삭제)
```

### 네이밍 규칙
- 테이블: snake_case, 복수형 (members, products, orders)
- 컬럼: snake_case (member_id, order_status)
- FK: {참조테이블_단수}_id (member_id, product_id)
- 인덱스: idx_{테이블}_{컬럼} (idx_products_category_id)

### 핵심 테이블 (요약)
<!-- 상세 ERD는 별도 관리, 여기는 핵심만 -->
```
members         : id, email, password, name, phone, grade, deleted_at
addresses       : id, member_id, address, detail, zipcode, is_default
products        : id, name, price, description, stock, category_id, deleted_at
categories      : id, name, parent_id
product_images  : id, product_id, url, sort_order
orders          : id, member_id, status, total_price, address
order_items     : id, order_id, product_id, quantity, price
payments        : id, order_id, method, amount, status, pg_transaction_id
deliveries      : id, order_id, status, tracking_number, carrier
```

---

## 7. 비기능 요구사항

### 보안
- 비밀번호: BCrypt 해싱
- JWT: Access 30분, Refresh 7일
- API 인증: Bearer 토큰
- 권한: ROLE_USER, ROLE_ADMIN
- SQL Injection 방지: JPA Parameterized Query
- XSS 방지: 입력값 검증 (@Valid)

### 성능
- 상품 목록 API: 200ms 이내 응답
- 페이징: 기본 20건, 최대 100건
- N+1 쿼리 금지: fetch join 또는 @EntityGraph 사용
- 반복 조회 대상: Redis 캐싱 (카테고리, 인기 상품)

### 코드 품질
- 테스트 커버리지: 80% 이상
- Checkstyle: Google Java Style 기반
- 정적 분석: SpotBugs (선택)

---

## 8. 프로젝트 구조

```
com.project.ecommerce
├── domain/                    # 엔티티, Repository, 도메인 서비스
│   ├── member/
│   │   ├── Member.java
│   │   ├── MemberRepository.java
│   │   └── MemberGrade.java (enum)
│   ├── product/
│   ├── order/
│   ├── payment/
│   └── delivery/
├── application/               # 서비스 레이어 (유스케이스)
│   ├── member/
│   │   ├── MemberService.java
│   │   ├── MemberCommand.java (요청 DTO)
│   │   └── MemberInfo.java (응답 DTO)
│   ├── product/
│   ├── order/
│   └── payment/
├── presentation/              # 컨트롤러 (REST API)
│   ├── member/
│   │   └── MemberController.java
│   ├── product/
│   ├── order/
│   └── payment/
├── infrastructure/            # 외부 연동
│   ├── pg/                    # PG사 결제 연동
│   ├── storage/               # 파일 저장소
│   └── mail/                  # 메일 발송
└── common/                    # 공통
    ├── config/                # Security, Redis, Swagger 등
    ├── exception/             # 커스텀 예외, GlobalExceptionHandler
    ├── response/              # ApiResponse, PageResponse
    └── util/                  # 유틸리티
```

---

## 9. 현재 진행 상태

<!-- 개발하면서 업데이트 -->

### 완료
- [ ] 프로젝트 초기 세팅 (Spring Boot, Gradle, Docker)
- [ ] 회원가입/로그인 API (JWT)
- [ ] 상품 CRUD API

### 진행 중
- [ ] 장바구니 기능
- [ ] n8n + AI 테스트 자동화 파이프라인

### 예정
- [ ] 주문/결제 프로세스
- [ ] 배송 관리
- [ ] 관리자 대시보드

---

## 10. AI 에이전트 지시사항

### 코드 작성 시 반드시 따를 것
1. 이 문서의 도메인 설계와 비즈니스 규칙을 따른다
2. API URL 규칙과 응답 형식을 따른다
3. 프로젝트 구조(패키지)를 따른다
4. 새 기능 구현 시 요구사항 ID(M-01, P-01 등)를 참조한다

### 코드 작성 시 하지 말 것
1. 요구사항에 없는 기능을 임의로 추가하지 않는다
2. 기존 API 응답 형식을 변경하지 않는다
3. Entity의 PK 전략을 변경하지 않는다
4. Security 설정을 수정하지 않는다

### 판단이 필요한 경우
- 요구사항이 모호하면 이 문서의 비즈니스 규칙 섹션을 우선 따른다
- 그래도 불분명하면 가장 보수적인 방향으로 구현한다
- 가정이 필요한 경우 코드 주석에 `// ASSUMPTION: ...`으로 명시한다