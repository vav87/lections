package example5_deadlock;

public class Scientist {

    private final Object telescope;
    private final Object superComputer;
    private final String name;

    public Scientist(String name, Object telescope, Object superComputer) {
        this.name = name;
        this.telescope = telescope;
        this.superComputer = superComputer;
    }

    public void researchGalaxy() {
        synchronized (telescope) {
            for (int dataCollect = 0; dataCollect < 10000000; dataCollect++) {
                //collect data
                if (dataCollect % 1000000 == 0) {
                    printProgress("data collect " + dataCollect / 1000000);
                }
            }

            printProgress("waiting for computer");
            synchronized (superComputer) {
                //calculate results
            }
        }
    }

    public void makeTheory() {
        synchronized (superComputer) {
            for (int calculateModel = 0; calculateModel < 10000000; calculateModel++) {
                if (calculateModel % 1000000 == 0) {
                    printProgress("calculate theory " + calculateModel / 1000000);
                }
            }

            printProgress("waiting for telescope");
            synchronized (telescope) {
                //check theory
            }
        }
    }

    private void printProgress(String job) {
        System.out.println(name + ": " + job);
    }
}
