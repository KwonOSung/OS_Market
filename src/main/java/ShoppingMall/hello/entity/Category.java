package ShoppingMall.hello.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "os_productcategory")//
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id; // DB : CATEGORY_ID

    @Column(name = "category_name")
    private String name; // DB : CATEGORY_NAME
}
