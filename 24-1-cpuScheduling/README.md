## 1. 목적
  이 프로그램에서는 다양한 CPU 스케줄링 알고리즘을 구현하고 시뮬레이션 결과를 확인하는 것이 목표이다. 총 4가지 스케줄링 알고리즘을 구현할 것이며, FCFS, SJF, SRTF 그리고 RR을 포함한다. 각 알고리즘을 통해 입력한 스레드들을 스케줄링을 시뮬레이션하고, 총 걸린 시간과 평균 대기 시간을 확인할 수 있다. 

## 2. 구현 계획
### 2.1 프로그램 언어 및 환경
- 프로그래밍언어 : Java
- 개발 환경
- Java : 17
- JDK : corretto-17 (Amazon Corretto version 17.0.10)
- IDE : IntelliJ IDEA

### 2.2 구현할 스케줄링 알고리즘
- FCFS (First-Come, First-Served)
- SJF (Shortest Job First)
- SRTF (Shortest Remaining Time First)
- RR (Round-Robin)

### 2.3 구현 내용
- 2.2에 서술된 스케줄링 알고리즘을 구현한다. 
- 사용자로부터 스레드의 도착 시간 및 실행 시간을 입력받는다. 
- 알고리즘에 따라 스레드를 스케줄링하고 실행한다. 
- 스케줄링 알고리즘에서 총 걸린 시간과 평균 대기 시간을 출력한다. 
