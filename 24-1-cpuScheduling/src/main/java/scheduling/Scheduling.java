package scheduling;

import java.util.List;
import java.util.Queue;

public class Scheduling {
    Queue<Thread> queue;        // 대기 큐
    List<Thread> threadList;    // 스레드 리스트
    Thread curThread;           // 현재 실행중인 스레드
    int currentTime;            // 현재 시간
    int threadCount;            // 도착한 스레드 개수

    public Scheduling(List<Thread> threadList, Queue<Thread> queue) {
        this.threadList = threadList;
        this.queue = queue;
        curThread = threadList.get(threadCount++);
        if (curThread.getArrivalTime() != 0) throw new IllegalArgumentException("초기 실행 스레드가 없습니다.");
    }

    // 종료 조건 검사
    protected boolean exit() {
        return !threadList.stream().allMatch(thread -> thread.getRemainingTime() == 0);
    }

    // Thread 도착 확인
    protected void checkingThreadArrival() {
        if (threadCount < threadList.size() && currentTime == threadList.get(threadCount).getArrivalTime()) {
            queue.add(threadList.get(threadCount++));
        }
    }

    // Thread 스케줄링
    protected void scheduling() {
        if (curThread.getRemainingTime() == 0) {
            curThread = queue.poll();
            if (this.curThread == null) throw new IllegalArgumentException("스케줄링에 공백이 생겼습니다.");
        }
    }

    // Thread 작업 처리
    protected void processingThread() {
        currentTime++;
        curThread.decreaseRemainingTime();
    }

    // 대기 큐 Thread 대기 시간 증가
    protected void plusWaitingTime() {
        if (!queue.isEmpty()) {
            for (Thread thread : queue) {
                thread.increaseWaitingTime();
            }
        }
    }
}
