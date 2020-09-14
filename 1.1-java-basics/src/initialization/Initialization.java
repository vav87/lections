package initialization;

public class Initialization {

    public static void main(String[] args) {
        new Child(); // 123456
    }
}

class Parent {
    static {
        System.out.print("1");
    }

    {
        System.out.print("3");
    }

    Parent() {
        System.out.print("4");
    }
}

class Child extends Parent {
    static {
        System.out.print("2");
    }

    {
        System.out.print("5");
    }

    Child() {
        System.out.print("6");
    }
}
