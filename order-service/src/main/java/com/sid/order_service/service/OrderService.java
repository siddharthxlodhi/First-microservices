package com.sid.order_service.service;

import com.sid.inventory_service.dto.InventoryResponse;
import com.sid.order_service.Dto.OrderLineItemsDto;
import com.sid.order_service.Dto.OrderRequest;
import com.sid.order_service.model.Order;
import com.sid.order_service.model.OrderLineItems;
import com.sid.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private final WebClient.Builder webClientBuilder;


    public void placeOrder(OrderRequest orderRequest) {
        Order order = Order.builder().orderNumber(UUID.randomUUID().toString()).orderLineItemsList(orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToOrder).toList()).build();
        List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();
        //Calling inventory service if product is in stock


        InventoryResponse[] inventoryResponsesArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        System.out.println(Arrays.toString(inventoryResponsesArray));
        boolean allProductIsInStock = Arrays.stream(inventoryResponsesArray).allMatch(InventoryResponse::isInStock);
        if (allProductIsInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Products is not in stock,Try later");
        }

    }

    private OrderLineItems mapToOrder(OrderLineItemsDto orderLineItemsDto) {
        return OrderLineItems.builder().Id(orderLineItemsDto.getId()).price(orderLineItemsDto.getPrice()).skuCode(orderLineItemsDto.getSkuCode()).quantity(orderLineItemsDto.getQuantity()).build();

    }

}
