package ShoppingMall.hello.Service;

import ShoppingMall.hello.Repository.MemberRepository;
import ShoppingMall.hello.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member m = memberRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("ID 없음"));
        // role 컬럼이 없다면 USER 고정
        return User.builder()
                .username(m.getId())
                .password(m.getPw())           // 저장 시 BCrypt로 암호화 필수
                .roles(m.getRole().name())
                .build();
    }
}
