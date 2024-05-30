package scheduling;

import java.util.LinkedList;
import java.util.List;

public class FCFS extends Scheduling {
    public FCFS(List<Thread> threadList) {
        super(threadList, new LinkedList<>());
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
