package com.foodies.fooddelivery.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.foodies.fooddelivery.entity.Order;
import com.foodies.fooddelivery.repository.CouponRepository;
import com.foodies.fooddelivery.repository.DeliveryPartnerRepository;
import com.foodies.fooddelivery.repository.OrderRepository;
import com.foodies.fooddelivery.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DeliveryPartnerRepository deliveryPartnerRepository;
    private final CouponRepository couponRepository;

    public OrderServiceImpl(OrderRepository orderRepository, DeliveryPartnerRepository deliveryPartnerRepository, CouponRepository couponRepository) {
        this.orderRepository = orderRepository;
        this.deliveryPartnerRepository = deliveryPartnerRepository;
        this.couponRepository = couponRepository;
    }

   @Override
public Order placeOrder(Order order) {

    if (order.getCouponId() != null) {

        var coupon = couponRepository.findById(order.getCouponId())
                .orElseThrow(() -> new RuntimeException("Coupon not found"));

        if (coupon.getExpiryDate().isBefore(java.time.LocalDate.now())) {
            throw new RuntimeException("Coupon expired");
        }

        if (order.getTotalPrice() < coupon.getMinimumOrder()) {
            throw new RuntimeException(
                    "Minimum order amount should be " + coupon.getMinimumOrder());
        }

        double discountedPrice =
                order.getTotalPrice() - coupon.getDiscount();

        order.setTotalPrice(Math.max(discountedPrice, 0));
    }

    return orderRepository.save(order);
}

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findAll()
                .stream()
                .filter(order -> order.getUserId().equals(userId))
                .toList();
    }

    @Override 
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order updateOrderStatus(Long id, String status) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        existingOrder.setStatus(status);

        return orderRepository.save(existingOrder);
    }

    @Override
    public void cancelOrder(Long id) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        existingOrder.setStatus("CANCELLED");

        orderRepository.save(existingOrder);
    }

    @Override
public Order assignDeliveryPartner(Long orderId, Long deliveryPartnerId) {

    Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));

    var deliveryPartner = deliveryPartnerRepository.findById(deliveryPartnerId)
            .orElseThrow(() -> new RuntimeException("Delivery Partner not found"));

    order.setDeliveryPartner(deliveryPartner);

    return orderRepository.save(order);
    }
    @Override
public List<Order> getOrdersByDeliveryPartnerId(Long deliveryPartnerId) {
    return orderRepository.findByDeliveryPartner_Id(deliveryPartnerId);
    }
}