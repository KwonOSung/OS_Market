package ShoppingMall.hello.DTO;

import ShoppingMall.hello.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberDTO {
    private String id;
    private String password;
    private String phone;
    private int age;
    private String name;
    private String email;
    private LocalDate birth;
    private String addressCity;
    private String addressDetail;
    private String postcode;
    private String gender;

    public MemberDTO() {}

    // Member 엔티티를 받아 DTO로 변환
    public MemberDTO(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.addressCity = member.getAddr01();
        this.addressDetail = member.getAddr02();
        this.postcode = member.getPostcode();
        this.password = member.getPw();
    }

    // MemberDTO -> Member로 변환 (업데이트용)
    public void updateMember(Member member) {
        member.setName(this.name);
        member.setEmail(this.email);
        member.setAddr01(this.addressCity);
        member.setAddr02(this.addressDetail);
        member.setPostcode(this.postcode);
        if (this.password != null && !this.password.isEmpty()) {
            member.setPw(this.password);
        }
    }
}
