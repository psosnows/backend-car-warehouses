# backend-car-warehouses

## About
Backend providing API small car warehouse application.

## Requirements
The application is designed to run only locally.

Before launching it requires mongoDB database to be already running.

Tested while running MongoDB Compass v-1.26.1 with MongoDB 4.4.5 Community

The database needs to:
* host itself on `localhost:27017`
* already contain:
  * collection called: `car_sales`
  * within collection:
    * document collection: `cars`
    * document collection: `places`

## Dev and run environment
Developed using IntelliJ IDEA 2021.1 (Community Edition)

Build #IC-211.6693.111, built on April 6, 2021

* Run and test configs included in repo.
