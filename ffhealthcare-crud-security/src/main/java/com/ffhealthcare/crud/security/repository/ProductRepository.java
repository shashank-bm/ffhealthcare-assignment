package com.ffhealthcare.crud.security.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ffhealthcare.crud.security.model.ProductDao;

@Repository
public interface ProductRepository extends JpaRepository<ProductDao, Integer> {
    Page<ProductDao> findAllBy(Pageable pageable);
    
}
