FROM java:8-jdk-alpine

COPY build/libs/* /usr/app/app.jar
WORKDIR /usr/app

ENTRYPOINT ["java","-jar","app.jar"]
