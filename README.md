# Daily Calorie Intake

**Daily Calorie Intake** – это REST API сервис для отслеживания дневной нормы калорий пользователя и учета съеденных блюд.

---

## Запуск проекта

1. **Склонируйте репозиторий**  
   ```sh
   git clone https://github.com/Almazov-Artyom/daily-calorie-intake.git
   ```

2. **Запустите сервис с помощью Docker Compose**  
   ```sh
   docker-compose up --build
   ```

3. **API будет доступно по адресу:**  
   ```
   http://localhost:8080
   ```

---

## Использование

### Регистрация и аутентификация
Для использования сервиса необходимо:

1. **Зарегистрироваться** через эндпоинт: `POST /auth/register`

2. **Войти** через эндпоинт: `POST /auth/login`

3. **Использовать токен** для доступа к защищенным эндпоинтам

---

## Доступные эндпоинты

### Auth

- **Регистрация:** `POST /auth/register`  
- **Авторизация:** `POST /auth/login`
- **Обновление токена:** `POST /auth/refresh`    

### Dish

- **Добавить блюдо:** `POST /dish/add`
- **Получить список блюд:** `GET /dish`

### User
- **Обновить рост:** `POST /user/update-height`
- **Обновить вес:** `POST /user/update-weight`
- **Обновить возраст:** `POST /user/update-age`

### User-Dish
- **Добавить прием пищи:** `POST /user-dish/add-food-intake`
- **Получить отчет за день:** `GET /user-dish/daily-report`
- **Проверить, уложился ли в дневную норму:** `GET /user-dish/check-daily-norm`
- **Получить историю питания по дням:** `GET /user-dish/history-food-intake`

---

## Запуск тестов

Для выполнения тестов используйте Gradle:  

```sh
./gradlew test
```

---

## Postman Collection

Для удобства тестирования API есть **Postman Collection**.  
