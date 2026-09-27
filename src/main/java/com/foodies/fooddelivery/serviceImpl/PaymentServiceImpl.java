package com.foodies.fooddelivery.serviceImpl;

import org.springframework.stereotype.Service;

import com.foodies.fooddelivery.entity.Order;
import com.foodies.fooddelivery.entity.Payment;
import com.foodies.fooddelivery.repository.OrderRepository;
import com.foodies.fooddelivery.repository.PaymentRepository;
import com.foodies.fooddelivery.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            OrderRepository orderRepository) {

        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Payment makePayment(Payment payment) {

        // COD payment remains PENDING
        // Online payment becomes PAID
        if ("COD".equalsIgnoreCase(payment.getPaymentMethod())) {
            payment.setStatus("PENDING");
        } else {
            payment.setStatus("PAID");
        }

        Payment savedPayment =
                paymentRepository.save(payment);

        Order order =
                orderRepository.findById(payment.getOrderId())
                        .orElseThrow(() ->
                                new RuntimeException("Order not found"));

        // Update order payment status
        if ("COD".equalsIgnoreCase(payment.getPaymentMethod())) {
            order.setPaymentStatus("PENDING");
        } else {
            order.setPaymentStatus("PAID");
        }

        order.setPaymentMethod(payment.getPaymentMethod());

        orderRepository.save(order);

        return savedPayment;
    }

    @Override
    public Payment getPaymentById(Long id) {

        return paymentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found"));
    }
}