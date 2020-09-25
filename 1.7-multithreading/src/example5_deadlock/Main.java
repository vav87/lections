package example5_deadlock;

public class Main {
    public static void main(String args[]) {

        Object telescope = new Object();
        Object superComputer = new Object();

        Scientist rick = new Scientist("Rick", telescope, superComputer);
        Scientist morty = new Scientist("Morty", telescope, superComputer);

        new Thread(() -> {
            rick.makeTheory();
        }).start();

        new Thread(() -> {
            morty.researchGalaxy();
        }).start();
    }
}
