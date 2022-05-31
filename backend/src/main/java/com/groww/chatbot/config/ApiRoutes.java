package com.groww.chatbot.config;

/**
 * Endpoints for various API routes
 */
public class ApiRoutes {

    // UserController APIs

    // login API
    public static final String LOGIN_URL = "/api/login";
    // register API
    public static final String REGISTER_URL = "/api/register";
    // get user account details
    public static final String GET_USER_URL = "/api/user/account";

    // ProductController APIs

    // get products by category API
    public static final String GET_PRODUCTS_URL = "/api/products/categories/{categoryId}";
    // get product by id
    public static final String GET_PRODUCT_URL = "/api/products/{productId}";

    // OrderController APIs

    // get orders
    public static final String GET_ORDERS_URL = "/api/user/orders";

    // FaqController APIs

    // get FAQ categories (or subcategories)
    public static final String GET_FAQ_CATEGORIES_URL = "/api/faq-categories";
    // get FAQs
    public static final String GET_FAQS_URL = "/api/faqs";

    // Whitelisted APIs
    public static final String[] WHITELISTED_URLS = {
            "/",
            LOGIN_URL,
            REGISTER_URL,
            GET_FAQS_URL,
            GET_PRODUCT_URL,
            GET_PRODUCTS_URL,
            GET_FAQ_CATEGORIES_URL
    };

}
