package com.groww.chatbot.service;

import com.groww.chatbot.exception.AccessDeniedException;
import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.exchanges.AddCategoryRequest;
import com.groww.chatbot.exchanges.CategoryResponse;
import com.groww.chatbot.exchanges.Context;
import com.groww.chatbot.exchanges.EditCategoryRequest;
import com.groww.chatbot.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class interface
 * for categories/subcategories
 * shows available methods
 */

public interface CategoryService {

    /**
     * gets categories
     *
     * @param context logged-in user's context (nullable)
     * @return list of categories
     * @throws NotFoundException if context's user id not found
     */
    public List<CategoryResponse> getCategories(Context context) throws NotFoundException;

    /**
     * gets subcategories
     * for the specified parent category
     *
     * @param parentId id of parent category
     * @param context logged-in user's context (nullable)
     * @return list of subcategories
     * @throws NotFoundException if parent id or context's user id not found
     * @throws AccessDeniedException if access requested to subcategories of private parent without valid user id
     */
    public List<CategoryResponse> getSubcategories(String parentId, Context context) throws NotFoundException,
                                                                                            AccessDeniedException;

    /**
     * add category/subcategory
     *
     * @param addCategoryRequest category/subcategory request body
     * @param parentId id of parent category
     * @return newly created category/subcategory
     * @throws NotFoundException if specified parent id not found
     */
    public Category addCategory(AddCategoryRequest addCategoryRequest, String parentId) throws NotFoundException;

    /**
     * edits category/subcategory
     *
     * @param editCategoryRequest edit category/subcategory request body
     * @param categoryId category/subcategory id
     * @return updated category/subcategory
     * @throws NotFoundException if specified category/subcategory not found
     */
    public Category editCategory(EditCategoryRequest editCategoryRequest, String categoryId) throws NotFoundException;

    /**
     * deletes category/subcategory
     *
     * @param categoryId id of category/subcategory to be deleted
     * @throws NotFoundException if specified category/subcategory not found
     */
    public void deleteCategory(String categoryId) throws NotFoundException;

}
