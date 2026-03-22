# Insurtech Rate Aggregator 🛡️💸

A Spring Boot 3 application designed for insurance policy calculation with real-time exchange rate integration via the **NBP (National Bank of Poland) API**.

## 🚀 Features

* **Policy Calculation:** Automatically fetches current exchange rates (A-table) from NBP to calculate policy values in PLN.
* **REST API:** Clean and documented endpoints for policy management.
* **Persistence:** Full integration with **PostgreSQL** for reliable data storage.
* **Security:** Configured Spring Security layer with custom SSL certificate handling for secure NBP API communication.
* **Quality Assurance:** Comprehensive test suite including Integration Tests with `MockMvc` and `MockRestServiceServer`.
* **Dockerized:** Fully portable environment using **Docker & Docker Compose**.

## 🛠️ Tech Stack

* **Java 21** & **Spring Boot 3.5.x**
* **Database:** PostgreSQL 15
* **Mapping:** MapStruct (for DTO/Entity separation)
* **Tools:** Lombok, Spring Data JPA, RestClient
* **DevOps:** Docker, Docker Compose

## 🏁 How to Run

You don't need Java or Maven installed on your machine. All you need is **Docker**.

1. **Clone the repository:**
   ```bash
   git clone https://github.com/KolegaSowa/insurtech-rate-aggregator.git
   ```
2. Launch the entire stack:
   ```bash
   cd insurtech-rate-aggregator
   docker compose up --build
   ```
4. The application will be available at http://localhost:8080

## 📖 API Usage

### Create a New Policy
**POST** `/api/policies`

**Request Body:**
```json
{
  "clientName": "Jan Kowalski",
  "amount": 150.00,
  "currencyCode": "EUR"
}
```
**Response (Example values for EUR on 2026-03-22):**
```json
{
  "id": 1,
  "clientName": "Jan Kowalski",
  "amount": 150.00,
  "currencyCode": "EUR",
  "exchangeRate": 4.2768,
  "valueAfterRate": 641.52,
  "createdAt": "2026-03-22T17:05:36Z"
}
```
## 🧪 Testing
To run tests locally (outside of Docker): 
```bash
./mvnw test
```
