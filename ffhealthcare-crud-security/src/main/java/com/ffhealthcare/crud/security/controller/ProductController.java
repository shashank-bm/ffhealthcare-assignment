package com.ffhealthcare.crud.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.ffhealthcare.crud.security.exception.ResourceNotFoundException;
import com.ffhealthcare.crud.security.model.ProductDao;
import com.ffhealthcare.crud.security.service.ProductService;

@RestController
@CrossOrigin
public class ProductController {
	@Autowired
	ProductService productService;

	/**
	 * API to get all products, Accessible without token pagination enabled
	 * 
	 * @param page
	 * @param pageSize
	 * @return ResponseEntity<Object>
	 */
	@GetMapping(value = "/getAllProducts")
	public ResponseEntity<Object> findAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int pageSize) {
		try {
			Pageable pageable = PageRequest.of(page, pageSize);

			return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProduct(pageable));
		} catch (ResourceNotFoundException ex) {
			// Handle the custom exception for resource not found
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
		}
	}

	/**
	 * API to add product
	 * 
	 * @param product
	 * @return ResponseEntity<Object>
	 */
	@PostMapping(value = "/addProduct")
	public ResponseEntity<Object> addproduct(@RequestBody ProductDao product) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(product));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
		}
	}

	/** API to update product by id
	 * 
	 * @param product
	 * @return ResponseEntity<Object>
	 */
	@PutMapping(value = "/updateProduct")
	public ResponseEntity<Object> updateProduct(@RequestBody ProductDao product) {
		try {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(productService.update(product));
		} catch (ResourceNotFoundException ex) {
			// Handle the custom exception for resource not found
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
		}
	}

	@DeleteMapping(value = "/deleteProduct/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable int id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(productService.delete(id));
		} catch (ResourceNotFoundException ex) {
			// Handle the custom exception for resource not found
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
		}
	}

}
