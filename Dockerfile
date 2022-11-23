FROM openjdk:11
EXPOSE 8080
ARG JAR_FILE=target/com.main-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} Main-app.jar
ENTRYPOINT ["java","-jar","/Main-app.jar"]