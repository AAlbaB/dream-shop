# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project
REST API e-Commerce (práctica) con Spring Boot 3.5.11, Java 17, MySQL, Spring Security + JWT (jjwt 0.12.6), ModelMapper 2.4.4, Lombok.

## Setup inicial
1. Crear la base de datos en MySQL:
   ```sql
   CREATE DATABASE dream_shops_db;
   ```
2. Configurar credenciales en `src/main/resources/application.properties`:
   ```
   spring.datasource.username=<tu-usuario>
   spring.datasource.password=<tu-password>
   auth.token.jwtSecret=<clave-HS256-base64>
   ```
3. Al arrancar, `DataInitializer` crea roles y usuarios semilla automáticamente (ver sección **Datos semilla**).

## Bash Commands
```bash
./mvnw clean compile
./mvnw spring-boot:run                        
./mvnw clean package
./mvnw test
./mvnw test -Dtest=DreamShopsApplicationTests
```

## Architecture
```
src/main/java/com/spring/dreamshop/
├── controller/    Endpoints REST (@RestController)
├── service/       Lógica de negocio; subcarpetas por dominio (cart, category, image, order, product, user)
├── repository/    Interfaces Spring Data JPA
├── model/         Entidades JPA (Product, Cart, CartItem, Order, OrderItem, User, Role, Category, Image)
├── dto/           Objetos de transferencia (salida hacia el cliente)
├── request/       Objetos de entrada (cuerpo de peticiones)
├── response/      ApiResponse (envoltorio genérico), JwtResponse
├── exceptions/    ResourceNotFoundException, AlreadyExistsException, GlobalExceptionHandler
├── security/      jwt/ (JwtUtils, JwtAuthFilter, JwtAuthEntryPoint), user/ (ShopUserDetails, ShopUserDetailsService)
├── config/        ShopConfig (SecurityFilterChain, ModelMapper, BCrypt, AuthManager)
├── data/          DataInitializer (semilla de roles y usuarios al arrancar)
└── enums/         OrderStatus
```

## Code Style
- Inyección por constructor con `@RequiredArgsConstructor` (campos `final`); nunca `@Autowired`.
- Cada servicio define `I<Nombre>Service` + `<Nombre>Service`; los controladores dependen de la interfaz.
- Entidades nunca expuestas directamente: se convierten a DTOs con `ModelMapper`.
- Excepciones de dominio (`ResourceNotFoundException`, `AlreadyExistsException`) lanzadas desde servicios y capturadas por `GlobalExceptionHandler` (`@RestControllerAdvice`).
- Modelos con `@Getter @Setter @NoArgsConstructor` (Lombok granular, no `@Data`).

## API Conventions
- Prefijo global: `/api/v1` (property `api.prefix`).
- Toda respuesta HTTP: `ResponseEntity<ApiResponse>` con `{message, data}` (`data` omitido si null — `@JsonInclude(NON_NULL)`).
- Único endpoint público: `POST /api/v1/auth/login`.
- Autorización por rol con `@PreAuthorize("hasRole('ROLE_ADMIN')")` en mutaciones (add/update/delete).

## Datos semilla
`DataInitializer` se ejecuta al arrancar y crea (solo si no existen):

| Rol        | Email            | Password |
|------------|------------------|----------|
| ROLE_ADMIN | admin1@email.com | ******** |

## Endpoints principales
Base URL: `http://localhost:9191/api/v1`

| Recurso        | Método | Path                                            | Admin |
|----------------|--------|-------------------------------------------------|-------|
| **Auth**       | POST   | `/auth/login`                                   |       |
| **Products**   | GET    | `/products/all`                                 |       |
|                | GET    | `/products/{id}`                                |       |
|                | POST   | `/products/add`                                 | ✓     |
|                | PUT    | `/products/{id}/update`                         | ✓     |
|                | DELETE | `/products/{id}/delete`                         | ✓     |
|                | GET    | `/products/name/{name}`                         |       |
|                | GET    | `/products/by-brand`                            |       |
|                | GET    | `/products/category/{category}`                 |       |
|                | GET    | `/products/brand-and-name`                      |       |
|                | GET    | `/products/category-and-brand`                  |       |
|                | GET    | `/products/count/brand-and-name`                |       |
| **Cart**       | GET    | `/carts/{cartId}/my-cart`                       |       |
|                | DELETE | `/carts/{cartId}/clear`                         |       |
|                | GET    | `/carts/{cartId}/total-price`                   |       |
| **CartItem**   | POST   | `/cartItems/item/add`                           |       |
|                | PUT    | `/cartItems/cart/{cartId}/item/{itemId}/update` |       |
|                | DELETE | `/cartItems/cart/{cartId}/item/{itemId}/remove` |       |
| **Orders**     | POST   | `/orders/order`                                 |       |
|                | GET    | `/orders/{orderId}/order`                       |       |
|                | GET    | `/orders/user/{userId}/order`                   |       |
| **Users**      | POST   | `/users/add`                                    |       |
|                | GET    | `/users/{userId}/user`                          |       |
|                | PUT    | `/users/{userId}/update`                        |       |
|                | DELETE | `/users/{userId}/delete`                        |       |
| **Images**     | POST   | `/images/upload`                                |       |
|                | GET    | `/images/download/{imageId}`                    |       |
|                | PUT    | `/images/{imageId}/update`                      |       |
|                | DELETE | `/images/{imageId}/delete`                      |       |
| **Categories** | GET    | `/categories/all`                               |       |
|                | GET    | `/categories/{id}`                              |       |
|                | GET    | `/categories/name/{name}`                       |       |
|                | POST   | `/categories/add`                               |       |
|                | PUT    | `/categories/{id}/update`                       |       |
|                | DELETE | `/categories/{id}/delete`                       |       |

## Testing
- Framework: JUnit 5 + `@SpringBootTest`.
- Solo existe el test `contextLoads()` — no hay tests de unidad ni integración. Área pendiente de desarrollo.

## Git Workflow
- Rama principal: `main`; desarrollo en `develop`.
- Commits con prefijo convencional: `feat:`, `refactor:`.

