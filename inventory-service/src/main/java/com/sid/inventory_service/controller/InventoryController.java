package com.sid.inventory_service.controller;
import com.sid.inventory_service.dto.InventoryResponse;
import com.sid.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private final InventoryService inventoryService;

    //http://localhost:8083/api/inventory/iphone_12
    // http://localhost:8083/api/inventory?sku-code=iphone_12,iphone_12_white
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        return inventoryService.isInStock(skuCode);
    }
}
