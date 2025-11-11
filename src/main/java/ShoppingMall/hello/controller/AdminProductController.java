package ShoppingMall.hello.controller;

import ShoppingMall.hello.Repository.ProductRepository;
import ShoppingMall.hello.Service.AdminProductService;
import ShoppingMall.hello.entity.Category;
import ShoppingMall.hello.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/products") // <-- 이 라인을 추가하여 모든 하위 매핑에 "/admin/products"를 붙입니다!

public class AdminProductController {

    private final AdminProductService adminProductService;
    private final ProductRepository productRepository;


    @GetMapping("/add")
    public String adminProduct(Model model) {
        List<Category> categories = adminProductService.getAllCategories();
        System.out.println("▶ 카테고리 개수 = " + categories.size());
        model.addAttribute("categories", categories);
        return "admin/adminProductInsert";
    }

    @GetMapping("/list")
    public String productList(Model model) {
        List<Product> products = productRepository.findAll(); // 또는 페이징 처리
        model.addAttribute("products", products);
        return "admin/productList"; // HTML 파일 경로
    }

    @GetMapping("/{id}/edit")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. ID=" + id));

        List<Category> categories = adminProductService.getAllCategories();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);

        return "admin/productEdit";
    }

    @PostMapping("/{id}/edit")
    public String updateProduct(@PathVariable Long id,
                                @RequestParam String productName,
                                @RequestParam String description,
                                @RequestParam int price,
                                @RequestParam int stock,
                                @RequestParam Long categoryId,
                                @RequestParam(required = false) MultipartFile productImage,
                                RedirectAttributes redirectAttributes) {
        try {
            adminProductService.updateProduct(id, productName, description, price, stock, categoryId, productImage);
            redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "수정 중 오류 발생: " + e.getMessage());
        }
        return "redirect:/admin/products/list";
    }



    @PostMapping("/add")
    public String registerProduct(
            @RequestParam("productName") String productName,
            @RequestParam("description") String description,
            @RequestParam("price") int price,
            @RequestParam("stock") int stock,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "productImage", required = false) MultipartFile productImage,
            RedirectAttributes redirectAttributes) {

        try {
            // AdminProductService를 호출하여 상품 등록 로직을 수행합니다.
            adminProductService.registerProduct(productName, description, price, stock, categoryId, productImage);
            // 성공 메시지를 RedirectAttributes에 담아 리다이렉트 후에도 메시지가 보이도록 합니다.
            redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 등록되었습니다!");
            // 상품 등록 후 다시 등록 페이지로 리다이렉트합니다.
            return "redirect:/admin/products/add";
            // TODO: 추후 상품 목록 페이지가 있다면 "redirect:/admin/products/list" 등으로 변경 가능
        } catch (IllegalArgumentException e) {
            // 유효하지 않은 카테고리 ID 등 비즈니스 로직 오류 시
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/products/register";
        } catch (IOException e) {
            // 이미지 파일 처리 중 IO 오류 발생 시
            redirectAttributes.addFlashAttribute("error", "이미지 업로드 중 오류가 발생했습니다: " + e.getMessage());
            // 에러 스택 트레이스도 콘솔에 출력하여 디버깅에 도움
            e.printStackTrace();
            return "redirect:/admin/products/register";
        } catch (Exception e) {
            // 그 외 알 수 없는 모든 오류
            redirectAttributes.addFlashAttribute("error", "상품 등록 중 알 수 없는 오류가 발생했습니다: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/admin/products/register";
        }
    }
}
