# Исходный образ, на основе которого делаем свой
FROM openjdk
# ADD or COPY копируем наш java класс
ADD src/MainClass.java .
# Запускаем javac и компилим класс
RUN javac MainClass.java
# default команда, когда кто-то будет стартовать контейнер
CMD ["java", "MainClass"]