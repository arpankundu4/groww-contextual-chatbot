## Groww - Contextual Chatbot for Investors

RESTful backend of a contextual chatbot and website modelled on Groww. Exposes basic REST APIs of the website and the chatbot.

This project is a part of **Crio Launch (2022) Externship** with **Groww**.

> Project is currently under development. More details to be updated shortly.

### APIs

```Website```

1. User login / registration
2. Browse products
3. Place order / get user's orders
4. Get user's account details

```Chatbot```

1. Get FAQ categories / subcategories (with context)
2. Get FAQs (with context)

### Tech Stack

1. Java
2. SpringBoot
3. MongoDB
4. JUnit
5. Mockito
6. Maven
7. Postman

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

### Run Backend Server Locally

From terminal:

1. Type `cd groww-contextual-chatbot`
2. Type `cd backend`
3. Type `chmod +x server_run.sh`
4. Type `./server_run.sh`
5. Server will start on `localhost:8081`