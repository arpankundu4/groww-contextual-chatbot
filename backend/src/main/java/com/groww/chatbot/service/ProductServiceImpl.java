package com.groww.chatbot.service;

import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.exchanges.AddProductRequest;
import com.groww.chatbot.exchanges.EditProductRequest;
import com.groww.chatbot.model.Product;
import com.groww.chatbot.repository.CategoryRepository;
import com.groww.chatbot.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.groww.chatbot.util.MiscUtil.getNullPropertyNames;
import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * Service class implementation for product
 * overrides methods of interface
 */

@Log4j2
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private final ModelMapper mapper = new ModelMapper();

    @Override
    public List<Product> getProducts(String categoryId) throws NotFoundException {
        return categoryRepository
                .findById(categoryId)
                .map(category -> productRepository
                .findAllByCategoryId(categoryId))
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }

    @Override
    public Product getProduct(String productId) throws NotFoundException {
        return productRepository
                .findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Override
    public Product addProduct(AddProductRequest addProductRequest, String categoryId) throws NotFoundException {
        return categoryRepository
                .findById(categoryId)
                .map(category -> {
                    Product product = mapper.map(addProductRequest, Product.class);
                    product.setCategoryId(categoryId);
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }

    @Override
    public Product editProduct(EditProductRequest editProductRequest, String productId) throws NotFoundException {
        return productRepository
                .findById(productId)
                .map(product -> {
                    copyProperties(editProductRequest, product, getNullPropertyNames(editProductRequest));
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Override
    public void deleteProduct(String productId) throws NotFoundException {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        productRepository.delete(product);
    }


}
