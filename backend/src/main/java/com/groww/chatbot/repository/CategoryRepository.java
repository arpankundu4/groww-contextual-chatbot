package com.groww.chatbot.repository;

import com.groww.chatbot.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * repository class for categories data
 */
public interface CategoryRepository extends MongoRepository<Category, String> {

    // finds categories/subcategories
    List<Category> findAllByParentId(String parentId);

    // finds category by title
    Optional<Category> findByTitle(String title);

}
