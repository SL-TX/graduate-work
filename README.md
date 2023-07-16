## Дипломная работа курса "Java разработчик"
# Описание
Back-end проекта платформы по перепродаже вещей.
# Структура проекта:

    /src/main/
        java/ru/skypro/homework/
            config/                # Конфигурация Spring security и Swagger
            controller/            # Контроллеры
            dto/                   # Data transfer objects
            entity/                # Сущности
            mapper/                # Мапперы
            repository/            # Репозитории
            service/               # Интерфейсы сервисов 
                impl/              # Их реализация
        resources/                 # Настройки приложения 
            liquibase              # Инициализация БД средствами Liquibase 
