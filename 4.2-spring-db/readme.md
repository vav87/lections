# JDBC / Hibernate / Spring Data JPA

## Что такое JDBC?

JDBC (Java DataBase Connectivity) — платформенно независимый промышленный стандарт, 
другими словами спецификация, описывающая взаимодействия Java-приложений 
с различными СУБД. Спецификация реализована в виде пакетов java.sql или javax.sql, 
входящих в состав JDK.

## Что такое JDBC-driver?

JDBC-driver - это компонент, специфичный для каждой базы данных. Для 
взаимодействия с определенной версии базы данных нам необходимо использовать
соответствующий драйвер

## Как зарегистрировать драйвер JDBC?

- java.sql.DriverManager.registerDriver(%объект класса драйвера%)
- Class.forName(«полное имя класса драйвера»).newInstance()
- Class.forName(«полное имя класса драйвера»)

## Какие есть особенности работы с JDBC?

- Много самописного кода
- Нет ORM
- Работает быстрее

## Что такое DriverManager в JDBC?

java.sql.DriverManager - позволяет загрузить и зарегистрировать необходимый JDBC-драйвер,
а затем получить соединение с базой данных

## Что такое Statement в JDBC?

java.sql.Statement, java.sql.PreparedStatement и java.sql.CallableStatement - 
это интерфейсы, которые позволяют сформировать и отправить запрос к источнику данных

## Что такое ResultSet в JDBC?

java.sql.ResultSet - объявляет методы, которые позволяют перемещаться по набору данных 
и считывать значения отдельных полей в текущей записи

## Что такое JPA?

JPA – это спецификация, описывающая технологию объектно-реляционное отображение простых 
Java объектов и предоставляющая API для сохранения, получения и управления такими 
объектами. Сам JPA не умеет ни сохранять, ни управлять объектами, JPA только определяет 
правила игры: как что-то будет действовать. JPA также определяет интерфейсы, 
которые должны будут быть реализованы провайдерами. Плюс к этому JPA определяет 
правила о том, как должны описываться метаданные отображения и о том, как должны 
работать провайдеры. Дальше, каждый провайдер, реализуя JPA определяет получение, 
сохранение и управление объектами. У каждого провайдера реализация разная

## Что такое Hibernate?

Hibernate — самая популярная реализация спецификации JPA, предназначенная для решения 
задач объектно-реляционного отображения (ORM), то есть для представления Java объектов 
в виде реляционных таблиц баз данных

- <img src="./media/архитектура_hibernate.jpg" width=800px>

## Как можно сконфигурировать Hibernate?

Существует четыре способа конфигурации работы с Hibernate:
— используя аннотации
— hibernate.cfg.xml
— hibernate.properties
— persistence.xml

## Что такое Entity и @Entity в Hibernate и какие к нему предъявляются требования?

Entity - это часть спецификации JPA. Определение Entity из спецификации JPA - это легковесный хранимый 
объект бизнес логики (persistent domain object), который менеджится Hibernate`ом. @Entity - это аннотация, 
которая указывает Hibernate, что данный класс является сущностью Entity в Hibernate (entity bean). 
Entity похожи на Bean в Spring. Entity имеет свой жизненный цикл

Чтобы класс мог быть сущностью, к нему предъявляются следующие требования:
- Entity класс должен быть отмечен аннотацией Entity или описан в XML файле конфигурации JPA
- Entity класс должен содержать public или protected конструктор без аргументов 
(он также может иметь конструкторы с аргументами)
- Entity класс должен быть классом верхнего уровня (top-level class)
- Entity класс не может быть enum или интерфейсом
- Entity класс не может быть финальным классом (final class)
- Entity класс не может содержать финальные поля или методы, если они участвуют в маппинге 
(persistent final methods or persistent final instance variables)
- Если объект Entity класса будет передаваться по значению как отдельный объект (detached object)
 например через удаленный интерфейс (through a remote interface), он так же должен реализовывать Serializable интерфейс
- Поля Entity класс должны быть напрямую доступны только методам самого Entity класса и не должны 
быть напрямую доступны другим классам, использующим этот Entity. Такие классы должны обращаться только 
к методам (getter/setter методам или другим методам бизнес-логики в Entity классе)
- Entity класс должен содержать первичный ключ, то есть атрибут или группу атрибутов которые уникально
 определяют запись этого Entity класса в базе данных

При этом entity может:
- Содержать непустые конструкторы
- Наследоваться и быть наследованным
- Содержать другие методы и реализовывать интерфейсы

## Какие бывают связи между Entity?

- @OneToOne
- @ManyToOne
- @ManyToMany

## Что такое EntityManager?

EntityManager - это объект в спецификации JPA, вызывая методы которого можно управлять сущностями. Предоставляет 
объектное API для разработчика для работы с базой данных. Фабрика EntityManagerFactory может создавать объекты 
EntityManager, которые в отличие от фабрики, достаточно легковесен и поэтому зачастую создаются по месту использования 
и в больших количествах. Если проводить аналогию с обычным JDBC, то EntityManagerFactory будет аналогом DataSource, 
а EntityManager аналогом Connection. Экземпляр EntityManager содержит в себе "персистентный контекст" – набор 
экземпляров сущностей, загруженных из БД или только что созданных. Персистентный контекст является 
своего рода кэшем данных в рамках транзакции. EntityManager автоматически сбрасывает в БД все изменения, 
сделанные в его персистентном контексте, в момент коммита транзакции, либо при явном вызове метода flush().

## Что такое Session в Hibernate?

Session - это интерфейс часть Hibernate API, предоставленный в Hibernate, с помощью которой мы можем управлять объектами Entity.
Session помогает создавать объекты запросов для получение персистентных объектов. 
(персистентный объект — объект который уже находится в базе данных; объект запроса — объект который получается когда 
мы получаем результат запроса в базу данных, именно с ним работает приложение). Session наследует EntityManager.
Обьект Session можно получить из SessionFactory 

```java
void someMethod() {
    Session session = sessionFactory.openSession();
}
```

Session является хранителем обязательного кэша первого уровня

## В каких бывают состояниях объекты в Hibernate?

- <img src="./media/цикл_hibernate.png" width=800px>
- <img src="./media/цикл_jpa.png" width=800px>

## Какие бывают уровни кеша в Hibernate?

Всего в Hibernate 3 уровня кеширования:
- Кеш первого уровня (First-level cache)
- Кеш второго уровня (Second-level cache)
- Кеш запросов (Query cache)

```java
public void cacheWorkExample() {
    SharedDoc persistedDoc = (SharedDoc) session.load(SharedDoc.class, docId);
    System.out.println(persistedDoc.getName());
    user1.setDoc(persistedDoc);

    persistedDoc = (SharedDoc) session.load(SharedDoc.class, docId);
    System.out.println(persistedDoc.getName());
    user2.setDoc(persistedDoc);
}
```

## Кеш первого уровня

Кеш первого уровня всегда привязан к объекту сессии. Hibernate всегда по умолчанию использует этот кеш и 
его нельзя отключить

```java
public void cacheWorkExample() {
    SharedDoc persistedDoc = (SharedDoc) session.load(SharedDoc.class, docId);
    System.out.println(persistedDoc.getName());
    user1.setDoc(persistedDoc);

    persistedDoc = (SharedDoc) session.load(SharedDoc.class, docId);
    System.out.println(persistedDoc.getName());
    user2.setDoc(persistedDoc);
}
```

## Кеш второго уровня

Если кеш первого уровня привязан к объекту сессии, то кеш второго уровня привязан к объекту-фабрике сессий 
(Session Factory object). Что как бы подразумевает, что видимость этого кеша гораздо шире кеша первого уровня

```java
public void cacheWorkExample() {
    Session session = factory.openSession();
    SharedDoc doc = (SharedDoc) session.load(SharedDoc.class, 1L);
    System.out.println(doc.getName());
    session.close();

    session = factory.openSession();
    doc = (SharedDoc) session.load(SharedDoc.class, 1L);   
    System.out.println(doc.getName());       
    session.close();  
}
```

В данном примере будет выполнено 2 запроса в базу, это связано с тем, что по умолчанию кеш второго уровня отключен. 
Для включения необходимо добавить следующие строки в Вашем конфигурационном файле JPA (persistence.xml):

```xml
<properties>
    ...
    <property name="hibernate.cache.use_second_level_cache" value="true"/>
    <property name="hibernate.cache.region.factory_class" 
              value="org.hibernate.cache.ehcache.EhCacheRegionFactory"/>
    ...
</properties>
```

Обратите внимание на вторую строку. На самом деле, хибернейт сам не реализует кеширование как таковое.
А лишь предоставляет структуру для его реализации, поэтому подключить можно любую реализацию, которая соответствует 
спецификации фреймворка. Из популярных реализаций можна выделить следующие:
- EHCache
- OSCache
- SwarmCache
- JBoss TreeCache

Помимо всего этого, вероятней всего, Вам также понадобится отдельно настроить и саму реализацию кеша. В случае с EHCache это нужно сделать в файле ehcache.xml. Ну и в завершение еще нужно указать самому хибернейту, что именно кешировать. К счастью, это очень легко можно сделать с помощью аннотаций, например так:

```java
@Entity
@Table(name = "shared_doc")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SharedDoc{
    private Set<User> users;
}
```

Только после всех этих манипуляций кеш второго уровня будет включен и в примере выше будет выполнен только 1 запрос в базу.
Еще одна важная деталь про кеш второго уровня про которую стоило бы упомянуть — хибернейт не хранит сами 
объекты Ваших классов. Он хранит информацию в виде массивов строк, чисел и т. д. И идентификатор 
объекта выступает указателем на эту информацию. Концептуально это нечто вроде Map, в которой id объекта — 
ключ, а массивы данных — значение. Приблизительно можно представить себе это так:

```
1 -> { "Pupkin", 1, null , {1,2,5} }
```

Что есть очень разумно, учитывая сколько лишней памяти занимает каждый объект.
Помимо вышесказанного, следует помнить — зависимости Вашего класса по умолчанию также не кешируются. 
Например, если рассмотреть класс выше — SharedDoc, то при выборке коллекция users будет доставаться из БД, 
а не из кеша второго уровня. Если Вы хотите также кешировать и зависимости, то класс должен выглядеть так:

```java
@Entity
@Table(name = "shared_doc")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SharedDoc{
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<User> users;
}
```

И последняя деталь — чтение из кеша второго уровня происходит только в том случае, если нужный объект не был найден 
в кеше первого уровня.

## Кеш запросов

Перепишем первый пример так:
```java
Query query = session.createQuery("from SharedDoc doc where doc.name = :name");

SharedDoc persistedDoc = (SharedDoc) query.setParameter("name", "first").uniqueResult();
System.out.println(persistedDoc.getName());
user1.setDoc(persistedDoc);

persistedDoc = (SharedDoc) query.setParameter("name", "first").uniqueResult();
System.out.println(persistedDoc.getName());
user2.setDoc(persistedDoc);
```

Результаты такого рода запросов не сохраняются ни кешом первого, ни второго уровня. 
Это как раз то место, где можно использовать кеш запросов. Он тоже по умолчанию отключен. 
Для включения нужно добавить следующую строку в конфигурационный файл:
```xml
<property name="hibernate.cache.use_query_cache" value="true"/>
```

а также переписать пример выше добавив после создания объекта Query (то же справедливо и для Criteria):
```java
Query query = session.createQuery("from SharedDoc doc where doc.name = :name");
query.setCacheable(true);
```

Кеш запросов похож на кеш второго уровня. Но в отличии от него — ключом к данным кеша выступает не
идентификатор объекта, а совокупность параметров запроса. А сами данные — это идентификаторы объектов соответствующих 
критериям запроса. Таким образом, этот кеш рационально использовать с кешем второго уровня.

- Взято из статьи автора https://habr.com/ru/post/135176/

## Что такое EntityManager и какие основные его функции вы можете перечислить?

EntityManager это интерфейс, который описывает API для всех основных операций над Enitity, 
получение данных и других сущностей JPA. По сути главный API для работы с JPA. Основные операции:
- Для операций над Entity: persist (добавление Entity под управление JPA), merge (обновление), remove (удаления), 
refresh (обновление данных), detach (удаление из управление JPA), lock (блокирование Enity от изменений в других thread),
- Получение данных: find (поиск и получение Entity), createQuery, createNamedQuery, 
createNativeQuery, contains, createNamedStoredProcedureQuery, createStoredProcedureQuery
- Получение других сущностей JPA: getTransaction, getEntityManagerFactory, getCriteriaBuilder, getMetamodel, getDelegate
- Работа с EntityGraph: createEntityGraph, getEntityGraph
- Общие операции над EntityManager или всеми Entities: close, isOpen, getProperties, setProperty, clear

## Какие четыре статуса жизненного цикла Entity объекта (Entity Instance’s Life Cycle) вы можете перечислить?

У Entity объекта существует четыре статуса жизненного цикла: new, managed, detached, или removed
- new — объект создан, но при этом ещё не имеет сгенерированных первичных ключей и пока ещё не сохранен в базе данных
- managed — объект создан, управляется JPA, имеет сгенерированные первичные ключи
- detached — объект был создан, но не управляется (или больше не управляется) JPA
- removed — объект создан, управляется JPA, но будет удален после commit'a транзакции

## Как влияет операция persist на Entity объекты каждого из четырех статусов?

- Если статус Entity new, то он меняется на managed и объект будет сохранен в базу при commit'е транзакции 
или в результате flush операций,
- Если статус уже managed, операция игнорируется, однако зависимые Entity могут поменять статус на managed, 
если у них есть аннотации каскадных изменений,
- Если статус removed, то он меняется на managed,
- Если статус detached, будет выкинут exception сразу или на этапе commit'а транзакции,

## Как влияет операция merge на Entity объекты каждого из четырех статусов?

- Если статус detached, то либо данные будет скопированы в существующей managed entity с тем же первичным ключом, 
либо создан новый managed в который скопируются данные
- Если статус Entity new, то будет создана новый managed entity, в который будут скопированы данные прошлого объекта
- Если статус managed, операция игнорируется, однако операция merge сработает на каскадно зависимые Entity, если их статус не managed
- Если статус removed, будет выкинут exception сразу или на этапе commit'а транзакции

## Как влияет операция remove на Entity объекты каждого из четырех статусов?

- Если статус Entity new, операция игнорируется, однако зависимые Entity могут поменять статус на removed, 
если у них есть аннотации каскадных изменений и они имели статус managed
- Если статус managed, то статус меняется на removed и запись объект в базе данных будет удалена при commit'е 
транзакции (так же произойдут операции remove для всех каскадно зависимых объектов)
- Если статус removed, то операция игнорируется
- Если статус detached, будет выкинут exception сразу или на этапе commit'а транзакции

## Зачем нужен метод flush()?

Объекты в Hibernate ни сразу попадают в базу данных, а живут какое-то время внутри Persistent контекста. 
Чтобы отправить все объекты из Persistent контекста в базу можно использовать явный вызов метода flush()

```java
	/**
	 * Force this session to flush. Must be called at the end of a
	 * unit of work, before committing the transaction and closing the
	 * session (depending on {@link #setFlushMode(FlushMode)},
	 * {@link Transaction#commit()} calls this method).
	 * <p/>
	 * <i>Flushing</i> is the process of synchronizing the underlying persistent
	 * store with persistable state held in memory.
	 *
	 * @throws HibernateException Indicates problems flushing the session or
	 * talking to the database.
	 */
	void flush() throws HibernateException;
```

## Что такое n+1 проблема в Hibernate?

Предположим, у вас есть коллекция из объектов класса House, и каждый House имеет коллекцию из объектов класса Flat. 
Другими словами, House -> Flat - это отношение one-to-many.

Теперь предположим, что вам нужно перебрать все квартиры во всех домах. 
Тогда можно столкнуться с проблемой, так как Hibernate делает следующее:

```sql
SELECT * FROM House;
```

И самое интересное, что потом для каждого дома попытается получить квартиру одну за одной:

```java
for (House house : houses) {
    SELECT * FROM House WHERE flatId = ?
}
```

Мы имеем один запрос на список домов, а затем N дополнительных запросов, где N-общее квартир. Такая схема работы 
является не оптимально и могла бы быть заменена на следующее выражение:

```sql
SELECT * FROM Wheel;
```

Такая оптимизация позволяет сократить число обращений к базе данных с N+1 до 2



