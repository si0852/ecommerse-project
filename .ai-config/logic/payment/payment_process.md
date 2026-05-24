1) [Front] 화면에서 토스 결제 위젯을 렌더링한다. (이때 백엔드에 이미 저장된 orderId와 amount를 위젯에 주입함)

2) [Front] 사용자가 결제 수단(신용카드 등)을 선택하고 토스 결제창에서 인증을 진행한다.

3) (토스 내부 동작) 인증이 완료되면 토스 서버가 paymentKey를 생성하고, Front를 설정된 successUrl로 리다이렉트 시킨다.

4) [Front -> Back] successUrl로 이동한 Front가 결제 승인을 위해 백엔드 API를 호출한다. (전달 데이터: orderId, amount, paymentKey)

5) [Back] (검증) 세션이 아닌, DB에서 orderId로 Order/Payment를 조회한다.

6) [Back] (비교) DB에 저장된 totalPrice와 Front가 토스를 거쳐 가져온 amount가 일치하는지 비교한다. (위변조 검증)

7) [Back -> Toss] 일치한다면, paymentKey, orderId, amount를 들고 Toss 결제 승인 API를 호출한다.

8) [Back] 토스 승인이 성공하면, DB의 Payment와 Order 상태를 PENDING -> DONE/PAID(완료)로 변경하고, TossPayment(PaymentDetails)에 토스 응답 데이터를 저장한다.

9) [Back -> Front] 최종 결제 성공 응답을 내려준다.