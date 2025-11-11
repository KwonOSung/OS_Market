package ShoppingMall.hello.Repository;

import ShoppingMall.hello.entity.Member;
import ShoppingMall.hello.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    List<Member> findAllByOrderByJoinDateDesc();

}
