package ShoppingMall.hello.controller;

import ShoppingMall.hello.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;



    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam int quantity) {
        // 로그인한 사용자 정보 가져오기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String memberId = auth.getName(); // UserDetails의 username 값 (memberId)

        cartService.addToCart(memberId, productId, quantity);
        return "redirect:/cart";
    }

    // 장바구니 조회
    @GetMapping
    public String viewCart( Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String memberId = auth.getName(); // UserDetails의 username 값

        model.addAttribute("cartItems", cartService.getCartItems(memberId));
        return "cart/view"; // cart/view.html
    }

    // 장바구니 항목 삭제
    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long cartId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }

        String memberId = ((UserDetails) auth.getPrincipal()).getUsername();

        cartService.removeFromCart(cartId, memberId);
        return "redirect:/cart";
    }
}
