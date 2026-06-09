# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project
REST API e-Commerce (práctica) con Spring Boot 3.5.11, Java 17, MySQL, Spring Security + JWT (jjwt 0.12.6), ModelMapper 2.4.4, Lombok.

## Bash Commands
```bash
./mvnw clean compile
./mvnw spring-boot:run                        # puerto 9191
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

## Testing
- Framework: JUnit 5 + `@SpringBootTest` (Spring Boot Test).
- Solo existe `contextLoads()` — sin tests de unidad ni integración adicionales.

## Git Workflow
- Rama principal: `main`; desarrollo en `develop`.
- Commits con prefijo convencional: `feat:`, `refactor:`.

## Environment
| Property                                 | Valor por defecto                            |
|------------------------------------------|----------------------------------------------|
| `server.port`                            | `9191`                                       |
| `spring.datasource.url`                  | `jdbc:mysql://localhost:3306/dream_shops_db` |
| `spring.datasource.username`             | `root`                                       |
| `spring.jpa.hibernate.ddl-auto`          | `update`                                     |
| `auth.token.expirationInMils`            | `3600000` (1 hora)                           |
| `auth.token.jwtSecret`                   | clave HS256 en properties                    |
| `spring.servlet.multipart.max-file-size` | `10MB`                                       |
