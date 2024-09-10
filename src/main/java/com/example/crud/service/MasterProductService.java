package com.example.crud.service;

import com.example.crud.dto.AddProductReq;
import com.example.crud.dto.EditPriceReq;
import com.example.crud.entity.MasterProductEntity;
import com.example.crud.repo.MasterProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterProductService {
    private final MasterProductRepo masterProductRepo;

    public MasterProductService(MasterProductRepo masterProductRepo) {
        this.masterProductRepo = masterProductRepo;
    }

    public List<MasterProductEntity> getAllProductData() {
        return masterProductRepo.findAll();
    }

    public MasterProductEntity getProductById(Long id) {
        return masterProductRepo.findById(id).orElseThrow(() -> new RuntimeException("Data not found!"));
    }

    public MasterProductEntity addProduct(AddProductReq request) {
        MasterProductEntity product = new MasterProductEntity();
        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        return masterProductRepo.save(product);
    }

    public MasterProductEntity editProductPrice(EditPriceReq request) {
        MasterProductEntity product = masterProductRepo.findById(request.getId()).orElseThrow(() -> new RuntimeException("Data not found!"));
        product.setPrice(request.getPrice());
        return masterProductRepo.save(product);
    }
}
