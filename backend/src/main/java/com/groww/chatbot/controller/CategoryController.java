package com.groww.chatbot.controller;

import com.groww.chatbot.exception.AccessDeniedException;
import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.exchanges.Context;
import com.groww.chatbot.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.groww.chatbot.config.ApiRoutes.*;

@Log4j2
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(GET_CATEGORIES_URL)
    public ResponseEntity<?> getFaqCategories(@RequestBody Context context,
                                              @RequestParam(required = false) String parentId) {
        try {
            if(parentId == null) {
                log.info("Get categories request");
                return ResponseEntity.ok(categoryService.getCategories(context));
            }
            log.info("Get subcategories request");
            return ResponseEntity.ok(categoryService.getSubcategories(parentId, context));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
