#Trivago Case Study
###Built With:
[Spring](https://spring.io/)


INSTALLATION
---
* cloning repository `git clone https://github.com/Wrona1990/trivago_case_study.git`
* generating .jar file `mvn clean package spring-boot:repackage`
* executing .jar file `java -jar trivago_case_study-0.0.1-SNAPSHOT.jar`
* port on which applications tomcat server is listening = 8080

RUNNING
---
To run the application you can either:
1. execute `docker-compose up`:
2. execute previously built `.jar` file
 <br>

Application accepts Http Requests: 
1. POST - Create new item
- CURL: `http://localhost:8080/items/create`
- BODY: `Json Payload` (look example below)
2. GET - Get item by ID
  - CURL: `http://localhost:8080/items/get?id=N`\
  where `N` = id of existing item to get
3. GET - Get items with given hotel name
  - CURL: `http://localhost:8080/items/getByHotel?hotelName=N`\
  where `N` = hotel name of items to find
4. GET - Get items with given rating
  - CURL: `http://localhost:8080/items/getByRating?rating=N`\
  where `N` = rating of items to find
5. GET - Get items with given city
  - CURL: `http://localhost:8080/items/getByCity?city=N`\
  where `N` = city of items to find
6. GET - Get items with given reputation badge
  - CURL: `http://localhost:8080/items/getByBadge?reputationBadge=N`\
  where `N` = reputation badge of items to find (Green, Yellow, Red)
7. PUT - Book available item
  - CURL: `http://localhost:8080/items/book?id=N`\
  where `N` = id of item from which reduce availability by 1

EXAMPLE
---
1. Curl Example:\
GET:`http://localhost:8080/items/create`
2. Json Body:
```{
     {
    "name": "TestName2",
    "rating": 2,
    "category": "ALTERNATIVE",
    "location": {
    "city": "Cuernavaca",
    "state": "Morelos",
    "country": "Mexico",
    "zip_code": "62448",
    "address": "Boulevard Díaz Ordaz No. 9 Cantarranas"
    },
    "image": "image_url",
    "reputation": 1,
    "reputationBadge": "GREEN",
    "price": 1,
    "availability": 1
    }
}
```

3. Response:

```
{
    "id": 5,
    "name": "TestName2",
    "rating": 2,
    "category": "ALTERNATIVE",
    "location": {
        "id": 5,
        "city": "Cuernavaca",
        "state": "Morelos",
        "country": "Mexico",
        "zip_code": 62448,
        "address": "Boulevard Díaz Ordaz No. 9 Cantarranas",
    },
    "image": "image_url",
    "reputationBadge": "GREEN",
    "price": 1,
    "availability": 1
}
```
