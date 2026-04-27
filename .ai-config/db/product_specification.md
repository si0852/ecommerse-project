# 데이터베이스 테이블 명세서 (Table Specification)

## 1. Product (상품)
상품의 핵심 정보를 저장하는 테이블입니다.

| 컬럼명 | 데이터 타입 | 제약 조건 | 설명 |
| :--- | :--- | :--- | :--- |
| id | BIGINT | PK, Auto Increment | 상품 고유 번호 |
| name | VARCHAR(255) | NOT NULL | 상품명 |
| category_id | INT | FK, NOT NULL | 카테고리 ID |
| description | TEXT | - | 상품 상세 설명 |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | 상품 등록 일시 |

---

## 2. Production_Option (상품 옵션)
상품에 속한 옵션 정보(색상, 사이즈 등)와 추가 금액을 저장합니다.

| 컬럼명 | 데이터 타입 | 제약 조건 | 설명 |
| :--- | :--- | :--- | :--- |
| id | BIGINT | PK, Auto Increment | 옵션 고유 번호 |
| product_id | BIGINT | FK, NOT NULL | 연결된 상품 ID |
| option_name | VARCHAR(100) | NOT NULL | 옵션명 (예: 티셔츠 색상, 사이즈 등) |
| additional_price | DECIMAL(10,2) | DEFAULT 0 | 옵션 선택 시 발생하는 추가 금액 |

---

## 3. Inventory (재고)
상품 옵션별 실제 재고 수량을 관리합니다.

| 컬럼명 | 데이터 타입 | 제약 조건 | 설명 |
| :--- | :--- | :--- | :--- |
| id | BIGINT | PK, Auto Increment | 재고 기록 고유 번호 |
| product_option_id | BIGINT | FK, NOT NULL | 연결된 상품 옵션 ID |
| stock_quantity | INT | DEFAULT 0 | 현재 재고 수량 |
| updated_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP ON UPDATE | 재고 수정 일시 |

---

## 4. Product_Image (상품 이미지)
상품과 관련된 이미지 경로와 출력 순서를 관리합니다.

| 컬럼명 | 데이터 타입 | 제약 조건 | 설명 |
| :--- | :--- | :--- | :--- |
| id | BIGINT | PK, Auto Increment | 이미지 고유 번호 |
| product_id | BIGINT | FK, NOT NULL | 연결된 상품 ID |
| image_url | VARCHAR(500) | NOT NULL | 이미지 파일 경로 또는 URL |
| image_type | VARCHAR(50) | - | 이미지 유형 (예: MAIN, SUB, DETAIL) |
| sorted_order | INT | DEFAULT 0 | 이미지 노출 순서 (낮을수록 우선) |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | 이미지 등록 일시 |
