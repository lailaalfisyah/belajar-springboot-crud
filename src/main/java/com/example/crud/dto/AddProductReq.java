package com.example.crud.dto;

import lombok.Data;

@Data
public class AddProductReq {
    private String productName;
    private Long price;
    private String description;
}
