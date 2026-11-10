# Система управления криптобиржей (Lab 2)

## Описание проекта
REST-сервис для управления пользователями и торговыми заявками криптобиржи.
Разработан на Java с использованием Spring Boot, Spring Data JPA и PostgreSQL.
Реализован паттерн MVC.

## Пакеты проекта
- `model` — JPA-сущности с наследованием (`@Inheritance`)
- `dto` — DTO-объекты для передачи данных с валидацией
- `repository` — интерфейсы Spring Data JPA
- `service` — бизнес-логика (интерфейсы + реализации)
- `controller` — REST-контроллеры

## REST API

### Пользователи (`/api/users`)
- `POST /api/users` — создать пользователя
- `PUT /api/users/{id}` — обновить пользователя
- `GET /api/users` — получить всех пользователей
- `GET /api/users/{id}` — получить пользователя по ID
- `GET /api/users/role/{role}` — получить пользователей по роли (фильтрация)
- `DELETE /api/users/{id}` — удалить пользователя

### Заявки (`/api/orders`)
- `POST /api/orders` — создать заявку
- `PUT /api/orders/{id}` — обновить заявку
- `GET /api/orders` — получить все заявки
- `GET /api/orders/{id}` — получить заявку по ID
- `GET /api/orders/symbol/{symbol}` — получить заявки по символу (фильтрация)
- `GET /api/orders/type/{type}` — получить заявки по типу (фильтрация)
- `DELETE /api/orders/{id}` — удалить заявку

## Технологии
- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- PostgreSQL
- Lombok
- H2 (для тестов)
- Mockito + @SpringBootTest