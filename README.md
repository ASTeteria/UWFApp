# Postman

### Register
{
   "username": "admin",(або модератор, юзер)
   "password": "1234",
   "roles": [ADMIN] (або MODERATOR, USER )
}

### Login
{
    "username": "admin",(або модератор, юзер)
    "password": "1234"
}

### Athletes

Команди:

1. Отримання всіх атлетів (GET /api/athletes):

Метод: GET
URL: http://localhost:8080/api/athletes
Заголовки:
Authorization: Bearer <токен> (для користувача з ролями ADMIN, MODERATOR або USER)
Очікуваний результат: HTTP 200 OK з JSON-масивом усіх атлетів.
2. Отримання конкретного атлета за ID (GET /api/athletes/{id}):

Метод: GET
URL: http://localhost:8080/api/athletes/{id}
Заголовки:
Authorization: Bearer <токен> (для користувача з ролями ADMIN, MODERATOR або USER)
Очікуваний результат: HTTP 200 OK з JSON-об'єктом атлета з вказаним ID.
3. Створення нового атлета (POST /api/athletes):

Метод: POST

URL: http://localhost:8080/api/athletes

Заголовки:

Authorization: Bearer <токен> (для користувача з роллю USER або ADMIN)
Content-Type: application/json
JSON

{
  "firstName": "Ганна",
  "lastName": "Пупкіна",
  "birthDate": "2000-01-01",
  "gender": "MALE",
  "programType": "CONTACT" (або TAOLU_TRADITIONAL, CONTACT, TAOLU_SPORT)
}
Очікуваний результат: HTTP 201 Created з JSON-об'єктом створеного атлета.

4. Оновлення існуючого атлета (PUT /api/athletes/{id}):

Метод: PUT

URL: http://localhost:8080/api/athletes/{id}

Заголовки:

Authorization: Bearer <токен> (для користувача з роллю ADMIN)
Content-Type: application/json

JSON

{
  "firstName": "Ганна",
  "lastName": "Пупкіна",
  "birthDate": "2000-01-01",
  "gender": "FEMALE",
  "programType": "CONTACT"
}
Очікуваний результат: HTTP 200 OK з JSON-об'єктом оновленого атлета.

5. Видалення атлета (DELETE /api/athletes/{id}):

Метод: DELETE
URL: http://localhost:8080/api/athletes/{id}
Заголовки:
Authorization: Bearer <токен> (для користувача з роллю ADMIN)
Очікуваний результат: HTTP 204 No Content (успішне видалення).


### CompetitionApplication

Команди:

1. Отримання всіх заявок (GET /api/competition-applications):

Метод: GET
URL: http://localhost:8080/api/competition-applications
Заголовки: Authorization: Bearer <токен> (для ролей ADMIN, MODERATOR, USER)
Очікуваний результат: HTTP 200 OK з JSON-масивом заявок.
2. Отримання заявки за ID (GET /api/competition-applications/{id}):

Метод: GET
URL: http://localhost:8080/api/competition-applications/{id}
Заголовки: Authorization: Bearer <токен> (для ролей ADMIN, MODERATOR, USER)
Очікуваний результат: HTTP 200 OK з JSON-об'єктом заявки.
3. Створення нової заявки (POST /api/competition-applications):

Метод: POST

URL: http://localhost:8080/api/competition-applications

Заголовки:

Authorization: Bearer <токен> (для ролей ADMIN, USER)
Content-Type: application/json

JSON

{
  "competitionName": "ЧУ 2025",
  "athleteFirstName": "Іван",
  "athleteLastName": "Сергієв",
  "birthDate": "2008-02-01",
  "gender": "MALE",
  "ageCategory": "ADULTS_18_PLUS",
  "weaponlessProgram": "CHANG_QUAN",
  "shortWeaponProgram": "DAO_SHU",
  "longWeaponProgram": "GUN_SHU",
  "duilian": "Микола Ростов"
}
Очікуваний результат: HTTP 201 Created з JSON-об'єктом створеної заявки.

4. Оновлення існуючої заявки (PUT /api/competition-applications/{id}):

Метод: PUT

URL: http://localhost:8080/api/competition-applications/{id}

Заголовки:

Authorization: Bearer <токен> (для ролі ADMIN)
Content-Type: application/json

JSON

{
   "competitionName": "ЧУ 2025",
   "athleteFirstName": "Сергій",
   "athleteLastName": "Сергієв",
   "birthDate": "2008-02-01",
   "gender": "MALE",
   "ageCategory": "ADULTS_18_PLUS",
   "weaponlessProgram": "CHANG_QUAN",
   "shortWeaponProgram": "DAO_SHU",
   "longWeaponProgram": "GUN_SHU",
   "duilian": "Микола Ростов"
}
Очікуваний результат: HTTP 200 OK з JSON-об'єктом оновленої заявки.

5. Видалення заявки (DELETE /api/competition-applications/{id}):

Метод: DELETE
URL: http://localhost:8080/api/competition-applications/{id}
Заголовки: Authorization: Bearer <токен> (для ролі ADMIN)
Очікуваний результат: HTTP 204 No Content.



### ContactCompetitionApplication

Команди:

1. Отримання всіх заявок (GET /api/contact-competition-applications):

Метод: GET
URL: http://localhost:8080/api/contact-competition-applications
Заголовки: Authorization: Bearer <токен> (для ролей ADMIN, MODERATOR, USER)
Очікуваний результат: HTTP 200 OK з JSON-масивом заявок.
2. Отримання заявки за ID (GET /api/contact-competition-applications/{id}):

Метод: GET
URL: http://localhost:8080/api/contact-competition-applications/{id}
Заголовки: Authorization: Bearer <токен> (для ролей ADMIN, MODERATOR, USER)
Очікуваний результат: HTTP 200 OK з JSON-об'єктом заявки.
3. Створення нової заявки (POST /api/contact-competition-applications):

Метод: POST

URL: http://localhost:8080/api/contact-competition-applications

Заголовки:

Authorization: Bearer <токен> (для ролі ADMIN, USER)
Content-Type: application/json

JSON

{
    "competitionName": "ЧУ 2025",
    "athleteFirstName": "Олег",
    "athleteLastName": "Олегов",
    "birthDate": "1995-05-20",
    "gender": "MALE",
    "ageCategory": "AGE_18_PLUS",
    "contactProgram": "SANDA",
    "weightCategory": "FROM_85_TO_90"
}
Очікуваний результат: HTTP 201 Created з JSON-об'єктом створеної заявки.

4. Оновлення існуючої заявки (PUT /api/contact-competition-applications/{id}):

Метод: PUT

URL: http://localhost:8080/api/contact-competition-applications/{id}

Заголовки:

Authorization: Bearer <токен> (для ролі ADMIN)
Content-Type: application/json

JSON
{
  "competitionName": "ЧУ 2025",
  "athleteFirstName": "Олег",
  "athleteLastName": "Олегов",
  "birthDate": "1995-05-20",
  "gender": "MALE",
  "ageCategory": "AGE_18_PLUS",
  "contactProgram": "LIGHT_SANDA",
  "weightCategory": "FROM_85_TO_90"
}
Очікуваний результат: HTTP 200 OK з JSON-об'єктом оновленої заявки.

5. Видалення заявки (DELETE /api/contact-competition-applications/{id}):

Метод: DELETE
URL: http://localhost:8080/api/contact-competition-applications/{id}
Заголовки: Authorization: Bearer <токен> (для ролі ADMIN)
Очікуваний результат: HTTP 204 No Content.








































# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.3/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.3/maven-plugin/build-image.html)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/3.4.3/specification/configuration-metadata/annotation-processor.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.4.3/reference/web/servlet.html)

### Guides

The following guides illustrate how to use some features concretely:

* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the
parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

