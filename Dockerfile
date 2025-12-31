FROM maven:3.9.9-eclipse-temurin-8

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn test

CMD ["java", "-cp", "target/classes", "com.deccan.batchprocessor.runner.Main"]