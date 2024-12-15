package com.sid.inventory_service.service;
import com.sid.inventory_service.dto.InventoryResponse;
import com.sid.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@RequiredArgsConstructor
@Service
public class InventoryService {
    @Autowired
    private final InventoryRepository inventoryRepository;

    public List<InventoryResponse> isInStock(List<String> skuCode) {
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory -> InventoryResponse.builder().isInStock(inventory.getQuantity() > 0).skuCode(inventory.getSkuCode()).build()).toList();
    }
}
