# Best Matched Restaurant Backend

## Overview

This application is a REST API for finding the best-matched restaurant based on search criteria.
The application is built using Kotlin and Spring Boot.

## Structure

The data contained in the CSV files is read and stored in an H2-database.
Three endpoint are provided in the ```http://localhost:8080```:
- ```GET /cuisines```: returns all the cuisines.
- ```GET /restaurants```: returns all the restaurants.
- ```GET /restaurants/filter```: return a list of restaurants based on specific parameters.
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