package ShoppingMall.hello.Service;

import ShoppingMall.hello.Repository.*;
import ShoppingMall.hello.entity.Member;
import ShoppingMall.hello.entity.Order;
import ShoppingMall.hello.entity.OrderItem;
import ShoppingMall.hello.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Transactional
    public Order createOrder(String userId, String address, String paymentMethod,
                             List<Long> productIds, List<Integer> quantities,
                             List<Long> cartIds)  {

        // 회원 찾기
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        // 주문 객체 생성
        Order order = new Order();
        order.setMember(member);
        order.setAddress(address);
        order.setPaymentMethod(paymentMethod);
        order.setStatus("ORDERED");

        int totalPrice = 0;

        // 주문상품 생성
        for (int i = 0; i < productIds.size(); i++) {
            Product product = productRepository.findById(productIds.get(i))
                    .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
            int quantity = quantities.get(i);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(product.getPrice() * quantity);

            totalPrice += orderItem.getPrice();
            order.getOrderItems().add(orderItem);
        }

        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        // 주문 완료 후 장바구니에서 삭제
        for (Long cartId : cartIds) {
            cartRepository.deleteById(cartId);
        }

        return savedOrder;


    }

    public List<Order> getOrdersByMember(String userId) {
        return orderRepository.findByMember_Id(userId);
    }


}
