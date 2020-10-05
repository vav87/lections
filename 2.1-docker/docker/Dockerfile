FROM openjdk
# Делаем сперва gradlew build, потом копируем из папки и переименовываем в service.jar
COPY build/libs/docker-simple-demo-1.0-SNAPSHOT.jar service.jar
# Начальная команда при запуске контейнера
ENTRYPOINT ["java", "-jar", "/service.jar"]
# CMD - похожа на ENTRYPOINT, но указывает на дефолт аргументы для той утилиты,
# что мы использовали в ENTRYPOINT
CMD ["noname"]
# маркер порта, в котором указываем, что данный порт можно пробросить.
EXPOSE 8080
