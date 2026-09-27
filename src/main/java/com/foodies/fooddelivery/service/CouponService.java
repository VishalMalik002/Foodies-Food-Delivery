package com.foodies.fooddelivery.service;

import java.util.List;
import java.util.Optional;

import com.foodies.fooddelivery.entity.Coupon;

public interface CouponService {

    Coupon createCoupon(Coupon coupon);

    Optional<Coupon> getCouponById(Long id);

    List<Coupon> getAllCoupons();

    Coupon updateCoupon(Long id, Coupon coupon);

    void deleteCoupon(Long id);

    Optional<Coupon> getCouponByCode(String code);
}