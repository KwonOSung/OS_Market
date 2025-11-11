package ShoppingMall.hello.Repository;

import ShoppingMall.hello.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByMemberId(String memberId);
}
