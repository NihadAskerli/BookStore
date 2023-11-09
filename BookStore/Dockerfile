FROM openjdk:17
WORKDIR /app
COPY target/bookstore*.jar .
EXPOSE 5000
ARG JAR_FILE=./bookstore*.jar
RUN mv ${JAR_FILE} ./bookstore.jar
ENTRYPOINT ["java","-jar","./bookstore.jar"]