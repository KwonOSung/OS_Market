package ShoppingMall.hello.entity;

import ShoppingMall.hello.enums.Role;
import ShoppingMall.hello.enums.Status;
import jakarta.persistence.*;  // JPA 어노테이션 import
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "os_member")//
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {


    @Column(name = "MI_SEQ", insertable = false, updatable = false)
    private Long seq;

    @Id
    @Column(name = "MI_ID")
    private String id;

    @Column(name = "MI_PW",  length = 100)
    private String pw;

    @Column(name = "MI_HP")
    private String ph;

    @Column(name = "MI_AGE")
    private int age;

    @Column(name = "MI_NAME")
    private String name;

    @Column(name = "MI_EMAIL")
    private String email;

    @Column(name = "MI_BIRTH")
    private LocalDate birth;

    @Column(name = "MI_ADDR01")
    private String addr01;

    @Column(name = "MI_ADDR02")
    private String addr02;

    @Column(name = "MI_POSTCODE")
    private String postcode;

    @Column(name = "MI_GENDER")
    private String gender;

    @Builder.Default
    @Enumerated(EnumType.STRING)      // ← Enum 값을 문자열로 보관
    @Column(name = "ROLE", length = 20, nullable = false)
    private Role role = Role.USER;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 20, nullable = false)
    private Status status = Status.ACTIVE;

    @Builder.Default
    @Column(name="JOIN_DATE")
    private LocalDate joinDate = LocalDate.now();
}
