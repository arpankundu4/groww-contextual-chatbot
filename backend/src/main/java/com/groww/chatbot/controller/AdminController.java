package com.groww.chatbot.controller;

import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.exchanges.*;
import com.groww.chatbot.service.CategoryService;
import com.groww.chatbot.service.FaqService;
import com.groww.chatbot.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.groww.chatbot.config.ApiRoutes.*;

/**
 * controller class
 * for admin related APIs
 *
 * refer to ApiRoutes in com.groww.chatbot.config
 * for all API URLs
 */

@Log4j2
@RestController
public class AdminController {

    @Autowired
    private FaqService faqService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping(ADD_CATEGORY_URL)
    public ResponseEntity<?> addCategory(@RequestParam(value = "parentId", required = false) String parentId,
                                         @RequestBody @Valid AddCategoryRequest addCategoryRequest) {
        log.info("Add category/subcategory request");
        try {
            return new ResponseEntity<>(categoryService.addCategory(addCategoryRequest, parentId),
                                        HttpStatus.CREATED);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(EDIT_CATEGORY_URL)
    public ResponseEntity<?> editCategory(@PathVariable("categoryId") String categoryId,
                                          @RequestBody EditCategoryRequest editCategoryRequest) {
        log.info("Edit category request");
        try {
            return ResponseEntity.ok(categoryService.editCategory(editCategoryRequest, categoryId));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(DELETE_CATEGORY_URL)
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") String categoryId) {
        log.info("Delete FAQ category/subcategory request");
        try {
            categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>("Category/subcategory deleted", HttpStatus.OK);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(ADD_FAQ_URL)
    public ResponseEntity<?> addFaq(@PathVariable("parentId") String parentId,
                                    @RequestBody @Valid AddFaqRequest addFaqRequest) {
        log.info("Add FAQ request");
        try {
            return new ResponseEntity<>(faqService.addFaq(addFaqRequest, parentId),
                                        HttpStatus.CREATED);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(EDIT_FAQ_URL)
    public ResponseEntity<?> editFaq(@PathVariable("faqId") String faqId,
                                     @RequestBody EditFaqRequest editFaqRequest) {
        log.info("Edit FAQ request");
        try {
            return ResponseEntity.ok(faqService.editFaq(editFaqRequest, faqId));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(DELETE_FAQ_URL)
    public ResponseEntity<?> deleteFaq(@PathVariable("faqId") String faqId) {
        log.info("Delete FAQ request");
        try {
            faqService.deleteFaq(faqId);
            return new ResponseEntity<>("FAQ deleted", HttpStatus.OK);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(ADD_PRODUCT_URL)
    public ResponseEntity<?> addProduct(@PathVariable("categoryId") String categoryId,
                                        @RequestBody @Valid AddProductRequest addProductRequest) {
        log.info("Add product request");
        try {
            return new ResponseEntity<>(productService.addProduct(addProductRequest, categoryId),
                                        HttpStatus.CREATED);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(EDIT_PRODUCT_URL)
    public ResponseEntity<?> editProduct(@PathVariable("productId") String productId,
                                         @RequestBody EditProductRequest editProductRequest) {
        log.info("Edit product request");
        try {
            return ResponseEntity.ok(productService.editProduct(editProductRequest, productId));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(DELETE_PRODUCT_URL)
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") String productId) {
        log.info("Delete product request");
        try {
            productService.deleteProduct(productId);
            return new ResponseEntity<>("Product deleted", HttpStatus.OK);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
