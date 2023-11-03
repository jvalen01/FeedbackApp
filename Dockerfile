# Build React static files
FROM node:14 AS frontend-build
WORKDIR /app
COPY feedback-app/package*.json ./
RUN npm install
COPY feedback-app/ ./
RUN npm run build

# Set up Spring Boot Application with React static files
# Build stage
FROM gradle:jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon --info

# Package stage
FROM openjdk:17
COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
