Nearby Event Finder App
Описание 
Nearby Event Finder App — это Android-приложение,
которое позволяет пользователям находить ближайшие события в зависимости 
от их местоположения и предпочтений. Приложение отображает список событий 
и предоставляет возможность фильтровать их по дате, типу и радиусу.

Функциональность
Отображение списка ближайших событий с информацией о названии, типе, дате и расстоянии.
Навигация на экран с детальной информацией о событии.
Фильтрация событий по дате, типу и радиусу.
Интеграция с Google Calendar для добавления событий.
Кэширование данных (обновление каждые 30 минут).
Обработка ошибок: отсутствие разрешения на локацию, подключение к сети, отсутствие событий.

Требования
Android Studio Arctic Fox или выше.
Устройство с версией Android 5.0 (Lollipop) или выше.
Подключение к интернету для первоначального загрузки данных.

Зависимости
В проекте используются следующие библиотеки:

Retrofit: для загрузки данных из JSON.
Room: для локального кэширования данных.
Material Components: для реализации пользовательского интерфейса.
Fused Location Provider: для получения местоположения пользователя.
ConstraintLayout и RecyclerView: для удобной адаптивной верстки и отображения списка событий.

