package ShoppingMall.hello.entity;

import ShoppingMall.hello.enums.productStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "os_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private int price;

    @Column(name = "STOCK")
    private int stock;

    @Column(name = "REG_DATE")
    private LocalDate regDate;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Enumerated(EnumType.STRING) // Enum 이름을 DB에 문자열로 저장
    @Column(name = "STATUS", nullable = false)
    private productStatus status;

    @ManyToOne(fetch = FetchType.LAZY) // N:1 관계
    @JoinColumn(name = "CATEGORY_ID", nullable = false) // 외래키 컬럼: category_id
    private Category category;
}
