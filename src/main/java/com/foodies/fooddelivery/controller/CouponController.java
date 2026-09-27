package com.foodies.fooddelivery.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foodies.fooddelivery.entity.Coupon;
import com.foodies.fooddelivery.service.CouponService;


@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
        public ResponseEntity<?> createCoupon(
                @RequestBody Coupon coupon) {

            try {

                return ResponseEntity.ok(
                        couponService.createCoupon(coupon));

            } catch (RuntimeException e) {

                return ResponseEntity
                        .status(409)
                        .body(e.getMessage());

            }
        }

            @PostMapping("/apply")
    public ResponseEntity<?> applyCoupon(
            @RequestParam String code,
            @RequestParam Double orderTotal) {

        return couponService.getCouponByCode(code)
                .map(coupon -> {

                    if (coupon.getExpiryDate() != null &&
                        coupon.getExpiryDate().isBefore(java.time.LocalDate.now())) {

                        return ResponseEntity
                                .badRequest()
                                .body("Coupon has expired");
                    }

                    if (coupon.getMinimumOrder() != null &&
                        orderTotal < coupon.getMinimumOrder()) {

                        return ResponseEntity
                                .badRequest()
                                .body("Minimum order amount is ₹"
                                        + coupon.getMinimumOrder());
                    }

                    if (coupon.getDiscount() == null ||
                        coupon.getDiscount() <= 0) {

                        return ResponseEntity
                                .badRequest()
                                .body("Invalid coupon discount");
                    }

                    double discount =
                            Math.min(coupon.getDiscount(), orderTotal);

                    double finalAmount =
                            orderTotal - discount;

                    return ResponseEntity.ok(
                            java.util.Map.of(
                                    "code", coupon.getCode(),
                                    "discount", discount,
                                    "finalAmount", finalAmount
                            )
                    );

                })
                .orElse(
                    ResponseEntity
                            .status(404)
                            .body("Invalid coupon code")
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getCouponById(
            @PathVariable Long id) {
        return couponService.getCouponById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        return ResponseEntity.ok(
                couponService.getAllCoupons());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Coupon> getCouponByCode(
            @PathVariable String code) {
        return couponService.getCouponByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coupon> updateCoupon(
            @PathVariable Long id,
            @RequestBody Coupon coupon) {
        return ResponseEntity.ok(
                couponService.updateCoupon(id, coupon));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(
            @PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.noContent().build();
    }
}