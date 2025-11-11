package ShoppingMall.hello.Service;

import ShoppingMall.hello.Repository.ProductRepository;
import ShoppingMall.hello.entity.Product;
import ShoppingMall.hello.enums.productStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return  productRepository.findAll();
    }

    public List<Product> getRecentProducts() {
        // 기존: productRepository.findByStatusOrderByRegDateDesc("판매중"); // (문제의 코드)
        // 수정: productStatus Enum의 '판매중' 상수를 직접 사용하여 타입 일치
        return productRepository.findByStatusOrderByRegDateDesc(productStatus.sale);
    }

    public List<Product> search(String keyword) {
        return productRepository.findByProductNameContaining(keyword);
    }
}
