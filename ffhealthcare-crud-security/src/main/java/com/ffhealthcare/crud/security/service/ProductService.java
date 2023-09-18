package com.ffhealthcare.crud.security.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ffhealthcare.crud.security.exception.ResourceNotFoundException;
import com.ffhealthcare.crud.security.model.ProductDao;
import com.ffhealthcare.crud.security.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	ProductRepository productRepository;

	// FindAll
	public Page<ProductDao> getAllProduct(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	// Create
	public ProductDao addProduct(ProductDao product) {
		return productRepository.save(product);
	}

	// Read
	public Optional<ProductDao> getProductById(int id) {
		return productRepository.findById(id);
	}

	// Update
	public ProductDao update(ProductDao product) {
		// Retrieve the existing product by ID
        ProductDao existingProduct = productRepository.findById((int) product.getId())
                .orElseThrow(() -> new ResourceNotFoundException("ProductDao not found with id: " + product.getId()));

        // Apply updates from the request
        if (product.getName() != null) {
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setStock(product.getStock());
        }
        // Save the updated product
		return productRepository.save(product);
	}
	// Delete if exists by id
	public String delete(int id) {
		// Check if the resource exists
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Resource with ID " + id + " not found");
        }
        // If the resource exists, delete it
        productRepository.deleteById(id);
        return "Resource deleted successfully";
	}
}
