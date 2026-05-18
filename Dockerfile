FROM mcr.microsoft.com/playwright/java:v1.59.0-noble

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn dependency:go-offline

CMD ["mvn", "clean", "test", "-Dheadless=true", "-Dtest=ProductApiTests"]
