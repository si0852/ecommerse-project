# 카트 추가 로직

## 1. 전체 프로세스
### 1-1) 유효성 체크
- 요청 수량이 0보다 작은지 확인
- 상품 및 상품 옵션이 현재 판매 중인 상태인지 확인 -> 현재 테이블에 구현이 안되어 있음....

### 1-2) Cart 조회
- 장바구니 내역 조회 : userId and production_option_id and cart_status

### 1-3) Inventory 검증
- a) Cart 정보 존재 : Target = 기존 Cart 수량 + 신규 요청 수량
- b) Cart 정보 존재X: Target = 신규 요청 수량
- Target이 Inventory의 수량보다 많은지 확인

### 1-4) Cart
- a)시 Update
- b)시 Insert
- 검증 미충족시 Throw Error



### 유니크키 필요
- userId, product_option_id, cart_status