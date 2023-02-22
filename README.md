# async-tasks-microservice

## Run the app

```bash
docker-compose up -d
./mvnw clean install -DskipTests && ./mvnw spring-boot:run
# Windows mvnw clean install -DskipTests && mvnw spring-boot:run
```

Created users:

- user:user
- admin:admin

```bash
docker-compose down -v
```

## Postman

You can import `async-tasks-postman-collection.json` as a Postman project.

## Swagger UI

Swagger UI is at [localhost:8080/swagger-ui.html](localhost:8080/swagger-ui.html)

## Properties

You can manage how long artificial processing (Thread.sleep) should last by changing `tasks.delay` property 
at``application.yml`:
```yaml
tasks:
  main-thread-pool: 3
  sub-thread-pool: 12
  cache:
    enabled: true
    entry-lifetime: 1h
  delay:
    min-millis: 3000
    max-millis: 10000
```

## Run the integration test

```bash
docker-compose up -d
./mvnw clean install
# Windows mvnw clean install

docker-compose down -v
```
