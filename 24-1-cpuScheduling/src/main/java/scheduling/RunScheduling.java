package scheduling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class RunScheduling {
    private static final int SCHEDULING_NUMBER = 4;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    List<Thread> threadList = new ArrayList<>();

    public RunScheduling() throws IOException {
        int input = selectSchedulingAlgorithm();
        inputThreadInformation();
        Collections.sort(threadList);
        processSchedulingAlgorithm(input);
        printThreadResult(threadList);
    }

    private int selectSchedulingAlgorithm() throws IOException {
        System.out.println("CPU 스케줄링 알고리즘");
        System.out.println("1.FCFS\t2.SJF\t3.SRTF\t4.RR");
        System.out.print("실행할 알고리즘의 숫자를 입력하세요 : ");

        return readIntegerInput(SCHEDULING_NUMBER, "실행할 알고리즘의 숫자를 입력하세요 : ");
    }

    private void inputThreadInformation() throws IOException {
        System.out.print("스레드 개수를 입력하세요 : ");
        int threadSize = readIntegerInput(Integer.MAX_VALUE, "스레드 개수를 입력하세요 : ");
        System.out.print("도착시간과 실행시간을 순서대로 입력하세요 : ");

        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            try {
                if (st.countTokens() != threadSize * 2) throw new IllegalArgumentException();

                for (int i = 0; i < threadSize; i++) {
                    int arrivalTime = Integer.parseInt(st.nextToken());
                    int runTime = Integer.parseInt(st.nextToken());
                    threadList.add(new Thread(arrivalTime, runTime));
                }

                break;
            } catch (IllegalArgumentException e) {
                System.out.print("도착시간과 실행시간을 순서대로 입력하세요 : ");
            }
        }
    }

    private void processSchedulingAlgorithm(int input) {
        Scheduling scheduling = switch (input) {
            case 1 -> new FCFS(threadList);
            case 2 -> new SJF(threadList);
            case 3 -> new SRTF(threadList);
            case 4 -> new RR(threadList);
            default -> throw new IllegalArgumentException("잘못된 알고리즘 숫자를 입력하셨습니다.");
        };
    }

    private double calcWaitingTime(List<Thread> threadList) {
        return threadList.stream()
                .mapToLong(Thread::getWaitingTime)
                .average()
                .orElse(0.0);
    }

    private void printThreadResult(List<Thread> threadList) {
        System.out.println("FCFS 스케줄링 결과:");
        System.out.println("평균 대기 시간 : " + calcWaitingTime(threadList));
        System.out.println("----- 스레드 별 소요 시간 및 대기 시간 -----");

        threadList.sort(new Comparator<Thread>() {
            @Override
            public int compare(Thread o1, Thread o2) {
                return o1.getId() - o2.getId();
            }
        });

        for (Thread thread : threadList) {
            System.out.println((thread.getId()) + "번째 스레드 - 소요 시간 : " + (thread.getRunTime() + thread.getWaitingTime())
                    + ", 대기 시간 : " + thread.getWaitingTime());
        }
    }

    private int readIntegerInput(int maxValue, String message) throws IOException {
        while (true) {
            try {
                int input = Integer.parseInt(br.readLine());
                if (input < 1 || input > maxValue) {
                    throw new IllegalArgumentException();
                }
                return input;
            } catch (IllegalArgumentException e) {
                System.out.print(message);
            }
        }
    }
}
