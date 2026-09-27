package com.foodies.fooddelivery.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.foodies.fooddelivery.entity.Coupon;
import com.foodies.fooddelivery.repository.CouponRepository;
import com.foodies.fooddelivery.service.CouponService;

@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public Coupon createCoupon(Coupon coupon) {

        if (couponRepository.existsByCodeIgnoreCase(
                coupon.getCode())) {

            throw new RuntimeException(
                    "Coupon code already exists"
            );
        }

        return couponRepository.save(coupon);
    }

    @Override
    public Optional<Coupon> getCouponById(Long id) {
        return couponRepository.findById(id);
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public Coupon updateCoupon(Long id, Coupon coupon) {
        Coupon existingCoupon = couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));

        existingCoupon.setCode(coupon.getCode());
        existingCoupon.setDiscount(coupon.getDiscount());
        existingCoupon.setMinimumOrder(coupon.getMinimumOrder());
        existingCoupon.setExpiryDate(coupon.getExpiryDate());

        return couponRepository.save(existingCoupon);
    }

    @Override
    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }

    @Override
    public Optional<Coupon> getCouponByCode(String code) {
        return couponRepository.findAll()
                .stream()
                .filter(coupon -> coupon.getCode().equalsIgnoreCase(code))
                .findFirst();
    }
}