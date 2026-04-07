# Transaction Aggregator Service 🚀

A high-performance Spring Boot microservice that aggregates transaction data from multiple remote sources simultaneously using **Asynchronous Processing** and **In-Memory Caching**.

## ✨ Key Features
- **Parallel Fetching**: Uses `CompletableFuture` and `@Async` to call multiple remote servers at once, cutting total response time by 50%.
- **Robust Retry Mechanism**: Automatically retries failed requests (up to 5 times) specifically for `503 Service Unavailable` and `529 Too Many Requests` errors.
- **Smart Caching**: Implements Spring Cache (`@Cacheable`) to serve subsequent requests for the same account instantly from RAM.
- **Data Consolidation**: Merges JSON streams from multiple sources and sorts them by timestamp (newest first).

## 🛠 Tech Stack
- **Java 17+**
- **Spring Boot 3.x**
- **Spring Web** (RestTemplate)
- **Spring Cache**
- **Gradle** (Build Tool)

## 🚀 Getting Started

### 1. Prerequisites
- JDK 17 or higher
- IntelliJ IDEA (Recommended)

### 2. Setup Remote Servers (Simulation)
This project is designed to aggregate data from two servers. To test locally, you can run the application three times on different ports:
1. **Aggregator (Main):** Port `8080` (Default)
2. **Remote Server 1:** Port `8888` (VM Option: `-Dserver.port=8888`)
3. **Remote Server 2:** Port `8889` (VM Option: `-Dserver.port=8889`)

### 3. Usage
Once all instances are running, call the aggregator:
```bash
GET http://localhost:8080/aggregate?account=02248
