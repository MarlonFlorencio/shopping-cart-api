FROM openjdk:8-jre-slim
COPY ./target/*.jar /usr/src/sc/app.jar
WORKDIR /usr/src/sc
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
