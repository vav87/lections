FROM openjdk:11-jdk

VOLUME /tmp
EXPOSE 8080

ADD build/libs/book-store.jar book-store.jar

RUN bash -c 'touch /book-store.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-XX:+UseContainerSupport","-jar","/book-store.jar"]