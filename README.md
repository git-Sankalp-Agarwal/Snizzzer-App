# 🐦 Snizzer – Your Thoughts, Unchained
## 📝 Project Description
Snizzer, a microblogging and social networking website, allows users to post short text messages, photos, and videos. A social media backend built using a microservices architecture with Spring Boot, Kafka, Docker, and Kubernetes. It features scalable, loosely coupled services for user management, tweeting, following, notifications, messaging, and image uploading. The system is secured using JWT authentication and supports inter-service communication via Feign clients and Apache Kafka.

## 🚀 Features
**🔐 JWT Authentication**: Secure login and signup with token-based access control.

**👤 User Service**: Manage user registration, profiles, and authentication.

**🐦 Tweet Service**: Create, delete, and retweet functionality with Kafka-based event streaming.

**➕ Follow Service**: Follow/unfollow other users and fetch followers/following lists.

**📢 Notification Service**: Asynchronous notification system using Kafka for mentions, follows, and retweets.

**💬 Message Service**: Direct messaging system between users with eventual consistency.

**🖼️ Uploader Service**: Upload profile pictures or tweet images, handled through an isolated file service.

**📡 API Gateway**: Unified entry point for all external API calls with route forwarding.

**🔍 Discovery Server (Eureka)**: Service registry for locating microservices dynamically.

**⚙️ Config Server**: Centralized configuration management for all services.

**📦 Docker & Kubernetes**: Containerized deployment and orchestration with horizontal scaling support.

## 🛠️ Tech Stack
**📦 Core Technologies**
Java 17

Spring Boot & Spring Cloud

Microservices Architecture

JWT Authentication

**🧩 Microservices Tools**
Feign Clients – Synchronous inter-service communication

Apache Kafka – Asynchronous event-driven messaging

Eureka Discovery Server – Service registration & discovery

Spring Cloud Gateway – API routing and gateway management

Spring Cloud Config Server – Centralized configuration for all services

**🐳 DevOps & Infrastructure**
Docker – Containerization of all services

Kubernetes – Container orchestration and scaling

**📁 Additional Tools**
Lombok – Boilerplate code reduction

MapStruct – DTO mapping

Swagger / OpenAPI – API documentation and testing


## 🔄 Workflow Overview
Here’s a high-level interaction flow for core user actions within the system:

**📌 1. User Registration & Login**
User sends credentials to API Gateway, which routes the request to the User Service.

Upon successful registration or login, a JWT token is generated and returned.

All future requests include this token for authentication and authorization via Spring Security.

**🐦 2. Post a Tweet**
Authenticated user sends a tweet request via API Gateway → forwarded to Tweet Service.

Tweet Service saves the tweet and publishes a Kafka event.

Notification Service consumes the Kafka event and notifies followers.

If an image is attached, Uploader Service handles the file upload before tweet persistence.

**🔁 3. Retweet**
Similar to posting a tweet, but the request includes the original tweet ID.

A new retweet record is created in Tweet Service.

A Kafka event is sent to Notification Service for follower updates.

**➕ 4. Follow/Unfollow a User**
Request routed to Follow Service via Gateway.

Follow relationships are saved, and a Kafka event is emitted.

Notification Service informs the followed user asynchronously.

**📨 5. Send a Direct Message**
Message is sent from User A to User B through Message Service.

Messages are stored and indexed per user pair.

Kafka may optionally be used for real-time delivery or analytics.

**🖼️ 6. Upload Profile or Tweet Images**
Images are uploaded via Uploader Service using multipart form data.

Uploader returns a file URL, which is attached to user profile or tweet metadata.

**🧠 Service Discovery & Configuration**
All services register with Eureka Discovery Server on startup.

Configuration is loaded from a centralized Config Server (via Git or native file storage).

Inter-service calls use Feign Clients, leveraging Eureka for dynamic resolution.


