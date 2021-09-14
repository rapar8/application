# application -> warehouse
# Getting Started

## Author - [Ravi Parekh](https://stackoverflow.com/users/410439/ravi-parekh)

For further reference, please consider the following sections:
* [Testcontainers Postgres In Memory Testing container Module Reference Guide](https://www.testcontainers.org/modules/databases/postgres/)
* [Testcontainers](https://www.testcontainers.org/)
* [Swagger API Documentation](https://warehouse-kb3lmrg37a-lz.a.run.app/swagger-ui/#/product-controller)
* [Internationalization i18n](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.internationalization)
* [Exception Handling](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.developing-web-applications.spring-mvc.error-handling)
* [OAuth2 Security resource server](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.security.oauth2)
* [Github Pipeline with GCP Run - warehouse.yml](https://github.com/ingka-group-digital/myhouse/)

### Monorepo
Microservice related to this application should be resided in this GitHub monorepo, the only problem with this monorepo is 'per-project access control'
- [Monorepo advantage and limitation](https://en.wikipedia.org/wiki/Monorepo)

### Microservice Architect
- Some component and utility can be moved to Common Component which can be used as dependency, which has not been done yet e.g. GlobalExceptionHandler.java, *Enum.java to Common
- Database per MS

### Caching
- ~~Used spring method cache and etag for performance and reduce further hits to DB~~ _Due to lack of time_
- Used materialized view in postgres to store/update the stocks sold and brought in once so need to hit the DB each time to
 available stocks until product sold out [Postgres materialized view](https://www.postgresql.org/docs/current/rules-materializedviews.html)

### Json Schema to Model
To Convert Json Schema to model plugin can be used
- jsonschema2pojo
- org.openapitools

### Message Translation
- Internationalization i18n is done, see resources/messages for ERROR handling
- In the Future, message translation can be taken to Phrase and Config Server so deployment in zero downtime
- Automated Sync between Phrase app and git should be done via script(hook)

### Testing
- Integration testing with in memory real time POSTGRES Db (test container) 
```
mvn clean verify
```

### Future implementation
- [R2DBC - reactive non-blocking programming APIs to relational databases]()
  - Only drawback of this is t does NOT offer caching, lazy loading, write-behind, or many other features of ORM frameworks
- [Spring WebFlux - reactive WebClient non-blocking API powered by projectreactor.io](https://projectreactor.io/)
  - It is non-blocking reactive client for calling remote http API over underlying HTTP client libraries such as Reactor Netty. It is also replacement of Rest-template
- [Security Oauth2 - code need to be uncomment to start user Authentication  with OAuth2](src/main/java/com/mywarehouse/configuration/SecurityConfig.java)
- DB migration [Use flyway for DB migration and DB versioning](https://flywaydb.org/documentation/usage/plugins/springboot.html)

### URL 

- [Application Swagger](https://warehouse-kb3lmrg37a-lz.a.run.app/swagger-ui/) - Initial request will take time due to Cold start in Google Run. If you see any error in swagger html page, try deleting cookies for that url, and it will work
- curl --location --request POST 'https://warehouse-kb3lmrg37a-lz.a.run.app/products/upload' \
--form 'file=@"/Users/raviparekh/Documents/warehouse/src/main/resources/schema/products.json"'
