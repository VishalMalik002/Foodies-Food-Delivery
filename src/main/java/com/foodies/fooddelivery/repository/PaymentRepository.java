package com.foodies.fooddelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodies.fooddelivery.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}