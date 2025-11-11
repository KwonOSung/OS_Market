package ShoppingMall.hello.Repository;

import ShoppingMall.hello.entity.Order;
import ShoppingMall.hello.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT oi.product.id, SUM(oi.quantity) " +
            "FROM OrderItem oi " +
            "GROUP BY oi.product.id " +
            "ORDER BY SUM(oi.quantity) DESC")
    List<Object[]> findBestSellingProducts();
}
