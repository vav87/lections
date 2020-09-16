#Лямбда исчисления
**_Опциональная лекция_**

В JDK 8 вместе с самой функциональностью лямбда-выражений также было добавлено некоторое количество встроенных функциональных интерфейсов, которые мы можем использовать в различных ситуациях и в различные API в рамках JDK 8. 

В частности, ряд далее рассматриваемых интерфейсов широко применяется в Stream API - новом прикладном интерфейсе для работы с данными. Рассмотрим основные из этих интерфейсов:

* **Predicate<T>**
```java
Predicate<Integer> isPositive = x -> x > 0;
 
System.out.println(isPositive.test(5)); // true
System.out.println(isPositive.test(-7)); // false
```
* **BinaryOperator<T>**
```java
BinaryOperator<Integer> multiply = (x, y) -> x*y;
 
System.out.println(multiply.apply(3, 5)); // 15
System.out.println(multiply.apply(10, -2)); // -20
```
* **UnaryOperator<T>**
```java
UnaryOperator<Integer> square = x -> x*x;
System.out.println(square.apply(5)); // 25
```
* **Function<T,R>**
```java
Function<Integer, String> convert = x-> String.valueOf(x) + " долларов";
System.out.println(convert.apply(5)); // 5 долларов

Function<LocalDateTime, String> converter = date -> format(
    "Day is:%s, month:%s, year:%s", date.getDayOfMonth(), date.getMonth(), date.getYear());
```
* **Consumer<T>**
```java
Consumer<Integer> printer = x-> System.out.printf("%d долларов \n", x);
printer.accept(600); // 600 долларов
```
* **Supplier<T>**
```java
Supplier<User> userFactory = ()->{
     
    Scanner in = new Scanner(System.in);
    System.out.println("Введите имя: ");
    String name = in.nextLine();
    return new User(name);
};
 
User user1 = userFactory.get();
User user2 = userFactory.get();
```

* **Свой интерфейс**
```java
    @FunctionalInterface
    public interface Calculator {
        int calc(int x, int y);
    }

Calculator multiply = (x, y) -> { return x*y; };
Calculator max = Integer::max;

System.out.println(multiply.calc(5, 3)); // 15
System.out.println(max.calc(5, 3)); // 5
```