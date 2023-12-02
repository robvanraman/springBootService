 Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim

# Set the working directory to /app
WORKDIR /app

# Clone the Spring Boot application code from GitHub
RUN apt-get update && apt-get install -y git
RUN git clone https://github.com/your-username/your-spring-boot-repo.git .

# Build the application
RUN ./mvnw package -DskipTests

# Expose the port that the application will run on
EXPOSE 8080

# Define the command to run the application
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]