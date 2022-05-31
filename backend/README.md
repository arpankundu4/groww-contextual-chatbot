### Setup

From terminal:

1. Type `cd arpan-kundu-4-ME_CWOD_GROWW_CHATBOT`
2. Type `cd backend`
3. Type `chmod +x setup.sh`
4. Type `./setup.sh`

Restore mongo-dump:

1. Start MongoDB on `localhost:27017`
2. Type `cd arpan-kundu-4-ME_CWOD_GROWW_CHATBOT`
3. Type `cd backend`
4. Type `mongorestore mongodb://localhost:27017  dump/`

### Run Backend Server Locally

From terminal:

1. Type `cd arpan-kundu-4-ME_CWOD_GROWW_CHATBOT`
2. Type `cd backend`
3. Type `chmod +x server_run.sh`
4. Type `./server_run.sh`
5. Server will start on `localhost:8081`