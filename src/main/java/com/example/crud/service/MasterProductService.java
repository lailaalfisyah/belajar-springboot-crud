package com.example.crud.service;

import com.example.crud.dto.AddProductReq;
import com.example.crud.dto.EditExpiredReq;
import com.example.crud.dto.EditPriceReq;
import com.example.crud.entity.MasterProductEntity;
import com.example.crud.repo.MasterProductRepo;
import com.example.crud.utility.StringUtil;
import jakarta.persistence.EntityManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;
import org.hibernate.query.sql.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MasterProductService {
    private final MasterProductRepo masterProductRepo;
    private final EntityManager entityManager;

    public MasterProductService(MasterProductRepo masterProductRepo, EntityManager entityManager) {
        this.masterProductRepo = masterProductRepo;
        this.entityManager = entityManager;
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    public Map<String, Object> getAllProductData() {
//        var allData = masterProductRepo.findAll();
//        return Map.of(
//                "totalAllData", allData.size(),
//                "totalExpData", allData.stream()
//                        .filter(data -> data.getExpiredDate() != null && data.getExpiredDate().isBefore(LocalDateTime.now())).count(),
//                "data", allData);
        List<Map<String, Object>> allData = ((NativeQueryImpl) entityManager.createNativeQuery("SELECT * FROM test_master_product"))
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).getResultList();
        return Map.of(
                "totalAllData", allData.size(),
                "totalExpData", allData.stream()
                        .filter(data -> data.get("expired_date") != null && ((Instant) data.get("expired_date")).isBefore(OffsetDateTime.now().toInstant())).count(),
                "data", StringUtil.toCamelCase(allData));
    }

    public MasterProductEntity getProductById(Long id) {
        return masterProductRepo.findById(id).orElseThrow(() -> new RuntimeException("Data not found!"));
    }

    public MasterProductEntity addProduct(AddProductReq request) {
        if (StringUtils.isBlank(request.getProductName()) || StringUtils.isBlank(request.getDescription()) || request.getPrice() == null) {
            throw new RuntimeException("productName, price, dan description harus diisi");
        }

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

    public MasterProductEntity editExpiredDate(EditExpiredReq req) {
        MasterProductEntity product = masterProductRepo.findById(req.getId()).orElseThrow(() -> new RuntimeException("Data not found!"));

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime newExpDate = LocalDateTime.parse(req.getNewExpDate(), dateFormatter);
        if (product.getExpiredDate() != null && (newExpDate.isBefore(now) || newExpDate.isBefore(product.getExpiredDate()))) {
            throw new RuntimeException("newExpDate harus lebih dari waktu saat ini dan lebih dari waktu expired yang sebelumnya.");
        }

        product.setExpiredDate(newExpDate);
        return masterProductRepo.save(product);
    }
}
