FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY build/libs/gdgoc-app.jar /app/gdgoc-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app/gdgoc-app.jar"]
