package scheduling;

import java.util.*;

public class SRTF extends Scheduling {

    public SRTF(List<Thread> threadList) {
        super(threadList, new PriorityQueue<>(Comparator.comparingInt(Thread::getRemainingTime)));
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

    @Override
    protected void scheduling() {
        if (curThread.getRemainingTime() == 0) {
            curThread = queue.poll();
            if (curThread == null) throw new IllegalArgumentException("스케줄링에 공백이 생겼습니다.");
        }
        else if (!queue.isEmpty() && queue.peek().getRemainingTime() < curThread.getRemainingTime()) {
            queue.add(curThread);
            curThread = queue.poll();
        }
    }
}
