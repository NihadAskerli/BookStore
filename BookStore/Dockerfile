FROM maven as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17
WORKDIR /app
COPY --from=build /app/target/*.jar /app/bookstore.jar
EXPOSE 5000
ENTRYPOINT ["java","-jar","bookstore.jar"]