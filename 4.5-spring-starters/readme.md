# Лекция: Spring Boot

## План лекции
1. Inversion Of Control
1. Spring Boot
1. Инициализация приложения Spring Boot
1. Этапы создания, родительский контекст и дочерний, событие RefreshContext
1. Listeners
1. Profiles

## Inversion Of Control
Мы пишем код:
```java
interface DriverTester {
    Status runTest(String path);
}

class DriverTesterRunner {
    private final DriverTester tester = new MockDriverTester();
    
    public void test() {
        tester.runTest("/tmp/default-driver");
    }
}
```
Получается что `DriverTesterRunner` имеет зону ответственности про запуск тестов, но по-факту он должен еще уметь создавать
(или выбирать какой создавать) драйвер для тестирования.  
Решение о создании объектов `DriverTester` перекладывается на фабрику, в какой-то момент таких фабрик получается такое количество,
что для их создания создается своя собственная фабрика, обычно она имеет название `ObjectFactory`. Таким образом,
мы получаем некий утильный класс, который ответственен за создание объектов.  
Более того, объекты, которые предназначены для фабрики, мы будем помечать какой-нибудь аннотацией (что логично, чтобы их не складывать в фабрику руками),
а в момент построения фабрики, через Reflection API обходим все классы и создаем необходимые нам. Что вообще говоря, занимает какое-то время. 
```java
class DriverTesterRunner {
    private final DriverTester tester = ObjectFactory.instance().create(DriverTester.class);
    
    public void test() {
        tester.runTest("/tmp/default-driver");
    }
}
``` 
Получается, из бизнес-кода мы имеем завязки на какой-то инфраструктурный код, что сильно нарушает принцип слабой связанности.
* На `ObjectFactory` получается завязаны _все_ объекты в системе, при изменении сигнатуры все сломается.
* Unit-тестирование осложнено, т.к. все объекты получаем из фабрики.

Здесь мы получаем понятие: инверсия контроля. Если мы хотим, чтобы инфраструктурный код сделал что-то для нас, нужно чтобы
инфраструктура сама понимала как создавать и настраивать наши классы. Т.е. мы инструктируем код с помощью аннотаций
(`@Service`, `@Controller`, `@Autowired`, `@Value` и т.п.), а инфраструктурный код на основе этого создает наши классы.
Потому что если бизнес-сервису нужно что-то от инфраструктуры, значит он завязан на эту инфраструктуру.

Почему самостоятельная реализация singleton–это плохо?  
Самая идея в основе singleton хорошая, но создание их руками (через статический метод instance()), приведет к тому,
что несколько классов по цепочке будут зависимы друг-от-друга, а значит создание одного приведет к созданию всей цепочки.
Более того, они могут быть циклически зависимы.  

## Spring Boot
Проект Spring Boot сделан для более быстрой и удобной разработки приложений на Spring. [link to docs](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#getting-started-introducing-spring-boot) 
Основные особенности:
* значительно упрощает погружение в технологию для людей без опыта и ускоряет разработку приложений для опытных пользователей;
* дает базовую настройку из коробки основных функций и возможность переопределять только необходимое поведение;
* дает большое количество необходимой функциональности: embedded-сервера, безопасность, метрики, health-чеки, внешняя конфигурация и т.п.
* все работает без кодогенерации и XML.

Раньше все web-приложения запускались как WAR (Web ARchive), с появлением Spring Boot контейнер сервлетов (по-дефолту embedded Tomcat)
стал упаковываться внутрь Jar.

Для запуска Spring Boot приложения в main вызывается создается `SpringApplication` (для краткости, может быть вызван через
`SpringApplication.run(<primary-class>, args)`. При запуске выполняет следующие действия:
* создает `ApplicationContext`;
* создает `CommandLinePropertySource`, который передает параметры запуска в приложение с наивысшим приоритетом;
* создает системные bean'ы, вызывает метод `refresh()` для создания пользовательских bean'ов;
* запускает все `CommandLineRunner` bean'ы.

Аннотация`@SpringBootApplication` содержит в себе `@ComponentScan`, который сканирует пакеты от текущего файла вглубь.
Поэтому располагать главный файл, содержащий `@SpringBootApplication` нужно на верхнем уровне проекта.   

[пример](command-line-properties)

## Spring Boot autoconfiguration
Автоконфигурации в Spring Boot пытаются автоматически сконфигурировать приложение на основе jar файлов, находящихся в classpath'е.
Например, если HSQLDB (inmemory db) находится в classpath, то Spring автоматически запустит HSQLDB и создаст подключение.

Основная идея в том, что большинство конфигураций однотипные и Spring Boot позволяет изменять лишь необходимые beans,
например конфигурировать DataSource для подключения к БД.

Для выключения автоконфигурации можно использовать либо `application-properties`:
```properties
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration
```
либо через `@SpringBootApplication(exclude = ActiveMQAutoConfiguration.class)`.
Так же большинство автоконфигураций позволяют их отключать с помощью переменных, например `spring.flyway.enabled=false`
выключает конфигурирование Flyway (миграции для базы данных).

[пример](autoconfiguration)

Фактически автоконфигурация – это `@Configuration`, который указывается в специальном файле `spring.factories`, который Spring Boot
ищет при старте и получает данные, _еще_ `@Configuration` загружать (кроме тех, которые `SpringBootApplication` нашел при сканировании пакетов).

Автокофигурацию можно включать по условию, основные:
* ConditionalOnBean / ConditionalOnMissingBean – если такой bean уже есть, либо если такого bean'а еще не создано;
* ConditionalOnClass / ConditionalOnMissingClass – наличие / отсутствие класса в classpath;
* ConditionalOnProperty – по значению переменной, например `spring.flyway.enabled=false`;
* ConditionalOnExpression – выражение SpEL;
* ConditionalOnWebApplication – если web-приложение;

[пример реализации](properties-decoder-starter) и [пример подключения](simple-application)

## Инициализация приложения Spring Boot
На вход SpringApplication передается src класс с аннотацией `@SpringBootApplication`, он попадает в контекст,
на нем находится аннотация `@EnableAutoConfiguration` и `@ComponentScan`, которая запускает полный цикл инициализации приложения.
через reflection обходятся все классы, ищутся с аннотацией `@Configuration` и `@Component`. Цель `AnnotatedBeanDefinitionReader` – создание всех BeanDefinition.

В первую контекст инициализируется системными bean'ами: `BeanFactory`, `SystemProperties`, `Environment` и т.д. Никакие пользовательские bean'ы
на этом этапе не создаются. `ApplicationContext` заводит bean definitions, а потом вызывает метод `refresh()`, который полностью пересоздает контекст.

[пример](refresh-context)

Центральный класс приложения – ApplicationContext, read-only класс во время работы приложения, но может быть перезагружен больше одного раза,
если это поддерживает реализация (например для `GenericXmlApplicationContext`)& Он реализует:
* фабричные методы для доступа к компонентам приложения;
* загрузка ресурсов, messages и доступ через единый интерфейс;
* публикация lifecycle-событий;
* наследование контекстов: дочерний контекст имеет приоритет над родительским. Используется, например, если один контекст
для всего web-приложения, но каждый servlet имеет свой собственный независимый контекст.

`BeanDefinition` (более общая реализация `AbstractBeanDefinition`) содержит в себе информацию о самом bean (имя, scope, init/destroy-методы и прочее),
аргументы конструктора и значения полей. 

После этого запускается цикл по всем `BeanFactoryPostProcessor` – их задача "подкрутить" bean definition до создания самих beans.

После этого `BeanFactory` (`ApplicationContext` является наследником) уже создает сами beans, для доработки beans существует `BeanPostProcessor`.

Интерфейс содержит два метода:
* `postProcessBeforeInitialization` – вызывается до initialization-callback'ов (например InitDestroyAnnotationBeanPostProcessor для вызова `@PostConstruct` методов);
* `postProcessAfterInitialization` – вызывается после (пример DataSourceInitializerPostProcessor).

Методы интерфейса вызываются уже _над созданным объектом_, используется для оборачивания в proxy (например, для добавления функционала)
или для модификации полей в объекте (например, валидация JSR-303 объектов `BeanValidationPostProcessor`).


## Listeners
Spring Boot извещает о ряде событий своего жизненного цикла:
* ApplicationStartingEvent – вызывается сразу после старта перед какой-либо обработкой, за исключением _регистрации_ слушателей и инициализаторов контекста.
* ApplicationEnvironmentPreparedEvent – вызывается до создания `ApplicationContext`, но когда `Environment` уже создан.
* ApplicationContextInitializedEvent – вызывается когда `ApplicationContext` создан и `ApplicationContextInitializers` вызваны, но до создания `BeanDefinitions`.
* ApplicationPreparedEvent – вызывается до вызова `ApplicationContext.refresh()`.
* ApplicationStartedEvent – вызывается после полного построения `ApplicationContext` (т.е. после вызова `ApplicationContext.refresh()`), но до вызова `CommandLineRunner`.
* AvailabilityChangeEvent – изменение состояния приложение (запущено / готово).
* ApplicationReadyEvent – отправляется после того, как все `CommandLineRunner` отработали;
* AvailabilityChangeEvent – приложение запущено и готово обрабатывать запросы;
* ApplicationFailedEvent – приложение не запущено.

Некоторые события вызываются до того момента, как `ApplicationContext` уже создан, таким образом, эти слушатели не могут
быть зарегистрированы как `@Bean`. Слушателей таких событий можно регистрировать через `SpringApplication.addListeners()`, либо через 
`spring.factories`, тогда они будут применяться автоматически.

В spring есть класс `ApplicationEventPublisher` для публикаций событий, событие можно наследовать от `ApplicationEvent`,
а слушателя, соответственно регистрировать через `@EventListener`.

## Profiles
Для удобного конфигурирования можно использовать профили. Общая конфигурация прописывается в `application.properties`, а специфичные настройки для профилей
описываются в `application-<profile>.properties`. Текущий активный профиль можно задавать из кода или как параметры запуска или переменные среды ``.
Например:
1. из приложения:
    ```properties
    # application.properties
    spring.profiles.active=stage,docker
    ``` 
1. при старте приложения:
    ```java
   new SpringApplicationBuilder()
                   .main(Application.class)
                   .profiles("stage", "docker")
                   .run(args);
    ```
1. через аргументы командной строки `-Dspring.profiles.active=staging` или через переменные среды: `export SPRING_PROFILES_ACTIVE`.  

Пример сложной конфигурации на основе профилей:
```java
    @Profile("test")
    @TestConfiguration
    static class TestDatasourceConfiguration{
        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .build();
        }
    }

    @Profile("!test")
    @TestConfiguration
    static class ProdDatasourceConfiguration{
        @Bean
        public DataSource dataSource(DataSourceProperties properties) {
            final HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(properties.getUrl());
            dataSource.setUsername(properties.getUsername());
            dataSource.setPassword(properties.getPassword());
            dataSource.setDriverClassName(properties.getDriverClassName());
            return dataSource;
        }
    }
```

## Ссылки
1. Spring Context Internals. [Part 1](https://www.codeproject.com/Articles/1195578/Spring-Context-Internals-Part-Refresh), [Part 2](https://www.codeproject.com/Articles/1209919/Spring-Context-Internals-Part-Bean-Sources)
1. [Spring изнутри. Этапы инициализации контекста](https://habr.com/ru/post/222579/)
1. Boot yourself, Spring is coming. [Часть 1](https://www.youtube.com/watch?v=UYre4_bytD4), [Часть 2](https://www.youtube.com/watch?v=wQcZK0Tln30)
1. [Spring – Глубоко и не очень](https://www.youtube.com/watch?v=nGfeSo52_8A)
1. [Spring Puzzlers: тонкости и нюансы работы Spring](https://www.youtube.com/watch?v=U8MtGYa04v8)
1. [Spring Puzzlers: второй сезон](https://www.youtube.com/watch?v=BFEgLtFLdRI)
1. [Spring Patterns](https://www.youtube.com/watch?v=61duchvKI6)
1. [Проклятие Spring Test](https://www.youtube.com/watch?v=7mZqJShu_3c)
1. [Spring Boot docs](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) 