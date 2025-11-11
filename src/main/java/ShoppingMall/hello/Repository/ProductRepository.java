package ShoppingMall.hello.Repository;

import ShoppingMall.hello.entity.Product;
import ShoppingMall.hello.enums.productStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByStatusOrderByRegDateDesc(productStatus status);
    List<Product> findByProductNameContaining(String keyword);
    List<Product> findByCategory_Name(String name);
    List<Product> findByProductNameContainingAndCategory_Name(String keyword, String categoryName);

}
