FROM java:8-jdk-alpine

COPY build/libs/TP_ProgComp-0.1.0.jar /usr/app/
#COPY hsqldb/ /usr/app/hsqldb
#COPY startApp.sh /usr/app

WORKDIR /usr/app

RUN sh -c 'touch TP_ProgComp-0.1.0.jar'

ENTRYPOINT ["java","-jar","TP_ProgComp-0.1.0.jar"]

#ENTRYPOINT ["sh","startApp.sh"]