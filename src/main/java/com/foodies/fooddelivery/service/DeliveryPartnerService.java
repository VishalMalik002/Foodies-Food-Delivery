package com.foodies.fooddelivery.service;

import java.util.List;
import java.util.Optional;

import com.foodies.fooddelivery.entity.DeliveryPartner;

public interface DeliveryPartnerService {

    DeliveryPartner createDeliveryPartner(DeliveryPartner deliveryPartner);

    Optional<DeliveryPartner> getDeliveryPartnerById(Long id);

    List<DeliveryPartner> getAllDeliveryPartners();

    DeliveryPartner updateDeliveryPartner(Long id, DeliveryPartner deliveryPartner);

    void deleteDeliveryPartner(Long id);
}