package ShoppingMall.hello.Service;

import ShoppingMall.hello.Repository.CategoryRepository;
import ShoppingMall.hello.Repository.ProductRepository;
import ShoppingMall.hello.entity.Category;
import ShoppingMall.hello.entity.Product;
import ShoppingMall.hello.enums.productStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime; // Product 엔티티의 REG_DATE가 LocalDate라면 LocalDate로 변경
import java.util.List;
import java.util.UUID;


@Service
public class AdminProductService {

    // 상품 및 카테고리 데이터 접근을 위한 리포지토리 의존성 주입
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    // application.properties에 설정할 이미지 저장 경로를 주입받습니다.
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired // 생성자 주입 방식으로 의존성을 주입합니다.
    public AdminProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * 모든 카테고리 목록을 조회합니다.
     * 상품 등록 폼의 카테고리 드롭다운을 채우는 데 사용됩니다.
     * @return 모든 Category 엔티티의 리스트
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional // 메서드 내의 모든 DB 작업이 하나의 트랜잭션으로 묶입니다.
    public Product registerProduct(String productName, String description, int price, // int price 사용
                                   int stock, Long categoryId, MultipartFile productImage) throws IOException { // int stock 사용

        // 1. 카테고리 ID로 Category 엔티티를 조회합니다.
        // 만약 해당 ID의 카테고리가 없으면 예외를 발생시킵니다.
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 카테고리 ID입니다: " + categoryId));

        // 2. 이미지 파일 처리: 파일이 존재하고 비어있지 않으면 서버에 저장하고 URL을 얻습니다.
        String imageUrl = null;
        if (productImage != null && !productImage.isEmpty()) {
            imageUrl = saveImage(productImage);
        }

        // 3. Product 엔티티를 빌더 패턴을 사용하여 생성하고 값을 설정합니다.
        // 현재 Product 엔티티 필드명에 맞게 변경했습니다 (product_id, product_name 등).
        // REG_DATE는 현재 Product 엔티티가 LocalDate이므로, LocalDateTime.now().toLocalDate() 사용
        Product product = Product.builder()
                .productName(productName)
                .description(description)
                .price(price) // int price 사용
                .stock(stock) // int stock 사용
                .regDate(LocalDateTime.now().toLocalDate()) // LocalDate 필드에 맞게 toLocalDate() 추가
                .imageUrl(imageUrl)
                .status(productStatus.sale) // 상품 등록 시 기본 상태는 '판매중'
                .category(category) // 조회한 Category 엔티티를 Product에 연결
                .build();

        // 4. 생성된 Product 엔티티를 데이터베이스에 저장합니다.
        return productRepository.save(product);
    }

    /**
     * 이미지 파일을 서버의 지정된 경로에 저장하고, 해당 이미지의 웹 접근 URL을 반환합니다.
     * @param imageFile 업로드할 MultipartFile 객체
     * @return 웹에서 접근 가능한 이미지 URL 경로
     * @throws IOException 파일 저장 중 오류 발생 시
     */
    private String saveImage(MultipartFile imageFile) throws IOException {
        // 파일명 중복을 피하기 위해 UUID를 사용하여 고유한 파일명을 생성합니다.
        String originalFilename = imageFile.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        // 이미지가 저장될 실제 파일 시스템 경로를 설정합니다.
        // 예: /Users/your_username/uploads/images/unique_filename.jpg
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs(); // 디렉토리가 없으면 생성
        }
        File dest = new File(uploadPath, uniqueFileName);
        imageFile.transferTo(dest); // 파일을 지정된 경로로 복사하여 저장합니다.

        // DB에 저장할 URL (웹에서 접근 가능한 경로)
        // WebConfig에서 /images/** 요청을 uploadDir로 매핑했으므로, 이렇게 반환합니다.
        return "/images/" + uniqueFileName;
    }

    @Transactional
    public void updateProduct(Long id, String productName, String description, int price, int stock,
                              Long categoryId, MultipartFile productImage) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("카테고리 없음"));

        product.setProductName(productName);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(category);

        if (productImage != null && !productImage.isEmpty()) {
            String imageUrl = saveImage(productImage);
            product.setImageUrl(imageUrl);
        }

        // 변경감지를 이용하므로 별도로 save() 호출하지 않아도 됩니다.
    }
}
