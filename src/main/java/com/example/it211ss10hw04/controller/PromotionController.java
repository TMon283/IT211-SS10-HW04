package com.example.it211ss10hw04.controller;

import com.example.it211ss10hw04.model.entity.Order;
import com.example.it211ss10hw04.model.entity.Promotion;
import com.example.it211ss10hw04.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/promo")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promoService;

    @PostMapping
    public ResponseEntity<Promotion> createPromo(@RequestBody Promotion promo) {
        return ResponseEntity.ok(promoService.createPromotion(promo));
    }

    @PostMapping("/apply")
    public ResponseEntity<Order> applyPromo(@RequestBody Map<String, String> request) {
        Long orderId = Long.valueOf(request.get("orderId"));
        String promoCode = request.get("promoCode");
        return ResponseEntity.ok(promoService.applyPromotion(orderId, promoCode));
    }
}

