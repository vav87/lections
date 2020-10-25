# Лекция: Spring Boot Test

## План лекции
1. Как писать тесты. Single Responsibility
1. Виды тестирования.
1. @SpringBootTest, сканирование classpath, поиск конфигураций
1. @WebMvcTest, @DataJpaTest, Embedded Database
1. Test Containers
1. Restdocs

## Тестирование
Unit-тесты – важная составляющая программы, которая гарантирует работоспособность
каждой части программы в отдельности. У нас есть [сервис](src/main/java/ru/digitalhabbits/sbt/service/UserServiceImpl.java):
```java
@Service
@RequiredArgsConstructor
public class UserServiceImpl
        implements UserService {
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    ...

    @Override
    @Transactional(readOnly = true)
    public UserInfoResponse createUser(CreateUserRequest request) {
        User user = new User()
                .setFirstName(request.getFirstName())
                .setMiddleName(request.getMiddleName())
                .setLastName(request.getLastName())
                .setAge(request.getAge());

        user = userRepository.save(user);
        notificationService.notify(EventType.CREATED, user.toString());

        return buildUserInfoResponse(user);
    }
    
    ...
}
```
В нем есть метод `createUser` – создание нового пользователя. Метод состоит
из вызова `userRepository.save(...)` – слой работы с базой данных и вызова другого сервиса `notificationService.notify(...)`.
Для unit-тестирования нас _не интересует_ корректность поведения других сервисов, мы проверяем логику работы `UserService`,
а вызовы в другие сервисы мы мокируем. Корректность работы других сервисов мы проверяем в соответствующих
unit-тестах. В результате мы получаем гарантию, что каждый сервис работает корректно в случае, если корректно работают сервисы, которые он вызывает.
Unit-тесты не дают гарантию, что система работает корректно (!), но они дают _надежду_, что при рефакторинге
поведение изменяемых классов остается корректным.

Для того, чтобы тесты было проще писать, нужно чтобы проверяемый класс отвечал только за свою функциональность
и не делал ничего больше, т.е. следовал принципу Single Responsibility.  
Если класс выполняем множество различных действий, то тест получится большим и тяжело поддерживаемым,
т.к. будет большим и зависить от большого числа компонентов.
 
### Разберемся с терминологией
* mock – замена реального объекта некоторой заглушкой, который мы задаем нужное нам поведение;
* spy – обертка над реальным объектом для контроля его выполнения. 

### Виды тестирования
* unit-тесты – проверка отдельного класса программы;
* сервисные тесты – проверка, что изолированная система работает корректно (т.е. все внешние вызовы замоканы);
* интеграционные тесты – тестирование контура с реальными запросами и ответами между системами;
* нагрузочные тесты (стресс-тесты) – тестирование поведения системы под большим объемом данных;   

## Spring Boot Test
Сервисы на Spring Boot можно грубо поделить на три части:
* тесты на API (слой контроллеров: `@Controller`, `@ControllerAdvice`, spring security и т.п.);
* тесты на БД (слой работы с БД: `@Entity`, `CrudRepository`, DAO и т.п.);
* тесты на бизнес логику (т.е. `@Service` и все остальное вокруг).

Для service тестов (фактически бизнес логика) лучше использовать "чистые" тесты без Spring. [пример](src/test/java/ru/digitalhabbits/sbt/service/UserServiceTestPure.java)  
Если это сделать нельзя, то для работы тестов нужно использовать аннотацию `@SpringBootTest`. Разберемся как она работает:
* Если указаны файлы конфигурации, то используются только они `@SpringBootTest(classes = TestRepositoryConfiguration.class)`.
* Если в `@SpringBootTest` не указана конфигурация, то выполняется поиск соответствующей конфигурации.
* поиск останавливается, если находится аннотация `@Configuration`;
* если встречается аннотация `@TestConfiguration`, то поиск выполняется до нахождения `@SpringBootConfiguration`,
появление `@Configuration` не остановит сканирование. Это сделано, чтобы в `@TestConfiguration` можно было "подправить" конфигурацию приложения.
* Т.к. тестовый classpath является наследником main, то поиск `@SpringBootConfiguration` происходит снизу вверх
по тестовому classpath (т.е. от `ru.digitalhabbits.sbt.service` -> `ru`), а потом по main от корня вверх (т.е. от `ru` -> `ru.digitalhabbits.sbt`),
т.е. в какой-то момент мы упремся в `@SpringBootApplication`, который запустит построение всего контекста приложения.

[пример](src/test/java/ru/digitalhabbits/sbt/service/UserServiceTest.java)

### @WebMvcTest
Аннотация используется для тестирования слоя MVC: использование этой аннотации _отключает_ автоконфигурацию всех компонентов,
не относящихся к MVC: т.е. оставляет `@Controller`, `@ControllerAdvice`, `@JsonComponent`, `Converter`/`GenericConverter`, `Filter`, 
`WebMvcConfigurer`, `HandlerMethodArgumentResolver` и bean'ы относящиеся к Spring Security, но не создает `@Component`, `@Service` или `@Repository` bean'ы.
[пример](src/test/java/ru/digitalhabbits/sbt/web/UserControllerTest.java)  

### @DataJpaTest
По-умолчанию, тесты аннотированные `@DataJpaTest` используют rollback-транзакции в конце каждого теста. Если не указано иное,
конфигурируется inmemory база данных, перезаписывающая `dataSource` bean'ы для работы с ней.

Обычно в роли inmemory базы данных используется [H2](https://www.h2database.com/html/main.html) – легковесная,
поддерживающая базовый синтаксис SQL92, скачивается и запускается как jar файл, т.е. для работы достаточно просто прописать в gradle.
С ней все хорошо работает, пока не используется специфичный для конкретной СУБД синтаксис или типы данных. Т.е. фактически,
пока все операции реализуются с помощью чистого jpa. 
[пример](src/test/java/ru/digitalhabbits/sbt/repository/UserRepositoryTestInMemoryDB.java)

## Test Containers
Но в какой-то момент проект становится большим и H2 начинает не хватать. В таком случае вознкиает необходимость
для тестов использовать реальную БД.  
Использование БД на тестовом контуре очень плохая практика, т.к. та база не изолирована и ее состояние может меняться со временем,
что не минуемо приведет к сложно обнаруживаемым проблемам. Например, вы работаете из-под пользователя с ID: 1, но кто-то случайно
изменил его статус с `FULL_IDENTIFIED` на `REGISTERED` и у вас перестали проходить операции оплаты с определенной суммы,
потому что для них требуется статус `FULL_IDENTIFIED`. Либо вы просто часто запускали тесты, и превысили лимит по операциям.

Решением этих проблем является пересоздание БД на каждый тест или группу тестов. Это и реализует проект
[Test Containers](https://www.testcontainers.org/): он поднимает нужный instance (не обязательно БД) в docker-контейнере,
а после прогона тестов, останавливает его. [пример](src/test/java/ru/digitalhabbits/sbt/repository/TestDatabaseConfiguration.java)
  
Соответственно, для него нужен Docker, а значит для локального запуска тестов он должен быть запущен на машине разработчика.
При прохождении CI/CD он должен быть либо установлен на runner'е, либо, если сборка уже происходит в docker,
то должен использоваться Docker-In-Docker образ.

## Rest DOCS
Практика хорошего кода говорит, что код должен быть читабельный. Документация в Confluence, комментарии в коде очень быстро
устаревают и утрачивают свою актуальность. Но что делать с описанием API, ведь его нужно предоставлять третьим лицам?    
Есть хорошее решение: OpenAPI (реализация [Springdoc OpenAPI](http://springdoc.org/) для Spring Boot приложений) –
по коду генерируется описание в формате json и отдается по запросу. Но иногда требуется "бумажный" вариант API,
для этого можно использовать проект [Spring REST Docs](https://spring.io/projects/spring-restdocs): он компанует документацию
в формате Asciidoctor с кусками описания, сгенерированных с помощью тестов. [пример](src/test/java/ru/digitalhabbits/sbt/web/UserControllerTest.java)

## Ссылки
1. [Проклятие Spring Test](https://www.youtube.com/watch?v=7mZqJShu_3c)
1. [Testcontainers: Год спустя](https://www.youtube.com/watch?v=xgZ8KyUDjvQ)
1. [Тестируем и плачем вместе со Spring Boot Test](https://www.youtube.com/watch?v=uc-cfX-5wQA)
1. [Spring Boot docs](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) 