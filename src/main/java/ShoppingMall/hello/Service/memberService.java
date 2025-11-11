package ShoppingMall.hello.Service;


import ShoppingMall.hello.DTO.MemberDTO;
import ShoppingMall.hello.Repository.MemberRepository;
import ShoppingMall.hello.entity.Member;
import ShoppingMall.hello.entity.Order;
import ShoppingMall.hello.enums.Role;
import ShoppingMall.hello.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class memberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerMember(MemberDTO dto) {
        Member member = Member.builder()
                .id(dto.getId())
                .pw(passwordEncoder.encode(dto.getPassword()))
                .ph(dto.getPhone())
                .age(dto.getAge())
                .name(dto.getName())
                .email(dto.getEmail())
                .birth(dto.getBirth())
                .addr01(dto.getAddressCity())
                .addr02(dto.getAddressDetail())
                .postcode(dto.getPostcode())
                .gender(dto.getGender())
                .role(Role.USER)
                .status(Status.ACTIVE)
                .build();

        memberRepository.save(member); // DB 저장
    }

    // 1. 회원 정보 조회 (DTO 반환)
    public MemberDTO getMemberById(String userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        return new MemberDTO(member);
    }

    // 2. 회원 정보 수정
    public void updateMember(String userId, MemberDTO memberDTO) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        member.setName(memberDTO.getName());
        member.setEmail(memberDTO.getEmail());
        member.setAddr01(memberDTO.getAddressCity());
        member.setAddr02(memberDTO.getAddressDetail());
        member.setPostcode(memberDTO.getPostcode());
        // 필요시 추가 필드 업데이트
        memberRepository.save(member); // JPA save로 업데이트
    }

    // 3. 비밀번호 변경
    // 비밀번호 변경
    public void changePassword(String userId, String currentPassword, String newPassword) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        // 현재 비밀번호 검증
        if (!passwordEncoder.matches(currentPassword, member.getPw())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        // 새 비밀번호 암호화 후 저장
        member.setPw(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }

    public void deleteMember(String userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        member.setStatus(Status.DORMANT);  // DORMANT로 상태 변경
        memberRepository.save(member);
    }

}
