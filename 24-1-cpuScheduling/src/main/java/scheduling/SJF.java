package scheduling;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SJF extends Scheduling {
    public SJF(List<Thread> threadList) {
        super(threadList, new PriorityQueue<>(Comparator.comparingInt(Thread::getRunTime)));
        run();
    }

    private void run() {
        while (exit()) {
            checkingThreadArrival();
            scheduling();
            processingThread();
            plusWaitingTime();
        }
    }
}
