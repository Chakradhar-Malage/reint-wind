FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copy Maven wrapper
COPY reint_wind/mvnw .
COPY reint_wind/.mvn .mvn
COPY reint_wind/pom.xml .

# Install dependencies
RUN ./mvnw dependency:resolve

# Copy the rest of the source code
COPY reint_wind/src ./src

# Build jar
RUN ./mvnw clean package -DskipTests

EXPOSE 8081

CMD ["java", "-jar", "target/reint_wind-0.0.1-SNAPSHOT.jar"]