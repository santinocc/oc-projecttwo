# Use an official OpenJDK runtime as a base image
FROM openjdk:17

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/projecttwo-0.0.1-SNAPSHOT.jar /app/projecttwo.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the JAR file
CMD ["java", "-jar", "/app/projecttwo.jar"]