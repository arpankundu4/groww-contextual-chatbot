package com.groww.chatbot.service;

import com.groww.chatbot.exception.AccessDeniedException;
import com.groww.chatbot.exception.NotFoundException;
import com.groww.chatbot.exchanges.*;
import com.groww.chatbot.model.Faq;

import java.util.List;

/**
 * Service class interface for FAQ
 * shows available methods
 */
public interface FaqService {

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

    /**
     * add FAQ
     *
     * @param addFaqRequest add FAQ request body
     * @param parentId id of parent to which FAQ belongs
     * @return newly created FAQ
     * @throws NotFoundException if parent not found
     */
    public Faq addFaq(AddFaqRequest addFaqRequest, String parentId) throws NotFoundException;

    /**
     * edits FAQ
     *
     * @param editFaqRequest edit FAQ request
     * @param faqId FAQ id
     * @return updated FAQ
     * @throws NotFoundException if FAQ not found
     */
    public Faq editFaq(EditFaqRequest editFaqRequest, String faqId) throws NotFoundException;

    /**
     * delete FAQ
     *
     * @param faqId id of FAQ to be deleted
     * @throws NotFoundException if FAQ not found
     */
    public void deleteFaq(String faqId) throws NotFoundException;

}
