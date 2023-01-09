# job4j_cinema

## Про проект:
Нужно написать интернет сервис для кинотеатра.
Сервис предусматривает авторизацию. 
Если пользователь не авторизовался в сервере, 
доступны главная страница, страницы регистрации, 
страницы авторизации.
Сервис должен на главной странице показать список киносеансов.
После выбора киносеанса нужно перейти в экран с выбором ряда в зале.
Потом перейти в экран для выбора места в ряду. Следущий экран покупка билета.
Для покупки билета - кнопка "купить". 
После покупки билета перейти на страницу со списком купленных билетов 
и записать результат покупки в базу данных.
Если покупка не удалась, предупредить покупателя.
Приложение должно состоять из 3х слоёв: Controller, Service, Persistence.
База данных содержать 3 таблицы: users, sessions, tickets.
users - таблица с описанием пользователей.
sessions - расписание киносеансов.
tickets - список купленных билетов.
movies - список кинофильмов.
room - список залов.
В таблице tickets созданы ограничения на уникальность полей:
киносеанс, номер ряда, номер места.
В таблице room - хранится список залов. В кинотеатре может быть несколько залов.

## Технологии: 
В проекте использованы: git, java 17, СУБД PostgreSQL 14, Spring boot, JUnit 5, assertj, 
mockito, liquibase 3.6.2, h2database 2.1.214, Maven 3.8, thymeleaf, checkstyle, 
log4j, net.jcip, commons-dbcp2 2.7.0, IntelliJ IDEA 2022.3.1 (Community Edition).

## Список ПО:
### - git
Установить с сайта: https://git-scm.com/downloads

### - java 17
Установить с сайта: https://www.oracle.com/java/technologies/downloads/

### - IntelliJ IDEA 2022.3.1 (Community Edition)
Установить с сайта: https://www.jetbrains.com/ru-ru/idea/

### - Maven 3.8
Установить с сайта: https://maven.apache.org/install.html

### - PostgreSQL 14
Установить с сайта: https://www.postgresql.org/download/

## Запуск проекта

### 1. Клонирование проекта с github.
Создайте каталог "job4j_cinema" в который будете клонировать проект.<br>
```c:\>mkdir  job4j_cinema``` <br>

Ссылка на проект: https://github.com/nikegreen/job4j_cinema.git <br>
Войдите внутрь своего каталога:<br>
 ```c:\>cd  job4j_cinema``` <br>
Склонируйте проект командой.
```c:\job4j_cinema\>git clone https://github.com/nikegreen/job4j_cinema.git``` <br>

### 2. Создание базы данных:
Запустите окно команд БД (ПУСК - Все программы - PostgreSQL 15 - SQL Shell(psql)).
Введите пользователя и пароль б.д. 
Создайте б.д. командой: "create database cinema" <br>
```
Server [localhost]:
Database [postgres]:
Port [5432]:
Username [postgres]:
Пароль пользователя postgres:
psql (15.1)
ПРЕДУПРЕЖДЕНИЕ: Кодовая страница консоли (866) отличается от основной
                страницы Windows (1251).
                8-битовые (русские) символы могут отображаться некорректно.
                Подробнее об этом смотрите документацию psql, раздел
                "Notes for Windows users".
Введите "help", чтобы получить справку.

postgres=# create database cinema;
 ``` 
### 3. Подготовка  таблиц к созданию и инициализации 
В папке "job4j_cinema\db\script" нужно выполнить последовательно скрипты с помощью
liquibase (пункт 5). 
Перед выполнением скриптов нужно очистить контрольные суммы командой:<br>
```
mvn liquibase:clearCheckSums
```

### 4. Настройка конфигурации БД в Job4j_cinema
В папке "job4j_cinema\srс\main\resources" в текстовом редакторе измените настройки в файле "db.properties".
```
jdbc.url=jdbc:postgresql://127.0.0.1:5432/cinema
jdbc.driver=org.postgresql.Driver
jdbc.username=postgres
jdbc.password=password
```
если у вас они отличаются.

### 5. Компиляция и запуск сервиса.
```
mvn spring-boot:run
```
### 6. Запуск сервиса без сборки.
```
cd c:\job4j_cinema\target
java -jar job4j_cinema-1.0-SNAPSHOT.jar 
```
### 7. Запуск клиента.
Открываем любым web - браузером адрес:
```
http://localhost:8080/index
```

## FEEDBACK
Все предложения и замечания направлять job4j_cinema.nikegreen@yandex.ru <br>
Проект учебный. Используйте на свой страх и риск. 
Я не несу ответственности за любой прямой и косвенный ущерб.