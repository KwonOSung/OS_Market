# 🛍️ 오성 마켓 : Spring Boot를 활용한 홈쇼핑 웹 서비스



![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=css3&logoColor=white)
<br><br><br>

## 🔒 보안 및 사용자 권한 관리
* **Spring Security 기반 인증/인가:** Spring Security를 도입하여 모든 페이지에 대한 접근 제어(Authorization) 및 사용자 세션 관리를 안전하게 처리했습니다.
* **관리자(ADMIN) 권한 분리:** 관리자 로그인을 통해 상품 등록, 회원 관리 등 민감한 기능은 오직 **ADMIN 권한**을 가진 사용자만 접근 가능하도록 역할(Role) 기반의 접근 제어를 구현했습니다.
<br><br>
## 🛒 쇼핑몰 핵심 로직
* **장바구니 및 주문 시스템:** 상품 선택 $\rightarrow$ 장바구니에 담기 $\rightarrow$ 주문 생성 $\rightarrow$ 재고 차감에 이르는 일련의 과정을 데이터 손실 없이 처리하여 데이터 무결성을 확보했습니다.

<br><br>
   
## 📊 데이터베이스 설계 (ERD)
<img width="1152" height="530" alt="DB설계" src="https://github.com/user-attachments/assets/1ef1dbd8-8465-4cc8-ab67-927bb8ddf0eb" />



   
### 🔑 핵심 테이블 관계
* **회원(Member) - 주문(Order):** 1:N 관계. 한 명의 회원은 여러 개의 주문을 가질 수 있습니다.
* **주문(Order) - 주문 상품(OrderItem):** 1:N 관계. 주문 하나는 여러 종류의 상품을 포함합니다. (주문 상세 정보 관리)
* **상품(Product) - 주문 상품(OrderItem):** N:1 관계. 여러 주문 상품이 하나의 상품 정보를 참조합니다.

<div align="center">
   <img width="1903" height="932" alt="메인화면" src="https://github.com/user-attachments/assets/abbb5ee8-67ae-4f45-ad08-6daedc52f6ff" />
   <img width="1867" height="792" alt="관리자페이지" src="https://github.com/user-attachments/assets/2dd04334-729b-484c-a787-98fb69adf64a" />
<div align="center">
  **메인 화면:** 깔끔한 UI와 직관적인 상품 배치를 통한 사용자 접근성 확보<br>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  **관리자 페이지:** 상품 CRUD 및 회원 관리 기능 구현을 통한 백엔드 관리 능력 증명<br>
</div>
<br>

<div align="center">
 <img width="1185" height="598" alt="마이페이지" src="https://github.com/user-attachments/assets/c31cf4e0-66e3-4def-a64d-7892aa10dae3" />
 <img width="815" height="811" alt="주문페이지" src="https://github.com/user-attachments/assets/d9bf1fd5-404e-464b-bc3b-beb78ce1fce9" />  
</div>
<div align="center">
  **상품 주문/결제:** 장바구니 기반 주문 생성 및 안전한 트랜잭션 처리<br>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  **마이페이지:** 회원 정보 관리 및 개인화된 주문 내역 조회 기능 구현<br>
</div>
<br>

<div align="center">
 <img width="1373" height="511" alt="게시판" src="https://github.com/user-attachments/assets/1d51870f-b55c-424e-9120-c124a2e1716c" />
 <div align="center">
  **자유 게시판:** 기본적인 글쓰기, 읽기, 수정, 삭제 기능을 갖춘 게시판 모듈
</div>
</div>
<br>


   
## 🚀 향후 개발 계획
현재 구현된 기능 외에 프로젝트의 완성도를 높이기 위해 다음 기능 개발을 예정하고 있습니다.
* **리뷰 및 평점 시스템:** 상품별 리뷰 게시판 및 별점 시스템 추가
* **댓글/답글 기능:** 자유 게시판에 계층형 댓글 및 대댓글 기능 구현
* 


  
