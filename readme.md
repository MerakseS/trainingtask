# Training task

### Инструкция по сборке

* Склонировать репозиторий на свой компьютер.
* Выполнить команду `gradlew build` в корне проекта.

### Инструкция по запуску

* Запустить сервер базы данных:
    * Перейти в папку базы данных HSQLDB;
    * Перейти в папку data;
    * Выполнить комманду:
    ```
  @java -classpath ../lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:taskdb --dbname.0 taskdb
  ```
* Если параметры соединения с базой данных отличаются от стандартных, то можно указать их в переменных окружения:
    * Хост - HSQLDB_PATH,
    * Имя базы данных - HSQLDB_NAME,
    * Пользователь - HSQLDB_USER,
    * Пароль - HSQLDB_PASSWORD.
  
* Для добавления сущностей в базу данных выполнить скрипт `resources/db/create.sql`.
* Запустить конфигурацию `Start`.