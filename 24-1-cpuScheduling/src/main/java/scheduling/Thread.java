package scheduling;

public class Thread implements Comparable<Thread> {
    private static int count = 0;   // 스레드 번호 생성을 위한 변수
    private int id;                 // 스레드 번호
    private int arrivalTime;        // 도착 시간
    private int runTime;            // 소요 시간
    private int waitingTime;        // 대기 시간
    private int remainingTime;      // 남은 소요 시간

    public Thread(int arrivalTime, int runTime) {
        this.id = ++count;
        this.arrivalTime = arrivalTime;
        this.runTime = runTime;
        this.remainingTime = runTime;
    }

    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getRunTime() {
        return runTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void increaseWaitingTime() {
        ++this.waitingTime;
    }

    public void decreaseRemainingTime() {
        this.remainingTime--;
    }

    @Override
    public int compareTo(Thread o) {
        return arrivalTime - o.getArrivalTime();
    }
}
