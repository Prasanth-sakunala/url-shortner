# url-shortner
# URL Shortener

A simple URL shortener service built with Spring Boot and Redis.

## Features

- Shorten long URLs to short, unique codes
- Redirect short URLs to their original long URLs
- Input validation for URLs
- Persistent storage using Redis

## Requirements

- Java 21+
- Maven
- Redis server running on `localhost:6379`

## Getting Started

### 1. Clone the repository

```sh
git clone https://github.com/yourusername/url-shortner.git
cd url-shortner
```
### 2. Start Redis

Make sure you have Redis running locally:

```sh
redis-server.exe
```
### 3. Build and run the application

```sh
./mvnw spring-boot:run
```
The application will start on http://localhost:8080.

## API Endpoints

### Shorten a URL

**POST** `/shortener`

**Request Body:**
```json
{
  "url": "https://www.example.com"
}
```

**Response:**
```
http://localhost:8080/abc123
```

### Redirect to Original URL

**GET** `/{id}`

Visit the shortened URL in your browser (e.g., `http://localhost:8080/abc123`) to be redirected to the original URL.

## Project Structure
src/main/java/com/java/urlshortner/Controller/URLController.java - REST API endpoints
src/main/java/com/java/urlshortner/Service/URLConverterService.java - URL shortening logic
src/main/java/com/java/urlshortner/Repo/URLRepository.java - Redis operations
src/main/java/com/java/urlshortner/Common/IDConverter.java - Base62 encoding/decoding
src/main/java/com/java/urlshortner/Common/URLValidator.java - URL validation

## Configuration
Edit src/main/resources/application.properties to change Redis host/port if needed.
