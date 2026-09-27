package com.foodies.fooddelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodies.fooddelivery.entity.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    boolean existsByCodeIgnoreCase(String code);
}