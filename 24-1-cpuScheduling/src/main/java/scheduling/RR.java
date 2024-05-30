package scheduling;

import java.util.*;

public class RR extends Scheduling {
    static final int TIMESLICE = 2;
    int curTimeSlice = 0;

    public RR(List<Thread> threadList) {
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

    @Override
    protected void scheduling() {
        if (curThread.getRemainingTime() == 0) changeThread();
        else if (!queue.isEmpty() && curTimeSlice == TIMESLICE) {
            queue.add(curThread);
            changeThread();
        }
    }

    private void changeThread() {
        curThread = queue.poll();
        if (curThread == null) throw new IllegalArgumentException("스케줄링에 공백이 생겼습니다.");
        curTimeSlice = 0;
    }

    @Override
    protected void processingThread() {
        currentTime++;
        curTimeSlice++;
        curThread.decreaseRemainingTime();
    }
}
