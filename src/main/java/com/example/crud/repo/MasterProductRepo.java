package com.example.crud.repo;

import com.example.crud.entity.MasterProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterProductRepo extends JpaRepository<MasterProductEntity, Long> {
}
