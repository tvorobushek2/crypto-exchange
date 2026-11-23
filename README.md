## Обработка исключений (Lab 3)

Реализован пакет `exception` с собственными классами исключений:
- `UserNotFoundException` — 404 Not Found
- `UserAlreadyExistsException` — 409 Conflict
- `InvalidUserDataException` — 400 Bad Request
- `OrderNotFoundException` — 404 Not Found
- `InvalidOrderDataException` — 400 Bad Request
- `InsufficientBalanceException` — 400 Bad Request

Глобальный обработчик `GlobalExceptionHandler` (пакет `exception.handler`)
использует `@RestControllerAdvice` и возвращает единый формат ошибки:

```json
{
  "timestamp": "2026-11-10T14:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Пользователь с ID 999 не найден. Проверьте правильность ID.",
  "path": "/api/users/999"
}