package com.sid.order_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_order_line_items")
public class OrderLineItems {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @jakarta.persistence.Id
    private Long Id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;


}