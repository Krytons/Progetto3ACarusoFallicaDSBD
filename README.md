# Project "3A" Caruso&Fallica DSBD 2020
## Project for Distributed Systems Big Data 2020
### Made by:
- **Caruso Bartolomeo**
- **Giuseppe Fallica**

---

## 1. Aim of this project
This project aims at creating a microservice that will be used to handle payments for an e-commerce distributed application.

In order to develop this project we have used the following elements:
- **MySQL database:** relational database, used inside a Docker container 
- **Kafka messaging system:** open source distributed event streaming platform, used to publish into a specific topic errors and critical data information  
- **Spring framework:** an open source framework used to develop java based applications.

---

## 2. POJO classes
In order to easily generate messages and JSON data, we've used the following POJO classes:
- **Kafka Message & Kafka Value**:
  ![KafkaMessages](./diagrams/kafkamessages.svg)
  

- **Paypal Ipn**:

  

- **Return Message**:
  

    
---

## 3. Kafka & Heartbeat
![Kafka](./diagrams/kafka.svg)
---

## 4. Payment controller & Payment service
![Controller](./diagrams/controller.svg)
---

## 5. Error handling
![HttpExceptionController](./diagrams/http.svg)
---

