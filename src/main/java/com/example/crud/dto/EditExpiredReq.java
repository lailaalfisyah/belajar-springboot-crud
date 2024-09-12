package com.example.crud.dto;

import lombok.Data;

@Data
public class EditExpiredReq {
    private Long id;
    private String newExpDate;
}
