# escape=\
# syntax=docker/dockerfile:1

ARG JAVA_VERSION=21
FROM --platform=$BUILDPLATFORM eclipse-temurin:${JAVA_VERSION}-jdk-alpine
# Set the working directory inside the container
WORKDIR /bot

# Add the wakeonlan command
RUN echo "http://dl-cdn.alpinelinux.org/alpine/edge/testing" >> /etc/apk/repositories \
    && apk update \
    && apk add --no-cache wakeonlan \
    && rm -f /etc/apk/repositories

# Copy the JAR file into the container
COPY build/libs/oroarmor-discord-bot-all.jar bot.jar

# Command to run the JAR file
ENTRYPOINT  ["java", "-jar", "bot.jar"]