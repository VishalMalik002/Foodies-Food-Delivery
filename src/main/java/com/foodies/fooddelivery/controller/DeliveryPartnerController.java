package com.foodies.fooddelivery.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foodies.fooddelivery.entity.DeliveryPartner;
import com.foodies.fooddelivery.service.DeliveryPartnerService;

@RestController
@RequestMapping("/api/delivery-partners")
public class DeliveryPartnerController {

    private final DeliveryPartnerService deliveryPartnerService;

    public DeliveryPartnerController(
            DeliveryPartnerService deliveryPartnerService) {
        this.deliveryPartnerService = deliveryPartnerService;
    }

    @PostMapping
    public ResponseEntity<DeliveryPartner> createDeliveryPartner(
            @RequestBody DeliveryPartner deliveryPartner) {
        return ResponseEntity.ok(
                deliveryPartnerService.createDeliveryPartner(deliveryPartner));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryPartner> getDeliveryPartnerById(
            @PathVariable Long id) {
        return deliveryPartnerService.getDeliveryPartnerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<DeliveryPartner>> getAllDeliveryPartners() {
        return ResponseEntity.ok(
                deliveryPartnerService.getAllDeliveryPartners());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryPartner> updateDeliveryPartner(
            @PathVariable Long id,
            @RequestBody DeliveryPartner deliveryPartner) {
        return ResponseEntity.ok(
                deliveryPartnerService.updateDeliveryPartner(
                        id, deliveryPartner));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeliveryPartner(
            @PathVariable Long id) {
        deliveryPartnerService.deleteDeliveryPartner(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/location")
    public ResponseEntity<DeliveryPartner> getDeliveryPartnerLocation(
        @PathVariable Long id) {

    return deliveryPartnerService
            .getDeliveryPartnerById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/location")
    public ResponseEntity<DeliveryPartner> updateDeliveryPartnerLocation(
        @PathVariable Long id,
        @RequestParam Double latitude,
        @RequestParam Double longitude) {

    DeliveryPartner partner =
            deliveryPartnerService
                    .getDeliveryPartnerById(id)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Delivery partner not found"));

    partner.setLatitude(latitude);
    partner.setLongitude(longitude);

    DeliveryPartner updatedPartner =
            deliveryPartnerService
                    .createDeliveryPartner(partner);

    return ResponseEntity.ok(updatedPartner);
    }
}