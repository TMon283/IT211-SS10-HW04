package com.example.it211ss10hw04.service;

import com.example.it211ss10hw04.model.entity.Order;
import com.example.it211ss10hw04.model.entity.Promotion;

public interface PromotionService {
    Promotion createPromotion(Promotion promo);
    Order applyPromotion(Long orderId, String promoCode);
}

