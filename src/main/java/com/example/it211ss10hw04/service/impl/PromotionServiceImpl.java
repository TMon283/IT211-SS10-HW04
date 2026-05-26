package com.example.it211ss10hw04.service.impl;

import com.example.it211ss10hw04.exception.PromotionException;
import com.example.it211ss10hw04.model.entity.Order;
import com.example.it211ss10hw04.model.entity.Promotion;
import com.example.it211ss10hw04.repository.OrderRepository;
import com.example.it211ss10hw04.repository.PromotionRepository;
import com.example.it211ss10hw04.service.PromotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promoRepo;
    private final OrderRepository orderRepo;

    @Override
    public Promotion createPromotion(Promotion promo) {
        if (promo.getDiscountPercent() < 1 || promo.getDiscountPercent() > 100) {
            throw new PromotionException("discountPercent phải nằm trong khoảng 1-100");
        }
        return promoRepo.save(promo);
    }

    @Override
    public Order applyPromotion(Long orderId, String promoCode) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new PromotionException("Không tìm thấy đơn hàng"));

        if ("EXPIRED".equalsIgnoreCase(promoCode)) {
            throw new PromotionException("Mã khuyến mãi đã hết hạn");
        }
        if ("CRASH".equalsIgnoreCase(promoCode)) {
            throw new NullPointerException("Simulated crash");
        }

        Promotion promo = promoRepo.findByCode(promoCode)
                .orElseThrow(() -> new PromotionException("Mã khuyến mãi không tồn tại"));

        if (!promo.getIsActive()) {
            throw new PromotionException("Mã khuyến mãi không hoạt động");
        }

        double discountAmount = order.getTotalAmount() * promo.getDiscountPercent() / 100.0;
        order.setDiscountAmount(discountAmount);
        order.setFinalAmount(order.getTotalAmount() - discountAmount);

        orderRepo.save(order);
        log.info("Áp dụng mã {} thành công cho Order {}", promoCode, orderId);
        return order;
    }
}
