package com.example.crud.controller;

import com.example.crud.dto.AddProductReq;
import com.example.crud.dto.EditExpiredReq;
import com.example.crud.dto.EditPriceReq;
import com.example.crud.service.MasterProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MasterProductController {
    private final MasterProductService masterProductService;

    public MasterProductController(MasterProductService masterProductService) {
        this.masterProductService = masterProductService;
    }

    // show all product list
    @GetMapping("/product/list")
    public ResponseEntity<?> getProductData() {
        var response = masterProductService.getAllProductData();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // show product data by ID
    @GetMapping("/product")
    public ResponseEntity<?> getProduct(Long id) {
        var response = masterProductService.getProductById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // add new product data
    @PostMapping("/product/add")
    public ResponseEntity<?> addProduct(@RequestBody AddProductReq req) {
        var response = masterProductService.addProduct(req);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // edit existing product data by ID
    @PostMapping("/product/edit")
    public ResponseEntity<?> addProduct(@RequestBody EditPriceReq req) {
        var response = masterProductService.editProductPrice(req);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/product/edit/expired")
    public ResponseEntity<?> addProduct(@RequestBody EditExpiredReq req) {
        var response = masterProductService.editExpiredDate(req);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
