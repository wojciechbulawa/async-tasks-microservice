# async-tasks-microservice

## Run the app

```bash
docker-compose up -d
./mvnw spring-boot:run
```

Created users:

- user:user
- admin:admin

```bash
docker-compose down -v
```

## Swagger UI

Swagger UI is at [localhost:8080/swagger-ui.html](localhost:8080/swagger-ui.html)

## Run the integration test

```bash
docker-compose up -d
./mvnw clean intall

docker-compose down -v
```
