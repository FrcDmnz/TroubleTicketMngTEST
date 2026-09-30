# Base Java 21 image
FROM eclipse-temurin:21-jre-alpine

# Work directory inside container
WORKDIR /app

# Copy the JAR file
COPY MSKTroubleTicketMng.jar app.jar

# App port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]