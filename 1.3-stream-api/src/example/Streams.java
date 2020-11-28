package example;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

class Streams {
    public static void main(String[] args) throws IOException {
        //Как найти все имена, начинающиеся с буквы "А" и вывести на консоль?
        List<String> names = List.of("Alex", "Mike", "Tony", "Artur", "Nick");

// Способы создания стримов

// Примитивные типы
//         IntStream intStream = IntStream.of(1, 2, 3, 4);
//         IntStream intStreamWithRange = IntStream.rangeClosed(1, 100); //1..100
//
//         DoubleStream doubleStream = DoubleStream.of(1.0, 2.0, 3.0, 4.0);

// Коллекции
// подойдут все коллекции: List, Set, Queue, кроме Map
//  Stream<String> names = List.of("Alex", "Mike", "Tony").stream();

// Объекты файловой системы
//        Stream<String> lines = Files.lines(Paths.get("example.txt"));

//Из значений
//        Stream<String> streamFromValues = Stream.of("a1", "a2", "a3");

//Массива
//        Stream<String> streamFromArrays = Arrays.stream(new String[]{"a1", "a2", "a3"});

//С помощью билдера
//        Stream.builder().add("a1").add("a2").add("a3").build();

//Бесконечные стримы с помощью iterate/generate
//        Stream<Event> events = Stream.generate(() -> new Event(UUID.randomUUID(), "description"));
//        events.forEach(System.out::println);

//        Stream<Integer> intStream = Stream.iterate(1, val -> val + 1);
//        Stream<Integer> intStream = Stream.iterate(1, val -> val <=10, val -> val + 1);
//       intStream.forEach(System.out::println);

//Параллельные стримы на примере коллекции
//        Stream<String> names = List.of("Alex", "Mike", "Tony").parallelStream();

//Терминальные операции

// Коллектор в для преобразования в коллекцию
//        var setOfNames = names.stream().collect(Collectors.toSet());
//        setOfNames.forEach(System.out::println);

// to Map
//        var mapOfNames = names.stream().collect(Collectors.toMap(
//                key -> key,
//                value -> "This is name"));

//Терминальный foreach
//        names.stream()
//                .forEach(System.out::println);

// Поиск первого попавшегося элемента или любого. Обычно используется вместе с filter
//        names.stream().findFirst();
//        names.stream().findAny();

// Поиск совпадений: anyMatch/noneMatch/allMatch -> возвращают boolean
//        names.stream()
//                .anyMatch(name -> name.startsWith("A"));
//        names.stream()
//                .noneMatch(name -> name.length() > 10);

//Подсчитать кол-во элементов
//        System.out.println(names.stream().count());

// reduce
//        Stream.of(1, 2, 4).reduce((s1, s2) -> s1 + s2).get();

// Statistic
//        IntStream.of(1, 2, 3, 4).summaryStatistics();

// Основные промежуточные операции

//Map - преобразование элементов стрима
//        names.stream()
//                .map(name -> "This is: " + name)
//                .forEach(System.out::println);

//Фильтрация элементов по условию
//        names.stream()
//                .filter(name -> name.startsWith("A"))
//                .forEach(System.out::println);

//Peek - взятие элемента, действие и возвращение стрима. Похож на foreach, но не терминальный. Удобно использовать с void
//        names.stream()
//                .peek(name -> System.out.println("This is: "))
//                .forEach(System.out::print);

// Пример украден))

//        List<Human> humans = List.of(
//                new Human("Sam", List.of("Buddy", "Lucy")),
//                new Human("Bob", List.of("Frankie", "Rosie")),
//                new Human("Marta", List.of("Simba", "Tilly")));
//
//        List<String> petNames = humans.stream()
//                .map(human -> human.getPets()) //преобразовываем Stream<Human> в Stream<List<Pet>>
//                .flatMap(pets -> pets.stream())//"разворачиваем" Stream<List<Pet>> в Stream<Pet>
//                .collect(Collectors.toList());
//
//        System.out.println(petNames); // output [Buddy, Lucy, Frankie, Rosie, Simba, Tilly]

//    names.parallelStream()
//        .peek(it -> System.out.printf("Thread [%s] peek: %s%n", Thread.currentThread().getName(), it))
//        .forEach(String::toLowerCase);


    }

    private static class Event {
        private final UUID uuid;
        private final String description;

        private Event(UUID uuid, String description) {
            this.uuid = uuid;
            this.description = description;
        }

        @Override
        public String toString() {
            return "Event{" +
                    "uuid=" + uuid +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    private static class Human {
        private final String name;
        private final List<String> pets;

        private Human(String name, List<String> pets) {
            this.name = name;
            this.pets = pets;
        }

        public String getName() {
            return name;
        }

        public List<String> getPets() {
            return pets;
        }
    }
}