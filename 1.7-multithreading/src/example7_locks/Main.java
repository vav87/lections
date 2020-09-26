package example7_locks;

public class Main {
    public static void main(String args[]) {

        Telescope telescope = new Telescope();
        SuperComputer superComputer = new SuperComputer();

        Scientist rick = new Scientist("Rick", telescope, superComputer);
        Scientist morty = new Scientist("Morty", telescope, superComputer);

        new Thread(() -> {
            try {
                rick.makeTheory();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try {
                morty.researchGalaxy();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
