FROM --platform=linux/amd64 openjdk:8u272-slim
# Add Maintainer Info
LABEL maintainer="rajkumarmca1982@outlook.com"
VOLUME /tmp
EXPOSE $PORT
ARG JAR_FILE=build/libs/ContactUsApp-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} contactservice.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/contactservice.jar"]
