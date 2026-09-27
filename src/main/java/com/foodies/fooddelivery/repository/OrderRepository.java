package com.foodies.fooddelivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodies.fooddelivery.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findByDeliveryPartner_Id(Long deliveryPartnerId);
}