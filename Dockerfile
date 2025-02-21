# Use an official OpenJDK runtime as a parent image
FROM openjdk:25-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file of the Report microservice
COPY target/Gym-CRM-System-Spring-0.0.1-SNAPSHOT.jar app.jar

# Expose the application's port
EXPOSE 8080

# Command to run the microservice
ENTRYPOINT ["java", "-jar", "app.jar"]