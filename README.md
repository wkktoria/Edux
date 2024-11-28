# Edux

![Edux's homepage](docs/images/edux_homepage.png)

Edux is a simple and intuitive Learning Management System (LMS) that makes it easy to manage and deliver online
learning.

## Building

**Requirements**

- [Docker](https://www.docker.com/products/docker-desktop)

1. Create .env file providing MySQL database connection details, it should include:
    - `PROD_MYSQLDB_HOST`
    - `PROD_MYSQLDB_PORT`
    - `PROD_MYSQLDB_DATABASE`
    - `PROD_MYSQLDB_USER`
    - `PROD_MYSQLDB_PASSWORD`
2. Build Docker image: `docker build -t edux .`.
3. Create Docker container: `docker create -p 8081:8081 --env-file .env --name edux edux`.
4. Run the container: ``

## Running

**Requirements**

- [Docker](https://www.docker.com/products/docker-desktop)

1. Create Docker container: `docker create -p 8081:8081 --env-file .env --name edux edux`.
2. Start the container: `docker start -ai edux`.

Application should be started on port `8081`.