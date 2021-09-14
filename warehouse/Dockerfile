FROM openjdk:16-slim
COPY target/*.jar warehouse.jar
ENTRYPOINT ["java","-Dspring.profiles.active=dev -Dfile.encoding=utf8","-jar","/warehouse.jar"]
