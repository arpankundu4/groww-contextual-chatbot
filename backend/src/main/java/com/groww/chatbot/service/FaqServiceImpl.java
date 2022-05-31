package com.groww.chatbot.service;

import com.groww.chatbot.exception.AccessDeniedException;
import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.exchanges.Context;
import com.groww.chatbot.exchanges.FaqCategoryResponse;
import com.groww.chatbot.exchanges.FaqResponse;
import com.groww.chatbot.model.Faq;
import com.groww.chatbot.model.FaqCategory;
import com.groww.chatbot.model.Order;
import com.groww.chatbot.model.User;
import com.groww.chatbot.repository.*;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class implementation for FAQ
 * overrides methods of interface
 */

// TODO: refactor category & subcategory methods into one

@Log4j2
@Service
public class FaqServiceImpl implements FaqService {

    @Autowired
    private FaqRepository faqRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FaqCategoryRepository faqCategoryRepository;

    private final ModelMapper mapper = new ModelMapper();

    @Override
    public List<FaqCategoryResponse> getFaqCategories(Context context) throws NotFoundException {
        List<FaqCategory> faqCategories;
        // for non logged-in user
        if(context.getUserId() == null) {
            // get only public categories
            log.info("Getting public categories");
            faqCategories = faqCategoryRepository
                    .findAllByParentId(null)
                    .stream()
                    .filter(faqCategory -> !faqCategory.isPrivate())
                    .collect(Collectors.toList());
        } else {
            // for logged-in user
            // check if user id is valid
            if(!userRepository.existsById(context.getUserId())) {
                throw new NotFoundException("Context user id not found");
            }
            // get all categories
            log.info("Getting all categories");
            faqCategories = faqCategoryRepository.findAllByParentId(null);
        }
        // map & return FAQ categories response
        return faqCategories
                .stream()
                .map(faqCategory -> mapper.map(faqCategory, FaqCategoryResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FaqCategoryResponse> getFaqSubcategories(String parentId, Context context)
                                                         throws NotFoundException, AccessDeniedException {
        List<FaqCategory> faqSubcategories;
        // check if parent exists
        FaqCategory parent = faqCategoryRepository
                .findById(parentId)
                .orElseThrow(() -> new NotFoundException("Parent not found"));
        // for non-logged in user
        if(context.getUserId() == null) {
            if(parent.isPrivate()) {
                throw new AccessDeniedException("Access denied to private parent");
            }
            // get only public subcategories
            log.info("Getting public subcategories");
            faqSubcategories = faqCategoryRepository
                    .findAllByParentId(parentId)
                    .stream()
                    .filter(faqSubcategory -> !faqSubcategory.isPrivate())
                    .collect(Collectors.toList());
        } else {
            // for logged-in user
            // check if user id is valid
            if(!userRepository.existsById(context.getUserId())) {
                throw new NotFoundException("Context user id not found");
            }
            // get all subcategories
            log.info("Getting all subcategories");
            faqSubcategories = faqCategoryRepository.findAllByParentId(parentId);
        }
        // map & return FAQ subcategories response
        return faqSubcategories
                .stream()
                .map(faqSubcategory -> mapper.map(faqSubcategory, FaqCategoryResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FaqResponse> getFaqsBySubcategory(String parentId, Context context)
                                          throws NotFoundException, AccessDeniedException {
        List<Faq> faqs;
        // check if parent exists
        FaqCategory faqSubcategory = faqCategoryRepository
                .findById(parentId)
                .orElseThrow(() -> new NotFoundException("Subcategory not found"));
        // for non logged-in user
        if(context.getUserId() == null) {
            if(faqSubcategory.isPrivate()) {
                throw new AccessDeniedException("Access denied to private subcategory");
            }
            // get only public FAQs
            log.info("Getting public FAQs");
            faqs = faqRepository
                    .findAllByParentId(parentId)
                    .stream()
                    .filter(faq -> !faq.isPrivate())
                    .collect(Collectors.toList());
        } else {
            // for logged-in user
            // check if user id is valid
            if(!userRepository.existsById(context.getUserId())) {
                throw new NotFoundException("Context user id not found");
            }
            // get all FAQs
            log.info("Getting all FAQs");
            faqs = faqRepository.findAllByParentId(parentId);
        }
        // map & return FAQs response
        return faqs
                .stream()
                .map(faq -> mapper.map(faq, FaqResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FaqResponse> getProductFaqs(Context context) throws NotFoundException {
        List<Faq> faqs;
        String userId = context.getUserId();
        String productId = context.getProductId();
        // check if product doesn't exist
        if(!productRepository.existsById(productId)) {
            throw new NotFoundException("Product not found");
        }
        // add generic product FAQs
        log.info("Adding generic product FAQs");
        faqs = faqRepository.findAllByParentId(productId);
        // for logged-in user
        if(userId != null) {
            // check if user id is valid
            User user = userRepository
                    .findById(userId)
                    .orElseThrow(() -> new NotFoundException("Context user id not found"));
            // add user-specific FAQs
            log.info("Adding user's order history specific FAQs");
            orderRepository
                    .findAllById(user.getOrderIds())
                    .stream()
                    .filter(order -> Objects.equals(order.getProduct().getId(), productId))
                    .map(Order::getStatus)
                    .collect(Collectors.toSet())
                    .forEach(status -> {
                        // for each status add relevant faqs
                        faqCategoryRepository
                                .findByTitle(status)
                                .ifPresent(faqCategory ->
                                 faqs.addAll(faqRepository.findAllByParentId(faqCategory.getId())));
                    });
        }
        // map & return FAQs response
        return faqs
                .stream()
                .map(faq -> mapper.map(faq, FaqResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FaqResponse> getOrderFaqs(Context context) throws NotFoundException {
        // check of order exists
        Order order = orderRepository
                .findById(context.getOrderId())
                .orElseThrow(() -> new NotFoundException("Order not found"));
        // get order status specific FAQs
        log.info("Getting order status specific FAQs");
        List<Faq> faqs = new ArrayList<>();
        faqCategoryRepository
                .findByTitle(order.getStatus())
                .ifPresent(faqCategory ->
                 faqs.addAll(faqRepository.findAllByParentId(faqCategory.getId())));
        // map & return FAQs response
        return faqs
                .stream()
                .map(faq -> mapper.map(faq, FaqResponse.class))
                .collect(Collectors.toList());
    }

}
