package com.groww.chatbot.service;

import com.groww.chatbot.exception.AccessDeniedException;
import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.exchanges.Context;
import com.groww.chatbot.exchanges.FaqCategoryResponse;
import com.groww.chatbot.exchanges.FaqResponse;
import com.groww.chatbot.model.Faq;

import java.util.List;

/**
 * Service class interface for FAQ
 * shows available methods
 */
public interface FaqService {

    /**
     * gets FAQ categories
     *
     * @param context logged-in user's context (nullable)
     * @return list of FAQ categories
     * @throws NotFoundException if context's user id not found
     */
    public List<FaqCategoryResponse> getFaqCategories(Context context) throws NotFoundException;

    /**
     * gets FAQ subcategories
     * for the specified parent category
     *
     * @param parentId id of parent category
     * @param context logged-in user's context (nullable)
     * @return list of FAQ subcategories
     * @throws NotFoundException if parent id or context's user id not found
     * @throws AccessDeniedException if access requested to subcategories of private parent without valid user id
     */
    public List<FaqCategoryResponse> getFaqSubcategories(String parentId, Context context) throws NotFoundException,
                                                                                                  AccessDeniedException;

    /**
     * gets FAQs by subcategory
     *
     * @param parentId id of parent subcategory
     * @param context logged-in user's context (nullable)
     * @return list of FAQs
     * @throws NotFoundException if parent id or context's user id not found
     * @throws AccessDeniedException if access requested to FAQs of private subcategory without valid user id
     */
    public List<FaqResponse> getFaqsBySubcategory(String parentId, Context context) throws NotFoundException,
                                                                                           AccessDeniedException;

    /**
     * gets product FAQs
     *
     * @param context logged-in user's user id (nullable) & product id (required)
     * @return list of FAQs
     * @throws NotFoundException if context's product id or user id not found
     */
    public List<FaqResponse> getProductFaqs(Context context) throws NotFoundException;

    /**
     * gets order FAQs
     *
     * @param context order id
     * @return list of FAQs
     * @throws NotFoundException if order not found
     */
    public List<FaqResponse> getOrderFaqs(Context context) throws NotFoundException;

}
