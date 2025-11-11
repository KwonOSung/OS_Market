package ShoppingMall.hello.Service;

import ShoppingMall.hello.Repository.CartRepository;
import ShoppingMall.hello.Repository.MemberRepository;
import ShoppingMall.hello.Repository.ProductRepository;
import ShoppingMall.hello.entity.Cart;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void addToCart(String memberId, Long productId, int quantity) {
        var member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품 없음"));

        // 이미 같은 상품이 있으면 수량만 증가
        var existingCarts = cartRepository.findByMemberId(memberId);
        for (Cart cart : existingCarts) {
            if (cart.getProduct().getId().equals(productId)) {
                cart.setQuantity(cart.getQuantity() + quantity);
                return;
            }
        }

        // 없으면 새로 추가
        Cart cart = Cart.builder()
                .member(member)
                .product(product)
                .quantity(quantity)
                .createdAt(LocalDateTime.now())
                .build();

        cartRepository.save(cart);
    }

    // 회원 장바구니 조회
    public List<Cart> getCartItems(String memberId) {
        return cartRepository.findByMemberId(memberId);
    }

    @Transactional
    public void removeFromCart(Long cartId, String memberId) {
        // 1. cartId로 장바구니 항목을 찾습니다.
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 항목을 찾을 수 없습니다."));

        // 2. 현재 로그인된 사용자의 ID와 장바구니 항목의 소유자 ID를 비교하여 검증합니다.
        if (!cart.getMember().getId().equals(memberId)) {
            // 소유자가 다르면 예외를 발생시킵니다.
            throw new IllegalArgumentException("장바구니 항목에 대한 권한이 없습니다.");
        }

        // 3. 검증이 통과하면 항목을 삭제합니다.
        cartRepository.delete(cart);
    }

    public List<Cart> getCartItemsByIds(List<Long> cartIds) {
        return cartRepository.findAllById(cartIds);
    }

    public List<Long> getProductIdsByCartIds(List<Long> cartIds) {
        return cartRepository.findAllById(cartIds)
                .stream()
                .map(cart -> cart.getProduct().getId()) // Product 엔티티에서 id 가져오기
                .toList();
    }

    public void removeCartItems(List<Long> cartIds) {
        cartRepository.deleteAllById(cartIds);
    }

}
