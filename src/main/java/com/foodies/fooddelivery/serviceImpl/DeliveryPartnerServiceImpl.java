package com.foodies.fooddelivery.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.foodies.fooddelivery.entity.DeliveryPartner;
import com.foodies.fooddelivery.repository.DeliveryPartnerRepository;
import com.foodies.fooddelivery.service.DeliveryPartnerService;

@Service
public class DeliveryPartnerServiceImpl implements DeliveryPartnerService {

    private final DeliveryPartnerRepository deliveryPartnerRepository;

    public DeliveryPartnerServiceImpl(
            DeliveryPartnerRepository deliveryPartnerRepository) {
        this.deliveryPartnerRepository = deliveryPartnerRepository;
    }

    @Override
    public DeliveryPartner createDeliveryPartner(DeliveryPartner deliveryPartner) {
        return deliveryPartnerRepository.save(deliveryPartner);
    }

    @Override
    public Optional<DeliveryPartner> getDeliveryPartnerById(Long id) {
        return deliveryPartnerRepository.findById(id);
    }

    @Override
    public List<DeliveryPartner> getAllDeliveryPartners() {
        return deliveryPartnerRepository.findAll();
    }

    @Override
    public DeliveryPartner updateDeliveryPartner(
            Long id, DeliveryPartner deliveryPartner) {

        DeliveryPartner existingPartner =
                deliveryPartnerRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Delivery partner not found"));

        existingPartner.setName(deliveryPartner.getName());
        existingPartner.setPhone(deliveryPartner.getPhone());
        existingPartner.setStatus(deliveryPartner.getStatus());
        existingPartner.setCurrentLocation(deliveryPartner.getCurrentLocation());
        existingPartner.setLatitude(deliveryPartner.getLatitude());
        existingPartner.setLongitude(deliveryPartner.getLongitude());
                

        return deliveryPartnerRepository.save(existingPartner);
    }

    @Override
    public void deleteDeliveryPartner(Long id) {
        deliveryPartnerRepository.deleteById(id);
    }
}