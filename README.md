# Demo Transactions

## Author: Josue Miguel Canaviri Martinez

### 🐋 Docker Setup:

* _Create_ the database:
  ```shell
  $ docker-compose up -d
  ```
* _Remove_ the container
    ```shell
  $ docker-compose down
  ```
* _Remove_ the container deleting the data
  ```shell
  $ docker-compose down -v
  ```


### 🚪 Endpoints:

GET http://localhost:8080/not-supported (not supported propagation)

GET http://localhost:8080/never (never propagation)

POST http://localhost:8080/nested (nested propagation)

GET http://localhost:8080/logs (logs propagation)
