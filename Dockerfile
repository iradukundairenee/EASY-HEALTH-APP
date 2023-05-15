FROM maven:3.6.3-jdk-11-slim
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn package
EXPOSE 8090
CMD ["mvn", "tomcat7:run"]
