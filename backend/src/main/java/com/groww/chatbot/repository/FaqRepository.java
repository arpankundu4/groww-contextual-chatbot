package com.groww.chatbot.repository;

import com.groww.chatbot.model.Faq;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * repository class for FAQs data
 */
public interface FaqRepository extends MongoRepository<Faq, String> {

    // finds FAQs
    List<Faq> findAllByParentId(String parentId);

}
