FROM openjdk:11-jdk

VOLUME /tmp

ADD build/libs/simple-web-server.jar simple-web-server.jar

RUN bash -c 'touch /warranty-service.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-XX:+UseContainerSupport","-Xmx512m","-Xms128m","-jar","/simple-web-server.jar"]