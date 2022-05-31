package com.groww.chatbot.repository;

import com.groww.chatbot.model.FaqCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * repository class for FAQ categories data
 */
public interface FaqCategoryRepository extends MongoRepository<FaqCategory, String> {

    // finds categories/subcategories
    List<FaqCategory> findAllByParentId(String parentId);

    // finds category by title
    Optional<FaqCategory> findByTitle(String title);

}
