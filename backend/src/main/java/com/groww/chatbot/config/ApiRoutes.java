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
    // get user account details API
    public static final String GET_USER_URL = "/api/user/account";
    // edit user account details API
    public static final String EDIT_USER_URL = "/api/user/account/edit";

    // ProductController APIs

    // get products by category API
    public static final String GET_PRODUCTS_URL = "/api/products/categories/{categoryId}";
    // get product by id API
    public static final String GET_PRODUCT_URL = "/api/products/{productId}";

    // OrderController APIs

    // get orders API
    public static final String GET_ORDERS_URL = "/api/user/orders";
    // place order API
    public static final String PLACE_ORDER_URL = "/api/user/place-order";

    // FaqController APIs

    // get categories (or subcategories) API
    public static final String GET_CATEGORIES_URL = "/api/categories";
    // get FAQs API
    public static final String GET_FAQS_URL = "/api/faqs";

    // AdminController APIs

    // add category (or subcategory) API
    public static final String ADD_CATEGORY_URL = "/api/admin/category/add";
    // edit category (or subcategory) API
    public static final String EDIT_CATEGORY_URL = "/api/admin/category/edit/{categoryId}";
    // delete category (or subcategory) API
    public static final String DELETE_CATEGORY_URL = "/api/admin/category/delete/{categoryId}";
    // add FAQ API
    public static final String ADD_FAQ_URL = "/api/admin/faq/add/{parentId}";
    // edit FAQ API
    public static final String EDIT_FAQ_URL = "/api/admin/faq/edit/{faqId}";
    // delete FAQ API
    public static final String DELETE_FAQ_URL = "/api/admin/faq/delete/{faqId}";
    // add product API
    public static final String ADD_PRODUCT_URL = "/api/admin/product/add/{categoryId}";
    // edit product API
    public static final String EDIT_PRODUCT_URL = "/api/admin/product/edit/{productId}";
    // delete product API
    public static final String DELETE_PRODUCT_URL = "/api/admin/product/delete/{productId}";

    // Whitelisted APIs
    public static final String[] WHITELISTED_URLS = {
            "/",
            LOGIN_URL,
            REGISTER_URL,
            GET_FAQS_URL,
            GET_PRODUCT_URL,
            GET_PRODUCTS_URL,
            GET_CATEGORIES_URL,
            // Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/openapi.yaml"
    };

    // Admin APIs
    public static final String ADMIN_URLS = "/api/admin/**";

}
