package ShoppingMall.hello.controller;

import ShoppingMall.hello.Repository.ProductRepository;
import ShoppingMall.hello.Service.ProductService;
import ShoppingMall.hello.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    @GetMapping("/product/list")
    public  String showProductList(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "productList";
    }

    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. ID=" + id));

        model.addAttribute("product", product);
        return "productDetail"; // 템플릿 경로
    }

    @GetMapping("/product/filter")
    public String getProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String type,
            Model model) {

        List<Product> products;

        if (keyword != null && type != null) {
            products = productRepository.findByProductNameContainingAndCategory_Name(keyword, type);
        } else if (keyword != null) {
            products = productRepository.findByProductNameContaining(keyword);
        } else if (type != null) {
            products = productRepository.findByCategory_Name(type);
        } else {
            products = productRepository.findAll();
        }

        model.addAttribute("products", products);
        return "MainPage";
    }

}
