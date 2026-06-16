# Smart Order Routing

A Spring Boot web application for simulated stock trading with **Smart Order Routing (SORT)** across regional exchanges. Users register by region, browse stocks, place buy and sell orders, and the system routes trades to the appropriate exchange while matching pending orders and tracking wallet balances.

## Features

- **User accounts** — Register, log in, and manage a wallet balance by region (NA, EMEA, APAC)
- **Stock dashboard** — View available stocks and exchange availability per region
- **Buy & sell orders** — Place orders routed to the exchange for the user's region
- **Order matching** — Automatically match compatible buy/sell orders and update balances
- **Pending orders** — View, cancel, and track order status (Pending, Fulfilled, Partially Fulfilled, Cancelled)
- **Trade history** — Review completed and past orders
- **Smart Order Routing** — Region-specific exchange lookup and lowest-price stock discovery across exchanges

## Tech Stack

| Layer | Technology |
|-------|------------|
| Backend | Java 11, Spring Boot 2.5.5 |
| Web | Spring MVC, Thymeleaf |
| Security | Spring Security (CSRF disabled; form auth partially configured) |
| Persistence | Spring Data JPA, Hibernate |
| Database | MySQL (default) or H2 (local dev profile) |
| Build | Maven (wrapper included) |

## Architecture

```
┌─────────────────────────────────────────────────────────┐
│                   Thymeleaf UI                          │
│  login · register · dashboard · buy/sell · wallet       │
└────────────────────────┬────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────┐
│                    Controllers                          │
│  UserController · OrderController · StockController     │
│  ExchangeController · StockExchangeController           │
└────────────────────────┬────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────┐
│                     Services                            │
│  OrderService · ExchangeService · StockExchangeService  │
│  SORTServiceNA · SORTServiceEMEA · SORTServiceAPAC      │
└────────────────────────┬────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────┐
│              JPA Repositories / MySQL or H2             │
└─────────────────────────────────────────────────────────┘
```

### Smart Order Routing

Orders are routed based on the user's region:

| Region | Service | Responsibility |
|--------|---------|----------------|
| **NA** | `SORTServiceNA` | North America exchanges and regional pending sale orders |
| **EMEA** | `SORTServiceEMEA` | Europe, Middle East, and Africa exchanges |
| **APAC** | `SORTServiceAPAC` | Asia-Pacific exchanges |

`ExchangeService.getExchangeIdByRegion()` selects the target exchange when a user places an order. `StockExchangeService.findLowestStockPrice()` finds the best available price for a stock across exchanges.

### Core Entities

- **User** — Trader with region, wallet balance, and holdings
- **Stock** — Tradable security (symbol, price)
- **Exchange** — Regional marketplace with fee ladder
- **OrderBook** — Order book linked to an exchange
- **StockExchange** — Stock listing on a specific exchange (price, available shares)
- **Order** — Buy or sell order with status and timestamps
- **UserStock** — User's stock holdings in their wallet

## Project Structure

```
Smart-Order-Routing/
└── SpringSmartOrderRouting/
    ├── pom.xml
    ├── mvnw / mvnw.cmd
    └── src/
        ├── main/
        │   ├── java/com/ab/
        │   │   ├── SpringSmartOrderRoutingApplication.java
        │   │   ├── configs/          # Security configuration
        │   │   ├── controllers/      # MVC controllers
        │   │   ├── dto/              # Data transfer objects
        │   │   ├── entities/         # JPA entities
        │   │   ├── repositories/     # Spring Data repositories
        │   │   └── services/         # Business logic + SORT services
        │   └── resources/
        │       ├── application.properties      # MySQL config (default)
        │       ├── application-h2.properties   # In-memory H2 config (dev)
        │       └── templates/                  # Thymeleaf HTML views
        └── test/java/com/ab/                   # Unit tests
```

## Prerequisites

- **Java 11** or later
- **Maven** — not required; the project includes `mvnw` / `mvnw.cmd`
- **MySQL 8** — only if running with the default profile (see below)

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/RanulKumarasinghe/Smart-Order-Routing.git
cd Smart-Order-Routing/SpringSmartOrderRouting
```

### 2. Choose a database profile

#### Option A — H2 in-memory (quickest for local development)

No external database required. Schema is created automatically on startup.

**Windows (PowerShell):**

```powershell
$env:JAVA_HOME = "C:\Program Files\Microsoft\jdk-11.0.31.11-hotspot"  # adjust if needed
.\mvnw.cmd "-Dmaven.test.skip=true" spring-boot:run "-Dspring-boot.run.arguments=--spring.profiles.active=h2"
```

**macOS / Linux:**

```bash
./mvnw -Dmaven.test.skip=true spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=h2
```

H2 console (optional): http://localhost:8080/h2-console  
JDBC URL: `jdbc:h2:mem:trading_db`

#### Option B — MySQL (production-style)

1. Install and start MySQL.
2. Create the database:

   ```sql
   CREATE DATABASE trading_db;
   ```

3. Update credentials in `src/main/resources/application.properties` if needed:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost/trading_db
   spring.datasource.username=root
   spring.datasource.password=
   ```

4. Run the application:

   ```bash
   ./mvnw -Dmaven.test.skip=true spring-boot:run
   ```

### 3. Open the app

The server starts on **http://localhost:8080** by default.

| Page | URL |
|------|-----|
| Login | http://localhost:8080/login |
| Register | http://localhost:8080/register |
| Dashboard | http://localhost:8080/dashboard |

The database starts empty. Register a new account, choose a region (NA, EMEA, or APAC), and set an initial wallet balance before placing trades.

## Usage

1. **Register** at `/register` with name, email, password, age, region, and starting balance.
2. **Log in** at `/login` to reach the dashboard.
3. **Browse stocks** on the dashboard — availability is shown per exchange.
4. **Buy** — Select a stock, enter a quantity, confirm the order.
5. **Sell** — Open your wallet, choose holdings, and place a sell order.
6. **Manage orders** — View pending orders, cancel if needed, and check trade history.

## Key Endpoints

### Web pages

| Method | Path | Description |
|--------|------|-------------|
| GET | `/login` | Login page |
| POST | `/login` | Authenticate user |
| GET | `/register` | Registration page |
| POST | `/register` | Create account |
| GET | `/dashboard` | Stock dashboard |
| GET | `/wallet` | User holdings and balance |
| GET | `/buyOrder/{id}/stock` | Buy order form |
| GET | `/pendingOrders` | Pending orders list |
| GET | `/history` | Trade history |
| GET | `/logout` | End session |

### REST API (selected)

| Method | Path | Description |
|--------|------|-------------|
| GET | `/stocks` | List all stocks |
| GET | `/exchanges/{exchangeId}` | Exchange details |
| GET | `/exchangewithlowestfees` | Exchange with lowest fees |
| GET | `/LowestStockPrice/{stockId}` | Lowest price for a stock |
| GET | `/userorders/{userId}` | Orders for a user |
| GET | `/tradehistory/{userId}` | Trade history for a user |

## Running Tests

```bash
./mvnw test
```

> **Note:** Test classes use JUnit 4 annotations (`org.junit.Test`). If compilation fails, run with `-Dmaven.test.skip=true` or migrate tests to JUnit 5.

## Configuration Reference

| File | Purpose |
|------|---------|
| `application.properties` | Default MySQL connection |
| `application-h2.properties` | In-memory H2 profile for local dev |

Common Spring properties:

```properties
spring.jpa.hibernate.ddl-auto=update   # Auto-create/update schema
spring.jpa.show-sql=true               # Log SQL statements
server.port=8080                       # HTTP port (default)
```

## License

This project is provided as-is for educational and demonstration purposes.

## Author

[RanulKumarasinghe](https://github.com/RanulKumarasinghe)
