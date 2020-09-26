package example7_locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SuperComputer {
    Lock lock = new ReentrantLock();
}
