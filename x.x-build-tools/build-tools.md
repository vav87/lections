# Инструменты сборки
## Maven
Все источники зависимостей(репозитории) прописываются в _C:\Users\your_username\.m2\settings.xml_, 
либо используется оригинальный _C:\Program Files\JetBrains\IntelliJ IDEA 2020.2.1\plugins\maven\lib\maven3\conf_

Также можно указать и напрямую в pom:
```xml
<project>
...
  <repositories>
    <repository>
      <id>my-repo1</id>
      <name>your custom repo</name>
      <url>http://...</url>
    </repository>
  </repositories>
...
</project>
```
У мавена тоже есть свой враппер, но он преследуюет другие цели, не как грэдл -
он больше про то, что как прописать все источники зависимостей на вашей машине. 

Пока его можно подключить так:

`mvn -N io.takari:maven:wrapper`, 

но с версии 3.7 он будет включен в стандартный дистрибутив maven

**Dependency Scopes**

Область действия зависимости scope определяет этап жизненного цикла проекта, в котором эта зависимость будет использоваться. 
Maven использует 6 областей:

* `compile` - область по умолчанию, используется, если scope не определена. Compile зависимости доступны во всех classpath проекта;
* `provided` - очень похоже на compile, но эта зависимость в сборку не попадает. 
Предполагается, что зависимость (артефакт) уже присутствует в JDK или в WEB-контейнере. 
Эта область доступна только на этапах компиляции и тестирования и не является транзитивной;

    _Пример_: контейнер сервлетов
* `runtime` - зависимость с данной областью видимости не обязательна для compilation и используется в фазе выполнения;

    _Пример_: JDBC драйвер
* `test` - зависимость используется при тестировании кода приложения;

    _Пример_: JUnit или любой другой фреймоворк для тестов
* `system` - область похожа на provided за исключением того, что необходимо определить физическое расположение артефакта на диске. 
Артефакт с этой областью видимости maven не ищет в репозитории;

    _Пример_: 
    ```xml
    <dependency>
        <groupId>ru.dhabits</groupId>
        <artifactId>some-api</artifactId>
        <version>1.0</version>
        <scope>system</scope>
        <systemPath>${project.basedir}/libs/some-api-1.0.jar</systemPath>
    </dependency>
    ```
* `import` - используется для импорта зависимостей из других артефактов и управлением зависимостями в сложных пакетах, состоящих из нескольких артефактов.

    _Пример_: 
    ```xml
    <dependency>
        <groupId>ru.dhabits</groupId>
        <artifactId>some-pom</artifactId>
        <version>1.0</version>
        <type>pom</type>
        <scope>import</scope>
    </dependency>
    ```

Список всех зависимостей можно посмотреть через:
```
mvn dependency:tree //в виде дерева
mvn dependency:list //просто списком
```

Транзитивные зависимости можно исключать так:

```xml
<dependency>
      <groupId>sample.ProjectA</groupId>
      <artifactId>Project-A</artifactId>
      <version>1.0</version>
      <scope>compile</scope>
      <exclusions>
        <exclusion>  <!-- declare the exclusion here -->
          <groupId>sample.ProjectB</groupId>
          <artifactId>Project-B</artifactId>
        </exclusion>
      </exclusions> 
    </dependency>
``` 

Также в Maven есть такая штука, как `Dependency Management`, которая позволяет описать те версии, 
что нужно будет использовать.

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>3.8</version>
        </dependency>
    </dependencies>
</dependencyManagement>

<dependencies>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId> <!--Версию уже указывать не нужно, т.к. она определена выше -->
    </dependency>
</dependencies>
```
### BOM (Bill of materials)
Также если мы не хотим указывать огромный список всех конкретных зависимостей в `Dependency Management`, мы можем использовать BOM-файлы.

На примере Spring Boot:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-dependencies</artifactId>
    <version>${spring-boot.version}</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>

<!--Версию уже указывать не нужно, т.к. она определена выше -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```

## Gradle
Основная дока Java плагину:
https://docs.gradle.org/current/userguide/java_library_plugin.html

Зависимости уровня `compile` подтягиваются в проект на уровне исходников, поэтому 
нам не надо сперва их компилировать, а потом только собирать зависимый модуль. Сейчас они Deprecated. 
Но Gradle разделил зависимости вида `compile` на `api` и `implementation`.
Если нужна транзитивность - юзаем `api`, если не нужна, то `implementation`.

Виды конфигураций в Gradle и аналогии со скоупами зависимости Maven:

| Maven Scope | Equivalent Gradle Configuration                                                           |
|-------------|-------------------------------------------------------------------------------------------|
| compile     | `api` если нужна транзитивность, `implementation` если нет                                |
| provided    | `compileOnly`                                                                             |
| runtime     | `runtimeOnly`                                                                             |
| test        | `testImplementation`                                                                      |

**gradlew** - это враппер скрипт, который скачивает грэдл по необходимости, 
т.е если на машине его нет - враппер сам его скачает из интернета

В Gradle используется немного другой подход в описании зависимостей(см. проекты):
```groovy
    dependencies {
        testImplementation "junit:junit:$junitVersion" //Где junitVersion=4.12 в файле gradle.properties
        api "com.fasterxml.jackson.core:jackson-databind:2.10.2"
        api group: 'com.fasterxml.jackson.core', name: 'jackson-databind', version: '2.10.2'
    }
```

В файле `gradle.properties` также можно указывать различные параметры для запуска Java приложения. 
Например, размер heap: `org.gradle.jvmargs=-Xmx4096m`

Один из очевидных плюсов gradle - это возможность писать свои таски и плагины.
Пример простой таски в build скрипте:
```groovy
task hello {
    print "It is API"
}
```

## Практика 
_Опционально:_ 

Написать свой плагин/таску в обоих сборщиках для вывода в лог информации о коммите. 
## Ссылки
[Отличное объяснения скоупов Gradle на stackoverflow.com (в картинках)](https://stackoverflow.com/questions/44493378/whats-the-difference-between-implementation-and-compile-in-gradle)
[Список всех зависимостей, что тянет Spring Boot](https://repo1.maven.org/maven2/org/springframework/boot/spring-boot-dependencies/2.2.1.RELEASE/spring-boot-dependencies-2.2.1.RELEASE.pom)