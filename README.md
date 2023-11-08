# Best Matched Restaurant Backend

## Overview

This application is an REST API to find the best restaurant based on a search with criteria.
The application is built using Kotlin and Spring Boot.

## Structure

The data storaged in the CSV files are read and stored in a H2-database.
Three endpoint are provided in the ```http://localhost:8080```:
- ```GET /cuisines```: return all the cuisines
- ```GET /restaurants```: return all the restaurants
- ```GET /restaurants/filter```: return a list of restaurants based on the parameters
  - The parameters can be passed in JSON format:
  ```json
  {
    "name": "yummy",
    "customerRating": 3,
    "distance": 15,
    "price": 35,
    "cuisine": "Mex"
  }
  ```
  - Or in the URL: 
  ```text
  http://localhost:8080/restaurants/filter?name=yummy&customerRating=3&distance=15&price=35&cuisine=Mex

## Build

```mvn clean install```

## Running

```java -jar .\target\best-matched-restaurants-ds-3.1.5.jar```

## Testing

```mvn test```