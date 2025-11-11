package ShoppingMall.hello.Repository;

import ShoppingMall.hello.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByMember_Id(String userId);
}
