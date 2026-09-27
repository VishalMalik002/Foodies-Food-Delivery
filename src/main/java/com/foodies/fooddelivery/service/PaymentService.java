package com.foodies.fooddelivery.service;

import com.foodies.fooddelivery.entity.Payment;

public interface PaymentService {

    Payment makePayment(Payment payment);

    Payment getPaymentById(Long id);
}