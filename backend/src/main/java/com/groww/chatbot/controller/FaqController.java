package com.groww.chatbot.controller;

import com.groww.chatbot.exception.AccessDeniedException;
import com.groww.chatbot.exception.BadRequestException;
import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.exchanges.Context;
import com.groww.chatbot.service.FaqService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.groww.chatbot.config.ApiRoutes.*;

/**
 * controller class
 * for FAQ related APIs
 *
 * refer to ApiRoutes in com.groww.chatbot.config
 * for all API URLs
 */

@Log4j2
@RestController
public class FaqController {

    @Autowired
    private FaqService faqService;

    @PostMapping(GET_FAQ_CATEGORIES_URL)
    public ResponseEntity<?> getFaqCategories(@RequestBody Context context,
                                              @RequestParam(required = false) String parentId) {
        try {
            if(parentId == null) {
                log.info("Get categories request");
                return ResponseEntity.ok(faqService.getFaqCategories(context));
            }
            log.info("Get subcategories request");
            return ResponseEntity.ok(faqService.getFaqSubcategories(parentId, context));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(GET_FAQS_URL)
    public ResponseEntity<?> getFaqs(@RequestBody Context context,
                                     @RequestParam(required = false) String parentId) {
        try {
            if(parentId != null) {
                return ResponseEntity.ok(faqService.getFaqsBySubcategory(parentId, context));
            }
            if(context.getProductId() != null) {
                return ResponseEntity.ok(faqService.getProductFaqs(context));
            }
            if(context.getOrderId() != null) {
                return ResponseEntity.ok(faqService.getOrderFaqs(context));
            }
            throw new BadRequestException("Invalid Request");
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException | BadRequestException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
