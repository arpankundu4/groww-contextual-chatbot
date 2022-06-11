<div align="center">
<h1 align="center">Groww</h1>

<a href = "https://groww-chatbot.herokuapp.com/swagger-ui/">OpenAPI Docs</a> | <a href = "https://groww-chatbot.herokuapp.com">Live Backend</a>

<h2 align ="center">Contextual Chatbot for Investors</h2>

<p align="center">
   <a href="https://github.com/arpankundu4/groww-contextual-chatbot/blob/master/LICENSE">
      <img src="https://img.shields.io/badge/License-MIT-green.svg" />
   </a>
</p>
</div>

<div align="center">
<img width="600" alt="groww-contextual-chatbot" src="https://user-images.githubusercontent.com/104189687/171997821-0873d0be-c3ef-4486-8bd7-3fd1d39a5917.png">
</div>

This project is a part of **[Crio Launch (2022) Externship](https://www.crio.do/launch/)** with **[Groww](https://groww.in/)**.

```OpenAPI Docs```
**https://groww-chatbot.herokuapp.com/swagger-ui/**

```Live Backend```
**https://groww-chatbot.herokuapp.com**

### Table of Contents

- [About](#about)
- [Features](#features)
- [System Design](#system-design)
- [Contextual Responses](#contextual-responses)
- [REST APIs](#rest-apis)
- [API Docs](#api-docs)
- [Tech Stack](#tech-stack)
- [Setup](#setup)
- [Run](#run)

### About

This application consists of a contextual chatbot on top of webpages modelled on [Groww](https://groww.in/).
The chatbot displays FAQs and information based on an investor’s current context on the website,
by making use of the user's context consisting of user's id, category of choice (Stocks, Mutual Funds, Gold, FDs),
web page selection (product or order pages), and other information to help the investor have a smooth & interactive
browsing experience.

<div align="center">
<img width="500" alt="stocks-page" src="https://user-images.githubusercontent.com/104189687/173176928-ef6260cb-dff4-4b5e-a28b-da979105f8c7.png">
</div>

<br>

<div align="center">
<img width="500" alt="stocks-product-page" src="https://user-images.githubusercontent.com/104189687/173176992-30777933-b619-405b-aad3-5608fca7c343.png">
</div>

### Features

```User```

- User login & register
- Browse categories & products
- View product details
- Place order for a product
- View user's orders
- Cancel an order
- View / edit user's account details

```Chatbot```

- Click to activate on any webpage
- Get relevant set of responses based on user's context (if logged in)
- Get categories (e.g. Stocks, FDs, etc.) based on user context
- Get subcategories based on categories or webpage selection (e.g. IPO, Investments, Charges, etc.)
- Get FAQs based on subcategories or webpage selection (e.g. product specific FAQs on product pages)

```Admin```

- Add / edit / delete categories
- Add / edit / delete subcategories
- Add / edit / delete FAQs
- Add / edit / delete products

### System Design

- Website Flows & APIs

<div align="center">
<img width="1000" alt="website-flows" src="https://user-images.githubusercontent.com/104189687/173180240-33835028-1f2b-4e2c-9517-6f75753d7ef3.png">
</div>

- Chatbot Flows

<div align="center">
<img width="500" alt="groww-chatbot-flows" src="https://user-images.githubusercontent.com/104189687/173180848-0e829c58-94e5-4d9a-9dcc-5bae26f77ed3.png">
</div>

<br>

- Database Structure

<div align="center">
<img width="1000" alt="db-structure" src="https://user-images.githubusercontent.com/104189687/173180997-9e020781-8acf-4721-81f3-94ed6ddb1701.png">
</div>

### Contextual Responses

- As an example, let's take a non-KYC user whose ```My Account``` looks like this:

<div align="center">
<img width="500" alt="non-kyc-account" src="https://user-images.githubusercontent.com/104189687/173177254-24de22da-873d-409d-bd2c-7273ed010f4d.png">
</div>

<br>

- The chatbot on a product page will show user's KYC status related FAQs in addition to product FAQs

<div align="center">
<img width="400" alt="non-kyc-chatbot-product-faqs" src="https://user-images.githubusercontent.com/104189687/173177410-800bb9d6-882b-4771-bec7-e18d113e2302.png">
</div>

- The chatbot on ```My Orders``` page will show FAQs relevant to the user's orders

<div align="center">
<img width="800" alt="failed-order-chatbot-order-faqs" src="https://user-images.githubusercontent.com/104189687/173177565-34bd8423-3cea-43f3-a510-d18cd4528165.png">
</div>

- The chatbot on product pages also shows FAQs relevant to order status, if the user has any order history for that product

<div align="center">
<img width="300" alt="product-page-chatbot-order-faqs" src="https://user-images.githubusercontent.com/104189687/173177676-35f8fd97-dd5f-435e-bce0-ddf139bb12c0.png">
</div>

### REST APIs

#### Webpages

1. User login / registration
   <br>```POST /api/login```
   <br> ```POST /api/register```

2. Browse products
   <br>```GET /api/products/categories/{categoryId}```
   <br>```GET /api/products/{productId}```

3. Place order / get user's orders*
   <br>```POST /api/user/place-order```
   <br>```GET /api/user/orders```

4. Get / edit user's account details*
   <br>```GET /api/user/account```
   <br>```PATCH /api/user/account/edit```

*Secure endpoints which need authorization for access

#### Chatbot

1. Get FAQ categories / subcategories (with context)
   <br>```POST /api/categories```

2. Get FAQs (with context)
   <br>```POST /api/faqs```

#### Context

```json
   {
      "userId": "string",
      "productId": "string",
      "orderId": "string"
   }
```

### API Docs

<div align="center">
<img width="500" alt="open-api-docs" src="https://user-images.githubusercontent.com/104189687/173178588-d36b55a4-5fe7-499f-9b02-6b74aa892f9e.png">
</div>

> Try out the APIs with complete documentation at the [OpenAPI Docs](https://groww-chatbot.herokuapp.com/swagger-ui/).

### Tech Stack

1. Java (JDK 11)
2. SpringBoot 2.6.7
3. MongoDB 4.2
4. JUnit 5
5. Mockito
6. Maven
7. OpenAPI (Swagger v3)
8. Postman

### Setup

From terminal:

1. Type `cd groww-contextual-chatbot`
2. Type `cd backend`
3. Type `chmod +x setup.sh`
4. Type `./setup.sh`

Restore mongo-dump:

1. Start MongoDB on `localhost:27017`
2. Type `cd groww-contextual-chatbot`
3. Type `cd backend`
4. Type `mongorestore mongodb://localhost:27017  dump/`

### Run

#### To run backend server locally

From terminal:

1. Type `cd groww-contextual-chatbot`
2. Type `cd backend`
3. Type `chmod +x server_run.sh`
4. Type `./server_run.sh`
5. Server will start on `localhost:8081`