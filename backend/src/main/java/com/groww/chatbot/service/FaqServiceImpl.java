package com.groww.chatbot.service;

import com.groww.chatbot.exception.AccessDeniedException;
import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.exchanges.*;
import com.groww.chatbot.model.Faq;
import com.groww.chatbot.model.Category;
import com.groww.chatbot.model.Order;
import com.groww.chatbot.model.User;
import com.groww.chatbot.repository.*;
import com.groww.chatbot.util.MiscUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.groww.chatbot.util.MiscUtil.getNullPropertyNames;
import static org.springframework.beans.BeanUtils.copyProperties;

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
    private CategoryRepository categoryRepository;

    private final ModelMapper mapper = new ModelMapper();

    @Override
    public List<FaqResponse> getFaqsBySubcategory(String parentId, Context context)
            throws NotFoundException, AccessDeniedException {
        List<Faq> faqs;
        // check if parent exists
        Category subcategory = categoryRepository
                .findById(parentId)
                .orElseThrow(() -> new NotFoundException("Subcategory not found"));
        // for non logged-in user
        if(context.getUserId() == null) {
            if(subcategory.isHidden()) {
                throw new AccessDeniedException("Access denied to private subcategory");
            }
            // get only public FAQs
            log.info("Getting public FAQs");
            faqs = faqRepository
                    .findAllByParentId(parentId)
                    .stream()
                    .filter(faq -> !faq.isHidden())
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
        log.info("Getting generic product FAQs");
        faqs = faqRepository.findAllByParentId(productId);
        // for logged-in user
        if(userId != null) {
            // check if user id is valid
            User user = userRepository
                    .findById(userId)
                    .orElseThrow(() -> new NotFoundException("Context user id not found"));
            // add user-specific FAQs
            log.info("Getting user's order history specific FAQs");
            orderRepository
                    .findAllByUserId(userId)
                    .stream()
                    .filter(order -> Objects.equals(order.getProduct().getId(), productId))
                    .map(Order::getStatus)
                    .collect(Collectors.toSet())
                    .forEach(status -> {
                        // for each status add relevant faqs
                        categoryRepository
                                .findByTitle(status)
                                .ifPresent(category -> faqs.addAll(faqRepository
                                .findAllByParentId(category.getId())));
                    });
            log.info("Getting user's KYC specific FAQs");
            if(!user.isKycDone()) {
                categoryRepository
                        .findByTitle("KYC")
                        .ifPresent(category -> faqs.addAll(faqRepository
                        .findAllByParentId(category.getId())));
            }
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
        categoryRepository
                .findByTitle(order.getStatus())
                .ifPresent(category ->
                 faqs.addAll(faqRepository.findAllByParentId(category.getId())));
        // map & return FAQs response
        return faqs
                .stream()
                .map(faq -> mapper.map(faq, FaqResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Faq addFaq(AddFaqRequest addFaqRequest, String parentId) throws NotFoundException {
        if(!categoryRepository.existsById(parentId) && !productRepository.existsById(parentId)) {
            throw new NotFoundException("Parent not found");
        }
        Faq faq = mapper.map(addFaqRequest, Faq.class);
        faq.setParentId(parentId);
        return faqRepository.save(faq);
    }

    @Override
    public Faq editFaq(EditFaqRequest editFaqRequest, String faqId) throws NotFoundException {
        return faqRepository
                .findById(faqId)
                .map(faq -> {
                    copyProperties(editFaqRequest, faq, getNullPropertyNames(editFaqRequest));
                    return faqRepository.save(faq);
                })
                .orElseThrow(() -> new NotFoundException("FAQ not found"));
    }

    @Override
    public void deleteFaq(String faqId) throws NotFoundException {
        Faq faq = faqRepository
                .findById(faqId)
                .orElseThrow(() -> new NotFoundException("FAQ not found"));
        faqRepository.delete(faq);
    }

}
