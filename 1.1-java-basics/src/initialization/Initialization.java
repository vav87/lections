package initialization;

public class Initialization {

    public static void main(String[] args) {
        new Person();
    }
}

class Person {

    Person() {
        System.out.println("constructor");
    }

    static {
        System.out.println("static block");
    }

    {
        System.out.println("non static block");
    }
}