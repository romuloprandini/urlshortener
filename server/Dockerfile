FROM openjdk:8-jdk-alpine
MAINTAINER Romulo Prandini Lima <romuloprandini@gmail.com>
ENV SPRING_PROFILES_ACTIVE=dev
VOLUME /tmp /configuration
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY pom.xml mvnw ./.mvn /usr/src/app/ 
COPY .mvn /usr/src/app/.mvn
RUN ls -la && ./mvnw -B dependency:resolve
COPY ./ /usr/src/app/
RUN ./mvnw -B package \
    && mv target/*.jar /
WORKDIR /
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
