FROM openjdk
COPY build/libs/docker-1.0-SNAPSHOT.jar service.jar
CMD java $JAVA_OPTS -jar /service.jar $PARAM
EXPOSE 8080
