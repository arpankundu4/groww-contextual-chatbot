package com.groww.chatbot.service;

import com.groww.chatbot.exception.AccessDeniedException;
import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.exchanges.AddCategoryRequest;
import com.groww.chatbot.exchanges.CategoryResponse;
import com.groww.chatbot.exchanges.Context;
import com.groww.chatbot.exchanges.EditCategoryRequest;
import com.groww.chatbot.model.Category;
import com.groww.chatbot.repository.CategoryRepository;
import com.groww.chatbot.repository.FaqRepository;
import com.groww.chatbot.repository.ProductRepository;
import com.groww.chatbot.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.*;
import static com.groww.chatbot.util.MiscUtil.*;

/**
 * Service class implementation for category
 * overrides methods of interface
 */

@Log4j2
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private FaqRepository faqRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private final ModelMapper mapper = new ModelMapper();

    @Override
    public List<CategoryResponse> getCategories(Context context) throws NotFoundException {
        List<Category> categories;
        // for non logged-in user
        if(context.getUserId() == null) {
            // get only public categories
            log.info("Getting public categories");
            categories = categoryRepository
                    .findAllByParentId(null)
                    .stream()
                    .filter(category -> !category.isHidden())
                    .collect(Collectors.toList());
        } else {
            // for logged-in user
            // check if user id is valid
            if(!userRepository.existsById(context.getUserId())) {
                throw new NotFoundException("Context user id not found");
            }
            // get all categories
            log.info("Getting all categories");
            categories = categoryRepository.findAllByParentId(null);
        }
        // map & return categories response
        return categories
                .stream()
                .map(category -> mapper.map(category, CategoryResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryResponse> getSubcategories(String parentId, Context context)
            throws NotFoundException, AccessDeniedException {
        List<Category> subcategories;
        // check if parent exists
        Category parent = categoryRepository
                .findById(parentId)
                .orElseThrow(() -> new NotFoundException("Parent not found"));
        // for non-logged in user
        if(context.getUserId() == null) {
            if(parent.isHidden()) {
                throw new AccessDeniedException("Access denied to private parent");
            }
            // get only public subcategories
            log.info("Getting public subcategories");
            subcategories = categoryRepository
                    .findAllByParentId(parentId)
                    .stream()
                    .filter(faqSubcategory -> !faqSubcategory.isHidden())
                    .collect(Collectors.toList());
        } else {
            // for logged-in user
            // check if user id is valid
            if(!userRepository.existsById(context.getUserId())) {
                throw new NotFoundException("Context user id not found");
            }
            // get all subcategories
            log.info("Getting all subcategories");
            subcategories = categoryRepository.findAllByParentId(parentId);
        }
        // map & return subcategories response
        return subcategories
                .stream()
                .map(subcategory -> mapper.map(subcategory, CategoryResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Category addCategory(AddCategoryRequest addCategoryRequest, String parentId) throws NotFoundException {
        // for new category
        if(parentId == null) {
            return categoryRepository.save(mapper.map(addCategoryRequest, Category.class));
        }
        // for new subcategory
        return categoryRepository
                .findById(parentId)
                .map(category -> mapper.map(addCategoryRequest, Category.class))
                .map(subcategory -> {
                    subcategory.setParentId(parentId);
                    log.info(subcategory.toString());
                    return categoryRepository.save(subcategory);
                })
                .orElseThrow(() -> new NotFoundException("Parent not found"));
    }

    @Override
    public Category editCategory(EditCategoryRequest editCategoryRequest, String categoryId) throws NotFoundException {
        return categoryRepository
                .findById(categoryId)
                .map(category -> {
                    copyProperties(editCategoryRequest, category, getNullPropertyNames(editCategoryRequest));
                    return categoryRepository.save(category);
                })
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }

    @Override
    public void deleteCategory(String categoryId) throws NotFoundException {
        // find category/subcategory
        Category faqCategory = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category/subcategory not found"));

        // in case of category
        // delete subcategories & faqs
        categoryRepository
                .findAllByParentId(categoryId)
                .forEach(subCategory -> {
                    faqRepository.deleteAllByParentId(subCategory.getId());
                    categoryRepository.delete(subCategory);
                });
        // delete products
        productRepository.deleteAllByCategoryId(categoryId);

        // in case of subcategory
        // delete faqs (in case of subcategory)
        faqRepository.deleteAllByParentId(categoryId);

        // delete category/subcategory finally
        categoryRepository.delete(faqCategory);
    }

}
