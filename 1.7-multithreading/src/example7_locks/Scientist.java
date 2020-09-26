package example7_locks;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Scientist {

    private final Telescope telescope;
    private final SuperComputer superComputer;
    private final String name;

    public Scientist(String name, Telescope telescope, SuperComputer superComputer) {
        this.name = name;
        this.telescope = telescope;
        this.superComputer = superComputer;
    }

    public void researchGalaxy() throws InterruptedException {
        telescope.lock.lock();
        try {
            for (int dataCollect = 0; dataCollect < 10000000; dataCollect++) {
                //collect data
                if (dataCollect % 1000000 == 0) {
                    printProgress("data collect " + dataCollect / 1000000);
                }
            }

            printProgress("trying get computer");
            if (!superComputer.lock.tryLock()) {
                printProgress("computer is busy. releasing telescope");
                telescope.lock.unlock();
                lockBoth();
            }
            finishResearchGalaxy();
            superComputer.lock.unlock();
        } finally {
            telescope.lock.unlock();
        }

    }

    private void finishResearchGalaxy() {
        printProgress("finish research");
    }

    public void makeTheory() throws InterruptedException {
        superComputer.lock.lock();
        try {
            for (int calculateModel = 0; calculateModel < 10000000; calculateModel++) {
                if (calculateModel % 1000000 == 0) {
                    printProgress("calculate theory " + calculateModel / 1000000);
                }
            }

            printProgress("trying get telescope");
            if (!telescope.lock.tryLock()) {
                printProgress("telescope is busy, releasing computer");
                superComputer.lock.unlock();
                lockBoth();
            }
            checkTheory();
            telescope.lock.unlock();
        } finally {
            superComputer.lock.unlock();
        }
    }

    private void checkTheory() {
        printProgress("check theory");
    }

    private void printProgress(String job) {
        System.out.println(name + ": " + job);
    }

    private void lockBoth() throws InterruptedException {
        boolean pcLock = false;
        boolean telescopeLock = false;
        while (!(pcLock && telescopeLock)) {
            printProgress("Something is busy");
            Thread.sleep(new Random().nextInt(100));
            pcLock = superComputer.lock.tryLock(1, TimeUnit.SECONDS);
            telescopeLock = telescope.lock.tryLock();
            if (!pcLock && telescopeLock) {
                telescope.lock.unlock();
            }
            if (!telescopeLock && pcLock) {
                superComputer.lock.unlock();
            }
        }
    }
}
