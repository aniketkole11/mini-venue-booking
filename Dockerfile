# Use Java 17 base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the full project into the container
COPY . .

# Make the Maven wrapper executable
RUN chmod +x mvnw

# Build the project using Maven wrapper
RUN ./mvnw clean package -DskipTests

# Run the final Spring Boot jar file (adjust the name if different)
CMD ["java", "-jar", "target/venuebooking-0.0.1-SNAPSHOT.jar"]
