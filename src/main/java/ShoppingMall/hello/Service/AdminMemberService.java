package ShoppingMall.hello.Service;

import ShoppingMall.hello.Repository.MemberRepository;
import ShoppingMall.hello.entity.Member;
import ShoppingMall.hello.enums.Role;
import ShoppingMall.hello.enums.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminMemberService {

    private final MemberRepository memberRepository;

    public List<Member> getMembers() {
        return memberRepository.findAllByOrderByJoinDateDesc();
    }

    public Member getMember(String id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("회원 없음"));
    }

    @Transactional
    public void updateRole(String id, Role role) {
        Member m = getMember(id);
        m.setRole(role);   // USER ↔ ADMIN
    }

    @Transactional
    public void updateStatus(String id, Status status) {
        Member m = getMember(id);
        m.setStatus(status);
    }

    @Scheduled(cron = "0 0 3 * * ?")   // 매일 새벽 3시
    @Transactional
    public void autoDormant() {
        LocalDate limit = LocalDate.now().minusMonths(6);
        memberRepository.findAll().forEach(m -> {
            if (m.getJoinDate().isBefore(limit) && m.getStatus() == Status.ACTIVE) {
                m.setStatus(Status.DORMANT);   // 문자열 → Enum 상수
            }
        });
    }

    public Optional<Member> getMemberOpt(String id) {
        return memberRepository.findById(id);   // 존재하면 Optional.of, 없으면 Optional.empty
    }

}
