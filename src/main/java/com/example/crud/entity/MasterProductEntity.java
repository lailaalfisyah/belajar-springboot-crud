package com.example.crud.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "test_master_product")
public class MasterProductEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private Long price;

    private String description;

    private LocalDateTime expiredDate;
}
