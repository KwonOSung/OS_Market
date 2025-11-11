# 🛍️ 오성 마켓 : Spring Boot를 활용한 홈쇼핑 웹 서비스



![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=css3&logoColor=white)


## 🔒 보안 및 사용자 권한 관리
* **Spring Security 기반 인증/인가:** Spring Security를 도입하여 모든 페이지에 대한 접근 제어(Authorization) 및 사용자 세션 관리를 안전하게 처리했습니다.
* **관리자(ADMIN) 권한 분리:** 관리자 로그인을 통해 상품 등록, 회원 관리 등 민감한 기능은 오직 **ADMIN 권한**을 가진 사용자만 접근 가능하도록 역할(Role) 기반의 접근 제어를 구현했습니다.

## 🛒 쇼핑몰 핵심 로직
* **장바구니 및 주문 시스템:** 상품 선택 $\rightarrow$ 장바구니에 담기 $\rightarrow$ 주문 생성 $\rightarrow$ 재고 차감에 이르는 일련의 과정을 데이터 손실 없이 처리하여 데이터 무결성을 확보했습니다.


   
## 📊 데이터베이스 설계 (ERD)
<img width="1152" height="530" alt="DB설계" src="https://github.com/user-attachments/assets/1ef1dbd8-8465-4cc8-ab67-927bb8ddf0eb" />



   
### 🔑 핵심 테이블 관계
* **회원(Member) - 주문(Order):** 1:N 관계. 한 명의 회원은 여러 개의 주문을 가질 수 있습니다.
* **주문(Order) - 주문 상품(OrderItem):** 1:N 관계. 주문 하나는 여러 종류의 상품을 포함합니다. (주문 상세 정보 관리)
* **상품(Product) - 주문 상품(OrderItem):** N:1 관계. 여러 주문 상품이 하나의 상품 정보를 참조합니다.



   
## 🚀 향후 개발 계획
현재 구현된 기능 외에 프로젝트의 완성도를 높이기 위해 다음 기능 개발을 예정하고 있습니다.
* **리뷰 및 평점 시스템:** 상품별 리뷰 게시판 및 별점 시스템 추가
* **댓글/답글 기능:** 자유 게시판에 계층형 댓글 및 대댓글 기능 구현
* 


  
