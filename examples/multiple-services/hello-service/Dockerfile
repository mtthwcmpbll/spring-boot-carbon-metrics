FROM eclipse-temurin:17-jre
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/application.jar"]