package ShoppingMall.hello.controller;

import ShoppingMall.hello.Service.CartService;
import ShoppingMall.hello.Service.OrderService;
import ShoppingMall.hello.entity.Cart;
import ShoppingMall.hello.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final CartService cartService;
    private final OrderService orderService;

    @PostMapping("/order/confirm")
    public String confirmOrder(@RequestParam(name = "cartIds", required = false) List<Long> cartIds,
                               @RequestParam(name = "quantities", required = false) List<Integer> quantities,
                               Model model) {

        if (cartIds == null || cartIds.isEmpty()) {
            throw new IllegalArgumentException("상품을 선택해주세요.");
        }

        // 체크된 장바구니 아이템만 불러오기
        List<Cart> selectedItems = cartService.getCartItemsByIds(cartIds);

        // 수량 업데이트 (혹시 사용자가 장바구니에서 변경했을 수 있음)
        Map<Long, Integer> cartQuantityMap = new HashMap<>();
        for (int i = 0; i < cartIds.size(); i++) {
            cartQuantityMap.put(cartIds.get(i), quantities.get(i));
        }

        for (Cart cart : selectedItems) {
            cart.setQuantity(cartQuantityMap.get(cart.getId()));
        }


        model.addAttribute("selectedItems", selectedItems);

        // 총합 계산해서 넘기면 더 편리
        int totalPrice = selectedItems.stream()
                .mapToInt(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        model.addAttribute("totalPrice", totalPrice);

        return "order/confirm"; // → templates/order/confirm.html 렌더링
    }

    @PostMapping("/order/complete")
    public String completeOrder(@RequestParam("cartIds") List<Long> cartIds,
                                @RequestParam("quantities") List<Integer> quantities,
                                @RequestParam("address") String address,
                                @RequestParam("paymentMethod") String paymentMethod,
                                Principal principal,
                                Model model) {
        // 로그인된 사용자 (예: SecurityContextHolder 에서 가져오기)
        String userId = getCurrentUserId(principal);

        // 디버깅을 위한 로그
        System.out.println("=== ORDER DEBUG INFO ===");
        System.out.println("Current User ID: " + userId);
        System.out.println("Cart IDs: " + cartIds);
        System.out.println("Quantities: " + quantities);
        System.out.println("Address: " + address);
        System.out.println("Payment Method: " + paymentMethod);

        try {
            // 상품 ID 리스트 추출
            List<Long> productIds = cartService.getProductIdsByCartIds(cartIds);
            System.out.println("Product IDs: " + productIds);

            // 서비스 호출해서 주문 생성
            Order order = orderService.createOrder(userId, address, paymentMethod, productIds, quantities, cartIds);


            // 완료 페이지에 전달할 데이터
            model.addAttribute("order", order);

            return "order/complete";

        } catch (Exception e) {
            System.err.println("Order creation failed: " + e.getMessage());
            e.printStackTrace();

            // 에러 페이지로 리다이렉트하거나 에러 메시지 표시
            model.addAttribute("errorMessage", "주문 처리 중 오류가 발생했습니다: " + e.getMessage());
            return "error/order-error"; // 에러 페이지 생성 필요
        }
    }

    private String getCurrentUserId(Principal principal) {
        // 방법 1: Principal 사용 (가장 간단)
        if (principal != null) {
            System.out.println("Using Principal: " + principal.getName());
            return principal.getName();
        }

        System.err.println("ERROR: No authenticated user found!");
        throw new IllegalStateException("로그인이 필요합니다. 다시 로그인해주세요.");
    }

    @GetMapping("/mypage/order")
    public String orderList(Model model, Principal principal) {
        String userId = principal.getName(); // 로그인 사용자 ID
        List<Order> orders = orderService.getOrdersByMember(userId);
        System.out.println("로그인된 사용자 ID: " + userId); // 디버깅 로그
        model.addAttribute("orders", orders);
        return "mypage/myorder"; // 뷰 템플릿
    }
}

